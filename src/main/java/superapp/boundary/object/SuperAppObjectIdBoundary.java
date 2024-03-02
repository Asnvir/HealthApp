package superapp.boundary.object;


import com.fasterxml.jackson.annotation.JsonProperty;

import static superapp.common.Consts.OBJECT_ID_ID;
import static superapp.common.Consts.OBJECT_ID_SUPERAPP;

public class SuperAppObjectIdBoundary {
    @JsonProperty(OBJECT_ID_SUPERAPP)
    private String superapp;

    @JsonProperty(OBJECT_ID_ID)
    private String id;

    public SuperAppObjectIdBoundary() {
    }

    public SuperAppObjectIdBoundary(String superapp, String id) {
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
