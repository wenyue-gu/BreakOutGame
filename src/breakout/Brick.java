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
 * Dependencies: Depends on Ball, Paddle, and PowerUp classes
 *
 * Assumptions: Since there are only a few images that has been defined in the resources,
 *              it its expected that the kind status would be between 1 and 4, and life
 *              status between 1 and 9.
 */

public class Brick{
    private int ROW = 9;
    private int COLUMN = 6;

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
     * @return the life status of brick
     */
    public int getBricklives() {
        return bricklives;
    }

    /**
     * Get the imageview of the brick
     * @return the imageview object associated with this brick
     */
    public ImageView imageview() {
        return brick;
    }

    /**
     * Create the layout of bricks in each level
     * @param level An integer that indicates the level of the configuration, NEED TO BE between 0 and 4
     * @param Bricks is modified (have elements added into) to represent all bricks on the pane
     * @return the count of powerups in this level
     */
    public int createPane(ArrayList<Brick> Bricks, int level){
        Scanner scanner = null;

        try {
            scanner = new Scanner(new File("lv"+level+".txt"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        int powerupCount = 0;
        for (int i = 0; i < ROW; i++) {
            for (int j = 0; j < COLUMN; j++) {
                int a = scanner.nextInt();
                if (a != 0) {
                    Brick new_brick = new Brick(a / 10, a % 10, 100 * j - 97, 40 * i + 50);
                    if (a / 10 == 4) powerupCount++;
                    Bricks.add(new_brick);
                }
            }
        }
        return powerupCount;
    }

    /**
     * Checks if the ball touches the brick
     * If the ball touches the brick, brick loses life according to the ball's attack
     * If the brick's life reach 0, the brick is removed, and powerup drop/special effect is triggered
     * @param ball the bouncer being checked
     * @param paddle the paddle
     * @param bricks should have at least one element, the remaining bricks on screen
     * @param powerup should have at least one element, the list of powerup for dropping
     */
    public void checkIfHit(ArrayList<Brick> bricks, Ball ball, Paddle paddle, ArrayList<PowerUp>powerup) {
        for (Iterator<Brick> iterator = bricks.iterator(); iterator.hasNext(); ) {
            Brick temp = iterator.next();
            if (ball.imageview().getBoundsInParent().intersects(temp.brick.getBoundsInParent())) {
                ball.whenHitBrick(temp);
                temp.updateBrickLife(ball.getStrength(), paddle);
                if (temp.bricklives <= 0) {
                    temp.dropPowerUps(ball, paddle, powerup);
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
     * @param ball The bouncer whose status shall be updated
     * @param paddle The paddle whose status is being updated
     * @param powerup list of powerup for dropping
     *
     */
    private void dropPowerUps(Ball ball, Paddle paddle, ArrayList<PowerUp>powerup){
        switch(type) {
            case 2:
                ball.setStrength(ball.getStrength() + 1);
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