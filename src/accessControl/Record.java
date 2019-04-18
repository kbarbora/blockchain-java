package accessControl;

import blockchain.ConsensusLevel;

import java.util.List;
import java.util.UUID;
import java.io.File;

public class Record {

    private List<DataKeeper> keepers;
    private UUID id;
    private ConsensusLevel consensus;
    private String pointer;
    private String hash;
    private File filerecord;

    public Record(List<DataKeeper> list, UUID id, ConsensusLevel consensus, File pointer, String hash){
        keepers = list;
        this.id = id;
        this.consensus = consensus;
        this.filerecord = pointer;
        this.hash = hash;

    }

    public void addDataKeeper(DataKeeper keeper){
        keepers.add(keeper);
    }

    public String getPointer() {
        return pointer;
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
