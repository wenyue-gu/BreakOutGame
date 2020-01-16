package breakout;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.*;


public class Powerup extends Game{
    private int type;
    private ImageView power;
    private ArrayList<Powerup> powerups = new ArrayList<>();
    boolean is_dropping;

    public Powerup(){
        double rand = Math.random();
        is_dropping = false;
        if(rand<0.5){
            type = 1;
        }
        else{
            type = 2;
        }
        Image image = new Image(this.getClass().getClassLoader().getResourceAsStream(this.type + "type.gif"));
        power = new ImageView(image);
        power.setVisible(false);
    }

    public void addtolist(){
        powerups.add(new Powerup());
    }
    public ArrayList<Powerup> getList(){
        return powerups;
    }
    public ImageView imageview(){
        return power;
    }
    public boolean dropping(){
        return is_dropping;
    }

    public void drop(double x, double y){

        this.power.setX(x + (new Brick()).imageview().getBoundsInLocal().getWidth()/2);
        this.power.setY(y + (new Brick()).imageview().getBoundsInLocal().getHeight()/2);
        this.power.setVisible(true);
        this.is_dropping = true;
    }

    public void update(double elapsedTime, Paddle Paddle, Ball Ball, ArrayList<Powerup> powerup){

        for (Iterator<Powerup> iterator = powerup.iterator(); iterator.hasNext(); ) {
            Powerup temp = iterator.next();
            if(temp.dropping())  temp.power.setY(temp.power.getY() + 50 * elapsedTime);
            if (Paddle.imageview().getBoundsInParent().intersects(temp.power.getBoundsInParent())) {
                if (temp.type == 1) {
                    Ball.giveLife();
                } else {
                    if (Paddle.getSize() < 1 || Paddle.getSize() > 1) Paddle.changesize(1);
                    else {
                        Paddle.changesize((Math.random() <= 0.5) ? 0.5 : 2);
                    }
                }
                temp.power.setImage(null);
                iterator.remove();
            } else if (temp.power.getY() > SIZE) {
                temp.power.setImage(null);
                iterator.remove();
            }
        }
    }

    private void check_if_hit(Paddle Paddle, Ball Ball, ArrayList<Powerup> powerup){

    }



}
