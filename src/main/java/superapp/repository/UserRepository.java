package superapp.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;
import superapp.entity.user.UserId;
import superapp.entity.user.UserEntity;

@Repository
public interface UserRepository extends
        ReactiveMongoRepository<UserEntity, UserId> {

    public Mono<Void> deleteByUserId(UserId userId);

    public Mono<UserEntity> findByUserId(UserId userId);
}
