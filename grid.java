import javax.swing.*;
import java.awt.*;

/**
 * grid object represents each grid inside the sodoku board
 */
public class grid extends JTextField {
    public int x;
    public int y;
    public boolean isProvided;
    public boolean isReady;// when user click on the region of a textField
                            // isReady will be set to true, and when the user click a number button
                            // the number will goes into the textField which is currently ready
    public Color originalColor;

    /**
     * constructor
     * @param x: the x-coordinate on JPanel screen of that grid
     * @param y: the y-coordinate on JPanel screen of that grid
     */
    public grid(int x, int y){
        this.x = x;
        this.y = y;
        this.isReady = false;
        this.isProvided = false;
    }

    /**
     * getter method
     * @return: x-coordinate of that grid
     */
    public int getX(){
        return this.x;
    }

    /**
     * setter method
     * @return: y-coordinate of that grid
     */
    public int getY(){
        return this.y;
    }

}
