import java.util.ArrayList;

public class square {
    public ArrayList<Integer> possibleNUm;
    public int number;
    public square(){
        possibleNUm = new ArrayList<>();
        for (int i = 1; i < 10; i++){
            possibleNUm.add(i);
        }
        number  = 0;
    }
}
