package co.pragma.ksjimen.r2dbc;

import co.pragma.ksjimen.model.user.User;
import co.pragma.ksjimen.model.user.gateways.UserRepository;
import co.pragma.ksjimen.r2dbc.entity.UserEntity;
import co.pragma.ksjimen.r2dbc.helper.ReactiveAdapterOperations;
import org.reactivecommons.utils.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.slf4j.Logger;
import reactor.core.publisher.Mono;

@Repository
public class UserRepositoryAdapter extends ReactiveAdapterOperations<User, UserEntity, Long, UserReactiveRepository> implements UserRepository {

    private static final Logger log = LoggerFactory.getLogger(UserRepositoryAdapter.class);

    public UserRepositoryAdapter(UserReactiveRepository repository, ObjectMapper mapper) {
        super(repository, mapper, entity -> mapper.map(entity, User.class));
    }

    @Override
    public Mono<Boolean> existUserByEmail(String email) {
        User user = User.builder()
                .email(email)
                .build();
        return super.findByExample(user)
                .doOnNext(exists -> log.debug("existUserByEmail with param: {} -> {}", email, exists))
                .next()
                .map(u -> true)
                .defaultIfEmpty(false);
    }

    @Override
    public Mono<User> update(User user) {
        return super.save(user)
                .doOnSuccess(u -> log.debug("update with param: {} -> {}", user, user));
    }

    @Override
    public Mono<User> findByEmail(String email) {
        User user = User.builder()
                .email(email)
                .build();
        return super.findByExample(user)
                .doOnNext(exist -> log.debug("findByEmail with param: {} -> {}", email, exist))
                .next()
                .map(u -> u);
    }

    @Override
    public Mono<Void> deleteById(Long userId) {
        return super.repository.deleteById(userId);
    }
}
