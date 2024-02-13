package superapp.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import superapp.entity.object.ObjectEntity;
import superapp.entity.object.ObjectId;



@Repository
public interface ObjectRepository extends
	ReactiveMongoRepository<ObjectEntity, ObjectId> {

}
