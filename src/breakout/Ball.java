package breakout;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.FileNotFoundException;
import java.util.*;

public class Ball extends Game {

    private int x_speed = 150;
    private int y_speed = 150;
    private double x_dir;
    private double y_dir;
    private int strength;
    private ImageView imageview;
    private int life;

    public Ball(){
        x_dir = 1;
        y_dir = -1;
        strength = 1;
        life = 3;
        Image image = new Image(Objects.requireNonNull(this.getClass().getClassLoader().getResourceAsStream(
                BOUNCER_IMAGE)));
        imageview = new ImageView(image);
        resetPos();
    }

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

    public void setStrength(int k){
        if(k<10) strength=k;
        else strength = 9;
    }
    public void setSpeed(int level){
        x_speed = 150 + level * 50;
        y_speed = 150 + level * 50;
    }
    public void giveLife(){
        this.life++;
    }

    void resetPos(){
        imageview.setX(SIZE / 2.0 - imageview.getBoundsInParent().getWidth()/2);
        imageview.setY(550);
        this.x_dir = (Math.random()<=0.5) ? -1:1;
        this.y_dir = -1;
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

    private void changepos(double elapsedTime){
        imageview.setX(xpos() + x_speed * elapsedTime * x_dir);
        imageview.setY(ypos() + y_speed * elapsedTime * y_dir);
    }

    public void update(Paddle Paddle, ArrayList<Brick> brick, ArrayList<PowerUp> powerup, double elapsedTime){
        checkXYDirection();
        checkIfHitPaddle(Paddle);
        (new Brick()).checkIfHit(brick, this, Paddle, powerup);
        changepos(elapsedTime);

    }
}