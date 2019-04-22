package accessControl;

import java.util.UUID;

public class Entity {

    protected UUID id;
    protected  String name;

    public Entity(String name){
        this.id = UUID.randomUUID();
        this.name = name;
    }

    public UUID getId(){
        return id;
    }
}
