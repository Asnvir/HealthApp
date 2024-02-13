package superapp.boundary;

import superapp.entity.user.UserId;
import superapp.entity.user.UserEntity;

public class UserBoundary {
    private UserId userId;
    private String role;
    private String username;
    private String avatar;

    private UserBoundary(Builder builder) {
        this.userId = builder.userId;
        this.role = builder.role;
        this.username = builder.userName;
        this.avatar = builder.avatar;
    }

    public UserBoundary(UserEntity entity) {
        this.userId = entity.getUserId();
        this.role = entity.getRole().toString();
        this.username = entity.getUserName();
        this.avatar = entity.getAvatar();
    }

    public UserBoundary(UserId userId, String role, String username, String avatar) {
        this.userId = userId;
        this.role = role;
        this.username = username;
        this.avatar = avatar;
    }

    public UserBoundary() {
    }


    public UserId getUserId() {
        return userId;
    }

    public void setUserId(UserId boundaryUserId) {
        this.userId = boundaryUserId;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }


    public UserEntity toEntity(String applicationName) {
        return new UserEntity.Builder()
                .withUserId(new UserId(applicationName, username))
                .withUserName(username)
                .withAvatar(avatar)
                .build();
    }


    @Override
    public String toString() {
        return "UserBoundary{" +
                "userId=" + userId +
                ", role=" + role +
                ", username='" + username + '\'' +
                ", avatar='" + avatar + '\'' +
                '}';
    }


    public static class Builder {
        private UserId userId;
        private String role;
        private String userName;
        private String avatar;

        public Builder() {
        }

        public Builder withUserId(UserId boundaryUserId) {
            this.userId = boundaryUserId;
            return this;
        }

        public Builder withRole(String role) {
            this.role = role;
            return this;
        }

        public Builder withUserName(String userName) {
            this.userName = userName;
            return this;
        }

        public Builder withAvatar(String avatar) {
            this.avatar = avatar;
            return this;
        }

        public UserBoundary build() {
            return new UserBoundary(this);
        }
    }
}
