package superapp.boundary;

import superapp.entity.user.UserId;
import superapp.entity.user.UserRole;
import superapp.entity.user.UserEntity;

public class NewUserBoundary {
    private String email;
    private String role;
    private String username;
    private String avatar;


    public NewUserBoundary() {
    }

    public NewUserBoundary(String email, String role, String username, String avatar) {
        super();
        this.email = email;
        this.role = role;
        this.username = username;
        this.avatar = avatar;
    }

    public String getEmail() {
        return email;
    }

    public String getRole() {
        return role;
    }


    public String getUsername() {
        return username;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public UserEntity toEntity(String applicationName) {
        return new UserEntity.Builder()
                .withUserId(new UserId(applicationName, this.email))
                .withRole(UserRole.valueOf(this.role))
                .withAvatar(this.avatar)
                .withUserName(this.username)
                .build();

    }

    @Override
    public String toString() {
        return "NewUserBoundary{" +
                "email='" + email + '\'' +
                ", role=" + role +
                ", userName='" + username + '\'' +
                ", avatar='" + avatar + '\'' +
                '}';
    }

}
