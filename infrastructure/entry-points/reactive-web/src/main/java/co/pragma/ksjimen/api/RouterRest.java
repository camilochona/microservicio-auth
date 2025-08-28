package co.pragma.ksjimen.api;

import co.pragma.ksjimen.api.config.UserPath;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.*;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
@OpenAPIDefinition(info = @Info(title = "CrediYa Authentication API", version = "1.0", description = "API for user management"))
@RequiredArgsConstructor
public class RouterRest {

    private final UserPath userPath;
    private final UserHandler userHandler;

    @Bean
    public RouterFunction<ServerResponse> routerFunction(UserHandler userHandler) {
        return route(POST(userPath.getUsers()), userHandler::listenSaveUser)
                .andRoute(GET(userPath.getUsers()), userHandler::listenGETUseCase)
                .andRoute(PUT(userPath.getUsers()), userHandler::listenPOSTUseCase)
                .and(route(DELETE(userPath.getUserById()), userHandler::listenGETOtherUseCase));
    }

    // Documentación explícita para cada endpoint
    @Operation(summary = "Create a new user", description = "Register a new user with the provided details")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "User created successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = co.pragma.ksjimen.model.user.User.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input data")
    })
    @Bean
    public RouterFunction<ServerResponse> routeSaveUser() {
        return route(POST(userPath.getUsers()), userHandler::listenSaveUser);
    }

    @Operation(summary = "Get all users", description = "Retrieve a list of all users")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = co.pragma.ksjimen.model.user.User.class)))
    })
    @Bean
    public RouterFunction<ServerResponse> routeGetUsers() {
        return route(GET(userPath.getUsers()), userHandler::listenGETUseCase);
    }
}
