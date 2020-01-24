package breakout;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.*;

/**
 * @author Lucy Gu
 *
 * The powerup class creates a single powerup
 *
 * Example usage: PowerUp p = new PowerUp(); The type of the powerup generated will be random,
 *                and the image will match this randomly selected type. The powerup, when first
 *                generated, will not show up on screen, until a special brick is hit and then it
 *                will begin to drop from the brick's center.
 *
 * Dependencies: It depends on Ball and Paddle class, since PowerUp need to check
 *               whether it hits the paddle, and need to distribute status changes to
 *               Ball and Paddle when effect is triggered.
 *
 */
public class PowerUp{
    private int NUM_POWERUP = 3;
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
     * @return the imageview of this object
     */
    public ImageView imageview(){
        return power;
    }

    /**
     * Check if the powerup is currently dropping
     * @return a boolean value
     */
    public boolean dropping(){
        return is_dropping;
    }

    /**
     * put the powerup to where it should be
     * @param x should be the x position of the brick that triggered this particular powerup drop
     * @param y should be the y position of the brick that triggered this particular powerup drop
     */
    public void drop(double x, double y){
        this.power.setX(x);
        this.power.setY(y);
        this.power.setVisible(true);
        this.is_dropping = true;
    }

    /**
     * Creates a list of powerup
     * @param powerups the list being modified to eventually have exactly "size" numbers of powerups
     * @param size the number of powerups that should be in the list
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
     * @param elapsedTime
     * @param SIZE the size of the screen (so that the power up disappears after reaching the bottom)
     * @param Paddle The paddle who is being checked against the powerups (whether they touch)
     * @param Ball the bouncer whose status will be changed in give effect
     * @param powerup the list of powerup that exists in the current game
     */
    public void update(double elapsedTime, int SIZE, Paddle Paddle, Ball Ball, ArrayList<PowerUp> powerup){
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
     * @param Paddle the Paddle whose status is being changed
     * @param Ball the bouncer whose status is being changed
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