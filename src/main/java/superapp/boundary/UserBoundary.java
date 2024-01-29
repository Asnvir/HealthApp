package superapp.boundary;

import superapp.entity.user.UserRole;
import superapp.entity.user.UserEntity;
import superapp.entity.user.UserId;

public class UserBoundary {
    private UserId userId;
    private UserRole role;
    private String userName;
    private String avatar;

    private UserBoundary(Builder builder) {
        this.userId = builder.userId;
        this.role = builder.role;
        this.userName = builder.userName;
        this.avatar = builder.avatar;
    }

    public UserBoundary(UserEntity entity) {
        this.userId = entity.getUserId();
        this.role = entity.getRole();
        this.userName = entity.getUserName();
        this.avatar = entity.getAvatar();
    }

    public UserBoundary(UserId userId, UserRole role, String userName, String avatar) {
        this.userId = userId;
        this.role = role;
        this.userName = userName;
        this.avatar = avatar;
    }


    public UserId getUserId() {
        return userId;
    }

    public UserRole getRole() {
        return role;
    }

    public String getUserName() {
        return userName;
    }

    public String getAvatar() {
        return avatar;
    }


    public UserEntity toEntity(String applicationName) {
        return new UserEntity.Builder()
                .withUserId(new UserId(applicationName, userName))
                .withUserName(userName)
                .withAvatar(avatar)
                .build();
    }

//    public static UserBoundary fromEntity(UserEntity userEntity) {
//        UserId userId = userEntity.getUserId();
//        UserRole role = userEntity.getRole();
//        String userName = userEntity.getUserName();
//        String avatar = userEntity.getAvatar();
//
//        return new UserBoundary(userId, role, userName, avatar);
//    }


    @Override
    public String toString() {
        return "UserBoundary{" +
                "userId=" + userId +
                ", role=" + role +
                ", username='" + userName + '\'' +
                ", avatar='" + avatar + '\'' +
                '}';
    }


    public static class Builder {
        private UserId userId;
        private UserRole role;
        private String userName;
        private String avatar;

        public Builder() {
        }

        public Builder withUserId(UserId userId) {
            this.userId = userId;
            return this;
        }

        public Builder withRole(UserRole role) {
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
