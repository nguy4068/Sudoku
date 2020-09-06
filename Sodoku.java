
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Random;

/**
 * This is Sodoku class without any GUI
 * The purpose of this class is to generate a random
 * board of Sodoku game, and check the availability
 * of a number input by user from GUI
 */
public class Sodoku {
    public int[][] grid1;//grid1 stores normal Sodoku board (a 2 dimensional list)
                        //1 big list containing 9 smaller lists, each smaller
                        //list represents a row in the board
    public int[][] grid2;//grid2 stores the 9 squares of the Sodoku board
                        //also a two-dimensional list, each smaller list
                        //represents a 3*3 square in the board
    private Random r;
    private ArrayList<int[]> providedPosition;//List of coordinators of spot filled in advance
    private int spotFilled;//number of spot filled in advance
    public ArrayList<int[]> emptySpot;//List of coordinators of empty spots
    int numEmpty;//number of empty spots
    ProcessData p;// ProcessData class used to randomly choose a Sodoku board
    ArrayList<int[][]> puzzleCollection;//list of puzzles
    ArrayList<int[][]> solutionCollection;//list of solutions
    public Sodoku(int spotFilled) throws FileNotFoundException {
        this.spotFilled = spotFilled;
        grid1 = new int[9][9];//initialize grid1
        grid2 = new int[9][9];//initialize grid2
        r = new Random();
        providedPosition = new ArrayList<>();
        //initialize ProcessData object
        p = new ProcessData("Sodoku.txt");
        //Access the list of puzzle
        puzzleCollection = p.ListPuzzle;
        //Access the list of solution
        solutionCollection = p.ListSolution;
        //fill in the board
        randomGenerateBoard();
        emptySpot = emptySpot();
        numEmpty = emptySpot.size();

    }

    /**
     * Used to calculate the position of a number in the second grid
     * @param xCoord: x-coordinate of that number in normal board
     * @param yCoord: y-coordinate of that number in normal board
     * @param number: value of that number
     */
    public void fillGrid2(int xCoord, int yCoord, int number){
        //find the square in second grid where that number should be located
        int indexSquare = (xCoord / 3) * 3 + (yCoord / 3);
        //find the index of that number in the indicated square
        int posOfIndexSquare = (xCoord % 3) * 3 + yCoord % 3;
        grid2[indexSquare][posOfIndexSquare] = number;

    }

    /**
     * check whether a number is valid in a square
     * @param xCoord: x-coordinate of that number in normal board
     * @param yCoord: y-coordinate of that number in normal board
     * @param num: the value of that number
     * @return: true if that number has appeared in that square, otherwise false
     */
    public boolean InSquare(int xCoord, int yCoord, int num){
        // found the square where that number is located
        int indexSquare = (xCoord/3) *3 + (yCoord/3);
        for (int i = 0; i < grid2[indexSquare].length; i++ ){
            if (num == grid2[indexSquare][i]){
                return true;
            }
        }
        return false;
    }

    /**
     * check whether a number has appeared in a row
     * @param xCoord: x-coordinate of that number in normal board
     * @param yCoord: y-coordinate of that number in normal board
     * @param num: value of that number
     * @return: true if that number has appeared in the row, otherwise false
     */
    public boolean inRow (int xCoord, int yCoord, int num){
        for (int i = 0; i < 9; i++){
            if (grid1[xCoord][i] == num){
                return true;
            }
        }
        return false;
    }

    /**
     * check whether that number is valid to be put in a col
     * @param xCoord: x-coordinate of that number in normal board
     * @param yCoord: y-coordinate of that number in normal board
     * @param num: value of that number
     * @return: true if that number has appeared in that row, false otherwise
     */
    public boolean inCol(int xCoord, int yCoord, int num){
        for (int i = 0; i < 9; i++){
            if (grid1[i][yCoord] == num){
                return true;
            }
        }
        return false;
    }

    /**
     * check available numbers at a certain position
     * @param xCoord: x-coordinate of that position
     * @param yCoord:y-coordinate of that position
     * @return: a list of numbers available at that position
     */
    public ArrayList<Integer> availableNumbers(int xCoord, int yCoord){
        int indexSquare = (xCoord/3) *3 + (yCoord/3);
        ArrayList<Integer> a = new ArrayList<>();
        ArrayList<Integer> Unavailable = new ArrayList<>();
        String square = "";
        String rows = "";
        String cols = "";
        for (int i = 0; i < grid2[indexSquare].length;i++){
            int num = grid2[indexSquare][i];
            if (num != 0) {
                square = square + num + " ";
                Unavailable.add(num);
            }
        }
        for (int i = 0; i < grid1[xCoord].length;i++){
            int num = grid1[xCoord][i];
            if (num != 0){
                rows = rows + num;
                Unavailable.add(num);
            }
        }
        for (int i = 0; i < 9; i++){
            int num = grid1[i][yCoord];
            if (num != 0 ){
                cols = cols + num + " ";
                Unavailable.add(num);
            }

        }
        for (int i = 1; i < 10; i++){
            if (!isIn(Unavailable,i)){
                a.add(i);
            }
        }
        return a;


    }

    /**
     * find the coordinates of all empty spots in the board
     * @return: list of coordinates of empty spots in the board
     */
    public ArrayList<int[]> emptySpot(){
        ArrayList<int[]> emptySpot = new ArrayList<>();
        for (int i = 0; i < 9; i++){
            for (int j = 0; j < 9; j++){
                if (grid1[i][j] == 0){
                    emptySpot.add(new int[] {i,j});
                }
            }
        }
        return emptySpot;
    }

    /**
     * solve the board using recursive back-tracking
     * @return: true if the board has a solution, false otherwise
     */
    public boolean solve(){
        //If the board has not been completely filled
        if (emptySpot.size() > 0){
            //random for an empty spot
            int index = r.nextInt(emptySpot.size());
            int[] chosenSpot = emptySpot.get(index);
            int xCoord = chosenSpot[0];
            int yCoord = chosenSpot[1];
            //try every number from 1 to 9 in that spot
            for (int i = 1; i < 10;i++){
                //Only proceeding to fill if that number is valid
                if (!inCol(xCoord,yCoord,i) && !inRow(xCoord,yCoord,i) && ! InSquare(xCoord,yCoord,i) ) {
                    grid1[xCoord][yCoord] = i;
                    fillGrid2(xCoord, yCoord, i);
                    emptySpot.remove(chosenSpot);
                    //if it's the last spot to be filled return true, the board is completed
                    if (emptySpot.size() == 0){
                        return true;
                    }
                    if (solve()) {//start the recursive called to check whether that
                                  //number matches the solution, only return true if there's no spot left to fill
                        return true;
                    } else {
                        //if that number is not a solution, remove that number and add the position
                        //of that spot back to the empty list
                        grid1[xCoord][yCoord] = 0;
                        fillGrid2(xCoord, yCoord, 0);
                        emptySpot.add(chosenSpot);
                    }
                }
            }
            //return false if could not found any valid number to fill in that spot
            return false;
        }
        //if the board has been completely filled, return true
        return true;
    }

    /**
     * check whether a number is in an array
     * @param a: the array
     * @param b: the number
     * @return; true if that number was found, false otherwise
     */
    public boolean isIn(ArrayList<Integer> a, int b){
        for (int i = 0; i < a.size(); i++){
            if (a.get(i).equals(b)){
                return true;
            }
        }
        return false;
    }
    public boolean isProvided(ArrayList<int[]> a, int[] pos){
        for (int i = 0; i < a.size(); i++){
            int x = a.get(i)[0];
            int y = a.get(i)[1];
            if (pos[0] == x && pos[1] == y){
                return true;
            }
        }
        return false;
    }

    /**
     * print out the board
     */
    public void printBoard(){
        String result = "";
        for (int i = 0; i < 9;i++){
            for(int j = 0; j < 9; j++){
                result = result + " " + grid1[i][j] + " ";
                if (j == 8){
                    result = result + "\r\n";
                }
            }
        }
        System.out.println(result);
        System.out.println();
        String result2 = "";
        int start = 0;
        int end = 3;
        for (int m = 0; m < 3; m++) {
            for (int i = 0; i < 3; i++) {
                for (int j = start; j < end; j++) {
                    result2 = result2 + " " + grid2[i][j] + " ";
                    if (i == 2 && j == end - 1) {
                        result2 = result2 + "\r\n";
                    }
                }
            }
            start = start + 3;
            end = end + 3;
        }
        System.out.println(result2);

    }

    /**
     * randomly choose a board in the list of 10000 board provided from the ProcessData class
     */
    public void randomGenerateBoard(){
        int index = r.nextInt(puzzleCollection.size());
        int[][] chosenBoard = puzzleCollection.get(index);
        int[][] solution = solutionCollection.get(index);
        for (int i = 0; i < 9; i++){
            for (int j = 0; j < 9; j++){
                int num = chosenBoard[i][j];
                grid1[i][j] = num;
                fillGrid2(i,j,num);
            }
        }
        puzzleCollection.remove(index);
    }

    public static void main(String[] args){
        try {
            Sodoku game = new Sodoku(0);
        }catch(FileNotFoundException e){
            System.out.println("File not found");
        }
    }
}
