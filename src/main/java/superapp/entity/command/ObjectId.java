package superapp.entity.command;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ObjectId {
    private String superapp;
    @JsonProperty("id")
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
    
    public void setID(String newID) {
        this.id = newID;
    }  

    public void setSuperapp(String superapp) {
        this.superapp = superapp;
    }    
    public String getID() {
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
