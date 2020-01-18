package breakout;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

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