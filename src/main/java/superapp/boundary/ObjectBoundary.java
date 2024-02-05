package superapp.boundary;

import java.util.Date;

import superapp.entity.object.CreatedBy;
import superapp.entity.object.ObjectDetails;
import superapp.entity.object.ObjectEntity;
import superapp.entity.common.ObjectId;

public class ObjectBoundary {
    private ObjectId objectId;
    private String type;
    private String alias;
    private Boolean active;
    private CreatedBy createdBy;
    private Date creationTimestamp;
    private ObjectDetails objectDetails;

    public ObjectBoundary() {
    }

    public ObjectBoundary(ObjectId objectId, String type, String alias, Boolean active, CreatedBy createdBy,
                          Date creationTimestamp, ObjectDetails objectDetails) {
        super();
        this.objectId = objectId;
        this.type = type;
        this.alias = alias;
        this.active = active;
        this.createdBy = createdBy;
        this.creationTimestamp = creationTimestamp;
        this.objectDetails = objectDetails;
    }

    public ObjectBoundary(ObjectEntity entity) {
        this.objectId = entity.getObjectId();
        this.type = entity.getType();
        this.alias = entity.getAlias();
        this.active = entity.getActive();
        this.createdBy = entity.getCreatedBy();
        this.creationTimestamp = entity.getCreationTimestamp();
        this.objectDetails = entity.getObjectDetails();
    }

    public ObjectId getObjectId() {
        return objectId;
    }

    public void setObjectId(ObjectId objectId) {
        this.objectId = objectId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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

    public Date getCreationTimestamp() {
        return creationTimestamp;
    }

    public void setCreationTimestamp(Date creationTimestamp) {
        this.creationTimestamp = creationTimestamp;
    }

    public ObjectDetails getObjectDetails() {
        return objectDetails;
    }

    public void setObjectDetails(ObjectDetails objectDetails) {
        this.objectDetails = objectDetails;
    }

    public ObjectEntity toEntity() {
        ObjectEntity entity = new ObjectEntity();
        entity.setObjectId(this.getObjectId());
        entity.setType(this.type);
        entity.setAlias(this.alias);
        entity.setActive(this.active);
        entity.setCreatedBy(this.createdBy);
        entity.setCreationTimestamp(this.creationTimestamp);
        entity.setObjectDetails(this.objectDetails);
        return entity;
    }

    @Override
    public String toString() {
        return "ObjectBoundary [objectId=" + objectId
                + ", type=" + type
                + ", alias=" + alias
                + ", active=" + active
                + ", createdBy=" + createdBy
                + ", creationTimestamp=" + creationTimestamp
                + ", objectDetails=" + objectDetails + "]";
    }
}
