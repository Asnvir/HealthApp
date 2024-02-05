package superapp.entity.command;

import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CommandId {
    private String superapp;
    @JsonProperty("miniapp")
    private String miniApp;
    @JsonProperty("id")
    private String id;

    public CommandId() {
    }

    public CommandId(String superapp, String miniApp) {
        this.superapp = superapp;
        this.miniApp = miniApp;
        this.id = UUID.randomUUID() + "";
    }

    public String getSuperapp() {
        return superapp;
    }

    public void setSuperapp(String superapp) {
        this.superapp = superapp;
    }

    public String getMiniApp() {
        return miniApp;
    }

    public void setMiniApp(String miniApp) {
        this.miniApp = miniApp;
    }

    @Override
    public String toString() {
        return "CommandId ["
                + "superapp=" + superapp
                + ", miniApp=" + miniApp
                + ", id=" + id
                + "]";
    }

    public void setID(String newID) {
        this.id = newID;
    }

}
