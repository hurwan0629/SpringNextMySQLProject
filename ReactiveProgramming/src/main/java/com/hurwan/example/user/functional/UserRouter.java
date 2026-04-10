package com.hurwan.example.user.functional;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.*;

/**
 * ── 함수형 라우팅 방식: Router ──
 *
 * RouterFunctions.route() 로 경로와 핸들러를 연결.
 * MVC 어노테이션 방식과 병행 사용 가능.
 *
 * 어노테이션 방식 vs 함수형 방식 비교:
 *   어노테이션: @GetMapping("/users") → 선언적, 익숙함
 *   함수형:     route(GET("/api/users"), handler::findAll) → 명시적, 테스트 용이
 */
@Configuration
public class UserRouter {

    @Bean
    public RouterFunction<ServerResponse> userRoutes(UserHandler handler) {
        return RouterFunctions.route()
                .GET("/api/users",        handler::findAll)
                .GET("/api/users/{id}",   handler::findById)
                .POST("/api/users",       handler::save)
                .PUT("/api/users/{id}",   handler::update)
                .DELETE("/api/users/{id}", handler::delete)
                .build();
    }
}
