package superapp.entity.user;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Objects;

@Document(collection = "USERS")
public class UserEntity {
    @Id
    private UserId userId;
    private String userName;
    private String avatar;

    private UserRole role;

    private UserEntity(Builder builder) {
        this.userId = builder.userId;
        this.userName = builder.userName;
        this.avatar = builder.avatar;
        this.role = builder.role;
    }

    public UserEntity() {
    }

    public UserEntity(UserId userId, String userName, String avatar, UserRole role) {
        this.userId = userId;
        this.userName = userName;
        this.avatar = avatar;
        this.role = role;
    }

    public UserId getUserId() {
        return userId;
    }

    public String getUserName() {
        return userName;
    }

    public String getAvatar() {
        return avatar;
    }

    public UserRole getRole() {
        return role;
    }

    public void setUserId(UserId boundaryUserId) {
        this.userId = boundaryUserId;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserEntity that)) return false;

        if (!Objects.equals(userId, that.userId)) return false;
        if (!Objects.equals(userName, that.userName)) return false;
        return Objects.equals(avatar, that.avatar);
    }

    @Override
    public int hashCode() {
        int result = userId != null ? userId.hashCode() : 0;
        result = 31 * result + (userName != null ? userName.hashCode() : 0);
        result = 31 * result + (avatar != null ? avatar.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "UserEntity{" +
                "userId=" + userId +
                ", userName='" + userName + '\'' +
                ", avatar='" + avatar + '\'' +
                ", role=" + role +
                '}';
    }

    // Static inner Builder class
    public static class Builder {
        private UserId userId;
        private String userName;
        private String avatar;
        private UserRole role;

        public Builder withUserId(UserId userId) {
            this.userId = userId;
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

        public Builder withRole(UserRole role) {
            this.role = role;
            return this;
        }

        public UserEntity build() {
            return new UserEntity(this);
        }
    }
}
