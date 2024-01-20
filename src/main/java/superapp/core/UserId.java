package superapp.core;

public class UserId {
    private String superapp;
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
    public int hashCode() {
        final int prime = 31;
        int result = 1;

        result = prime * result + ((superapp == null) ? 0 : superapp.hashCode());
        result = prime * result + ((email == null) ? 0 : email.hashCode());

        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;

        if (obj == null) return false;

        if (getClass() != obj.getClass()) return false;

        UserId other = (UserId) obj;

        if (superapp == null) {
            if (other.superapp != null) return false;
        } else if (!superapp.equals(other.superapp)) return false;

        if (email == null) {
            if (other.email != null) return false;
        } else if (!email.equals(other.email)) return false;

        return true;
    }
    @Override
    public String toString() {
        return "UserId ["
            + "superapp=" + superapp
            + ", email=" + email
            + "]";
    }
}
