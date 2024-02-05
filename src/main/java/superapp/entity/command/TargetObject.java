package superapp.entity.command;

import superapp.entity.common.ObjectId;

import java.io.Serializable;

public class TargetObject implements Serializable {
	ObjectId objectId;

	public TargetObject() {
	}

	public TargetObject(ObjectId objectId) {
		this.objectId = objectId;
	}

	public ObjectId getObjectId() {
		return objectId;
	}

	public void setObjectId(ObjectId objectId) {
		this.objectId = objectId;
	}

	@Override
	public String toString() {
		return "TargetObject ["
				+ "objectId=" + objectId.toString()
				+ "]";
	}

}