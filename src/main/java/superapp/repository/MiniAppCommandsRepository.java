package superapp.repository;

import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

import reactor.core.publisher.Flux;
import superapp.entity.command.CommandId;
import superapp.entity.command.MiniAppCommandEntity;
import superapp.entity.user.UserId;

@Repository
public interface MiniAppCommandsRepository extends
        ReactiveMongoRepository<MiniAppCommandEntity, CommandId> {
    @Query(value = "{ 'commandId.miniapp' : ?0 } " ) //find all commands by miniapp
    public Flux<MiniAppCommandEntity> findAllByMiniApp(String miniapp);
}
