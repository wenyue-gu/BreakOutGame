package breakout;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Scene;
import javafx.scene.effect.Reflection;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.*;

public class Level extends Game {

    private int level;
    private Game game;
    private Timeline t;


    public Level(int l) {
        level = l;
    }

    public Timeline animation(){
        return t;
    }
    public void stop(){
        t.stop();
    }


    public void createLevel(Stage s, Scene scene, Timeline animation, int lv){
        level = lv;
        game = new Game();
        scene = game.setupGame(SIZE, SIZE, BACKGROUND,level, false);
        s.setScene(scene);
        s.setTitle(TITLE);
        s.show();
        //game.setAnimation();
    }



}
