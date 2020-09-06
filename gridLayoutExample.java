import java.awt.GridLayout;
import javax.swing.*;

public class gridLayoutExample {
    gridLayoutExample() {
        JFrame frame = new JFrame("Flow Layout");
        JButton button, button1, button2, button3, button4;
        JTextField box1, box2, box3;
        JTextField[][] grid = new JTextField[9][9];
        for (int i = 0; i < 9; i++){
            for (int j = 0; j < 9; j++){
                grid[i][j] = new JTextField("" + j);
                grid[i][j].setHorizontalAlignment(JTextField.CENTER);
                frame.add(grid[i][j]);
            }
        }

        frame.setLayout(new GridLayout(9, 9));
        frame.setSize(300, 300);
        frame.setVisible(true);

    }

    public static void main(String[] args) {
        new gridLayoutExample();
    }
}