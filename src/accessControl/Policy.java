package accessControl;

import blockchain.PermissionLevel;
import blockchain.ThirdEntity;

import java.util.UUID;

public class Policy {

    private ThirdEntity entity;
    private Record record;
    private UUID id;
    private PermissionLevel level;

    public Policy(ThirdEntity entity, Record record, UUID id, PermissionLevel level) {
        this.entity = entity;
        this.record = record;
        this.id = id;
        this.level = level;
    }

    public ThirdEntity getEntity() {
        return entity;
    }

    public Record getRecord() {
        return record;
    }

    public UUID getId() {
        return id;
    }

    public PermissionLevel getLevel() {
        return level;
    }
}