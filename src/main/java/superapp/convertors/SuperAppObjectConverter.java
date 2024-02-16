package superapp.convertors;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;
import superapp.boundary.object.SuperAppObjectBoundary;
import superapp.boundary.object.SuperAppObjectIdBoundary;
import superapp.entity.object.ObjectId;
import superapp.entity.object.SuperAppObjectEntity;

import java.util.Map;

@Component
public class SuperAppObjectConverter
{
    private ObjectMapper mapper;

    public SuperAppObjectConverter()
    {
        this.mapper = new ObjectMapper();
    }

    public SuperAppObjectEntity toEntity(SuperAppObjectBoundary boundary) {
        SuperAppObjectEntity entity = new SuperAppObjectEntity();
        ObjectId objectId = new ObjectId(boundary.getObjectId().getSuperapp(), boundary.getObjectId().getId());
        entity.setObjectId(objectId);
        entity.setType(boundary.getType());
        entity.setAlias(boundary.getAlias());
        entity.setActive(boundary.getActive());
        entity.setCreatedBy(boundary.getCreatedBy());
        entity.setCreationTimestamp(boundary.getCreationTimestamp());
        entity.setObjectDetails(detailsToString(boundary.getObjectDetails()));
        return entity;
    }


    public SuperAppObjectBoundary toBoundary(SuperAppObjectEntity entity) {
        SuperAppObjectBoundary boundary = new SuperAppObjectBoundary();
        SuperAppObjectIdBoundary objectId = new SuperAppObjectIdBoundary(entity.getObjectId().getSuperapp(), entity.getObjectId().getId());
        boundary.setObjectId(objectId);
        boundary.setType(entity.getType());
        boundary.setAlias(entity.getAlias());
        boundary.setActive(entity.getActive());
        boundary.setCreatedBy(entity.getCreatedBy());
        boundary.setCreationTimestamp(entity.getCreationTimestamp());
        boundary.setObjectDetails(stringToDetails(entity.getObjectDetails()));
        return boundary;
    }

    public String detailsToString(Map<String, Object> objectDetails) {
        try {
            return mapper.writeValueAsString(objectDetails);
        } catch (Exception e) {
            throw new IllegalArgumentException(e);
        }
    }

    public Map<String, Object> stringToDetails(String objectDetails) {
        try {
            return (Map<String, Object>) mapper.readValue(objectDetails, Map.class);
        } catch (Exception e) {
            throw new IllegalArgumentException(e);
        }
    }
}
