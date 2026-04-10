package com.hurwan.example.user;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * ── 어노테이션 방식 WebFlux 컨트롤러 ──
 *
 * 기존 Spring MVC와 거의 동일한 문법이지만,
 * 반환 타입이 Mono<T> / Flux<T> 인 것이 핵심 차이.
 *
 * 엔드포인트 목록:
 *   GET    /users              전체 조회
 *   GET    /users/{id}         단건 조회
 *   GET    /users/search?name= 이름 검색
 *   GET    /users/range?min=&max= 나이 범위 검색
 *   GET    /users/count        총 개수
 *   POST   /users              저장
 *   PUT    /users/{id}         수정
 *   DELETE /users/{id}         삭제
 *   GET    /users/stream       SSE 스트리밍
 */
@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    // ── 전체 조회 ──────────────────────────────────────────────
    @GetMapping
    public Flux<User> findAll() {
        return userService.findAll();
    }

    // ── 단건 조회 ──────────────────────────────────────────────
    @GetMapping("/{id}")
    public Mono<ResponseEntity<User>> findById(@PathVariable Long id) {
        return userService.findById(id)
                .map(ResponseEntity::ok)
                // UserNotFoundException 발생 시 404 반환
                .onErrorReturn(UserNotFoundException.class,
                        ResponseEntity.notFound().build());
    }

    // ── 이름 검색 ──────────────────────────────────────────────
    @GetMapping("/search")
    public Flux<User> findByName(@RequestParam String name) {
        return userService.findByName(name);
    }

    // ── 나이 범위 검색 ─────────────────────────────────────────
    @GetMapping("/range")
    public Flux<User> findByAgeBetween(@RequestParam int min, @RequestParam int max) {
        return userService.findByAgeBetween(min, max);
    }

    // ── 전체 카운트 ────────────────────────────────────────────
    @GetMapping("/count")
    public Mono<Long> count() {
        return userService.count();
    }

    // ── 저장 ───────────────────────────────────────────────────
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<User> save(@RequestBody User user) {
        return userService.save(user);
    }

    // ── 수정 ───────────────────────────────────────────────────
    @PutMapping("/{id}")
    public Mono<ResponseEntity<User>> update(@PathVariable Long id, @RequestBody User user) {
        return userService.update(id, user)
                .map(ResponseEntity::ok)
                .onErrorReturn(UserNotFoundException.class,
                        ResponseEntity.notFound().build());
    }

    // ── 삭제 ───────────────────────────────────────────────────
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Mono<Void> delete(@PathVariable Long id) {
        return userService.delete(id);
    }

    /**
     * SSE (Server-Sent Events) 스트리밍
     *
     * MediaType.TEXT_EVENT_STREAM_VALUE 를 produces에 지정하면
     * 클라이언트에 데이터를 실시간으로 푸시할 수 있다.
     *
     * 테스트: curl -N http://localhost:8080/users/stream
     */
    @GetMapping(value = "/stream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<User> stream() {
        return userService.streamAllUsers();
    }
}
