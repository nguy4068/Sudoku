import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

/**
 * This class is used to split 1 million sodoku puzzles stored in
 * txt file into 100 smaller files
 */
public class SplitData {
    private String Filename;
    Scanner s;
    PrintWriter[] listWriter;

    /**
     * constructor
     * @param fileName: the txt file storing 1 million sodoku puzzles
     * @throws FileNotFoundException: error caught when the file is not found
     */
    public SplitData(String fileName) throws FileNotFoundException{
        Filename = fileName;
        s = new Scanner(new File(Filename));
        listWriter = new PrintWriter[100];
        for (int i = 0; i < listWriter.length; i++){
            String Name = "part" + i + ".txt";
            listWriter[i] = new PrintWriter(new File(Name));
        }
        writeNewFile();


    }
    public void writeNewFile() throws FileNotFoundException{
        int countFile = 0;
        while(s.hasNextLine()){
              countFile = countFile/10000;
              String line = s.nextLine();
              listWriter[countFile].write(line);
              countFile = countFile + 1;
              if (countFile%10000 == 0){
                  listWriter[countFile].close();
              }
        }

    }
    public static void main(String[] args){
        try{
            SplitData a = new SplitData("Sodoku.txt");
        } catch(FileNotFoundException e){
            System.out.println("File not found!");
        }
    }

}
