package superapp.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import superapp.core.UserId;

import java.util.Objects;

@Document(collection = "USERS")
public class UserEntity {
    @Id
    private UserId userId;
    private String userName;
    private String avatar;

    public UserEntity() {
    }


    public  UserId getUserId() {
        return userId;
    }

    public void setUserId(UserId userId) {
        this.userId = userId;
    }
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
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
        result = 31 * result;
        result = 31 * result + (userName != null ? userName.hashCode() : 0);
        result = 31 * result;
        result = 31 * result + (avatar != null ? avatar.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "UserEntity{" +
            "userId=" + userId +
            ", userName='" + userName + '\'' +
            ", avatar='" + avatar + '\'' +
            '}';
    }

}
