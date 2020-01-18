package breakout;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.*;

/**
 * @author Lucy Gu
 * @author Robert C. Duvall
 */
public class Game extends Application {
    public static final String TITLE = "breakout";
    public static final int SIZE = 600;
    public static final int FRAMES_PER_SECOND = 60;
    public static final int MILLISECOND_DELAY = 1000 / FRAMES_PER_SECOND;
    public static final double SECOND_DELAY = 1.0 / FRAMES_PER_SECOND;
    public static final Paint BACKGROUND = Color.SNOW;
    public static final String BOUNCER_IMAGE = "ball.gif";
    public static final String PADDLE_IMAGE = "pad.gif";

    private Group root;
    private Scene myScene;
    private Timeline animation;
    private Stage myStage;

    private Ball myBouncer;
    private Paddle myPaddle;
    private ArrayList<Brick> myBricks = new ArrayList<>();
    private ArrayList<PowerUp> myPowerUp = new ArrayList<>();
    private TextGroup myText;

    private int level;

    private boolean is_new_level = true;
    private boolean is_running = false;
    private boolean game_over = false;

    /**
     * Initialize what will be displayed and how it will be updated.
     */
    @Override
    public void start (Stage stage) {
        myStage = stage;
        myBouncer = new Ball();
        myPaddle = new Paddle();
        setSplashScreen(SIZE, SIZE, BACKGROUND, 0);
    }

    private void displayActiveGame(int lv) {
        myScene = setActiveGame(SIZE, SIZE, BACKGROUND, lv);

        myStage.setScene(myScene);
        myStage.setTitle(TITLE);
        myStage.show();

        KeyFrame frame = new KeyFrame(Duration.millis(MILLISECOND_DELAY), e -> step(SECOND_DELAY));
        animation = new Timeline();
        animation.setCycleCount(Timeline.INDEFINITE);
        animation.getKeyFrames().add(frame);
    }

    private Scene setActiveGame(int width, int height, Paint background, int lv) {
        Brick brick = new Brick();
        PowerUp p = new PowerUp();
        myBricks = brick.createPane(lv, p);
        myPowerUp = p.getList();
        level = lv;
        is_new_level = true;
        add_to_root();
        myText.displayStarter();
        Scene scene = new Scene(root, width, height, background);
        scene.setOnKeyPressed(e -> handleKeyInput(e.getCode()));
        return scene;
    }

    private void setSplashScreen(int width, int height, Paint background, int screen){
        myText = new TextGroup();
        root = new Group();
        root = myText.addAll(root);
        myScene = new Scene(root, width, height, background);
        myScene.setOnKeyPressed(e -> handleSplashInput(e.getCode()));

        myStage.setScene(myScene);
        myStage.setTitle(TITLE);
        myStage.show();

        switch(screen){
            case 0:
                game_over = false;
                myText.displaySplash();
                break;
            case 1:
                myText.displayEnd(myBouncer, myPaddle, false);
                break;
            case 2:
                myText.displayEnd(myBouncer, myPaddle, true);
                break;
            case 3:
                myText.rules();
                break;
            case 4:
                myText.cheatSheet();
                break;
        }
    }

    private void setSplashScreen(int screen){
        setSplashScreen(SIZE, SIZE, BACKGROUND, screen);
    }

    private void add_to_root(){
        root = new Group();
        root.getChildren().add(myBouncer.imageview());
        for(Brick k : myBricks){
            root.getChildren().add(k.imageview());
        }
        if(myPowerUp.size()>0) {
            for (PowerUp p : myPowerUp) {
                root.getChildren().add(p.imageview());
            }
        }
        root.getChildren().add(myPaddle.imageview());
        root = myText.addAll(root);
    }



    private void step(double elapsedTime) {

        myBouncer.update(myPaddle, myBricks, myPowerUp, elapsedTime);
        myPaddle.edgeCheck();
        (new PowerUp()).update(elapsedTime, myPaddle, myBouncer, myPowerUp);

        myText.updateDuringGame(myBouncer,myPaddle);

        checkLevelClear();
        checklose();

    }

    private void checklose(){
        if(myBouncer.getLife()==0){
            animation.stop();
            setSplashScreen(1);
            game_over = true;
            is_running = false;
        }
    }

    private void handleKeyInput (KeyCode code) {
        if(is_new_level && code==KeyCode.SPACE){
            animation.play();
            is_running = true;
            is_new_level = false;
            myText.displayGame();
        }
        if(!is_new_level && code == KeyCode.P){
            if(is_running) animation.pause();
            if(!is_running) animation.play();
            is_running = !is_running;
        }

        if(is_running) {
            switch(code) {
                case RIGHT:
                    myPaddle.imageview().setX(myPaddle.imageview().getX() + myPaddle.getspeed());
                    break;
                case LEFT:
                    myPaddle.imageview().setX(myPaddle.imageview().getX() - myPaddle.getspeed());
                    break;
                case C:
                    SkipRound();
                    break;
                case L:
                    myBouncer.giveLife();
                    break;
                case A:
                    myBouncer.setStrength(myBouncer.getStrength() + 1);
                    break;
            }
        }
        if(!is_running){
            switch (code) {
                case Q:
                    new_level(0);
                    break;
                case W:
                    new_level(1);
                    break;
                case E:
                    new_level(2);
                    break;
                case R:
                    new_level(3);
                    break;
                case T:
                    new_level(4);
                    break;
                case F:
                    game_over = true;
                    if(level!=4) setSplashScreen(1);
                    else setSplashScreen(2);
                    break;
            }
        }
    }

    private void handleSplashInput (KeyCode code) {
        if(!game_over) {
            switch (code) {
                case SPACE:
                    displayActiveGame(0);
                    animation.play();
                    is_running = true;
                    is_new_level = false;
                    myText.displayGame();
                    break;
                case M:
                    if(myText.displayingRule()) setSplashScreen(0);
                    else setSplashScreen(3);
                    break;
                case C:
                    if(myText.displayingCheat()) setSplashScreen(0);
                    else setSplashScreen(4);
                    break;
            }
        }
        else{
            if(code==KeyCode.S){
                myBouncer = new Ball();
                myPaddle = new Paddle();
                setSplashScreen(0);
            }
        }
    }

    private void SkipRound() {
        int a = 0;
        for(Brick b: myBricks){
            a+=b.getBricklives();
        }
        myPaddle.addscore(a);
        new_level(level+1);
    }

    private void checkLevelClear(){
        if(myBricks.size()==0 && myPowerUp.size()==0)  new_level(level+1);
    }

    private void new_level(int level_n){
        animation.stop();
        is_running = false;
        myBouncer.resetPos();
        myPaddle.resetPos();
        if(level_n<5){
            displayActiveGame(level_n);
        }
        else{
            animation.stop();
            setSplashScreen(2);
            game_over = true;
            is_running = false;
        }
    }


    /**
     * Start the program.
     */
    public static void main (String[] args) {
        launch(args);
    }
}
