package accessControl;

import java.util.UUID;

public class Entity {

    protected UUID id;
    protected  String name;

    public Entity( UUID id, String name){
        this.id = id;
        this.name = name;
    }

    public Entity(String name) {
        this.name = name;
    }

    public UUID getId(){
        return id;
    }
}
