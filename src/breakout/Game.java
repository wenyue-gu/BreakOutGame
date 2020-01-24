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
    public static final int MAX_LEVEL = 4;

    private Group root;
    private Scene myScene;
    private Timeline animation;
    private Stage myStage;

    private Paddle myPaddle;
    private ArrayList<Brick> myBricks = new ArrayList<>();
    private ArrayList<Ball> bouncers = new ArrayList<>();
    private ArrayList<PowerUp> myPowerUp = new ArrayList<>();
    private TextGroup myText;

    private int level;

    private boolean isResetedLevel = true;
    private boolean is_running = false;
    private boolean game_over = false;

    /**
     * Initialize what will be displayed and how it will be updated.
     */
    @Override
    public void start (Stage stage) {
        myStage = stage;
        bouncers.add(new Ball());
        myPaddle = new Paddle(SIZE);
        setSplashScreen(SIZE, SIZE, BACKGROUND, 0);
    }

    private void displayActiveGame(int lv) {
        setActiveGame(SIZE, SIZE, BACKGROUND, lv);

        myStage.setScene(myScene);
        myStage.setTitle(TITLE + " Level "+ lv);
        myStage.show();

        setAnimation();
    }

    private void setActiveGame(int width, int height, Paint background, int lv) {
        int powerupCount = (new Brick()).createPane(myBricks,lv);
        (new PowerUp()).createList(myPowerUp,powerupCount);
        for(Ball b:bouncers) { b.setSpeed(lv); }
        level = lv;
        isResetedLevel = true;
        add_to_root();
        myText.displayStarter();
        createScene(root,width,height,background);
    }

    /**
     * Redefined the scene to use a new root that contains everything left in the old root plus an extra ball
     */
    private void increaseBall(int width, int height, Paint background){
        animation.stop();
        Ball b = new Ball(bouncers.get(0));
        bouncers.add(b);
        Group temp = new Group();
        (temp).getChildren().addAll(root);
        temp.getChildren().add(b.imageview());
        root = temp;
        createScene(root,width,height,background);

        myStage.setScene(myScene);

        setAnimation();
    }

    private void createScene(Group g, int width, int height, Paint background){
        myScene = new Scene(g, width, height, background);
        myScene.setOnKeyPressed(e -> handleKeyInputDuringGame(e.getCode()));
        myScene.setOnMouseClicked(e -> handleMouseInput());
    }

    private void setAnimation(){
        KeyFrame frame = new KeyFrame(Duration.millis(MILLISECOND_DELAY), e -> step(SECOND_DELAY));
        animation = new Timeline();
        animation.setCycleCount(Timeline.INDEFINITE);
        animation.getKeyFrames().add(frame);
    }

    /**
     * Set the splash screen and show the texts based on what screen is showing
     */
    private void setSplashScreen(int width, int height, Paint background, int screen){
        myText = new TextGroup();
        root = new Group();
        root = myText.addAll(root);
        myScene = new Scene(root, width, height, background);
        myScene.setOnKeyPressed(e -> handleSplashInput(e.getCode()));

        myStage.setScene(myScene);
        myStage.setTitle(TITLE + " Welcome");
        myStage.show();

        switch(screen){
            case 0:
                game_over = false;
                myText.displaySplash();
                break;
            case 1:
                myText.displayEnd(bouncers, myPaddle, false);
                break;
            case 2:
                myText.displayEnd(bouncers, myPaddle, true);
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
        for(Ball b:bouncers) {
            root.getChildren().add(b.imageview());
        }
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

        for(Ball b:bouncers) {
            b.update(myPaddle, elapsedTime);
            (new Brick()).checkIfHit(myBricks, b, myPaddle, myPowerUp);
        }
        (new Ball()).updateStrength(bouncers);
        myPaddle.edgeCheck(SIZE);
        (new PowerUp()).update(elapsedTime, SIZE, myPaddle, bouncers.get(0), myPowerUp);
        myText.updateDuringGame(bouncers, myPaddle);
        myPaddle.move(elapsedTime);

        checkLevelClear();
        checklose();
    }

    private void checkLevelClear(){
        if(myBricks.size()==0 && myPowerUp.size()==0)  Level(level+1);
    }

    private void checklose(){
        for (Iterator<Ball> iterator = bouncers.iterator(); iterator.hasNext(); ) {
            Ball temp = iterator.next();
            if (temp.getLife() == 0 && bouncers.size() < 2) {
                animation.stop();
                setSplashScreen(1);
                game_over = true;
                is_running = false;
            } else if(temp.getLife()==0 && bouncers.size()>=2) {
                temp.imageview().setImage(null);
                iterator.remove();
            }
        }
    }

    private void Level(int level_n, boolean new_level){

        is_running = false;
        myPaddle.resetPos(SIZE);
        Ball iball = bouncers.get(0);
        iball.resetPos();
        animation.pause();
        for(Ball b:bouncers){
            if(!b.equals(iball)) b.imageview().setImage(null);
        }
        bouncers.removeAll(bouncers);
        bouncers.add(iball);
        myText.displayStarter();
        isResetedLevel = true;
        if(new_level) {
            myBricks.removeAll(myBricks);
            if (level_n <= MAX_LEVEL) {
                displayActiveGame(level_n);
            } else {
                setSplashScreen(2);
                game_over = true;
            }
        }
    }

    private void Level(int level_n){
        Level(level_n, true);
    }


    /**
     * Handle inputs
     */
    private void handleKeyInputDuringGame(KeyCode code) {
        startAndPause(code);
        if(is_running) inputWhilePlaying(code);
        else inputWhilePause(code);
    }

    private void startAndPause(KeyCode code){
        if(isResetedLevel && code==KeyCode.SPACE){
            animation.play();
            is_running = true;
            isResetedLevel = false;
            myText.displayGame();
        }
        if(!isResetedLevel && code == KeyCode.P){
            if(is_running) animation.pause();
            if(!is_running) animation.play();
            is_running = !is_running;
        }
    }

    private void inputWhilePlaying(KeyCode code){
        switch(code) {
            case RIGHT:
                myPaddle.setDir(1);
                break;
            case LEFT:
                myPaddle.setDir(-1);
                break;
            case DOWN:
                myPaddle.setDir(0);
                break;
            case C:
                SkipRound();
                break;
            case L:
                bouncers.get(0).giveLife();
                break;
            case A:
                bouncers.get(0).setStrength(bouncers.get(0).getStrength() + 1);
                (new Ball()).updateStrength(bouncers);
                break;
            case R:
                Level(level, false);
                break;
        }
    }

    private void inputWhilePause(KeyCode code){
        switch (code) {
            case DIGIT0:
                Level(0);
                break;
            case DIGIT1:
                Level(1);
                break;
            case DIGIT2:
                Level(2);
                break;
            case DIGIT3:
                Level(3);
                break;
            case DIGIT4:
            case DIGIT5:
            case DIGIT6:
            case DIGIT7:
            case DIGIT8:
            case DIGIT9:
                Level(4);
                break;
            case F:
                game_over = true;
                if(level!=MAX_LEVEL) setSplashScreen(1);
                else setSplashScreen(2);
                break;
        }
    }

    private void handleSplashInput (KeyCode code) {
        if(!game_over) {
            switch (code) {
                case SPACE:
                    displayActiveGame(1);
                    animation.play();
                    is_running = true;
                    isResetedLevel = false;
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
                bouncers = new ArrayList<>();
                bouncers.add(new Ball());
                myBricks = new ArrayList<>();
                myPaddle = new Paddle(SIZE);
                setSplashScreen(0);
            }
        }
    }

    private void handleMouseInput () {
        if(is_running){
            increaseBall(SIZE,SIZE,BACKGROUND);
            animation.play();
        }
    }

    private void SkipRound() {
        int a = 0;
        for(Brick b: myBricks){
            a+=b.getBricklives();
        }
        myPaddle.addscore(a);
        isResetedLevel = true;
        Level(level+1);
    }

    /**
     * Start the program.
     */
    public static void main (String[] args) {
        launch(args);
    }
}
