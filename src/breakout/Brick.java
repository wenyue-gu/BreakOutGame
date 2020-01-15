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


    public Brick(){
        this(1,1);
    }

    public Brick(int kind, int lives){

        type = kind;
        bricklives = lives;
        Image brickImage = new Image(getClass().getClassLoader().getResourceAsStream(lives + ".gif"));
        brick = new ImageView(brickImage);
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

    public ArrayList<Brick> create(int i){
        int[][] layout = new int[][]{};
        ArrayList<Brick> Bricks = new ArrayList<>();
        if(i==1){
            layout = lv1;
        }

        for(int k = 0; k<layout.length;k++){
            for(int j = 0; j<layout[0].length;j++){
                if(layout[k][j]>0) {
                    Brick brick = new Brick(1, layout[k][j]);
                    brick.setBrickPos(brick.imageview().getBoundsInLocal().getWidth() / 2 + 100 * j - 0.08*SIZE,
                            brick.imageview().getBoundsInLocal().getHeight() / 2 + 40 * k );
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
