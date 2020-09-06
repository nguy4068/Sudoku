import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import javax.swing.*;

public class SodokuGUI implements ActionListener, MouseListener {
    Sodoku game;
    int[][] board;
    JTextField[][] grid;
    boolean loadingPuzzle;
    public SodokuGUI() throws FileNotFoundException {
        loadingPuzzle = false;
        JFrame frame = new JFrame("Sodoku game");
        game = new Sodoku(10);
        board = game.grid1;
        Button button = new Button("Solve Sodoku");
        button.setActionCommand("button");
        button.addActionListener(this);
        button.setBounds(300,10,80,25);
        Button button1 = new Button("Input puzzle");
        button1.setActionCommand("button1");
        button1.setBounds(300,45,80,25);
        button1.addActionListener(this);
        Button button2 = new Button("Finish input");
        button2.setActionCommand("button2");
        button2.setBounds(300,75,80,25);
        button2.addActionListener(this);
        grid = new JTextField[9][9];
        Button[] number = new Button[9];
        int xNumber = 0;
        for (int i = 0; i < 9; i++){
            int num = i + 1;
            number[i] = new Button("" + num);
            number[i].setBounds(xNumber,300,30,30);
            xNumber = xNumber + 30;
            number[i].setActionCommand("" + num);
            number[i].addActionListener(this);
            frame.add(number[i]);
        }
        frame.addMouseListener(this);
        int y = 0;
        for (int i = 0; i < 9; i++){
            int x = 0;
            for (int j = 0; j < 9; j++){
                int indexSquare = (i / 3) * 3 + (j / 3);
                if (board[i][j] != 0) {
                    grid[i][j] = new grid(i,j);
                    if (indexSquare % 2 == 0){
                        grid[i][j].setBackground(Color.LIGHT_GRAY);
                    }
                    grid[i][j].setText("" + board[i][j]);
                    grid[i][j].setEditable(false);
                } else {
                    grid[i][j] = new grid(i,j);
                    if (indexSquare % 2 == 0){
                        grid[i][j].setBackground(Color.LIGHT_GRAY);
                    }
                    grid[i][j].setText("");
                    grid[i][j].addActionListener(this);
                }
                grid[i][j].setHorizontalAlignment(JTextField.CENTER);
                grid[i][j].setActionCommand("text");
                grid[i][j].setBounds(x,y,30,30);
                frame.add(grid[i][j]);
                x = x +30;
            }
            y = y + 30;
        }
        frame.add(button);
        frame.add(button1);
        frame.add(button2);
        frame.setLayout(new LayoutManager() {
            @Override
            public void addLayoutComponent(String s, Component component) {

            }

            @Override
            public void removeLayoutComponent(Component component) {

            }

            @Override
            public Dimension preferredLayoutSize(Container container) {
                return null;
            }

            @Override
            public Dimension minimumLayoutSize(Container container) {
                return null;
            }

            @Override
            public void layoutContainer(Container container) {

            }
        });
        frame.setSize(400, 400);
        frame.setVisible(true);

    }

    public static void main(String[] args) {
        try {
            new SodokuGUI();
        }catch (FileNotFoundException e){
            System.out.println("File not found");
        }
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        String event = actionEvent.getActionCommand();
        if (event.equals("text")) {
            grid a = (grid) actionEvent.getSource();
            int x = a.getX();
            int y = a.getY();
            String text = a.getText();
            if (!loadingPuzzle) {
                //System.out.println("Hi");
                if (text.equals("")) {
                    a.setText("");
                    board[x][y] = 0;
                    game.fillGrid2(x, y, 0);
                } else {
                    int num = Integer.parseInt(text);
                    ArrayList<Integer> availableNum = game.availableNumbers(x, y);
                    String number = "";
                    for (int i = 0; i < availableNum.size();i++){
                        number = number + availableNum.get(i);
                    }
                    System.out.println(number);
                    boolean check = game.isIn(availableNum, num);
                    if (!check) {
                        a.setForeground(Color.red);
                    } else if (check) {
                        a.setForeground(Color.blue);
                    }
                    a.setText("" + num);
                    board[x][y] = num;
                    game.fillGrid2(x, y, num);
                }
                game.printBoard();
            } else {
                if (text.equals("")) {
                    a.setText("");
                    board[x][y] = 0;
                    game.fillGrid2(x, y, 0);
                } else {
                    int num = Integer.parseInt(text);
                    a.setText("" + num);
                    board[x][y] = num;
                    game.fillGrid2(x, y, num);
                }

            }
        }else if (event.equals("button")){
            boolean a = game.solve();
            System.out.println(a);
            for (int i = 0; i < 9; i ++){
                for (int j = 0; j < 9; j++){
                    if (grid[i][j].isEditable()){
                        grid[i][j].setForeground(Color.BLUE);
                    }
                    grid[i][j].setText("" + board[i][j]);
                }
            }
        }else if (event.equals("button1")){
            loadingPuzzle = true;
            for (int i = 0; i < 9; i++){
                for (int j = 0; j < 9; j++){
                    grid[i][j].setText("");
                    grid[i][j].setEditable(true);
                    grid[i][j].setActionCommand("text");
                    grid[i][j].addActionListener(this);
                    board[i][j] = 0;
                    game.fillGrid2(i,j,0);
                }
            }
        } else if (event.equals("button2")){
            loadingPuzzle = false;
            for (int i = 0; i < 9; i++){
                for (int j = 0; j < 9; j++){
                    if (board[i][j] != 0){
                        grid[i][j].setEditable(false);
                    }
                }
            }
        }
    }

    @Override
    public void mouseClicked(MouseEvent mouseEvent) {

    }

    @Override
    public void mousePressed(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseReleased(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseEntered(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseExited(MouseEvent mouseEvent) {

    }
}