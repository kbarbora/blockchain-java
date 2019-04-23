package blockchain;

import accessControl.Policy;
import accessControl.Record;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.SignatureException;
import java.util.ArrayList;
import java.util.List;

public class Blockchain {

    private int difficulty;
    private List<Block> blocks;
    private ConsensusLevel consensusLevel;

    public Blockchain(int difficulty, ConsensusLevel consensus) {
        this.difficulty = difficulty;
        this.consensusLevel = consensus;

        blocks = new ArrayList<>();
        // create the first block
        Block b = new Block(0, null);
        b.mineBlock(difficulty);
        blocks.add(b);
    }

    public int getDifficulty() {
        return difficulty;
    }

    public Block latestBlock() {
        return blocks.get(blocks.size() - 1);
    }

    public Block newBlock(PrivateKey prikey, List<Record> records, List<Policy> policies) {
        Block latestBlock = latestBlock();
        Block block = null;
        try {
            block = new Block(latestBlock.getIndex() + 1, latestBlock.getHash(),
                    prikey, policies, records);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SignatureException e) {
            e.printStackTrace();
        }
        return block;
    }

    public void addBlock(Block b) {
        if (b != null) {
            b.mineBlock(difficulty);
            blocks.add(b);
        }
    }

    public boolean isFirstBlockValid() {
        Block firstBlock = blocks.get(0);

        if (firstBlock.getIndex() != 0) {
            return false;
        }

        if (firstBlock.getPreviousHash() != null) {
            return false;
        }

        if (firstBlock.getHash() == null ||
                !Block.calculateHash(firstBlock).equals(firstBlock.getHash())) {
            return false;
        }

        return true;
    }

    public boolean isValidNewBlock(Block newBlock, Block previousBlock) {
        if (newBlock != null  &&  previousBlock != null) {
            if (previousBlock.getIndex() + 1 != newBlock.getIndex()) {
                return false;
            }

            if (newBlock.getPreviousHash() == null  ||
                    !newBlock.getPreviousHash().equals(previousBlock.getHash())) {
                return false;
            }

            if (newBlock.getHash() == null  ||
                    !Block.calculateHash(newBlock).equals(newBlock.getHash())) {
                return false;
            }

            return true;
        }

        return false;
    }

    public boolean isBlockChainValid() {
        if (!isFirstBlockValid()) {
            return false;
        }

        for (int i = 1; i < blocks.size(); i++) {
            Block currentBlock = blocks.get(i);
            Block previousBlock = blocks.get(i - 1);

            if (!isValidNewBlock(currentBlock, previousBlock)) {
                return false;
            }
        }

        return true;
    }

    public String toString() {
        StringBuilder builder = new StringBuilder();

        for (Block block : blocks) {
            builder.append(block).append("\n");
        }

        return builder.toString();
    }

}