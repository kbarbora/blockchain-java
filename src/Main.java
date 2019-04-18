import blockchain.Blockchain;
import blockchain.ConsensusLevel;

public class Main {
    public static void main(String[] args) {
        Blockchain blockchain = new Blockchain(5, ConsensusLevel.ALL);
        blockchain.addBlock(blockchain.newBlock("Tout sur le Bitcoin"));
        blockchain.addBlock(blockchain.newBlock("Sylvain Saurel"));
        blockchain.addBlock(blockchain.newBlock("Kevin Barba"));

        System.out.println("blockchain.Blockchain valid ? " + blockchain.isBlockChainValid());
        System.out.println(blockchain);
    }
}
