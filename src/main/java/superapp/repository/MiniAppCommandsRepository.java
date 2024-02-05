package superapp.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

import reactor.core.publisher.Flux;
import superapp.entity.command.CommandId;
import superapp.entity.command.MiniAppCommandEntity;


@Repository
public interface MiniAppCommandsRepository extends
        ReactiveMongoRepository<MiniAppCommandEntity, CommandId> {

    public Flux<MiniAppCommandEntity> findByCommandIdMiniApp(String miniapp);
}
