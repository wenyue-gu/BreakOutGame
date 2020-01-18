package breakout;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.*;

public class PowerUp extends Game{
    private int type;
    private ImageView power;
    private ArrayList<PowerUp> PoweUpList = new ArrayList<>();
    boolean is_dropping;

    public PowerUp(){
        type = (Math.random() <= 0.5) ? 1 : 2;
        Image image = new Image(Objects.requireNonNull(this.getClass().getClassLoader().getResourceAsStream(
                this.type + "type.gif")));
        power = new ImageView(image);
        power.setVisible(false);
    }

    public void addtolist(){
        PoweUpList.add(new PowerUp());
    }
    public ArrayList<PowerUp> getList(){
        return PoweUpList;
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

    public void update(double elapsedTime, Paddle Paddle, Ball Ball, ArrayList<PowerUp> powerup){
        for (Iterator<PowerUp> iterator = powerup.iterator(); iterator.hasNext(); ) {
            PowerUp temp = iterator.next();
            if(temp.dropping())  temp.power.setY(temp.power.getY() + 50 * elapsedTime);
            if (Paddle.imageview().getBoundsInParent().intersects(temp.power.getBoundsInParent())) {
                temp.giveEffect(Paddle, Ball);
                temp.power.setImage(null);
                iterator.remove();
            } else if (temp.power.getY() > SIZE) {
                temp.power.setImage(null);
                iterator.remove();
            }
        }
    }

    private void giveEffect(Paddle Paddle, Ball Ball) {
        switch (type) {
            case 1:
                Ball.giveLife();
                break;
            case 2:
                if (Paddle.getSize()!=1) Paddle.changesize(1);
                else {
                    Paddle.changesize((Math.random() <= 0.5) ? 0.5 : 2);
                }
                break;
        }
    }
}