package breakout;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

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
        Image image = new Image(this.getClass().getClassLoader().getResourceAsStream(BOUNCER_IMAGE));
        imageview = new ImageView(image);
        imageview.setScaleX(imageview.getScaleX() * Math.pow(1.2, strength-1));
        imageview.setScaleY(imageview.getScaleY() *  Math.pow(1.2, strength-1));
        resetPos();
    }

    public Ball(Ball b){
        this();
        setStrength(b.strength);
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
        this.strength=k;
    }

    public void giveLife(){
        this.life++;
    }

    void resetPos(){
        imageview.setX(SIZE / 2);
        imageview.setY(500);
        this.x_dir = 1;
        this.y_dir = -1;
    }

    private void checkdir(){
        if(imageview.getX()>=SIZE || imageview.getX()<=0){ x_dir *= -1;}
        if(imageview.getY()<=0){ y_dir *= -1;}
        if(imageview.getY()>=SIZE){
            y_dir *= -1;
            life = life-1;
        }


    }

    private void checkbrick(Brick b){
        if ((imageview.getLayoutBounds().getMinX() <= b.imageview().getLayoutBounds().getMaxX()
                && imageview.getLayoutBounds().getMaxX() >= b.imageview().getLayoutBounds().getMaxX()) ||
                (imageview.getLayoutBounds().getMinX() <= b.imageview().getLayoutBounds().getMinX()
                        && imageview.getLayoutBounds().getMaxX() >= b.imageview().getLayoutBounds().getMinX())) {
            x_dir *= -1;
        }
        else if ((imageview.getLayoutBounds().getMinY() <= b.imageview().getLayoutBounds().getMaxY()
                && imageview.getLayoutBounds().getMaxY() >= b.imageview().getLayoutBounds().getMaxY()) ||
                (imageview.getLayoutBounds().getMinY() <= b.imageview().getLayoutBounds().getMinY()
                        && imageview.getLayoutBounds().getMaxY() >= b.imageview().getLayoutBounds().getMinY())) {
            y_dir *= -1;
        }

    }

    private void checkpaddle(Paddle Paddle){
        if(Paddle.imageview().getBoundsInParent().intersects(imageview.getBoundsInParent()) && y_dir>0) {
            double bouncerx = imageview.getX();
            double padwidth = Paddle.imageview().getBoundsInLocal().getWidth() / 6;
            double padx = Paddle.imageview().getX();
            if (bouncerx <= padx +   padwidth) {
                x_dir = -1.1;
                y_dir = -0.9;
            } else if (bouncerx <= padx +2* padwidth) {
                x_dir = -0.5;
                y_dir = -1.3;
            } else if (bouncerx >= padwidth + 2* padwidth && bouncerx <=padx + 3* padwidth) {
                y_dir = -1;
            } else if (bouncerx >= padwidth + 3* padwidth && bouncerx <=padx + 4* padwidth) {
                y_dir = -1;
            } else if (bouncerx >= padwidth + 4* padwidth && bouncerx <=padx + 5* padwidth) {
                x_dir = 0.5;
                y_dir = -1.3;
            } else if (bouncerx >= padx + 5* padwidth) {
                x_dir = 1.1;
                y_dir = -0.9;
            }
            else{
                y_dir = -1;
            }
        }


    }

    private void changepos(double elapsedTime){
        imageview.setX(xpos() + x_speed * elapsedTime * x_dir);
        imageview.setY(ypos() + y_speed * elapsedTime * y_dir);
    }



    public Ball update(Paddle Paddle, ArrayList<Brick> brick, ArrayList<Powerup> powerup, double elapsedTime){
        checkdir();
        checkpaddle(Paddle);

        for(Brick b:brick) {
            if (b.imageview().getBoundsInParent().intersects(imageview.getBoundsInParent())) {
                checkbrick(b);
                b.update(brick, this, Paddle, powerup);
            }
        }

        changepos(elapsedTime);
        return this;
    }



}
