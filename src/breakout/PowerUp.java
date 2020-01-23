package breakout;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.*;

/**
 * @author Lucy Gu
 *
 * The powerup class creates a single powerup
 * The powerup determines its type randomly, and select the according image
 *
 * Example usage: PowerUp p = new PowerUp();
 *
 * Dependencies: It extends the Game class
 *               Methods use information and calls functions from Ball and Paddle class
 *
 */
public class PowerUp extends Game{
    public static final int NUM_POWERUP = 3;

    private int type;
    private ImageView power;
    boolean is_dropping;
    private int speed = 50;

    public PowerUp(){
        type = (new Random()).nextInt(NUM_POWERUP) + 1;
        Image image = new Image(Objects.requireNonNull(this.getClass().getClassLoader().getResourceAsStream(
                this.type + "type.gif")));
        power = new ImageView(image);
        power.setVisible(false);
    }

    /**
     * Get the imageview
     */
    public ImageView imageview(){
        return power;
    }

    /**
     * Check if the powerup is currently dropping
     */
    public boolean dropping(){
        return is_dropping;
    }

    /**
     * put the powerup to where it should be
     * @param x should be valid double
     * @param y should be valid double
     */
    public void drop(double x, double y){
        this.power.setX(x);
        this.power.setY(y);
        this.power.setVisible(true);
        this.is_dropping = true;
    }

    /**
     * Creates a new list of powerup with size number of new elements in it
     * @param powerups
     * @param size
     */
    public void createList(ArrayList<PowerUp> powerups, int size){
        powerups.removeAll(powerups);
        for(int i = 0; i<size; i++){
            powerups.add(new PowerUp());
        }
    }

    /**
     * Updates the status of the powerup
     * If the powerup is supposed to be dropping, move the powerup image to "drop" it
     * If the powerup touches the Paddle, the powerup is "used" so remove it and activate effect
     * If the powerup reaches the bottom of the screen, it also should effectively disappear
     */
    public void update(double elapsedTime, Paddle Paddle, Ball Ball, ArrayList<PowerUp> powerup){
        for (Iterator<PowerUp> iterator = powerup.iterator(); iterator.hasNext(); ) {
            PowerUp temp = iterator.next();
            if(temp.dropping())  temp.power.setY(temp.power.getY() + speed * elapsedTime);
            if (Paddle.imageview().getBoundsInParent().intersects(temp.power.getBoundsInParent())) {
                temp.giveEffect(Paddle, Ball);
                temp.power.setImage(null);
                iterator.remove();
            } else if (temp.power.getY() > SIZE) {
                temp.power.setImage(null);
                iterator.remove();
            }
        }
    }

    /**
     * Activate effects depending on the type of powerup generated:
     * Type 1 adds 1 life to the ball;
     * Type 2 changes paddle size;
     * Type 3 changes paddle speed
     */
    private void giveEffect(Paddle Paddle, Ball Ball) {
        switch (type) {
            case 1:
                Ball.giveLife();
                break;
            case 2:
                if (Paddle.getSize()!=1) Paddle.changesize(1);
                else {
                    Paddle.changesize((Math.random() <= 0.5) ? 0.5 : 2);
                }
                break;
            case 3:
                Paddle.changespeed(1);
        }
    }
}