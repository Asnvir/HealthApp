package superapp.entity.user;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

import static superapp.common.Consts.OBJECT_CREATED_BY_USERID_EMAIL;
import static superapp.common.Consts.OBJECT_CREATED_BY_USERID_SUPERAPP;

public class UserId implements Serializable {
    @JsonProperty(OBJECT_CREATED_BY_USERID_SUPERAPP)
    private String superapp;
    @JsonProperty(OBJECT_CREATED_BY_USERID_EMAIL)
    private String email;

    public UserId() {
    }

    public UserId(String superapp, String email) {
        this.superapp = superapp;
        this.email = email;
    }

    public String getSuperapp() {
        return superapp;
    }

    public void setSuperapp(String superapp) {
        this.superapp = superapp;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    @Override
    public String toString() {
        return "UserId ["
                + "superapp=" + superapp
                + ", email=" + email
                + "]";
    }
}
