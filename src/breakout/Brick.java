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
            {1,-2,0,0,-1,1},
            {1,0,2,2,0,1},
            {0,1,1,1,1,0},
            {0,1,0,0,1,0},
            {0,1,-3,1,1,0},
            {0,0,1,1,0,0}};


    private int[][] lv0 = new int[][]{
            {0,0,0,0,5,0},
            {0,0,0,0,0,0},
            {0,0,0,0,0,0},
            {0,0,0,0,0,0},
            {0,0,0,0,0,0},
            {0,0,0,0,0,0},
            {0,0,0,0,0,0},
            {0,-3,-3,-3,-3,0},
            {0,-3,-3,-3,-3,0}};



    private int[][] lv2 = new int[][]{
            {5,0,0,0,0,5},
            {0,5,2,2,5,0},
            {-1,4,0,0,4,-3},
            {0,5,0,0,5,0},
            {0,2,3,3,2,0},
            {0,5,-2,-1,5,0},
            {0,2,2,2,2,0},
            {1,2,5,5,2,1},
            {0,0,1,1,0,0}};

    private int[][] lv3 = new int[][]{
            {10,0,-2,-2,0,10},
            {10,10,0,0,10,10},
            {10,8,8,8,8,10},
            {7,7,0,0,7,7},
            {10,0,10,10,0,10},
            {0,-3,-3,-3,-3,0},
            {5,5,5,5,5,5},
            {0,10,5,5,10,0},
            {0,0,-1,5,0,0}};

    private int[][] lv4 = new int[][]{
            {10,10,10,10,10,10},
            {10,10,10,10,10,10},
            {10,10,10,10,10,10},
            {10,10,10,10,10,10},
            {10,10,10,10,10,10},
            {10,10,10,10,10,10},
            {10,10,10,10,10,10},
            {10,10,10,10,10,10},
            {10,10,10,10,10,10}};


    private ArrayList<int[][]> lvs = new ArrayList<>();


    public Brick(){
        this(1,1);
    }

    public Brick(int kind, int lives){
        type = kind;
        bricklives = lives;
        Image brickImage = null;
        if(kind==1) {
            brickImage = new Image(getClass().getClassLoader().getResourceAsStream(lives + ".gif"));
        }
        else if(kind==2){
            brickImage = new Image(getClass().getClassLoader().getResourceAsStream("x.gif"));
        }
        else if(kind==3){
            brickImage = new Image(getClass().getClassLoader().getResourceAsStream("y.gif"));
        }
        else if(kind==4){
            brickImage = new Image(getClass().getClassLoader().getResourceAsStream("z.gif"));
        }
        brick = new ImageView(brickImage);
        lvs.add(lv0);
        lvs.add(lv1);
        lvs.add(lv2);
        lvs.add(lv3);
        lvs.add(lv4);
    }

    public void hit(int strength, Paddle paddle){
        if(bricklives>strength){
            paddle.addscore(strength);
            bricklives =  bricklives-strength;
        }
        else{
            paddle.addscore(bricklives);
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

    public ArrayList<Brick> create(int level, Powerup p){
        int[][] layout = lvs.get(level);
        ArrayList<Brick> Bricks = new ArrayList<>();

        for(int k = 0; k<layout.length;k++){
            for(int j = 0; j<layout[0].length;j++){
                if(layout[k][j]!=0) {
                    Brick new_brick;
                    if(layout[k][j]==-1){
                        new_brick = new Brick(2, 1);
                    }
                    else if(layout[k][j]==-2){
                        new_brick = new Brick(3, 1);
                    }
                    else if(layout[k][j]==-3){
                        new_brick = new Brick(4, 1);
                        p.addtolist();
                    }
                    else {
                        new_brick = new Brick(1, layout[k][j]);
                    }
                    new_brick.setBrickPos(new_brick.imageview().getBoundsInLocal().getWidth() / 2 + 100 * j - 50,
                            new_brick.imageview().getBoundsInLocal().getHeight() / 2 + 40 * k + 40 );
                    Bricks.add(new_brick);
                }
            }
        }
        return Bricks;
    }

    public int gettype(){
        return type;
    }


    public void update(ArrayList<Brick> bricks, Ball ball, Paddle paddle,ArrayList<Powerup>powerup){
        this.hit(ball.getStrength(), paddle);
        if(this.bricklives<=0){
            if(this.gettype()==2){
                ball.setStrength(ball.getStrength()+1);
            }
            if(this.gettype()==3){
                paddle.addscore(10);
            }
            if(this.gettype()==4){
                System.out.println("hit");
                for(Powerup p:powerup) {
                    System.out.println("here");
                    if(!p.dropping()){
                        System.out.println("tried");
                        p.drop(this.xpos, this.ypos);
                        break;
                    }
                }
            }
            this.brick.setImage(null);
            bricks.remove(this);
        }
        else{
            Image newImage = new Image(getClass().getClassLoader().getResourceAsStream(this.bricklives + ".gif"));
            this.brick.setImage(newImage);
        }


    }






}
