package superapp.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import superapp.entity.UserEntity;

public interface UserCRUD extends
        ReactiveMongoRepository<UserEntity, String> {
}
