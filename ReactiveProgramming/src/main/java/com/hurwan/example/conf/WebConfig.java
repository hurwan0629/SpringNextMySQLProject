package com.hurwan.example.conf;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.config.CorsRegistry;
import org.springframework.web.reactive.config.WebFluxConfigurer;

/*
 * Cors 설정을 하기 위한 WebConfig 클래스
 * 설정 파일임을 명시하기 위한 @Configuration 명시
 * 그런데 spring-boot-starter-webflux의 경우에는 WebFluxConfigurer을 구현하는 방식을 사용하나봄
 * Filter 방식으로 MvcFilter을 설정 가능한 모양인데 일단 WebFlux만 사용하고 있으니 이렇게 가야겠음
 */
@Configuration
public class WebConfig implements WebFluxConfigurer{
	
	/*
	 * 일단 설정 객체 생성을 위한 @Bean 메서드 생성
	 * corsConfigurer이라는 커스텀 메서드를 통해 WebFluxConfigurer이라는 객체를 반환해주는 메
	 * 메서드를 생성해준다.
	 */
	/*
	 * 위 설명은 WebMvcConfigurer의 경우이고 아래는 오버라이딩 방식이기 때문에 단순하게 리턴 없이 구현하는것으로 보임
	 * 
	 */
	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/**")
			.allowCredentials(true)
			.allowedHeaders("*")
			.allowedMethods("GET", "POST", "DELETE", "PUT", "OPTIONS")
			.allowedOrigins("http://localhost:3000", "https://hoppscotch.io/")
			.maxAge(3600);
	}
}

