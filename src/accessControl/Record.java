package accessControl;

import blockchain.ConsensusLevel;

import java.security.*;
import java.util.List;
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

    public Record(List<DataKeeper> list, ConsensusLevel consensus, String location, String pathFile, PrivateKey prk) throws NoSuchAlgorithmException, InvalidKeyException, SignatureException{
        keepers = list;
        this.id = UUID.randomUUID();
        this.consensus = consensus;
        this.location = location;
        this.pathFile = pathFile;
        this.hash = calculateHash();
        this.signature = generateSignature(prk);
    }

    private String calculateHash() throws NoSuchAlgorithmException{
        if (id != null) {
            MessageDigest digest = null;
                digest = MessageDigest.getInstance("SHA-256");

            String rec = str();
            final byte bytes[] = digest.digest(rec.getBytes());
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
        rsa.update(calculateHash().getBytes());
        return rsa.sign();
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
}
