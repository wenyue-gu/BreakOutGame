package breakout;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.*;

/**
 * @author Lucy Gu
 * The ball class creates a single ball
 * The x direction at which the ball travels is randomly selected between left and right, but
 * the initial y direction is up
 * The initial speed in both x and y direction is 150
 * The initial life status is 3 and strength status is 1
 *
 * Example usage: Ball b = new Ball(); This will create a ball that has the image ball.gif
 *                and travels either in the up-left or up-right direction
 *
 * Dependencies: It extends the Game class
 *               Methods use information and calls functions from  Paddle, brick, and powerup class
 *
 */
public class Ball extends Game {

    private int x_speed = 150;
    private int y_speed = 150;
    private double x_dir;
    private double y_dir;
    private int strength;
    private ImageView imageview;
    private int life;

    public Ball(){
        strength = 1;
        life = 3;
        Image image = new Image(Objects.requireNonNull(this.getClass().getClassLoader().getResourceAsStream(
                BOUNCER_IMAGE)));
        imageview = new ImageView(image);
        resetPos();
    }

    /**
     * Duplicate a ball with the same strength, BUT has only 1 life
     */
    public Ball(Ball b){
        life = 1;
        strength = b.strength;
        Image image = new Image(Objects.requireNonNull(this.getClass().getClassLoader().getResourceAsStream(
                BOUNCER_IMAGE)));
        imageview = new ImageView(image);
        resetPos();
    }

    /**
     * Getters
     */
    public ImageView imageview(){
        return imageview;
    }
    public int getStrength(){
        return strength;
    }
    public int getLife(){
        return life;
    }
    private double xpos(){
        return imageview.getX();
    }
    private double ypos(){
        return imageview.getY();
    }

    /**
     * Change strength for all balls in the arraylist
     * triggers when a special brick is cleared
     * @param balls It should have at least an element
     */
    public void setStrength(int k, ArrayList<Ball> balls){
        for(Ball b: balls) {
            if (k < 10) b.strength = k;
            else b.strength = 9;
        }
    }

    /**
     * Changes the speed of the ball according to level (integer provided)
     */
    public void setSpeed(int level){
        x_speed = 150 + level * 50;
        y_speed = 150 + level * 50;
    }

    /**
     * give 1 extra life to the ball
     */
    public void giveLife(){
        life++;
    }

    /**
     * reset position of ball to right above paddle
     */
    public void resetPos(){
        imageview.setX(SIZE / 2.0 - imageview.getBoundsInParent().getWidth()/2);
        imageview.setY(550);
        this.x_dir = (Math.random()<=0.5) ? -1:1;
        this.y_dir = -1;
    }

    /**
     * gets the maximum life status in an arraylist of balls
     * @param balls It should have at least an element
     */
    public int maxLife(ArrayList<Ball> balls){
        int ret = 0;
        if(balls.size()>0){
            for (Ball b : balls) {
                if (b.life > ret) ret = b.life;
            }
        }
        return ret;
    }


    private void checkXYDirection(){
        if(imageview.getX()>=SIZE && x_dir>0) x_dir *= -1;
        if(imageview.getX()<=0 && x_dir<0) x_dir *= -1;
        if(imageview.getY()<=70 && y_dir<0) y_dir *= -1;
        if(imageview.getY()>=SIZE && y_dir>0){
            y_dir *= -1;
            life--;
        }
    }

    /**
     * Change direction when coming into contact with a brick
     */
    public void whenHitBrick(Brick b){

        double ballminx = imageview.getLayoutBounds().getMinX();
        double ballmaxx = imageview.getLayoutBounds().getMaxX();
        double ballminy = imageview.getLayoutBounds().getMinY();
        double ballmaxy = imageview.getLayoutBounds().getMaxY();
        double brickminx = b.imageview().getLayoutBounds().getMinX();
        double brickmaxx = b.imageview().getLayoutBounds().getMaxX();
        double brickminy = b.imageview().getLayoutBounds().getMinY();
        double brickmaxy = b.imageview().getLayoutBounds().getMaxY();

        if ((ballminx <= brickmaxx && ballmaxx >= brickmaxx && x_dir<0)
                || (ballminx <= brickminx && ballmaxx >= brickminx && x_dir>0)) x_dir *= -1;

        else if ((ballminy <= brickmaxy && ballmaxy >= brickmaxy && y_dir<0)
                || (ballminy <= brickminy && ballmaxy >= brickminy && y_dir>0))  y_dir *= -1;
    }

    /**
     * Change direction when hitting paddle
     */
    private void checkIfHitPaddle(Paddle Paddle){
        if(Paddle.imageview().getBoundsInParent().intersects(imageview.getBoundsInParent()) && y_dir>0) {
            double bouncerx = imageview.getX();
            double PaddlePortion = Paddle.imageview().getBoundsInLocal().getWidth() / 6;
            double padx = Paddle.imageview().getX();
            if (bouncerx <= padx + PaddlePortion) {
                x_dir = -1.1;
                y_dir = -0.9;
            } else if (bouncerx <= padx +2* PaddlePortion) {
                x_dir = -0.5;
                y_dir = -1.3;
            } else if (bouncerx >= PaddlePortion + 2* PaddlePortion && bouncerx <=padx + 3* PaddlePortion) {
                y_dir *= -1;
            } else if (bouncerx >= PaddlePortion + 3* PaddlePortion && bouncerx <=padx + 4* PaddlePortion) {
                y_dir *= -1;
            } else if (bouncerx >= PaddlePortion + 4* PaddlePortion && bouncerx <=padx + 5* PaddlePortion) {
                x_dir = 0.5;
                y_dir = -1.3;
            } else if (bouncerx >= padx + 5* PaddlePortion) {
                x_dir = 1.1;
                y_dir = -0.9;
            }
            else{
                y_dir *= -1;
            }
        }
    }

    /**
     * Update all necessary information during normal game play
     * (such as checking if ball hit edge of screen, paddle, brick, and other normal movements)
     */
    public void update(Paddle Paddle, ArrayList<Brick> brick, ArrayList<PowerUp> powerup, ArrayList<Ball> balls, double elapsedTime){
        checkXYDirection();
        checkIfHitPaddle(Paddle);
        (new Brick()).checkIfHit(brick, this, Paddle, powerup, balls);
        changepos(elapsedTime);
    }

    private void changepos(double elapsedTime){
        imageview.setX(xpos() + x_speed * elapsedTime * x_dir);
        imageview.setY(ypos() + y_speed * elapsedTime * y_dir);
    }
}