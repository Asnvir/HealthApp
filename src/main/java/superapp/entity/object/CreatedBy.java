package superapp.entity.object;


import com.fasterxml.jackson.annotation.JsonProperty;
import superapp.entity.user.UserId;

import static superapp.common.Consts.OBJECT_CREATED_BY_USERID;

public class CreatedBy {
	@JsonProperty(OBJECT_CREATED_BY_USERID)
	private UserId userId;

	
	public CreatedBy() {
	}
	
	public CreatedBy(UserId userId) {
		super();
		this.userId = userId;
	}

	public UserId getUserId() {
		return userId;
	}

	public void setUserId(UserId userId) {
		this.userId = userId;
	}

	@Override
	public String toString() {
		return "CreatedBy [userId=" + userId + "]";
	}
	
}
