package breakout;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.*;


public class Ball extends Game {

    private int x_speed = 150;
    private int y_speed = 150;
    private int x_dir;
    private int y_dir;
    private int strength;
    private ImageView imageview;


    public Ball(){
        x_dir = -1;
        y_dir = 1;
        strength = 1;
        Image image = new Image(this.getClass().getClassLoader().getResourceAsStream(BOUNCER_IMAGE));
        imageview = new ImageView(image);
        imageview.setScaleX(imageview.getScaleX() * strength);
        imageview.setScaleY(imageview.getScaleY() * strength);
        imageview.setX(SIZE / 2);
        imageview.setY(10*40);
    }

    public Ball(Ball b){
        new Ball();
        this.setStrength(b.strength-1);
    }

    public ImageView imageview(){
        return imageview;
    }
    public int getStrength(){
        return strength;
    }

    private double xpos(){
        return imageview.getX();
    }
    private double ypos(){
        return imageview.getY();
    }

    private void setStrength(int k){
        this.strength +=k;
    }

    private void checkdir(ArrayList<Brick> brick, Paddle Paddle){
        if(imageview.getX()>=SIZE || imageview.getX()<=0){ x_dir *= -1;
        System.out.println("edges");}
        if(imageview.getY()>=SIZE || imageview.getY()<=0){ y_dir *= -1;}
        if(Paddle.imageview().getBoundsInParent().intersects(imageview.getBoundsInParent()) && y_dir>0) y_dir*=-1;
        for(Brick b:brick) {
            if (b.imageview().getBoundsInParent().intersects(imageview.getBoundsInParent())) {
                checkbrick(b);
                b.update(brick, this);
            }
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

    private void changepos(double elapsedTime){
        imageview.setX(xpos() + x_speed * elapsedTime * x_dir);
        imageview.setY(ypos() + y_speed * elapsedTime * y_dir);
    }

    public Ball update(Paddle Paddle, ArrayList<Brick> brick, double elapsedTime){
        checkdir(brick, Paddle);
        changepos(elapsedTime);
        return this;
    }



}
