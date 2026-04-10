package com.hurwan.example.user.functional;

import com.hurwan.example.user.User;
import com.hurwan.example.user.UserNotFoundException;
import com.hurwan.example.user.UserService;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

/**
 * ── 함수형 라우팅 방식: Handler ──
 *
 * MVC 어노테이션 방식과 달리, 요청/응답을 ServerRequest/ServerResponse 로 직접 다룬다.
 * Router에서 경로를 정의하고, Handler에서 로직을 처리하는 역할 분리 구조.
 *
 * 엔드포인트 경로: /api/users/** (UserRouter 참조)
 */
@Component
public class UserHandler {

    private final UserService userService;

    public UserHandler(UserService userService) {
        this.userService = userService;
    }

    // GET /api/users
    public Mono<ServerResponse> findAll(ServerRequest request) {
        return ServerResponse.ok()
                .body(userService.findAll(), User.class);
    }

    // GET /api/users/{id}
    public Mono<ServerResponse> findById(ServerRequest request) {
        Long id = Long.parseLong(request.pathVariable("id"));
        return userService.findById(id)
                .flatMap(user -> ServerResponse.ok().bodyValue(user))
                .onErrorResume(UserNotFoundException.class,
                        e -> ServerResponse.notFound().build());
    }

    // POST /api/users
    public Mono<ServerResponse> save(ServerRequest request) {
        return request.bodyToMono(User.class)
                .flatMap(userService::save)
                .flatMap(saved -> ServerResponse.status(201).bodyValue(saved));
    }

    // PUT /api/users/{id}
    public Mono<ServerResponse> update(ServerRequest request) {
        Long id = Long.parseLong(request.pathVariable("id"));
        return request.bodyToMono(User.class)
                .flatMap(updateData -> userService.update(id, updateData))
                .flatMap(updated -> ServerResponse.ok().bodyValue(updated))
                .onErrorResume(UserNotFoundException.class,
                        e -> ServerResponse.notFound().build());
    }

    // DELETE /api/users/{id}
    public Mono<ServerResponse> delete(ServerRequest request) {
        Long id = Long.parseLong(request.pathVariable("id"));
        return userService.delete(id)
                .then(ServerResponse.noContent().build())
                .onErrorResume(UserNotFoundException.class,
                        e -> ServerResponse.notFound().build());
    }
}
