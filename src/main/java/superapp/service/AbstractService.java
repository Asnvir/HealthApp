package superapp.service;

import reactor.core.publisher.Mono;
import superapp.entity.user.UserEntity;
import superapp.entity.user.UserId;
import superapp.entity.user.UserRole;
import superapp.exception.NotFoundException;
import superapp.repository.UserRepository;

public class AbstractService {
    public final Mono<Boolean> isValidUserCredentials(UserId userId, UserRole role,
                                                UserRepository repository) {
        return repository.findById(userId)
                .map(user -> user.getRole().equals(role))
                .switchIfEmpty(Mono.error(new NotFoundException(String.format("User with %s not found", userId))));
    }
}
