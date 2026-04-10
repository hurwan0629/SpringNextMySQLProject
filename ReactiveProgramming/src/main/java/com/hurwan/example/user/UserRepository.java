package com.hurwan.example.user;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;

/**
 * ReactiveCrudRepository: 기본 CRUD를 Mono/Flux로 제공
 *
 * 주요 메서드 (자동 제공):
 *   - findAll()        → Flux<User>
 *   - findById(id)     → Mono<User>
 *   - save(user)       → Mono<User>
 *   - deleteById(id)   → Mono<Void>
 *   - count()          → Mono<Long>
 */
public interface UserRepository extends ReactiveCrudRepository<User, Long> {

    // 쿼리 메서드: 이름으로 검색 (Flux = 여러 건)
    Flux<User> findByName(String name);

    // 나이 범위로 검색
    Flux<User> findByAgeBetween(int minAge, int maxAge);
}
