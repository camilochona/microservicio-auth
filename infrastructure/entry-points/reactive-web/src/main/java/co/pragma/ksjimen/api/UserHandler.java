package co.pragma.ksjimen.api;

import co.pragma.ksjimen.api.dto.UserRequestDTO;
import co.pragma.ksjimen.api.dto.ValidationErrorResponse;
import co.pragma.ksjimen.api.exception.ValidationException;
import co.pragma.ksjimen.model.user.User;
import co.pragma.ksjimen.usecase.user.UserUseCase;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.validation.Validator;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Mono;
import org.springframework.transaction.reactive.TransactionalOperator;

import java.util.List;
import java.util.Map;
import java.util.stream.Collector;
import java.util.stream.Collectors;


@Component
@RequiredArgsConstructor
public class UserHandler {

    private final Logger log = LoggerFactory.getLogger(UserHandler.class);
    private final TransactionalOperator transactionalOperator;
    private final UserUseCase userUseCase;
    private final Validator validator;

    public Mono<ServerResponse> listenSaveUser(ServerRequest serverRequest) {
        log.debug("listenSaveUser start path={} method={}", serverRequest.path(), serverRequest.method().name());
        return serverRequest.bodyToMono(UserRequestDTO.class)
                .doOnNext(u -> log.debug("listenSaveUser with data: {} ", u.toString()))
                .flatMap(this::validateRequest)
                .map(u -> User.builder()
                        .name(u.getName())
                        .lastName(u.getLastName())
                        .email(u.getEmail())
                        .documentType(u.getDocumentType())
                        .documentNumber(u.getDocumentNumber())
                        .baseSalary(u.getBaseSalary())
                        .birthdate(u.getBirthdate())
                        .role(u.getRole())
                        .build())
                .flatMap(userUseCase::save)
                .as(transactionalOperator::transactional)
                .flatMap(saved -> ServerResponse.created(serverRequest.uriBuilder().path("/{idUsuario}").build(saved.getIdUser()))
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(saved)
                );
    }

    private Mono<UserRequestDTO> validateRequest(UserRequestDTO requestDTO) {
        Errors errors = new BeanPropertyBindingResult(requestDTO, UserRequestDTO.class.getName());
        validator.validate(requestDTO, errors);

        if (errors.hasErrors()) {
            Map<String, List<String>> fieldErrors = errors.getFieldErrors().stream()
                    .collect(Collectors.groupingBy(
                            FieldError::getField,
                            Collectors.mapping(FieldError::getDefaultMessage, Collectors.toList())
                    ));
            log.debug("Errors while listenSaveUser: {}", fieldErrors);
            throw new ValidationException(fieldErrors);
        }

        return Mono.just(requestDTO);
    }

    public Mono<ServerResponse> listenGETUseCase(ServerRequest serverRequest) {
        // useCase.logic();
        return ServerResponse.ok().bodyValue("");
    }

    public Mono<ServerResponse> listenGETOtherUseCase(ServerRequest serverRequest) {
        // useCase2.logic();
        return ServerResponse.ok().bodyValue("");
    }

    public Mono<ServerResponse> listenPOSTUseCase(ServerRequest serverRequest) {
        // useCase.logic();
        return ServerResponse.ok().bodyValue("");
    }


}
