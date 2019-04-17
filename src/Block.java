import java.security.*;
import java.util.Base64;
import java.util.Date;
import java.util.ArrayList;
import java.util.List;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.Serializable;
import java.io.FileOutputStream;
import java.io.IOException;


public class Block{
    private int index;
    private long timestamp;
    private String hash;
    private String previousHash;
    private String data;
    private int nonce;
    private SignedObject blockSigned;


    public Block(int index, long timestamp, String previousHash, String data, PrivateKey prikey) throws NoSuchAlgorithmException, InvalidKeyException, IOException, SignatureException {
        this.index = index;
        this.timestamp = timestamp;
        this.previousHash = previousHash;
        this.data = data;
        nonce = 0;
        hash = BlockHash.calculateHash(this);
        this.blockSigned = new SignedObject(hash, prikey, Signature.getInstance("SHA256withRSA") );
    }

    public String getSignature(){
        return Base64.getEncoder().encodeToString(blockSigned.getSignature());
    }

    public String str() {
        return index + timestamp + previousHash + data + nonce;
    }

    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("Block #").append(index).append(" [previousHash: ").append(previousHash).append(", ").
                append("timestamp : ").append(new Date(timestamp)).append(", ").append("data : ").append(data).append(", ").
                append("hash : ").append(hash).append("]");
        return builder.toString();
    }


    public void mineBlock(int difficulty){
        nonce = 0;
        while(!getHash().substring(0, difficulty).equals(zeros(difficulty))){
            nonce++;
            hash = BlockHash.calculateHash(this);
        }
    }

    public String zeros(int zeros){
        String zerosString = new String();
        for( int i = 0; i < zeros; i++)
            zerosString += '0';
        return zerosString;
    }

    public String getData() {
        return data;
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
