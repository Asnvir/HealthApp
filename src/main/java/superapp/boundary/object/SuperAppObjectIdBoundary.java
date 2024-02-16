package superapp.boundary.object;


public class SuperAppObjectIdBoundary {
    private String superapp;


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
