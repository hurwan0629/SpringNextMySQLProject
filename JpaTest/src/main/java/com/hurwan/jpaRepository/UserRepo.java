package com.hurwan.jpaRepository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hurwan.jpaEntity.User;

public interface UserRepo extends JpaRepository<User, Long>{
	// 기본 메서드만 일단 사용
}
