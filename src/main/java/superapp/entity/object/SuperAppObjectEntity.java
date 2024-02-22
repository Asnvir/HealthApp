package superapp.entity.object;

import java.util.Date;
import java.util.Map;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


@Document(collection = "OBJECTS")
public class SuperAppObjectEntity {
@Id private ObjectId objectId;
	private String type;
    private String alias;
    private Boolean active;
    private CreatedBy createdBy;
    private Date creationTimestamp;
	private Map<String, Object> objectDetails;
    
	public ObjectId getObjectId() {
		return objectId;
	}
	public void setObjectId(ObjectId objectId) {
		this.objectId = objectId;
	}
	public String getAlias() {
		return alias;
	}
	public void setAlias(String alias) {
		this.alias = alias;
	}
	public Boolean getActive() {
		return active;
	}
	public void setActive(Boolean active) {
		this.active = active;
	}
	public CreatedBy getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(CreatedBy createdBy) {
		this.createdBy = createdBy;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public Date getCreationTimestamp() {
		return creationTimestamp;
	}
	public void setCreationTimestamp(Date creationTimestamp) {
		this.creationTimestamp = creationTimestamp;
	}
	public Map<String, Object>  getObjectDetails() {
		return objectDetails;
	}
	public void setObjectDetails(Map<String, Object> objectDetails) {
		this.objectDetails = objectDetails;
	}
	@Override
	public String toString() {
		return "ObjectEntity [objectId=" + objectId
				+ ", type=" + type
				+ ", alias=" + alias
				+ ", active=" + active
				+ ", createdBy=" + createdBy
				+ ", creationTimestamp=" + creationTimestamp
				+ ", objectDetails="
				+ objectDetails + "]";
	}  
}
