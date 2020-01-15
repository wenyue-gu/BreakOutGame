package breakout;

import javafx.animation.Animation;
import javafx.animation.Timeline;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.*;


public class Brick extends Game {
    private int bricklives;
    private int type;
    private ImageView brick;
    private double ypos;
    private double xpos;
    private int[][] lv1 = new int[][]{
            {0,3,0,0,3,0},
            {1,1,2,2,1,1},
            {0,3,1,1,3,0},
            {1,1,0,0,1,1},
            {1,0,1,1,0,1},
            {0,1,1,1,1,0},
            {0,1,0,0,1,0},
            {0,1,1,1,1,0},
            {0,0,1,1,0,0}};


    private int[][] lv0 = new int[][]{
            {0,0,0,0,0,0},
            {0,0,0,0,0,0},
            {0,0,0,0,0,0},
            {0,0,0,0,0,0},
            {0,0,0,0,0,0},
            {0,0,0,0,0,0},
            {0,0,0,0,0,0},
            {0,0,0,0,0,0},
            {0,0,0,0,1,0}};



    private int[][] lv2 = new int[][]{
            {5,0,0,0,0,5},
            {0,5,2,2,5,0},
            {1,4,0,0,4,0},
            {0,5,0,0,5,0},
            {0,2,3,3,2,0},
            {0,5,1,1,5,0},
            {0,2,2,2,2,0},
            {1,2,5,5,2,1},
            {0,0,1,1,0,0}};

    private int[][] lv3 = new int[][]{
            {0,3,0,0,3,0},
            {1,1,2,2,1,1},
            {0,3,1,1,3,0},
            {1,1,0,0,1,1},
            {1,0,1,1,0,1},
            {0,1,1,1,1,0},
            {0,1,0,0,1,0},
            {0,1,1,1,1,0},
            {0,0,1,1,0,0}};

    private ArrayList<int[][]> lvs = new ArrayList<>();


    public Brick(){
        this(1,1);
    }

    public Brick(int kind, int lives){
        type = kind;
        bricklives = lives;
        Image brickImage = new Image(getClass().getClassLoader().getResourceAsStream(lives + ".gif"));
        brick = new ImageView(brickImage);
        lvs.add(lv0);
        lvs.add(lv1);
        lvs.add(lv2);
        lvs.add(lv3);
    }

    public void hit(int strength){
        if(bricklives>strength) bricklives =  bricklives-strength;
        else{
            bricklives = 0;
        }
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

    public ArrayList<Brick> create(int level){
        int[][] layout = new int[][]{};
        ArrayList<Brick> Bricks = new ArrayList<>();

        layout = lvs.get(level);

        for(int k = 0; k<layout.length;k++){
            for(int j = 0; j<layout[0].length;j++){
                if(layout[k][j]>0) {
                    Brick brick = new Brick(1, layout[k][j]);
                    brick.setBrickPos(brick.imageview().getBoundsInLocal().getWidth() / 2 + 100 * j - 50,
                            brick.imageview().getBoundsInLocal().getHeight() / 2 + 40 * k + 40 );
                    Bricks.add(brick);
                }
            }
        }
        return Bricks;
    }


    public void update(ArrayList<Brick> bricks, Ball ball){
        this.hit(ball.getStrength());
        if(this.bricklives<=0){
            this.brick.setImage(null);
            bricks.remove(this);
        }
        else{
            Image newImage = new Image(getClass().getClassLoader().getResourceAsStream(this.bricklives + ".gif"));
            this.brick.setImage(newImage);
        }


    }






}
