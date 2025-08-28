package co.pragma.ksjimen.usecase.user;

import co.pragma.ksjimen.model.user.User;
import co.pragma.ksjimen.model.user.gateways.UserRepository;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class UserUseCase {

    private final UserRepository userRepository;

    public Mono<Boolean> existUserByEmail(String email){
        return userRepository.existUserByEmail(email);
    }

    public Mono<User> save(User user){
        return userRepository.existUserByEmail(user.getEmail())
                .flatMap(exist -> {
                    if(exist) return Mono.error(new IllegalStateException("The email is already registered"));
                    return userRepository.save(user);
                });

    }

    public Flux<User> findAll(){
        return userRepository.findAll();
    }

    public Mono<User> update(User user){
        return userRepository.save(user);
    }

    public Mono<User> findByEmail(String email){
        return userRepository.findByEmail(email);
    }

    public Mono<Void> deleteById(Long userId){
        return userRepository.deleteById(userId);
    }
}
