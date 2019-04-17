import java.util.ArrayList;
import java.util.List;

public class Blockchain {

    private int difficulty;
    private List<Block> blocks;

    public Blockchain(int difficulty){
        this.difficulty = difficulty;
        blocks = new ArrayList<>();
        Block b = new Block(0, System.currentTimeMillis(), null, "Genesis Block", )
    }
}
