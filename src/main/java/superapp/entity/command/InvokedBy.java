package superapp.entity.command;


import com.fasterxml.jackson.annotation.JsonProperty;
import superapp.entity.user.UserId;

import static superapp.common.Consts.COMMAND_INVOKEDBY_USERID;

public class InvokedBy{

	@JsonProperty(COMMAND_INVOKEDBY_USERID)
	private UserId userId;

	public InvokedBy() {
	}

	public InvokedBy(UserId userId) {
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
		return "InvokedBy ["
				+ this.userId.toString()
				+ "]";
	}

}
