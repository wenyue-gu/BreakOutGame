package breakout;


import javafx.scene.Group;
import javafx.scene.text.Text;

import java.util.Collections;

public class TextGroup extends Game{
    private boolean displayingRule;
    private boolean displayingCheat;

    private Text splash_screen_text1;
    private Text press_for_rule;
    private Text press_for_cheat;
    private Text rule_brick;
    private Text rule_special_brick;
    private Text rule_bouncer;
    private Text rule_powerup;
    private Text cheat_headline;
    private Text cheat_running;
    private Text cheat_headline2;
    private Text cheat_paused;

    private Text press_for_start;
    private Text losing_text;
    private Text winning_text;
    private Text press_to_restart;

    private Text Life;
    private Text Scores;
    private Text Strength;


    public TextGroup(){

        splash_screen_text1 = SetXCenter("Welcome to Breakout", 200);
        press_for_start = SetXCenter("Press SPACE to Start",200);
        press_for_rule = SetXCenter("Press M to display rules", 320);
        press_for_cheat = SetXCenter("Press C for a list of cheat codes", 340);

        rule_brick = SetXCenter("Ordinary Bricks: \n\n" +
                "Red-tainted bricks are ordinary bricks. They do not drop power ups.\n" +
                "Red bricks have hardness status varying from 1 to 9\n" +
                "The deeper the red color, the harder it is to break\n" +
                "Hitting a brick with a strength of X will subtract X from the brick's life\n", 88, 40);
        rule_special_brick = SetXCenter("Special Bricks:\n\n" +
                "Green bricks awards 50 points\n" +
                "Blue bricks award 1 strength status to bouncer\n" +
                "Yellow bricks drop power-up\n", 88, 150);
        rule_bouncer = SetXCenter("Bouncer & Paddle:\n\n" +
                "Initial strength, or attack power, of ball is 1\n" +
                "Initial life of ball is 3\n" +
                "The higher the level, the faster the speed of the bouncer\n" +
                "Hitting different area of the paddle changes the direction of the ball\n" +
                "Hitting the bottom of the screen deducts one life\n" +
                "Press LEFT and RIGHT to control the direction of the paddle\n" +
                "Press DOWN to stop the paddle from moving", 88, 240);
        rule_powerup = SetXCenter("Powerup:\n\n"+
                "Pink powerup adds a life, \n" +
                "Yellow powerup might both increase or reduce the size of the paddle\n" +
                "Red powerup increases the speed of the paddle", 88, 390);

        cheat_headline = SetXCenter("While the game is actively going on:",80);

        cheat_running = SetXCenter( "Press C to clear round\n" +
                "You will enter next level and your score will increase as if you cleared all bricks in this level\n" +
                "HOWEVER, no power up effect will be distributed (ie, no extra point, life, strength, etc)\n\n" +
                "Press L to give the bouncer an extra life\n\n" +
                "Press A to give the bouncer an extra strength status (max strength = 9)\n\n" +
                "Press P to pause game and press P again to resume\n\n",120);

        cheat_headline2 = SetXCenter("While the game is paused:",280);

        cheat_paused = SetXCenter( "Press Q, W, E, R, T to enter level 0, 1, 2, 3, and bonus level respectively\n" +
                "Note: score and other status will not reset or update in the process\n\n" +
                "Press F to finish game and calculate score\n" +
                "Note: if you are currently on level 1, 2, or 3, ending game with F is a auto lose.",320);

        losing_text = SetXCenter("You lost!",200);
        press_to_restart = SetXCenter("Press S to go back to Welcome screen", 400);
        winning_text = SetXCenter("You won!",200);

        Life = new Text(10, 25, "Life: ");
        Scores = new Text(500,25,"Score: ");
        Strength = new Text(500,40,"Strength:" );

    }

    //Methods that help with constructor
    private Text SetXCenter(String s, int x, int y){
        Text t = new Text(s);
        t.setX(x);
        t.setY(y);
        return t;
    }

    private Text SetXCenter(String s, int y){
        Text t = SetXCenter(s, 0, y);
        t.setX(SIZE/2.0 - t.getBoundsInParent().getWidth()/2);
        return t;
    }

    //getter methods
    public boolean displayingRule(){
        return displayingRule;
    }
    public boolean displayingCheat(){
        return displayingCheat;
    }

    //add everything to group
    public Group addAll(Group group){
        Collections.addAll(group.getChildren(), splash_screen_text1,
                press_for_rule,
                press_for_cheat,
                press_for_start,
                press_to_restart,
                rule_brick,
                rule_bouncer,
                rule_special_brick,
                rule_powerup,
                cheat_headline,
                cheat_headline2,
                cheat_paused,
                cheat_running,
                losing_text,
                winning_text,
                Life,
                Scores,
                Strength
        );
        return group;
    }


    //what to display in each situation
    public void updateDuringGame(Ball ball, Paddle paddle){
        Life.setText("Life: " + ball.getLife());
        Scores.setText("Score: " + paddle.getscore());
        Strength.setText("Strength: " + ball.getStrength());

    }

    public void displayStarter(){
        displayNothing();

        displayStatus(true);
        press_for_start.setVisible(true);
        press_for_start.setY(480);
    }

    public void displaySplash(){
        displayNothing();

        displayInitial(true);
        press_for_start.setY(300);
    }

    public void displayGame(){
        displayNothing();

        displayStatus(true);
    }

    public void displayEnd(Ball ball, Paddle paddle, boolean win){
        displayNothing();

        winning_text.setVisible(win);
        losing_text.setVisible(!win);
        press_to_restart.setVisible(true);

        Scores.setVisible(true);
        Scores.setText("Total Score = Score " + paddle.getscore() + " + Life " + ball.getLife() +
                " * 30 = " + (paddle.getscore()+ball.getLife()*30));
        Scores.setX(SIZE/2.0 - Scores.getBoundsInParent().getWidth()/2);
        Scores.setY(300);
    }

    public void rules(){
        displayNothing();

        displayInitial(true);
        splash_screen_text1.setVisible(false);
        displayRule(true);
        moveDownPress(1);
    }

    public void cheatSheet(){
        displayNothing();

        displayInitial(true);
        splash_screen_text1.setVisible(false);
        displayCheat(true);
        moveDownPress(2);

    }

    private void moveDownPress(int screen){
        switch(screen){
            case 1:
                press_for_rule.setText("Press M again to go back to welcome screen");
                press_for_rule.setX(SIZE/2.0 - press_for_rule.getBoundsInParent().getWidth()/2);
                break;
            case 2:
                press_for_cheat.setText("Press C again to go back to welcome screen");
                press_for_cheat.setX(SIZE/2.0 - press_for_cheat.getBoundsInParent().getWidth()/2);
                break;

        }
        press_for_rule.setY(520);
        press_for_start.setY(500);
        press_for_cheat.setY(540);
    }

    //setting text visibility for text that should appear/disappear at the same time/on the same screen
    private void displayStatus(boolean d){
        Life.setVisible(d);
        Strength.setVisible(d);
        Scores.setVisible(d);
    }

    private void displayInitial(boolean d){
        splash_screen_text1.setVisible(d);
        press_for_rule.setVisible(d);
        press_for_cheat.setVisible(d);
        press_for_start.setVisible(d);
    }

    private void displayRule(boolean d){
        rule_brick.setVisible(d);
        rule_special_brick.setVisible(d);
        rule_bouncer.setVisible(d);
        rule_powerup.setVisible(d);
        displayingRule = d;
    }

    private void displayCheat(boolean d){
        cheat_headline.setVisible(d);
        cheat_headline2.setVisible(d);
        cheat_paused.setVisible(d);
        cheat_running.setVisible(d);
        displayingCheat = d;
    }

    private void notEndginText(){
        losing_text.setVisible(false);
        winning_text.setVisible(false);
        press_to_restart.setVisible(false);
    }

    private void displayNothing(){
        notEndginText();
        displayStatus(false);
        displayRule(false);
        displayCheat(false);
        displayInitial(false);
    }

}
