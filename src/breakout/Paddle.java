package breakout;

import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;


public class Paddle extends Game {

    private int speed;
    private double size;
    private ImageView imageview;
    private int score;


    public Paddle(){
        score = 0;
        speed = 20;
        size = 1;
        Image image = new Image(this.getClass().getClassLoader().getResourceAsStream(PADDLE_IMAGE));
        imageview = new ImageView(image);
        imageview.setFitWidth(100*size);
        imageview.setFitHeight(15);
        imageview.setX(SIZE / 2 - imageview.getBoundsInLocal().getWidth() / 2);
        imageview.setY(SIZE - imageview.getBoundsInLocal().getHeight()*2);
    }

    public ImageView imageview(){
        return imageview;
    }

    public double getspeed(){
        return speed;
    }

    public int getscore(){
        return score;
    }

    public void addscore(int s){
        score = score + s;
    }

    public double getSize(){
        return size;
    }

    public void changesize(double k){
        this.size = k;
        imageview.setFitWidth(100 * size);
    }

    public void changespeed(int k){
        this.speed *= k;
    }


    public Paddle update(int speedchange){
        changespeed(speedchange);
        changepos();
        return this;
    }

    void changepos(){
        if(imageview.getX()>SIZE*0.9) imageview.setX(-0.1*SIZE);
        if(imageview.getX()<-0.1*SIZE) imageview.setX(SIZE*0.9);
    }

    void resetPos(){
        imageview.setX(SIZE / 2 - imageview.getBoundsInLocal().getWidth() / 2);
        imageview.setY(SIZE - imageview.getBoundsInLocal().getHeight()*2);
        imageview.setFitWidth(100);
    }


}
