package superapp.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import superapp.entity.user.UserId;
import superapp.entity.user.UserEntity;

@Repository
public interface UserRepository extends
        ReactiveMongoRepository<UserEntity, UserId> {

}
