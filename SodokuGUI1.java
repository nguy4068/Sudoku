import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.FileNotFoundException;
import javax.swing.*;

/**
 * This is the GUI of the sodoku game
 */

public class SodokuGUI1 implements ActionListener, MouseListener {
    Sodoku game;//initialize the embedded sodoku board
    int[][] board;// initialize the board
    grid[][] grid;//initialize the list of grid object (JText object implemented)
    boolean loadingPuzzle;
    grid readyForInput;//initialize the pointer to the clicked spot
    int emptySpot;// number of empty spots
    private Label message;// message to announce player game's state
    private Label message1;//message to announce player game's state
    Timer t;// timer
    int timePassed;// counting the time passed since the user start the game
    public SodokuGUI1() throws FileNotFoundException {
        loadingPuzzle = false;
        JFrame frame = new JFrame("Sodoku game");// initialize the frame
        t = new Timer(1000,this);// timer delay 1 second, after 1 second, actionPerformed will be called
        t.setActionCommand("time");//set action command for timer
        game = new Sodoku(10);
        emptySpot = game.numEmpty;
        board = game.grid1;// access the board provided by class Sodoku
        //Solving sodoku button
        Button button = new Button("Solve Sodoku");
        button.setActionCommand("button");
        button.addActionListener(this);
        button.setBounds(300,10,80,25);
        //Start to input button
        Button button1 = new Button("Input puzzle");
        button1.setActionCommand("button1");
        button1.setBounds(300,45,80,25);
        button1.addActionListener(this);
        //Finish Input button
        Button button2 = new Button("Finish input");
        button2.setActionCommand("button2");
        button2.setBounds(300,75,80,25);
        button2.addActionListener(this);
        // Generate button
        Button button3 = new Button("Generate Puzzle");
        button3.setActionCommand("button3");
        button3.setBounds(280,105,140,25);
        button3.addActionListener(this);
        //Message
        message = new Label("Welcome to Sodoku");
        message.setBounds(100,280,150,10);
        frame.add(message);
        //Message1
        message1 = new Label("Timer: ");
        message1.setBounds(300,140,150,10);
        frame.add(message1);
        grid = new grid[9][9];// initialize the list of grid object
        Button[] number = new Button[9];//initialize rows of button from 1 to 9
        int xNumber = 0;//x-coordinate of the the first button
        for (int i = 0; i < 9; i++){
            int num = i + 1;
            number[i] = new Button("" + num);
            number[i].setBounds(xNumber,300,30,30);
            xNumber = xNumber + 30;
            number[i].setActionCommand("" + num);
            number[i].addActionListener(this);
            frame.add(number[i]);
        }
        //erase button
        Button eraseButton = new Button("Erase");
        eraseButton.setBounds(300,300,50,30);
        eraseButton.setActionCommand("Erase");
        eraseButton.addActionListener(this);
        frame.add(eraseButton);
        int y = 0;//y-coordinate of the first grid
        //initialize all of the grids inside the board
        for (int i = 0; i < 9; i++){
            int x = 0;
            for (int j = 0; j < 9; j++){
                int indexSquare = (i / 3) * 3 + (j / 3);
                if (board[i][j] != 0) {
                    grid[i][j] = new grid(i,j);
                    grid[i][j].setBackground(Color.white);
                    Color color = Color.white;
                    if (indexSquare % 2 == 0){
                        grid[i][j].setBackground(Color.LIGHT_GRAY);
                        color = Color.LIGHT_GRAY;
                    }
                    grid[i][j].setText("" + board[i][j]);
                    grid[i][j].setEditable(false);
                    grid[i][j].isProvided = true;
                    grid[i][j].originalColor = color;

                } else {
                    grid[i][j] = new grid(i,j);
                    grid[i][j].setBackground(Color.white);
                    Color color = Color.white;
                    if (indexSquare % 2 == 0){
                        grid[i][j].setBackground(Color.LIGHT_GRAY);
                        color = Color.LIGHT_GRAY;
                    }
                    grid[i][j].setText("");
                    grid[i][j].setEditable(false);
                    grid[i][j].originalColor = color;
                }
                grid[i][j].addMouseListener(this);
                grid[i][j].setHorizontalAlignment(JTextField.CENTER);
                grid[i][j].setBounds(x,y,30,30);
                frame.add(grid[i][j]);
                x = x +30;
            }
            y = y + 30;
        }
        frame.addMouseListener(this);
        frame.add(button);
        frame.add(button1);
        frame.add(button2);
        frame.add(button3);
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
        frame.setSize(500, 400);
        frame.setVisible(true);
        frame.setResizable(false);
        t.start();

    }

    public static void main(String[] args) {
        try {
            new SodokuGUI1();
        }catch(FileNotFoundException e){
            System.out.println("File not found");
        }
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        //get the action command to identify according performance
        String event = actionEvent.getActionCommand();
        if (event.equals("button")){//solve the board
            game.emptySpot = game.emptySpot();
            boolean a = game.solve();
            message.setText("Puzzle solved");
            System.out.println(a);
            for (int i = 0; i < 9; i ++){
                for (int j = 0; j < 9; j++){
                    if (!grid[i][j].isProvided){
                        grid[i][j].setForeground(Color.BLUE);
                    } else {
                        grid[i][j].setForeground(Color.black);
                    }
                    grid[i][j].setText("" + board[i][j]);
                }
            }
        }else if (event.equals("button1")){//input button
            loadingPuzzle = true;
            for (int i = 0; i < 9; i++){
                for (int j = 0; j < 9; j++){
                    grid[i][j].setText("");
                    grid[i][j].isProvided = false;
                    board[i][j] = 0;
                    game.fillGrid2(i,j,0);
                }
            }
        } else if (event.equals("button2")){//when user finished input
            loadingPuzzle = false;
            for (int i = 0; i < 9; i++){
                for (int j = 0; j < 9; j++){
                    if (board[i][j] != 0){
                        grid[i][j].isProvided = true;
                    }
                }
            }
            emptySpot = game.emptySpot().size();
        } else if (event.equals("button3")){//self-generate the board for user
            game.randomGenerateBoard();
            for (int i = 0; i < 9; i++){
                for(int j = 0; j < 9; j++){
                    int num = board[i][j];
                    if (num == 0){
                        grid[i][j].setText("");
                        grid[i][j].isProvided = false;
                    }else{
                        grid[i][j].setText("" + num);
                        grid[i][j].isProvided = true;
                        grid[i][j].setForeground(Color.black);
                    }
                }
            }
            message.setText("Welcome to Sodoku");

        } else if (event.equals("time")){//action command fired by Timer
            //start to count time and display it on the screen for user
            timePassed = timePassed + 1;
            if (timePassed > 60){
                int minutes = timePassed/60;
                int seconds = timePassed%60;
                message1.setText("Timer: " + minutes+":"+seconds);
            }else {
                message1.setText("Timer: " + timePassed);
            }

        } else {//action command fired from the number button
            int xCoord = readyForInput.x;//identify the grid where has clicked
            int yCoord = readyForInput.y;
            if (event.equals("Erase")){// if the button is the erase-button
                //set the displayed content of that grid to "" and set the number at that grid to 0
                readyForInput.setText("");
                board[xCoord][yCoord] = 0;
                game.fillGrid2(xCoord,yCoord,0);
                emptySpot = emptySpot + 1;
            } else {//if the button is number button
                int num = Integer.parseInt(event);//convert the string into number
                //as each number-button has an action command named after its value
                if (!game.InSquare(xCoord, yCoord, num) && !game.inRow(xCoord, yCoord, num) && !game.inCol(xCoord, yCoord, num)) {
                    readyForInput.setForeground(Color.blue);
                    readyForInput.setText(event);
                    board[xCoord][yCoord] = num;
                    game.fillGrid2(xCoord,yCoord,num);
                    emptySpot = emptySpot - 1;
                    if (emptySpot == 0){//if there's no empty spot left
                        message.setText("Puzzle completed!");
                    }
                } else {
                    readyForInput.setForeground(Color.red);
                    readyForInput.setText(event);
                    board[xCoord][yCoord] = num;
                    game.fillGrid2(xCoord,yCoord,num);
                    emptySpot = emptySpot - 1;
                }
            }


        }
    }

    @Override
    /**
     * Handle mouse event
     */
    public void mouseClicked(MouseEvent mouseEvent) {
        grid a = null;
        //if there's mouse clicked on the grid
        if (mouseEvent.getComponent() instanceof grid){
            a = (grid) mouseEvent.getComponent();
            assert a != null;
            if (!a.isProvided) {//if the grid is not provided with a number in advance
                if (readyForInput == null) {//if it's the first grid to be clicked
                    readyForInput = a;//set the ready for input pointer to that grid
                    readyForInput.setBackground(Color.pink);//switch the color of that grid
                    readyForInput.isReady = true;
                } else {//if it's not the first grid to be clicked
                    //reset the color of the last grid
                    readyForInput.setBackground(readyForInput.originalColor);
                    readyForInput.isReady = false;
                    readyForInput = a;//reset the  pointer to the recent grid being clicked
                    readyForInput.setBackground(Color.pink);
                    readyForInput.isReady = true;

                }
            }
        }




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