import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Random;
public class ProcessData {
    String fileName;
    public ArrayList<int[][]> ListPuzzle;
    public ArrayList<int[][]> ListSolution;

    public ProcessData(String fileName) throws FileNotFoundException{
        ListPuzzle = new ArrayList<>();
        ListSolution = new ArrayList<>();
        this.fileName = fileName;
        generateData();
    }
    public void generateData() throws FileNotFoundException{
        File f = new File(fileName);
        Scanner s = new Scanner(f);
        while(s.hasNextLine()){
            String[] dataCollection = s.nextLine().split(",");
            String puzzleString = dataCollection[0];
            String solutionString = dataCollection[1];
            int[][] puzzle = new int[9][9];
            int[][] solution = new int[9][9];
            for (int i = 0; i < puzzleString.length(); i++){
                int rows = i/9;
                int cols = i %9;
                char c = puzzleString.charAt(i);
                int num1 = Integer.parseInt(String.valueOf(c));
                char c1 = solutionString.charAt(i);
                int num2 = Integer.parseInt(String.valueOf(c));
                puzzle[rows][cols] = num1;
                solution[rows][cols] = num2;

            }
            ListPuzzle.add(puzzle);
            ListSolution.add(solution);

        }
    }
    public static void main(String[] args){
        Random r = new Random();
        int num = r.nextInt(100);
        String filename = "part" + num + ".txt";
        try {
            ProcessData p = new ProcessData(filename);
        }catch (FileNotFoundException e){
            System.out.println("File not found");
        }
    }

}
