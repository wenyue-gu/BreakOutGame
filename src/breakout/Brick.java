package breakout;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

/**
 * @author Lucy Gu
 * Dependencies: It extends the Game class
 *               Methods use information and calls functions from Ball, Powerup, and Paddle class
 */
public class Brick extends Game {
    private int bricklives;
    private int type;
    private ImageView brick;
    private double ypos;
    private double xpos;


    public Brick(){
        this(1,1);
    }

    public Brick(int kind, int lives){
        type = kind;
        bricklives = lives;
        brick = new ImageView(new Image(
                Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream(
                        "kind" + kind + "life" + lives + ".gif"))));
    }

    /**
     * Getter methods
     */
    public int getBricklives() {
        return bricklives;
    }
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
                    Brick new_brick = new Brick(a / 10, a % 10);
                    if (a / 10 == 4) p.addtolist();
                    new_brick.setBrickPos(new_brick.imageview().getBoundsInLocal().getWidth() / 2 + 100 * j - 45,
                            new_brick.imageview().getBoundsInLocal().getHeight() / 2 + 40 * i + 50);
                    new_brick.imageview().setFitWidth(new_brick.imageview().getBoundsInLocal().getWidth() - 8);
                    new_brick.imageview().setFitHeight(new_brick.imageview().getBoundsInLocal().getHeight() - 8);
                    Bricks.add(new_brick);
                }
            }
        }
        return Bricks;
    }

    private void setBrickPos(double x, double y){
        xpos = x;
        ypos = y;
        this.brick.setX(xpos);
        this.brick.setY(ypos);
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
                for (PowerUp p : powerup) {
                    if (!p.dropping()) {
                        p.drop(xpos + brick.getBoundsInLocal().getWidth()/2, ypos + brick.getBoundsInLocal().getHeight()/2);
                        break;
                    }
                }
                break;
        }
    }
}