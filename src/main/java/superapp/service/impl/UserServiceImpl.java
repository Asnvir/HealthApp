package superapp.service.impl;

import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import superapp.boundary.NewUserBoundary;
import superapp.boundary.UserBoundary;
import superapp.repository.UserCRUD;
import superapp.service.UserService;

import java.util.Date;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {

    private UserCRUD userCrud;

    public UserServiceImpl(UserCRUD userCrud) {
        this.userCrud = userCrud;
    }

    @Override
    public Mono<UserBoundary> create(NewUserBoundary user) {
       return null;
    }

    @Override
    public Mono<UserBoundary> get() {
        return null;
    }

    @Override
    public Mono<Void> update(UserBoundary user) {
        return null;
    }
}
