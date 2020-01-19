package breakout;


import javafx.scene.Group;
import javafx.scene.text.Text;

import java.util.ArrayList;
import java.util.Collections;


/**
 * @author Lucy Gu
 */
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
                "Bouncer speed increases every level\n" +
                "Press LEFT and RIGHT to control the direction of paddle movement\n" +
                "Press DOWN to stop the paddle from moving", 88, 240);
        rule_powerup = SetXCenter("Powerup:\n\n"+
                "Pink powerup adds a life, \n" +
                "Yellow powerup might both increase or reduce the size of the paddle\n" +
                "Red powerup increases the speed of the paddle", 88, 410);

        cheat_headline = SetXCenter("While the game is actively going on:",80);

        cheat_running = SetXCenter( "Press C to clear round\n" +
                "You will enter next level and your score will increase as if you cleared all bricks in this level\n" +
                "HOWEVER, no power up effect will be distributed (ie, no extra point, life, strength, etc)\n\n" +
                "Press L to give the bouncer an extra life\n\n" +
                "Press A to give the bouncer an extra strength status (max strength = 9)\n\n" +
                "Press P to pause game and press P again to resume\n\n" +
                "If you click anywhere on the screen during play you will get an extra ball with life 1\n",120);

        cheat_headline2 = SetXCenter("While the game is paused:",290);

        cheat_paused = SetXCenter( "Press 0 through 4 to enter level 0, 1, 2, 3, and bonus (4) level respectively\n" +
                "Note: score and other status will not reset or update in the process\n\n" +
                "Press F to finish game and calculate score\n" +
                "Note: if you are currently on level 1, 2, or 3, ending game with F is a auto lose.",330);

        losing_text = SetXCenter("You lost!",200);
        press_to_restart = SetXCenter("Press S to go back to Welcome screen", 400);
        winning_text = SetXCenter("You won!",200);

        Life = new Text(10, 25, "Life: ");
        Scores = new Text(500,25,"Score: ");
        Strength = new Text(500,40,"Strength:" );

    }

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

    /**
     * Check if the game is displaying rule/cheat screen
     * @return a boolean value
     */
    public boolean displayingRule(){
        return displayingRule;
    }
    public boolean displayingCheat(){
        return displayingCheat;
    }

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


    /**
     * Actively update text as the game is going on
     * @param balls ALL balls that exist on screen (the maximum life status is displayed)
     */
    public void updateDuringGame(ArrayList<Ball> balls, Paddle paddle){
        int i = (new Ball()).maxLife(balls);
        Life.setText("Life: " + i);
        Scores.setText("Score: " + paddle.getscore());
        Strength.setText("Strength: " + balls.get(0).getStrength());

    }

    /**
     * Display the starter as one enter or reset level
     */
    public void displayStarter(){
        displayNothing();

        displayStatus(true);
        press_for_start.setVisible(true);
        press_for_start.setY(480);
    }

    /**
     * Display the Welcome screen
     */
    public void displaySplash(){
        displayNothing();

        displayInitial(true);
        press_for_start.setY(300);
    }

    /**
     * Display status during game
     */
    public void displayGame(){
        displayNothing();

        displayStatus(true);
    }

    /**
     * Displaying end text
     * @param win whether the game is won or not - displays winning or losing text based on this input
     */
    public void displayEnd(ArrayList<Ball> balls, Paddle paddle, boolean win){
        displayNothing();
        winning_text.setVisible(win);
        losing_text.setVisible(!win);
        press_to_restart.setVisible(true);
        Scores.setVisible(true);
        int maxLife = (new Ball()).maxLife(balls);
        Scores.setText("Total Score = Score " + paddle.getscore() + " + Life " + maxLife +
                " * 30 = " + (paddle.getscore()+maxLife));
        Scores.setX(SIZE/2.0 - Scores.getBoundsInParent().getWidth()/2);
        Scores.setY(300);
    }

    /**
     * Displays rule screen
     */
    public void rules(){
        displayNothing();

        displayInitial(true);
        splash_screen_text1.setVisible(false);
        displayRule(true);
        moveDownPress(1);
    }

    /**
     * Displays cheat codes
     */
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
        press_for_rule.setY(540);
        press_for_start.setY(520);
        press_for_cheat.setY(560);
    }



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
