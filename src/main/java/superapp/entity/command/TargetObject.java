package superapp.entity.command;

import com.fasterxml.jackson.annotation.JsonProperty;
import superapp.entity.object.ObjectId;

import java.io.Serializable;

import static superapp.common.Consts.COMMAND_TARGETOBJECT_OBJECTID;

public class TargetObject implements Serializable {
	@JsonProperty(COMMAND_TARGETOBJECT_OBJECTID)
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