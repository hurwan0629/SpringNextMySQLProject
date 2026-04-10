package com.hurwan.example.user;

import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;

/**
 * WebFlux 서비스 계층 연습
 *
 * 핵심 개념:
 *   Mono<T>  = 0~1개의 비동기 결과
 *   Flux<T>  = 0~N개의 비동기 스트림
 *
 * 주요 연산자:
 *   map()         → 동기 변환 (T → R)
 *   flatMap()     → 비동기 변환 (T → Mono<R>)
 *   filter()      → 조건 필터
 *   switchIfEmpty() → 비어있을 때 대체값
 *   doOnNext()    → 사이드이펙트 (로그 등), 값은 그대로
 *   onErrorReturn() → 에러 시 기본값 반환
 */
@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // ── 전체 조회 ──────────────────────────────────────────────
    public Flux<User> findAll() {
        return userRepository.findAll()
                .doOnNext(user -> System.out.println("[findAll] " + user));
    }

    // ── 단건 조회 ──────────────────────────────────────────────
    public Mono<User> findById(Long id) {
        return userRepository.findById(id)
                // 없으면 에러 대신 빈 Mono → 404 처리는 컨트롤러에서
                .switchIfEmpty(Mono.error(new UserNotFoundException(id)));
    }

    // ── 이름 검색 ──────────────────────────────────────────────
    public Flux<User> findByName(String name) {
        return userRepository.findByName(name);
    }

    // ── 나이 범위 검색 ─────────────────────────────────────────
    public Flux<User> findByAgeBetween(int min, int max) {
        return userRepository.findByAgeBetween(min, max);
    }

    // ── 저장 ───────────────────────────────────────────────────
    public Mono<User> save(User user) {
        return userRepository.save(user)
                .doOnSuccess(saved -> System.out.println("[save] " + saved));
    }

    // ── 수정 ───────────────────────────────────────────────────
    public Mono<User> update(Long id, User updateData) {
        return userRepository.findById(id)
                .switchIfEmpty(Mono.error(new UserNotFoundException(id)))
                // flatMap: Mono 안에서 다시 Mono를 반환할 때 사용
                .flatMap(existing -> {
                    existing.setName(updateData.getName());
                    existing.setAge(updateData.getAge());
                    return userRepository.save(existing);
                });
    }

    // ── 삭제 ───────────────────────────────────────────────────
    public Mono<Void> delete(Long id) {
        return userRepository.findById(id)
                .switchIfEmpty(Mono.error(new UserNotFoundException(id)))
                .flatMap(user -> userRepository.deleteById(user.getId()));
    }

    // ── 전체 카운트 ────────────────────────────────────────────
    public Mono<Long> count() {
        return userRepository.count();
    }

    /**
     * SSE(Server-Sent Events) 실습용:
     * 1초마다 전체 사용자 목록을 스트리밍
     */
    public Flux<User> streamAllUsers() {
        return Flux.interval(Duration.ofSeconds(1))
                .flatMap(tick -> userRepository.findAll())
                .doOnNext(user -> System.out.println("[stream] tick → " + user));
    }
}
