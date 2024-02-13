package superapp.entity.command;


public class CommandId {
    private String superapp;

    private String miniApp;

    private String id;

    public CommandId() {
    }

    public CommandId(String superapp, String miniApp, String id) {
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
