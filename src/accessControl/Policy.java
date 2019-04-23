package accessControl;

import blockchain.PermissionLevel;

import java.security.PrivateKey;
import java.util.UUID;

public class Policy {

    private ThirdEntity entity;
    private Record record;
    private UUID id;
    private PermissionLevel level;
    private PrivateKey accessKey;

    public Policy(ThirdEntity entity, Record record, PermissionLevel level) {
        this.entity = entity;
        this.record = record;
        this.id = UUID.randomUUID();
        this.level = level;
    }

    public ThirdEntity getEntity() {
        return entity;
    }

    public Record getRecord() throws NotEnoughPermission {
        if(level == PermissionLevel.WRITE){
            throw new NotEnoughPermission();
        }
        return this.record;
    }

    public void setRecord(Record record) throws NotEnoughPermission {
        if(level == PermissionLevel.READ) {
            throw new NotEnoughPermission();
        }
            this.record = record;
    }

    public UUID getId() {
        return id;
    }

    public PermissionLevel getLevel() {
        return level;
    }

    public class NotEnoughPermission extends Exception {
        public NotEnoughPermission(){
            super("Bad permission level");
        }
    }
}
