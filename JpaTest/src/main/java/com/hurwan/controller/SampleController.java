package com.hurwan.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hurwan.jpaEntity.User;
import com.hurwan.jpaRepository.UserRepo;

@RestController
@RequestMapping("/sample")
public class SampleController {
	
	@Autowired
	private UserRepo userRepo;
	
	@PostMapping("/save")
	public ResponseEntity<Map> save(@RequestBody User user) {
		// save의 request객체로 User의 name, age, hobby 날아온다. 저장하기
		Long userId = this.userRepo.save(user).getId();
		
		return ResponseEntity.ok().body(Map.of("savedUserId", userId));
	}
	
	@GetMapping("/check")
	public ResponseEntity<Map> check() {
		return ResponseEntity.ok().body(Map.of("message", "ok"));
	}
}
