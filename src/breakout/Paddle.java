package breakout;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.Objects;

/**
 * @author Lucy Gu
 * Dependencies: It extends the Game class
 */
public class Paddle extends Game {

    private double speed;
    private double size;
    private ImageView imageview;
    private int score;
    private int dir;

    public Paddle(){
        score = 0;
        speed = 200;
        size = 1;
        dir = 0;
        Image image = new Image(Objects.requireNonNull(this.getClass().getClassLoader().getResourceAsStream(
                PADDLE_IMAGE)));
        imageview = new ImageView(image);
        imageview.setFitWidth(100*size);
        imageview.setFitHeight(15);
        imageview.setX(SIZE / 2.0 - imageview.getBoundsInLocal().getWidth() / 2);
        imageview.setY(SIZE - imageview.getBoundsInLocal().getHeight()*2);
    }

    /**
     * Getters
     */
    public ImageView imageview(){
        return imageview;
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

    /**
     * Edit size status of the paddle
     * @param k should be larger than 0
     */
    public void changesize(double k){
        this.size = k;
        imageview.setFitWidth(100 * size);
    }

    /**
     * Edit speed status of the paddle
     * @param k should be larger than 0
     */
    public void changespeed(double k){
        this.speed = speed + k*50;
    }

    /**
     * makes the paddle "warps" around when reaching edges
     */
    public void edgeCheck(){
        if(imageview.getX()>SIZE*0.9) imageview.setX(-0.1*SIZE);
        if(imageview.getX()<-0.1*SIZE) imageview.setX(SIZE*0.9);
    }

    /**
     * reset back to original size and center of screen
     */
    public void resetPos(){
        imageview.setFitWidth(100);
        imageview.setX(SIZE / 2.0 - imageview.getBoundsInLocal().getWidth() / 2);
        imageview.setY(SIZE - imageview.getBoundsInLocal().getHeight()*2);
        dir = 0;
    }

    /**
     * Determines the movement of the paddle
     */
    public void move(double elapsedTime){
        imageview.setX(imageview.getX() + elapsedTime*speed * dir);
    }

    /**
     * Changes the direction in which the paddle moves (-1, 0, 1)
     * @param d the program shouldn't break with any integer d, but only -1, 0, and 1 are expected
     *          as "left", "stop", and "right"
     */
    public void setDir(int d){
        dir = d;
    }

}