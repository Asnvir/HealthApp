package superapp.boundary;

import superapp.core.UserRole;
import superapp.entity.UserEntity;
import superapp.core.UserId;

public class UserBoundary {
    private UserId userId;

    private UserRole role;

    private String username;
    private String avatar;

    public UserBoundary(UserEntity userEntity) {
        this.userId = userEntity.getUserId();

    }

    public UserId getUserId() {
        return userId;
    }

    public void setUserId(UserId userId) {
        this.userId = userId;
    }


    public UserEntity toEntity() {
        UserEntity userEntity = new UserEntity();
        userEntity.setUserId(userId);
        userEntity.setUserName(username);
        userEntity.setAvatar(avatar);
        return userEntity;
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
}
