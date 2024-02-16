package superapp.service.impl;

import java.util.Date;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import superapp.boundary.ObjectBoundary;
import superapp.entity.object.ObjectEntity;
import superapp.entity.object.ObjectId;
import superapp.exception.NotFoundException;
import superapp.repository.ObjectRepository;
import superapp.service.ObjectService;

import static ch.qos.logback.core.util.OptionHelper.isNullOrEmpty;
import static superapp.common.Consts.APPLICATION_NAME;

@Service
public class ObjectServiceImpl implements ObjectService {
	private static final Logger logger = LoggerFactory.getLogger(ObjectServiceImpl.class);
	private ObjectRepository objectRep;

	@Autowired
	private Environment environment;
	
	public ObjectServiceImpl(ObjectRepository objectRep) {
		super();
		this.objectRep = objectRep;
	}

	@Override
	public Mono<ObjectBoundary> create(ObjectBoundary object) {
		logger.info("Creating object {}", object);
		validateObject(object);
		object.setObjectId(new ObjectId(environment.getProperty(APPLICATION_NAME), UUID.randomUUID().toString()));
		object.setCreationTimestamp(new Date());
		return this.objectRep
				.save(object.toEntity())
				.map(ObjectBoundary::new)
				.log();
	}

	@Override
	public Mono<Void> update(String superApp, ObjectBoundary objectToUpdate, String id) {
		ObjectId objectId = new ObjectId(superApp,id);
		validateObject(objectToUpdate);
		return this.objectRep
				.findById(objectId)
				.switchIfEmpty(Mono.error(new NotFoundException(String.format("Object with %s not found", objectId))))
				.flatMap(entity-> updateObjectEntity(entity, objectToUpdate))
				.flatMap(objectRep::save)
				.doOnSuccess(unused -> logger.info("Object details updated for userId: {}", objectId))
				.log()
				.then();
	}
	
	 private Mono<ObjectEntity> updateObjectEntity(ObjectEntity objectEntity,
			 ObjectBoundary objectToUpdate) {
	        objectEntity.setType(objectToUpdate.getType());
	        objectEntity.setObjectDetails(objectToUpdate.getObjectDetails());
	        objectEntity.setAlias(objectToUpdate.getAlias());
	        objectEntity.setActive(objectToUpdate.getActive());
	        return Mono.just(objectEntity).log();
	    }

	@Override
	public Mono<ObjectBoundary> get(String superapp, String id) {
		ObjectId objectId = new ObjectId(superapp,id);
		return this.objectRep.findById(objectId)
				.map(entity->new ObjectBoundary(entity))
				.log();
	}

	@Override
	public Flux<ObjectBoundary> getAll() {
		return this.objectRep.findAll()
				.map(ObjectBoundary::new)
				.log();
	}

	@Override
	public Flux<ObjectBoundary> getObjectsByType(String type) {
		return this.objectRep.findByType(type)
				.map(ObjectBoundary::new)
				.log();
	}

	@Override
	public Flux<ObjectBoundary> getObjectsByAlias(String alias) {
		return this.objectRep.findByAlias(alias)
				.map(ObjectBoundary::new)
				.log();
	}


	private void validateObject(ObjectBoundary objectBoundary) {
		if(!isNullOrEmpty(objectBoundary.getType())) {
			throw new IllegalArgumentException("Object type is required");
		}
		if(!isNullOrEmpty(objectBoundary.getAlias())) {
			throw new IllegalArgumentException("Object alias is required");
		}
	}
}
