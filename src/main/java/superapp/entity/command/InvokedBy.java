package superapp.entity.command;


import superapp.entity.user.UserId;

public class InvokedBy{
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
