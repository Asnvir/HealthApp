package superapp.boundary.command;

import com.fasterxml.jackson.annotation.JsonProperty;

import static superapp.common.Consts.*;

public class MiniAppCommandIdBoundary {
    @JsonProperty(COMMAND_ID_SUPERAPP)
    private String superapp;
    @JsonProperty(COMMAND_ID_MINIAPP)
    private String miniApp;
    @JsonProperty(COMMAND_ID_ID)
    private String id;

    public MiniAppCommandIdBoundary() {
    }

    public MiniAppCommandIdBoundary(String superapp, String miniApp, String id) {
        this.superapp = superapp;
        this.miniApp = miniApp;
        this.id = id;
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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
