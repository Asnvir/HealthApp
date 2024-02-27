package superapp.entity.object;

import org.springframework.data.annotation.Id;

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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "UserId ["
                + "superapp=" + superapp
                + ", Id=" + id
                + "]";
    }
}
