package breakout;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.Objects;

public class Paddle extends Game {

    private double speed;
    private double size;
    private ImageView imageview;
    private int score;
    private int dir;
    private boolean isMoving;

    public Paddle(){
        score = 0;
        speed = 200;
        size = 1;
        dir = 1;
        isMoving = false;
        Image image = new Image(Objects.requireNonNull(this.getClass().getClassLoader().getResourceAsStream(
                PADDLE_IMAGE)));
        imageview = new ImageView(image);
        imageview.setFitWidth(100*size);
        imageview.setFitHeight(15);
        imageview.setX(SIZE / 2.0 - imageview.getBoundsInLocal().getWidth() / 2);
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
    public void changespeed(double k){
        this.speed = speed + k*50;
    }

    public void edgeCheck(){
        if(imageview.getX()>SIZE*0.9) imageview.setX(-0.1*SIZE);
        if(imageview.getX()<-0.1*SIZE) imageview.setX(SIZE*0.9);
    }

    public void resetPos(){
        imageview.setFitWidth(100);
        imageview.setX(SIZE / 2.0 - imageview.getBoundsInLocal().getWidth() / 2);
        imageview.setY(SIZE - imageview.getBoundsInLocal().getHeight()*2);
        isMoving = false;
    }

    public void move(double elapsedTime){
        if(isMoving) {
            imageview.setX(imageview.getX() + elapsedTime*speed * dir);
        }
    }

    public void setDir(int d){
        dir = d;
    }
    public void setMoving(boolean moving){
        isMoving = moving;
    }
}