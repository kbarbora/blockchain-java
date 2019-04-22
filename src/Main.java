import accessControl.DataKeeper;
import accessControl.Policy;
import accessControl.Record;
import blockchain.Blockchain;
import blockchain.ConsensusLevel;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {

        DataKeeper master = null;
        DataKeeper hospital = null;
        DataKeeper athlete = null;
        try {
            master = new DataKeeper("Master");
            hospital = new DataKeeper("Hospital");
            athlete = new DataKeeper("High tech athlete");
        } catch (Exception e) {e.printStackTrace();}
        List<DataKeeper> dk = new ArrayList<DataKeeper>();
        dk.add(master);
        dk.add(hospital);
        dk.add(athlete);

        List<Policy> policies;
        List<Record> records;
        Record[] r = {null, null, null, null, null};
        Policy p;

        for(int i=0; i<10; i++){
            try {
                r[0] = new Record(dk, ConsensusLevel.ALL, "ems-solutions.com",
                        "blog/your-medical-record-is-worth-more-to-hackers-than-your-credit-card/", master.getPrivateKey());
                r[1] = new Record(dk, ConsensusLevel.ALL, "ems-solutions.com",
                        "blog/your-medical-record-is-worth-more-to-hackers-than-your-credit-card/", master.getPrivateKey());
            }catch (Exception e) {e.printStackTrace();}
        }
        Blockchain blockchain = new Blockchain(3, ConsensusLevel.ALL);
//        blockchain.addBlock(blockchain.newBlock(0, "");
//        blockchain.addBlock(blockchain.newBlock("Sylvain Saurel"));
//        blockchain.addBlock(blockchain.newBlock("Kevin Barba"));

        System.out.println("blockchain.Blockchain valid ? " + blockchain.isBlockChainValid());
        System.out.println(blockchain);
    }
}
