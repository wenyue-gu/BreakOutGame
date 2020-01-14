package breakout;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;


public class Paddle extends Game {

    public static final String Paddle_Image = "pad.gif";
    private int speed = 20;
    private int size;
    private ImageView imageview;


    public Paddle(){
        size = 1;
        Image image = new Image(this.getClass().getClassLoader().getResourceAsStream(Paddle_Image));
        imageview = new ImageView(image);
        imageview.setScaleX(imageview.getScaleX() * size);
        imageview.setX(SIZE / 2 - imageview.getBoundsInLocal().getWidth() / 2);
        imageview.setY(SIZE - imageview.getBoundsInLocal().getHeight()*2);
    }

    public ImageView imageview(){
        return imageview;
    }

    private void changesize(int k){
        this.size = k;
        imageview.setScaleX(imageview.getScaleX() * size);
    }


    private void handleKeyInput (KeyCode code) {
        if (code == KeyCode.RIGHT) {
            if(this.imageview.getX()>SIZE) this.imageview.setX(0);
            this.imageview.setX(this.imageview.getX() + speed);
        }
        else if (code == KeyCode.LEFT) {
            if(this.imageview.getX()<0) this.imageview.setX(SIZE);
            this.imageview.setX(this.imageview.getX() - speed);
        }
    }


}
