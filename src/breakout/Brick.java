package breakout;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

/**
 * @author Lucy Gu
 *
 * The brick class creates a single brick defined by its type and life status.
 * By default, the brick sits at (0,0), has a type and life of 1, and a corresponding
 * image in the resources folder defined by kind1life1.gif
 *
 * Example usage: Brick b = new Brick(3,1,0,0) creates a brick located at the top left corner of the screen
 *                with its image defined by kind3life1.gif
 *
 * Dependencies: It extends the Game class
 *               Methods within this class use information and calls functions from Ball, Powerup, and Paddle class
 *
 * Assumptions: Since there are only a few images that has been defined in the resources,
 *              it its expected that the kind status would be between 1 and 4, and life
 *              status between 1 and 9.
 */

public class Brick extends Game {
    private int bricklives;
    private int type;
    private ImageView brick;
    private double ypos;
    private double xpos;


    public Brick(){
        this(1,1,0,0);
    }

    public Brick(int kind, int lives, double x, double y){
        type = kind;
        bricklives = lives;
        brick = new ImageView(new Image(
                Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream(
                        "kind" + kind + "life" + lives + ".gif"))));
        brick.setX(x  + brick.getBoundsInLocal().getWidth());
        brick.setY(y + brick.getBoundsInLocal().getHeight());
        xpos = brick.getX();
        ypos = brick.getY();

    }

    /**
     * Get the life status of the brick
     */
    public int getBricklives() {
        return bricklives;
    }

    /**
     * Get the imageview of the brick
     */
    public ImageView imageview() {
        return brick;
    }

    /**
     * Create the layout of bricks in each level
     * @param level An integer that indicates the level of the configuration, NEED TO BE between 0 and 4
     * @param p a powerup object that stores the information of powerups required for this level
     * @return An Arraylist of bricks with each position, life, and type specified by the level document
     */
    public ArrayList<Brick> createPane(int level, PowerUp p){
        ArrayList<Brick> Bricks = new ArrayList<>();
        Scanner scanner = null;

        try {
            scanner = new Scanner(new File("lv"+level+".txt"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 6; j++) {
                int a = scanner.nextInt();
                if (a != 0) {
                    Brick new_brick = new Brick(a / 10, a % 10, 100 * j - 97, 40 * i + 50);
                    if (a / 10 == 4) p.addtolist();
                    Bricks.add(new_brick);
                }
            }
        }
        return Bricks;
    }

    /**
     * Checks if the ball touches the brick
     * If the ball touches the brick, brick loses life according to the ball's attack
     * If the brick's life reach 0, the brick is removed, and powerup drop/special effect is triggered
     * @param bricks should have at least one element
     * @param powerup should have at least one element
     * @param balls should have at least one element
     */
    public void checkIfHit(ArrayList<Brick> bricks, Ball ball, Paddle paddle, ArrayList<PowerUp>powerup, ArrayList<Ball> balls) {
        for (Iterator<Brick> iterator = bricks.iterator(); iterator.hasNext(); ) {
            Brick temp = iterator.next();
            if (ball.imageview().getBoundsInParent().intersects(temp.brick.getBoundsInParent())) {
                ball.whenHitBrick(temp);
                temp.updateBrickLife(ball.getStrength(), paddle);
                if (temp.bricklives <= 0) {
                    temp.dropPowerUps(ball, paddle, powerup, balls);
                    temp.brick.setImage(null);
                    iterator.remove();
                } else {
                    temp.brick.setImage(new Image(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream(
                            "kind" + temp.type + "life" + temp.bricklives + ".gif"))));
                }
            }
        }
    }

    private void updateBrickLife(int strength, Paddle paddle){
        if(bricklives>strength){
            paddle.addscore(strength);
            bricklives =  bricklives-strength;
            return;
        }
        paddle.addscore(bricklives);
        bricklives = 0;
    }

    /**
     * Activate effect depending on the type of the brick broken
     * Case 2 and 3: directly change the paddle and ball status
     * Case 4: go through the list of powerup, drop the first that isn't dropping
     * @param ball The bouncer that is being checked
     * @param powerup list of powerup for dropping
     * @param balls The arraylist of all balls that exist on screen;
     *              since strength change in any ball is inherited by all balls this parameter is require
     *
     */
    private void dropPowerUps(Ball ball, Paddle paddle, ArrayList<PowerUp>powerup, ArrayList<Ball> balls){
        switch(type) {
            case 2:
                ball.setStrength(ball.getStrength() + 1, balls);
                break;
            case 3:
                paddle.addscore(50);
                break;
            case 4:
                if(powerup.size()>0) {
                    for (PowerUp p : powerup) {
                        if (!p.dropping()) {
                            p.drop(xpos + brick.getBoundsInLocal().getWidth() / 2, ypos + brick.getBoundsInLocal().getHeight() / 2);
                            break;
                        }
                    }
                }
                break;
        }
    }
}