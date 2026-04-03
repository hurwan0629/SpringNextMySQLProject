package com.hurwan.common;

import org.aspectj.lang.annotation.Pointcut;

public class SamplePointcut {
	@Pointcut("execution(* com.hurwan.controller.*.*(..))")
	public void pointcutSampleController() {}
}
