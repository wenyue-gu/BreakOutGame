package breakout;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.*;


/**
 * @author Lucy Gu
 * @author Robert C. Duvall
 */
public class Game extends Application {
    public static final String TITLE = "Example JavaFX";
    public static final int SIZE = 600;
    public static final int FRAMES_PER_SECOND = 60;
    public static final int MILLISECOND_DELAY = 1000 / FRAMES_PER_SECOND;
    public static final double SECOND_DELAY = 1.0 / FRAMES_PER_SECOND;
    public static final Paint BACKGROUND = Color.SNOW;
    public static final String BOUNCER_IMAGE = "ball.gif";
    public static final String PADDLE_IMAGE = "pad.gif";



    private Group root;
    private Scene myScene;
    private Ball myBouncer;
    private Paddle myPaddle;
    private Timeline animation;
    private int level;
    private Stage myStage;

    private ArrayList<Ball> bouncers = new ArrayList<>();
    private ArrayList<Brick> myBricks = new ArrayList<>();
    private ArrayList<Powerup> myPowerUp = new ArrayList<>();

    private boolean is_new_level = true;
    private boolean is_running = false;
    private boolean is_reseted = true;
    private boolean splash_sceen = true;


    private Text starting_text;
    private Text losing_text;
    private Text winning_text;
    private Text Life;
    private Text Scores;
    private Text Strength;



    /**
     * Initialize what will be displayed and how it will be updated.
     */
    @Override
    public void start (Stage stage) {
        myStage = stage;
        setLevel(-1);
    }


    private void setLevel(int lv) {
        System.out.println(lv);
        System.out.println(splash_sceen);
        if(lv>=0) {
            level = lv;
            is_new_level = true;
            myScene = setupGame(SIZE, SIZE, BACKGROUND, level);
            starting_text.setVisible(true);
            losing_text.setVisible(false);
            winning_text.setVisible(false);
        }
        else{
            myScene = setupsplash(SIZE, SIZE, BACKGROUND);
        }

        myStage.setScene(myScene);
        myStage.setTitle(TITLE);
        myStage.show();

        KeyFrame frame = new KeyFrame(Duration.millis(MILLISECOND_DELAY), e -> step( SECOND_DELAY));
        animation = new Timeline();
        animation.setCycleCount(Timeline.INDEFINITE);
        animation.getKeyFrames().add(frame);
    }

    Scene setupGame (int width, int height, Paint background, int lv) {

        Brick brick = new Brick();
        Powerup p = new Powerup();
        myBricks = brick.create(lv, p);
        myPowerUp = p.getList();
            bouncers.removeAll(bouncers);
            myBouncer = new Ball();
            bouncers.add(myBouncer);
            myPaddle = new Paddle();
        level = lv;

        initialize_text();
        add_to_root();

        Scene scene = new Scene(root, width, height, background);
        scene.setOnKeyPressed(e -> handleKeyInput(e.getCode()));
        return scene;
    }

    Scene setupsplash(int width, int height, Paint background){
        root = new Group();
        root.getChildren().add(new Text(300,300,"PRESS"));
        Scene scene = new Scene(root, width, height, background);
        scene.setOnKeyPressed(e -> handleSplashInput(e.getCode()));
        return scene;
    }

    private void add_to_root(){
        root = new Group();
        for(Ball b : bouncers){
            root.getChildren().add(b.imageview());
        }
        for(Brick k : myBricks){
            root.getChildren().add(k.imageview());
        }
        if(myPowerUp.size()>0) {
            for (Powerup p : myPowerUp) {
                root.getChildren().add(p.imageview());
            }
        }
        root.getChildren().add(myPaddle.imageview());
        root.getChildren().add(starting_text);
        root.getChildren().add(losing_text);
        root.getChildren().add(winning_text);
        root.getChildren().add(Life);
        root.getChildren().add(Scores);
        root.getChildren().add(Strength);
    }

    // Change properties of shapes in small ways to animate them over time
    // Note, there are more sophisticated ways to animate shapes, but these simple ways work fine to start
    void step(double elapsedTime) {

        for(Ball b : bouncers){
            b.update(myPaddle, myBricks, myPowerUp, elapsedTime);
            if(b.getLife()==0 && bouncers.size()<2){
                losing_text.setVisible(true);
                b.resetPos();
                myPaddle.resetPos();
                is_running = false;
                animation.stop();
            }
        }
        myBouncer = bouncers.get(0);

        (new Powerup()).update(elapsedTime, myPaddle, myBouncer, myPowerUp);


        String life = "Life: " + myBouncer.getLife();
        Life.setText(life);

        String score = "Score: " + myPaddle.getscore();
        Scores.setText(score);

        String strength = "Strength: " + myBouncer.getStrength();
        Strength.setText(strength);

        checkclear();

    }

    // What to do each time a key is pressed
    private void handleKeyInput (KeyCode code) {
        if(is_new_level && code==KeyCode.SPACE){
            animation.play();
            is_running = true;
            is_new_level = false;
            starting_text.setVisible(false);
        }
        if(!is_new_level && code == KeyCode.P){
            if(is_running) animation.pause();
            if(!is_running) animation.play();
            is_running = !is_running;
        }

        if(is_running) {
            if (code == KeyCode.RIGHT) {
                myPaddle.imageview().setX(myPaddle.imageview().getX() + myPaddle.getspeed());
            }
            if (code == KeyCode.LEFT) {
                myPaddle.imageview().setX(myPaddle.imageview().getX() - myPaddle.getspeed());
            }
            if (code == KeyCode.C) {
                new_level();
            }
            if (code == KeyCode.L) {
                myBouncer.giveLife();
            }
            if (code == KeyCode.A) {
                myBouncer.setStrength(myBouncer.getStrength() + 1);
            }
        }
        if(!is_running){
            if (code == KeyCode.Q) {
                new_level(0);
            }
            if (code == KeyCode.W) {
                new_level(1);
            }
            if (code == KeyCode.E) {
                new_level(2);
            }
            if (code == KeyCode.R) {
                new_level(3);
            }
            if (code == KeyCode.T) {
                new_level(4);
            }
            if (code == KeyCode.S) {
                reset(-1);
            }
        }

        if(code==KeyCode.B){
            Ball bouncer = new Ball(myBouncer);
            bouncers.add(bouncer);
            root.getChildren().add(bouncer.imageview());
        }

    }




    private void handleSplashInput (KeyCode code) {
        if (code == KeyCode.SPACE) {
            splash_sceen = false;
            setLevel(0);
            starting_text.setVisible(false);
            animation.play();
            is_running = true;
            is_new_level = false;
        }
    }

    public void checkclear(){
        if(myBricks.size()==0 && myPowerUp.size()==0)  new_level();
    }

    private void new_level(){
        new_level(level+1);
    }

    private void new_level(int level_n){
        animation.stop();
        is_running = false;
        myBouncer.resetPos();
        myPaddle.resetPos();
        level = level_n;
        if(level<5){
            is_reseted = false;
            setLevel(level);
        }
        else{
            winning_text.setVisible(true);
        }
    }

    private void reset(int level_n){
        is_reseted = true;
        setLevel(level_n);
    }

    public void initialize_text(){
        starting_text = new Text(300, 200,"Press Space to Start");
        starting_text.setX(SIZE/2 - starting_text.getBoundsInParent().getWidth()/2);
        losing_text = new Text(300, 200,"You Lost");
        losing_text.setX(SIZE/2 - losing_text.getBoundsInParent().getWidth()/2);
        winning_text = new Text(300, 200,"You won");
        winning_text.setX(SIZE/2 - winning_text.getBoundsInParent().getWidth()/2);
        String life = "Life: " + myBouncer.getLife();
        Life = new Text(10, 25, life);
        String score = "Score: " + myPaddle.getscore();
        Scores = new Text(500,25,score);
        String strength = "Strength: " + myBouncer.getStrength();
        Strength = new Text(500,40,strength);

    }






    /**
     * Start the program.
     */
    public static void main (String[] args) {
        launch(args);
    }
}
