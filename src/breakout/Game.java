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


/**
 * A basic example JavaFX program for the first lab.
 * @author Lucy Gu
 * @author Robert C. Duvall
 */
public class Game extends Application {
    public static final String TITLE = "Example JavaFX";
    public static final int SIZE = 400;
    public static final int FRAMES_PER_SECOND = 60;
    public static final int MILLISECOND_DELAY = 1000 / FRAMES_PER_SECOND;
    public static final double SECOND_DELAY = 1.0 / FRAMES_PER_SECOND;
    public static final Paint BACKGROUND = Color.AZURE;
    public static final Paint HIGHLIGHT = Color.OLIVEDRAB;
    public static final String BOUNCER_IMAGE = "ball.gif";
    public static final int BOUNCER_SPEED = 50;
    public static final int BOUNCER_SPEED2 = 50;
    public static final String PADDLE_IMAGE = "pad.gif";
    public static final int PADDLE_SPEED = 10;
    //public static final Paint MOVER_COLOR = Color.PLUM;
    //public static final int MOVER_SIZE = 50;
    //public static final int MOVER_SPEED = 5;

    // some things needed to remember during game
    private Scene myScene;
    private Ball myBouncer;
    private ImageView myPaddle;
    //private Rectangle myMover;

    private int dir = 1;
    private int dir2 = 1;


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
        // create one top level collection to organize the things in the scene
        Group root = new Group();
        // make some shapes and set their properties
        //Image image = new Image(this.getClass().getClassLoader().getResourceAsStream(BOUNCER_IMAGE));
        myBouncer = new Ball();
        //myBouncer.imageview().setX(width / 2 - myBouncer.imageview().getBoundsInLocal().getWidth() / 2);
        //myBouncer.imageview().setY(height / 2 - myBouncer.imageview().getBoundsInLocal().getHeight() / 2);

        Image image = new Image(this.getClass().getClassLoader().getResourceAsStream(PADDLE_IMAGE));
        myPaddle = new ImageView(image);
        myPaddle.setX(width / 2 - myPaddle.getBoundsInLocal().getWidth() / 2);
        myPaddle.setY(height - myPaddle.getBoundsInLocal().getHeight()*2);



        //myMover = new Rectangle(width / 2 - MOVER_SIZE / 2, height / 2 - 100, MOVER_SIZE, MOVER_SIZE);
        //myMover.setFill(MOVER_COLOR);
        // order added to the group is the order in which they are drawn
        //root.getChildren().add(myMover);

        root.getChildren().add(myBouncer.imageview());
        root.getChildren().add(myPaddle);

        // create a place to see the shapes
        Scene scene = new Scene(root, width, height, background);
        // respond to input
        scene.setOnKeyPressed(e -> handleKeyInput(e.getCode()));
        scene.setOnMouseClicked(e -> handleMouseInput(e.getX(), e.getY()));
        return scene;
    }

    // Change properties of shapes in small ways to animate them over time
    // Note, there are more sophisticated ways to animate shapes, but these simple ways work fine to start
    private void step (double elapsedTime) {

        myBouncer = myBouncer.update(myPaddle,elapsedTime);


    }

    // What to do each time a key is pressed
    private void handleKeyInput (KeyCode code) {
        if (code == KeyCode.RIGHT) {
            if(myPaddle.getX()>SIZE) myPaddle.setX(0);
            myPaddle.setX(myPaddle.getX() + PADDLE_SPEED);
        }
        else if (code == KeyCode.LEFT) {
            if(myPaddle.getX()<0) myPaddle.setX(SIZE);
            myPaddle.setX(myPaddle.getX() - PADDLE_SPEED);
        }
        //else if (code == KeyCode.UP) {
        //    myMover.setY(myMover.getY() - MOVER_SPEED);
        //}
        //else if (code == KeyCode.DOWN) {
        //    myMover.setY(myMover.getY() + MOVER_SPEED);
        //}
    }

    // What to do each time a key is pressed
    private void handleMouseInput (double x, double y) {

    }

    /**
     * Start the program.
     */
    public static void main (String[] args) {
        launch(args);
    }
}
