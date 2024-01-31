package superapp.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

import superapp.entity.command.CommandId;
import superapp.entity.command.MiniAppCommandEntity;
import superapp.entity.user.UserId;

@Repository
public interface MiniAppCommandsRepository extends
        ReactiveMongoRepository<MiniAppCommandEntity, CommandId> {
}
