package blockchain;

import accessControl.Policy;

import java.security.*;
import java.util.Base64;
import java.util.Date;
import java.io.IOException;
import java.util.List;
import accessControl.Record;


public class Block{
    private int index;
    private long timestamp;
    private String hash;
    private String previousHash;
    private List<Policy> policies;
    private List<Record> records;
    private int nonce;
    private SignedObject blockSigned;


    public Block(int index, String previousHash)
            throws NoSuchAlgorithmException, InvalidKeyException, IOException, SignatureException {
        this.index = index;
        this.timestamp = System.currentTimeMillis();
        this.previousHash = previousHash;
        nonce = 0;
        hash = calculateHash(this);
    }

    public Block(int index, String previousHash, PrivateKey prikey, List<Policy> policies)
            throws NoSuchAlgorithmException, InvalidKeyException, IOException, SignatureException {
        this.index = index;
        this.timestamp = System.currentTimeMillis();
        this.previousHash = previousHash;
        nonce = 0;
        hash = calculateHash(this);
        this.blockSigned = new SignedObject(hash, prikey, Signature.getInstance("SHA256withRSA") );
    }

    public Block(int index, String previousHash, PrivateKey prikey, List<Policy> policies, List<Record> records)
            throws NoSuchAlgorithmException, InvalidKeyException, IOException, SignatureException {
        this.index = index;
        this.timestamp = System.currentTimeMillis();
        this.previousHash = previousHash;
        nonce = 0;
        hash = calculateHash(this);
        this.blockSigned = new SignedObject(hash, prikey, Signature.getInstance("SHA256withRSA") );
    }

    public String getSignature(){
        return Base64.getEncoder().encodeToString(blockSigned.getSignature());
    }

    public static String calculateHash(Block block) {
        if (block != null) {
            MessageDigest digest = null;

            try {
                digest = MessageDigest.getInstance("SHA-256");
            } catch (NoSuchAlgorithmException e) {
                return null;
            }
            String txt = block.str();
            final byte bytes[] = digest.digest(txt.getBytes());
            final StringBuilder builder = new StringBuilder();
            for(final byte b : bytes) {
                String hex = Integer.toHexString(0xff & b);
                if(hex.length() == 1){
                    builder.append('0');
                }
                builder.append(hex);
            }
            return builder.toString();
        }
        return null;
    }

    public String str() { return index + timestamp + previousHash + nonce;
    }

    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("blockchain.Block #").append(index).append(" [previousHash: ").append(previousHash).append(", ").
                append("timestamp : ").append(new Date(timestamp)).append(", ").
                append("hash : ").append(hash).append("]");
        return builder.toString();
    }

    public void mineBlock(int difficulty) {
        nonce = 0;
        while (!getHash().substring(0,  difficulty).equals(zeros(difficulty))) {
            nonce++;
            hash = Block.calculateHash(this);
        }
    }


    public String zeros(int zeros){
        String zerosString = new String();
        for( int i = 0; i < zeros; i++)
            zerosString += '0';
        return zerosString;
    }

    public int getIndex() {
        return index;
    }

    public String getPreviousHash() {
        return previousHash;
    }

    public String getHash() {
        return hash;
    }

    public long getTimestamp() {
        return timestamp;
    }


}
