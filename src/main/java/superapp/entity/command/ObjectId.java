package superapp.entity.command;

import java.io.Serializable;

public class ObjectId implements Serializable {
    private String superapp;
    private String id;

    public ObjectId() {
    }

    public ObjectId(String superapp, String id) {
        this.superapp = superapp;
        this.id = id;
    }

    public String getSuperapp() {
        return superapp;
    }

    public void setSuperapp(String superapp) {
        this.superapp = superapp;
    }    
    public String getCommandID() {
        return id;
    }


    @Override
    public String toString() {
        return "ObjectId ["
                + "superapp=" + superapp
                + ", id=" + id
                + "]";
    }
}
