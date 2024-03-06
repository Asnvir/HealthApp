package superapp.entity.object;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.data.annotation.Id;

import java.io.Serializable;

import static superapp.common.Consts.COMMAND_TARGETOBJECT_OBJECTID_ID;
import static superapp.common.Consts.COMMAND_TARGETOBJECT_OBJECTID_SUPERAPP;

public class ObjectId implements Serializable {
    @JsonProperty(COMMAND_TARGETOBJECT_OBJECTID_SUPERAPP)
	private String superapp;
    @JsonProperty(COMMAND_TARGETOBJECT_OBJECTID_ID)
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
