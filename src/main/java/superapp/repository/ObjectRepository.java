package superapp.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import superapp.entity.object.ObjectEntity;
import superapp.entity.object.ObjectId;



@Repository
public interface ObjectRepository extends
	ReactiveMongoRepository<ObjectEntity, ObjectId> {

	public Flux<ObjectEntity> findByType(@Param("type")String type);

	public Flux<ObjectEntity> findByAlias(@Param("alias")String alias);

}
