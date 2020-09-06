import java.awt.*;
import javax.swing.*;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;
public class Sodoku1 {
    public square[][] grid1;
    public square[][] grid2;
    private Random r;
    private ArrayList<int[]> providedPosition;
    private int spotFilled;
    public ArrayList<int[]> emptySpot;
    int numEmpty;
    public Sodoku1(int spotFilled){
        this.spotFilled = spotFilled;
        grid1 = new square[9][9];
        grid2 = new square[9][9];
        r = new Random();
        providedPosition = new ArrayList<>();
        for (int i = 0; i < 9; i++){
            for (int j = 0; j < 9; j++){
                int indexSquare = (i / 3) * 3 + (j / 3);
                int posOfIndexSquare = (i % 3) * 3 + j % 3;
                grid1[i][j] = new square();
                grid2[indexSquare][posOfIndexSquare] = grid1[i][j];
            }
        }
        grid1[0][0].number = 6;
        fillGrid2(0,0,6);
        grid1[0][3].number = 1;
        fillGrid2(0,3,1);
        grid1[0][8].number = 2;
        fillGrid2(0,8,2);
        grid1[1][0].number = 8;
        fillGrid2(1,0,8);
        grid1[1][2].number = 1;
        fillGrid2(1,2,1);
        grid1[1][4].number = 9;
        fillGrid2(1,4,9);
        grid1[2][1].number = 7;
        fillGrid2(2,1,7);
        grid1[2][2].number = 5;
        fillGrid2(2,2,5);
        grid1[2][4].number = 8;
        fillGrid2(2,4,8);
        grid1[2][5].number = 4;
        fillGrid2(2,5,4);
        grid1[3][0].number = 4;
        fillGrid2(3,0,4);
        grid1[3][1].number = 3;
        fillGrid2(3,1,3);
        grid1[3][4].number = 2;
        fillGrid2(3,4,2);
        grid1[3][6].number = 5;
        fillGrid2(3,6,5);
        grid1[3][7].number = 6;
        fillGrid2(3,7,6);
        grid1[3][8].number = 1;
        fillGrid2(3,8,1);
        grid1[4][0].number = 5;
        fillGrid2(4,0,5);
        grid1[4][1].number = 1;
        fillGrid2(4,1,1);
        grid1[4][2].number = 8;
        fillGrid2(4,2,8);
        grid1[4][3].number = 7;
        fillGrid2(4,3,7);
        grid1[4][6].number = 4;
        fillGrid2(4,6,4);
        grid1[4][8].number = 9;
        fillGrid2(4,8,9);
        grid1[5][1].number = 9;
        fillGrid2(5,1,9);
        grid1[5][2].number = 6;
        fillGrid2(5,2,6);
        grid1[5][3].number = 4;
        fillGrid2(5,3,4);
        grid1[5][4].number = 1;
        fillGrid2(5,4,1);
        grid1[5][6].number = 3;
        fillGrid2(5,6,3);
        grid1[6][4].number = 7;
        fillGrid2(6,4,7);
        grid1[7][1].number = 6;
        fillGrid2(7,1,6);
        grid1[7][4].number = 3;
        fillGrid2(7,4,3);
        grid1[7][5].number = 1;
        fillGrid2(7,5,1);
        grid1[7][7].number = 5;
        fillGrid2(7,7,5);
        grid1[8][0].number = 7;
        fillGrid2(8,0,7);
        grid1[8][2].number = 2;
        fillGrid2(8,2,2);
        grid1[8][3].number  = 5;
        fillGrid2(8,3,5);
        grid1[8][4].number = 4;
        fillGrid2(8,4,4);
        grid1[8][6].number = 6;
        fillGrid2(8,6,6);
        grid1[8][8].number = 3;
        fillGrid2(8,8,3);
        emptySpot = emptySpot();
        numEmpty = emptySpot.size();
        printBoard();
        for (int i = 0; i < 9; i++){
            for(int j = 0; j < 9; j++){
                String result = "";
                if (grid1[i][j].number == 0){
                    System.out.println ("Point: " + i + " " + j);
                    for (int m = 0; m < grid1[i][j].possibleNUm.size(); m ++){
                        result = result + " " + grid1[i][j].possibleNUm.get(m);
                    }
                    System.out.println("Possible number: " + result);
                }
            }
        }
        solve();
        printBoard();

    }
    public void fillGrid2(int xCoord, int yCoord, int number){
        int indexSquare = (xCoord / 3) * 3 + (yCoord / 3);
        int posOfIndexSquare = (xCoord % 3) * 3 + yCoord % 3;
        grid2[indexSquare][posOfIndexSquare].number = number;
        update(xCoord,yCoord,number);

    }
    public void update(int xCoord,int yCoord, int num){
        int indexSquare = (xCoord / 3) * 3 + (yCoord / 3);
        int posOfIndexSquare = (xCoord % 3) * 3 + yCoord % 3;
        Integer a = num;
        // update rows
        for (int i = 0; i < 9; i++){
            if (i != yCoord) {
                grid1[xCoord][i].possibleNUm.remove(a);
            }
        }
        //update cols
        for (int i = 0; i < 9; i++){
            if (i != xCoord) {
                grid1[i][yCoord].possibleNUm.remove(a);
            }
        }
        // update square
        for (int i = 0 ; i < grid2[indexSquare].length; i++){
            if (i != posOfIndexSquare) {
                grid2[indexSquare][i].possibleNUm.remove(a);
            }
        }
    }
    public void reAdd(int xCoord, int yCoord, int num){
        int indexSquare = (xCoord / 3) * 3 + (yCoord / 3);
        int posOfIndexSquare = (xCoord % 3) * 3 + yCoord % 3;
        Integer a = num;
        // update rows
        for (int i = 0; i < 9; i++){
            if (i!=yCoord) {
                grid1[xCoord][i].possibleNUm.add(a);
            }
        }
        //update cols
        for (int i = 0; i < 9; i++){
            if (i!=xCoord) {
                grid1[i][yCoord].possibleNUm.add(a);
            }
        }
        // update square
        for (int i = 0 ; i < grid2[indexSquare].length; i++){
            if (i != posOfIndexSquare) {
                grid2[indexSquare][i].possibleNUm.add(a);
            }
        }
    }


    public boolean InSquare(int xCoord, int yCoord, int num){
        int indexSquare = (xCoord/3) *3 + (yCoord/3);
        for (int i = 0; i < grid2[indexSquare].length; i++ ){
            if (num == grid2[indexSquare][i].number){
                return true;
            }
        }
        return false;
    }
    public boolean inRow (int xCoord, int yCoord, int num){
        for (int i = 0; i < 9; i++){
            if (grid1[xCoord][i].number == num){
                return true;
            }
        }
        return false;
    }
    public boolean inCol(int xCoord, int yCoord, int num){
        for (int i = 0; i < 9; i++){
            if (grid1[i][yCoord].number == num){
                return true;
            }
        }
        return false;
    }
    public ArrayList<Integer> availableNumbers(int xCoord, int yCoord){
        int indexSquare = (xCoord/3) *3 + (yCoord/3);
        ArrayList<Integer> a = new ArrayList<>();
        ArrayList<Integer> Unavailable = new ArrayList<>();
        String square = "";
        String rows = "";
        String cols = "";
        for (int i = 0; i < grid2[indexSquare].length;i++){
            int num = grid2[indexSquare][i].number;
            if (num != 0) {
                square = square + num + " ";
                Unavailable.add(num);
            }
        }
        for (int i = 0; i < grid1[xCoord].length;i++){
            int num = grid1[xCoord][i].number;
            if (num != 0){
                rows = rows + num;
                Unavailable.add(num);
            }
        }
        for (int i = 0; i < 9; i++){
            int num = grid1[i][yCoord].number;
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
    public ArrayList<int[]> emptySpot(){
        ArrayList<int[]> emptySpot = new ArrayList<>();
        for (int i = 0; i < 9; i++){
            for (int j = 0; j < 9; j++){
                if (grid1[i][j].number == 0){
                    emptySpot.add(new int[] {i,j});
                }
            }
        }
        return emptySpot;
    }
    public boolean solve(){
        if (emptySpot.size() > 0){
            int index = r.nextInt(emptySpot.size());
            int[] chosenSpot = emptySpot.get(index);
            int xCoord = chosenSpot[0];
            int yCoord = chosenSpot[1];
            for (int i = 0; i < grid1[xCoord][yCoord].possibleNUm.size();i++){
                int num = grid1[xCoord][yCoord].possibleNUm.get(i);
                grid1[xCoord][yCoord].number = num;
                update(xCoord,yCoord,num);
                emptySpot.remove(chosenSpot);
                if (solve()) {
                    return true;
                } else {
                    grid1[xCoord][yCoord].number = 0;
                    emptySpot.add(chosenSpot);
                    reAdd(xCoord,yCoord,num);
                }
            }
            return false;
        }
        return true;
    }
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
    public void printBoard(){
        String result = "";
        for (int i = 0; i < 9;i++){
            for(int j = 0; j < 9; j++){
                result = result + " " + grid1[i][j].number + " ";
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
                    result2 = result2 + " " + grid2[i][j].number + " ";
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

    public static void main(String[] args){
        Sodoku1 game = new Sodoku1(0);
    }
}