package superapp.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import superapp.entity.user.UserId;
import superapp.entity.command.MiniappCommandEntity;

@Repository
public interface MiniAppCommandsRepository extends
        ReactiveMongoRepository<MiniappCommandEntity, UserId> {
}
