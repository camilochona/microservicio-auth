package co.pragma.ksjimen.model.user.gateways;

import co.pragma.ksjimen.model.user.User;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface UserRepository {

    Mono<Boolean> existUserByEmail(String email);

    Mono<User> save(User user);

    Flux<User> findAll();

    Mono<User> update(User user);

    Mono<User> findByEmail(String email);

    Mono<Void> deleteById(Long userId);
}
