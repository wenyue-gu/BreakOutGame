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
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
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

    private ArrayList<Ball> bouncers = new ArrayList<>();
    private ArrayList<Brick> myBricks = new ArrayList<>();


    /**
     * Initialize what will be displayed and how it will be updated.
     */
    @Override
    public void start (Stage stage) {
        // attach scene to the stage and display it
        myScene = setupGame(SIZE, SIZE, BACKGROUND);
        stage.setScene(myScene);
        stage.setTitle(TITLE);
        stage.show();
        // attach "game loop" to timeline to play it (basically just calling step() method repeatedly forever)
        KeyFrame frame = new KeyFrame(Duration.millis(MILLISECOND_DELAY), e -> step(SECOND_DELAY));
        Timeline animation = new Timeline();
        animation.setCycleCount(Timeline.INDEFINITE);
        animation.getKeyFrames().add(frame);
        animation.play();
    }

    // Create the game's "scene": what shapes will be in the game and their starting properties
    private Scene setupGame (int width, int height, Paint background) {


        brick = new Brick();
        myBricks = brick.create(1);
        myBouncer = new Ball();
        bouncers.add(myBouncer);
        myPaddle = new Paddle();



        root = new Group();
        for(Ball b : bouncers){
            root.getChildren().add(b.imageview());
        }
        for(Brick k : myBricks){
            root.getChildren().add(k.imageview());
        }
        root.getChildren().add(myPaddle.imageview());

        // create a place to see the shapes
        Scene scene = new Scene(root, width, height, background);
        // respond to input
        scene.setOnKeyPressed(e -> handleKeyInput(e.getCode()));
        return scene;
    }

    // Change properties of shapes in small ways to animate them over time
    // Note, there are more sophisticated ways to animate shapes, but these simple ways work fine to start
    private void step (double elapsedTime) {

        myPaddle = myPaddle.update(1,1);

        for(Ball b : bouncers){
            b.update(myPaddle, myBricks, elapsedTime);
        }

    }

    // What to do each time a key is pressed
    private void handleKeyInput (KeyCode code) {
        if (code == KeyCode.RIGHT) {
            myPaddle.imageview().setX(myPaddle.imageview().getX() + myPaddle.getspeed());
        }
        else if (code == KeyCode.LEFT) {
            myPaddle.imageview().setX(myPaddle.imageview().getX() - myPaddle.getspeed());
        }
        //else if(code==KeyCode.B){
        //    Ball bouncer = new Ball(myBouncer);
        //    bouncers.add(bouncer);
        //    root.getChildren().add(bouncer.imageview());
        //}
    }


    /**
     * Start the program.
     */
    public static void main (String[] args) {
        launch(args);
    }
}
