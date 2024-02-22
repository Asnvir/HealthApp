package superapp.boundary.object;

import java.util.Date;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonProperty;
import superapp.entity.object.CreatedBy;
import superapp.entity.object.ObjectId;
import superapp.entity.object.SuperAppObjectEntity;

public class SuperAppObjectBoundary {
    private SuperAppObjectIdBoundary superAppObjectIdBoundary;

    @JsonProperty("type")
    private String type;
    private String alias;
    private Boolean active;
    private Date creationTimestamp;
    private CreatedBy createdBy;
    private Map<String, Object> objectDetails;

    public SuperAppObjectBoundary() {
    }

    public SuperAppObjectBoundary(SuperAppObjectIdBoundary objectId, String type, String alias, Boolean active, CreatedBy createdBy,
                                  Date creationTimestamp,  Map<String, Object> objectDetails) {
        super();
        this.superAppObjectIdBoundary = objectId;
        this.type = type;
        this.alias = alias;
        this.active = active;
        this.createdBy = createdBy;
        this.creationTimestamp = creationTimestamp;
        this.objectDetails = objectDetails;
    }

    public SuperAppObjectBoundary(SuperAppObjectEntity superAppObjectEntity){
        super();
        this.superAppObjectIdBoundary = new SuperAppObjectIdBoundary(superAppObjectEntity.getObjectId().getSuperapp(), superAppObjectEntity.getObjectId().getId());
        this.type = superAppObjectEntity.getType();
        this.alias = superAppObjectEntity.getAlias();
        this.active = superAppObjectEntity.getActive();
        this.createdBy = superAppObjectEntity.getCreatedBy();
        this.creationTimestamp = superAppObjectEntity.getCreationTimestamp();
        this.objectDetails = superAppObjectEntity.getObjectDetails();
    }

    public SuperAppObjectEntity toEntity(){
        SuperAppObjectEntity entity = new SuperAppObjectEntity();
        entity.setObjectId(new ObjectId(this.superAppObjectIdBoundary.getSuperapp(), this.superAppObjectIdBoundary.getId()));
        entity.setType(this.type);
        entity.setAlias(this.alias);
        entity.setActive(this.active);
        entity.setCreatedBy(this.createdBy);
        entity.setCreationTimestamp(this.creationTimestamp);
        entity.setObjectDetails(this.objectDetails);
        return entity;
    }


    public SuperAppObjectIdBoundary getObjectId() {
        return superAppObjectIdBoundary;
    }

    public void setObjectId(SuperAppObjectIdBoundary objectId) {
        this.superAppObjectIdBoundary = objectId;
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

    public Map<String, Object> getObjectDetails() {
        return objectDetails;
    }

    public void setObjectDetails( Map<String, Object> objectDetails) {
        this.objectDetails = objectDetails;
    }

    @Override
    public String toString() {
        return "ObjectBoundary [objectId=" + superAppObjectIdBoundary
                + ", type=" + type
                + ", alias=" + alias
                + ", active=" + active
                + ", createdBy=" + createdBy
                + ", creationTimestamp=" + creationTimestamp
                + ", objectDetails=" + objectDetails + "]";
    }
}
