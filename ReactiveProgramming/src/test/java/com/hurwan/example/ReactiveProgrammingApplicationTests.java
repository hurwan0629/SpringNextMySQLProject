package com.hurwan.example;

import com.hurwan.example.user.User;
import com.hurwan.example.user.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.test.StepVerifier;

/**
 * WebTestClient: WebFlux 테스트 전용 HTTP 클라이언트
 * StepVerifier: Mono/Flux 스트림 단계별 검증 도구
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
class ReactiveProgrammingApplicationTests {

    @Autowired
    WebTestClient webTestClient;

    @Autowired
    UserRepository userRepository;

    // ── 어노테이션 방식 테스트 ──────────────────────────────────

    @Test
    void 전체_유저_조회() {
        webTestClient.get().uri("/users")
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(User.class)
                .hasSize(3); // data.sql 에 3건 입력
    }

    @Test
    void 단건_유저_조회() {
        webTestClient.get().uri("/users/1")
                .exchange()
                .expectStatus().isOk()
                .expectBody(User.class)
                .value(user -> {
                    assert user.getId() == 1L;
                    assert user.getName().equals("kim");
                });
    }

    @Test
    void 없는_유저_조회_404() {
        webTestClient.get().uri("/users/999")
                .exchange()
                .expectStatus().isNotFound();
    }

    @Test
    void 유저_저장() {
        User newUser = new User("choi", 28);
        webTestClient.post().uri("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(newUser)
                .exchange()
                .expectStatus().isCreated()
                .expectBody(User.class)
                .value(user -> {
                    assert user.getId() != null;
                    assert user.getName().equals("choi");
                });
    }

    @Test
    void 유저_수정() {
        User updated = new User("kim-updated", 21);
        webTestClient.put().uri("/users/1")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(updated)
                .exchange()
                .expectStatus().isOk()
                .expectBody(User.class)
                .value(user -> {
                    assert user.getName().equals("kim-updated");
                });
    }

    @Test
    void 유저_삭제() {
        webTestClient.delete().uri("/users/3")
                .exchange()
                .expectStatus().isNoContent();
    }

    // ── 함수형 라우팅 방식 테스트 ──────────────────────────────

    @Test
    void 함수형_전체_유저_조회() {
        webTestClient.get().uri("/api/users")
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(User.class)
                .hasSize(3);
    }

    // ── StepVerifier: 리포지토리 직접 테스트 ──────────────────

    @Test
    void StepVerifier_이름으로_검색() {
        // StepVerifier로 Flux 스트림을 단계별 검증
        StepVerifier.create(userRepository.findByName("lee"))
                .expectNextMatches(user -> user.getName().equals("lee"))
                .verifyComplete(); // 스트림이 정상 종료되는지 확인
    }

    @Test
    void StepVerifier_나이_범위_검색() {
        StepVerifier.create(userRepository.findByAgeBetween(20, 26))
                .expectNextCount(2) // kim(20), lee(25)
                .verifyComplete();
    }
}
