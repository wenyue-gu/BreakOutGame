package breakout;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;


public class Ball extends Game {

    public static final String BOUNCER_IMAGE = "ball.gif";
    private int x_speed = 50;
    private int y_speed = 50;
    private int x_dir;
    private int y_dir;
    private int strength;
    private ImageView imageview;


    public Ball(){
        x_dir = -1;
        y_dir = 1;
        strength = 1;
        Image image = new Image(this.getClass().getClassLoader().getResourceAsStream(BOUNCER_IMAGE));
        imageview = new ImageView(image);
        imageview.setX(SIZE / 2 - imageview.getBoundsInLocal().getWidth() / 2);
        imageview.setY(SIZE / 2 - imageview.getBoundsInLocal().getHeight() / 2);
    }

    public Ball another(Ball b){
        Ball ret = new Ball();
        ret.setStrength(b.strength-1);
        return ret;
    }

    public ImageView imageview(){
        return imageview;
    }

    private double xpos(){
        return this.imageview().getX();
    }
    private double ypos(){
        return this.imageview().getY();
    }

    private void setStrength(int k){
        this.strength +=k;
    }

    private void checkdir(ImageView Paddle){
        if(this.imageview.getX()>=SIZE || this.imageview.getX()<=0){ x_dir *= -1;}
        if(this.imageview.getY()>=SIZE || this.imageview.getY()<=0){ y_dir *= -1;}
        if(Paddle.getBoundsInParent().intersects(this.imageview.getBoundsInParent()) && y_dir>0) y_dir*=-1;
    }

    private void changepos(double elapsedTime){
        this.imageview.setX(this.xpos() + x_speed * elapsedTime * x_dir);
        this.imageview.setY(this.ypos() + y_speed * elapsedTime * y_dir);
    }

    public Ball update(ImageView Paddle, double elapsedTime){
        checkdir(Paddle);
        changepos(elapsedTime);
        return this;
    }


}
