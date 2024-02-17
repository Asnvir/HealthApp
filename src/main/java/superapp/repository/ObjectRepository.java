package superapp.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import superapp.entity.object.SuperAppObjectEntity;
import superapp.boundary.object.SuperAppObjectBoundary;
import superapp.entity.object.ObjectId;



@Repository
public interface ObjectRepository extends
	ReactiveMongoRepository<SuperAppObjectEntity, ObjectId> {

	public Flux<SuperAppObjectEntity> findByType(@Param("type")String type);

	public Flux<SuperAppObjectEntity> findByAlias(@Param("alias")String alias);

	public Flux<SuperAppObjectEntity> findByAliasRegex(@Param("pattern")String pattern);



}
