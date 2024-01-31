package superapp.entity.command;

import java.io.Serializable;
import java.util.UUID;

public class CommandId implements Serializable {
    private String superapp;
    private String miniApp;
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
    
    public String getCommandID() {
        return id;
    }


    @Override
    public String toString() {
        return "CommandId ["
                + "superapp=" + superapp
                + ", miniApp=" + miniApp
                + ", id=" + id
                + "]";
    }
}
