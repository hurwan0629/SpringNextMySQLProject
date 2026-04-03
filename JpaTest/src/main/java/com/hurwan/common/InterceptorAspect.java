package com.hurwan.common;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

@Aspect // 공통 기능 묶음 클래스
@Component // 객체 생성 어노테이션
public class InterceptorAspect {
	
	// advice라는 실행 시점에
	// execution(pointcut)이라는 실행 위치에서 실행
	@Around("com.hurwan.common.SamplePointcut.pointcutSampleController()")  
	public Object requestInterceptor(ProceedingJoinPoint pjp) throws Throwable{
		System.out.println("[메서드 요청 실행] " + pjp.getSignature().getName());
		
		StopWatch sw = new StopWatch();
		sw.start();
		Object result = pjp.proceed();
		sw.stop();
		
		System.out.println("[메서드 요청 종료] " + pjp.getSignature().getName() + "\n실행 시간: " + sw.getTotalTimeMillis() + "ms");
		return result;
	}
}
