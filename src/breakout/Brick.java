package breakout;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.*;

public class Brick extends Game {
    private int bricklives;
    private int type;
    private ImageView brick;
    private double ypos;
    private double xpos;

    private int[][] lv0 = new int[][]{
            {15,15,15,15,15,15},
            {0,0,0,0,0,0},
            {0,0,0,0,0,0},
            {0,0,0,0,0,0},
            {0,0,0,0,0,0},
            {0,0,0,0,0,0},
            {0,41,21,31,41,0},
            {0,21,31,41,21,0},
            {0,31,41,31,41,0}};

    private int[][] lv1 = new int[][]{
            {0,13,0,0,13,0},
            {11,11,12,12,11,11},
            {0,13,11,11,13,0},
            {11,21,0,0,31,11},
            {11,0,12,12,0,11},
            {0,11,11,11,11,0},
            {0,11,0,0,11,0},
            {0,11,41,11,11,0},
            {0,0,11,11,0,0}};

    private int[][] lv2 = new int[][]{
            {15,0,0,0,0,15},
            {0,15,12,12,15,0},
            {21,14,0,0,14,41},
            {0,15,0,0,15,0},
            {0,12,13,13,12,0},
            {0,15,31,41,15,0},
            {0,12,12,12,12,0},
            {11,12,15,15,12,11},
            {0,0,11,11,0,0}};

    private int[][] lv3 = new int[][]{
            {19,0,21,31,0,19},
            {19,19,0,0,19,19},
            {19,18,18,18,18,19},
            {17,17,0,0,17,17},
            {19,0,19,19,0,19},
            {0,41,41,31,41,0},
            {15,15,15,15,15,15},
            {0,19,15,15,19,0},
            {0,0,21,21,0,0}};

    private int[][] lv4 = new int[][]{
            {19,19,19,19,19,19},
            {19,19,19,19,19,19},
            {19,19,19,19,19,19},
            {19,19,19,19,19,19},
            {19,19,19,19,19,19},
            {19,19,19,19,19,19},
            {19,19,19,19,19,19},
            {19,19,19,19,19,19},
            {19,19,19,19,19,19}};

    private ArrayList<int[][]> lvs = new ArrayList<>();

    public Brick(){
        this(1,1);
    }
    public Brick(int kind, int lives){
        Collections.addAll(lvs, lv0,lv1,lv2,lv3,lv4);
        type = kind;
        bricklives = lives;
        brick = new ImageView(new Image(Objects.requireNonNull(
                getClass().getClassLoader().getResourceAsStream (
                        "kind" + kind + "life" + lives + ".gif"))));
    }

    public int getBricklives() {
        return bricklives;
    }

    public ImageView imageview() {
        return brick;
    }

    public void setBrickPos(double x, double y){
        xpos = x;
        ypos = y;
        this.brick.setX(xpos);
        this.brick.setY(ypos);
    }

    public ArrayList<Brick> createPane(int level, PowerUp p){
        int[][] layout = lvs.get(level);
        ArrayList<Brick> Bricks = new ArrayList<>();
        for(int k = 0; k<layout.length;k++){
            for(int j = 0; j<layout[0].length;j++){
                if(layout[k][j]>0) {
                    Brick new_brick = new Brick(layout[k][j] / 10, layout[k][j] % 10);
                    if (layout[k][j] / 10 == 4) p.addtolist();
                    new_brick.setBrickPos(new_brick.imageview().getBoundsInLocal().getWidth() / 2 + 100 * j - 45,
                            new_brick.imageview().getBoundsInLocal().getHeight() / 2 + 40 * k + 50);
                    new_brick.imageview().setFitWidth(new_brick.imageview().getBoundsInLocal().getWidth() - 8);
                    new_brick.imageview().setFitHeight(new_brick.imageview().getBoundsInLocal().getHeight() - 8);
                    Bricks.add(new_brick);
                }
            }
        }
        return Bricks;
    }

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

    private void dropPowerUps(Ball ball, Paddle paddle, ArrayList<PowerUp>powerup){
        switch(type) {
            case 2:
                ball.setStrength(ball.getStrength() + 1);
                break;
            case 3:
                paddle.addscore(50);
                break;
            case 4:
                for (PowerUp p : powerup) {
                    if (!p.dropping()) {
                        p.drop(xpos, ypos);
                        break;
                    }
                }
                break;
        }
    }
}
