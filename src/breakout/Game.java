package breakout;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.*;


/**
 * A basic example JavaFX program for the first lab.
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

    // some things needed to remember during game
    private Group root;
    private Scene myScene;
    private Ball myBouncer;
    private Paddle myPaddle;
    private Brick brick;
    private Timeline animation;
    private int level;
    private Stage myStage;

    private ArrayList<Ball> bouncers = new ArrayList<>();
    private ArrayList<Brick> myBricks = new ArrayList<>();

    private boolean is_new_level = true;
    private boolean is_running = false;


    private Text starting_text;
    private Text losing_text;
    private Text winning_text;



    /**
     * Initialize what will be displayed and how it will be updated.
     */
    @Override
    public void start (Stage stage) {
        // attach scene to the stage and display it
        level = 0;
        setLevel(stage, level, true);
    }

    private void setLevel(Stage stage, int lv, boolean first_time) {
        level = lv;
        myStage = stage;
        is_new_level = true;

        myScene = setupGame(SIZE, SIZE, BACKGROUND,level, first_time);
        myStage.setScene(myScene);
        myStage.setTitle(TITLE);
        myStage.show();
        KeyFrame frame = new KeyFrame(Duration.millis(MILLISECOND_DELAY), e -> step( SECOND_DELAY));
        animation = new Timeline();
        animation.setCycleCount(Timeline.INDEFINITE);
        animation.getKeyFrames().add(frame);

        starting_text.setVisible(true);
        losing_text.setVisible(false);
        winning_text.setVisible(false);
    }

    // Create the game's "scene": what shapes will be in the game and their starting properties
    Scene setupGame (int width, int height, Paint background, int lv, boolean is_first_level) {

        brick = new Brick();
        myBricks = brick.create(lv);
        if(is_first_level){
            myBouncer = new Ball();
            bouncers.add(myBouncer);
        }
        myPaddle = new Paddle();
        level = lv;
        starting_text = new Text(300, 200,"Press Space to Start");
        losing_text = new Text(300, 200,"You Lost");
        winning_text = new Text(300, 200,"You won");


        root = new Group();
        for(Ball b : bouncers){
            root.getChildren().add(b.imageview());
        }
        for(Brick k : myBricks){
            root.getChildren().add(k.imageview());
        }
        root.getChildren().add(myPaddle.imageview());
        root.getChildren().add(starting_text);
        root.getChildren().add(losing_text);
        root.getChildren().add(winning_text);


        // create a place to see the shapes
        Scene scene = new Scene(root, width, height, background);
        // respond to input
        scene.setOnKeyPressed(e -> handleKeyInput(e.getCode()));
        return scene;
    }

    // Change properties of shapes in small ways to animate them over time
    // Note, there are more sophisticated ways to animate shapes, but these simple ways work fine to start
    void step( double elapsedTime) {

        myPaddle = myPaddle.update(1,1);

        for(Ball b : bouncers){
            b.update(myPaddle, myBricks, elapsedTime);
            if(b.getLife()==0 && bouncers.size()<2){
                losing_text.setVisible(true);
                b.resetPos();
                animation.stop();
            }
        }

        checkclear(myBricks);




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
        if (code == KeyCode.RIGHT) {
            myPaddle.imageview().setX(myPaddle.imageview().getX() + myPaddle.getspeed());
        }
        if (code == KeyCode.LEFT) {
            myPaddle.imageview().setX(myPaddle.imageview().getX() - myPaddle.getspeed());
        }
        if(code==KeyCode.B){
            Ball bouncer = new Ball(myBouncer);
            bouncers.add(bouncer);
            root.getChildren().add(bouncer.imageview());
        }

    }

    public void checkclear(ArrayList<Brick> b){
        if(b.size()==0){
            animation.stop();
            myBouncer.resetPos();
            if(level<3){
                level++;
                setLevel(myStage,level, false);
            }
            else{
                winning_text.setVisible(true);
            }
        }
    }








    /**
     * Start the program.
     */
    public static void main (String[] args) {
        launch(args);
    }
}
