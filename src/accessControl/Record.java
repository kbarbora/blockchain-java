package accessControl;

import blockchain.ConsensusLevel;

import java.security.*;
import java.util.List;
import java.util.ListIterator;
import java.util.UUID;

public class Record {

    private List<DataKeeper> keepers;
    private UUID id;
    private ConsensusLevel consensus;
    private String hash;
    private String location;
    private String pathFile;
    private byte[] signature;
    private long signTimestamp;
    private String data;

    public Record(List<DataKeeper> list, ConsensusLevel consensus, String location, String pathFile, String data, PrivateKey prk) throws NoSuchAlgorithmException, InvalidKeyException, SignatureException{
        keepers = list;
        this.id = UUID.randomUUID();
        this.consensus = consensus;
        this.location = location;
        this.pathFile = pathFile;
        this.hash = calculateHash(str());
        this.signature = generateSignature(prk);
        this.data = data;
    }

    private static String calculateHash(String toHash) throws NoSuchAlgorithmException{
            MessageDigest digest;
            digest = MessageDigest.getInstance("SHA-256");
            final byte bytes[] = digest.digest(toHash.getBytes());
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

    public static String getListHash(List<Record> records) throws NoSuchAlgorithmException {
        String hashes = "";
        ListIterator<Record> iter = records.listIterator();
        while(iter.hasNext())
            hashes += iter.next().getHash();
        return calculateHash(hashes);
    }

    private String str(){
        return location + pathFile + id.toString() + signTimestamp;
    }

    public void addDataKeeper(DataKeeper keeper){
        keepers.add(keeper);
    }

    public String getPathFile() {
        return pathFile;
    }

    public byte[] generateSignature(PrivateKey prk) throws NoSuchAlgorithmException, InvalidKeyException, SignatureException {
        signTimestamp = System.currentTimeMillis();
        Signature rsa = Signature.getInstance("SHA256withRSA");
        rsa.initSign(prk);
        rsa.update(calculateHash(str()).getBytes());
        return rsa.sign();
    }

    public String getData() {
        return data;
    }

    public String getHash() {
        return hash;
    }

    public List<DataKeeper> getKeepers() {
        return keepers;
    }

    public ConsensusLevel getConsensus() {
        return consensus;
    }

    public UUID getId() {
        return id;
    }

    public void setData(String s) {
        this.data = s;
    }
}
