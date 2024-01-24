package superapp.boundary;

import superapp.entity.user.UserId;
import superapp.entity.user.UserRole;
import superapp.entity.user.UserEntity;

public class NewUserBoundary {
    private String email;
    private String role;
    private String userName;
    private String avatar;


    public NewUserBoundary() {
    }

    public NewUserBoundary(String email, String role, String userName, String avatar) {
        super();
        this.email = email;
        this.role = role;
        this.userName = userName;
        this.avatar = avatar;
    }

    public String getEmail() {
        return email;
    }

    public String getRole() {
        return role;
    }


    public String getUserName() {
        return userName;
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

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public UserEntity toEntity(String applicationName) {
        return new UserEntity.Builder()
                .withUserId(new UserId(applicationName, this.email))
                .withRole(UserRole.valueOf(this.role))
                .withAvatar(this.avatar)
                .withUserName(this.userName)
                .build();

    }

    @Override
    public String toString() {
        return "NewUserBoundary{" +
                "email='" + email + '\'' +
                ", role=" + role +
                ", userName='" + userName + '\'' +
                ", avatar='" + avatar + '\'' +
                '}';
    }

}
