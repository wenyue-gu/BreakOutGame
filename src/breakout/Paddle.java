package breakout;

import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;


public class Paddle extends Game {

    private int speed;
    private double size;
    private ImageView imageview;


    public Paddle(){
        speed = 20;
        size = 1;
        Image image = new Image(this.getClass().getClassLoader().getResourceAsStream(PADDLE_IMAGE));
        imageview = new ImageView(image);
        imageview.setFitWidth(100);
        imageview.setFitHeight(15);
        imageview.setScaleX(imageview.getScaleX() * size);
        imageview.setX(SIZE / 2 - imageview.getBoundsInLocal().getWidth() / 2);
        imageview.setY(SIZE - imageview.getBoundsInLocal().getHeight()*2);
    }

    public ImageView imageview(){
        return imageview;
    }

    public double getspeed(){
        return speed;
    }

    private void changesize(int k){
        this.size = k;
        imageview.setScaleX(imageview.getScaleX() * size);
    }

    private void changespeed(int k){
        this.speed *= k;
    }


    public Paddle update(int sizechange, int speedchange){
        changesize(sizechange);
        changespeed(speedchange);
        changepos();
        return this;
    }

    public void changepos(){
        if(imageview.getX()>SIZE*0.9) imageview.setX(-0.1*SIZE);
        if(imageview.getX()<-0.1*SIZE) imageview.setX(SIZE*0.9);
    }


}
