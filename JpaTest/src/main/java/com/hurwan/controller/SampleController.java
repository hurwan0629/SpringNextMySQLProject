package com.hurwan.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hurwan.dto.ContentWriteDTO;
import com.hurwan.jpaEntity.Board;
import com.hurwan.jpaEntity.User;
import com.hurwan.jpaRepository.BoardRepo;
import com.hurwan.jpaRepository.UserRepo;

@RestController
@RequestMapping("/sample")
public class SampleController {
	
	@Autowired
	private UserRepo userRepo;
	@Autowired
	private BoardRepo boardRepo;
	
	@Value("${server.port}")
	private int port;
	
	@PostMapping("/save")
	public ResponseEntity<Map> save(@RequestBody User user) {
		// save의 request객체로 User의 name, age, hobby 날아온다. 저장하기
		Long userId = this.userRepo.save(user).getId();
		
		return ResponseEntity.ok().body(Map.of("savedUserId", userId));
	}
	
	@GetMapping("/check")
	public ResponseEntity<Map> check() {
		return ResponseEntity.ok().body(Map.of("message", "ok", "port", this.port));
	}
	
	@PostMapping("/write")
	public ResponseEntity<?> writeContent(@RequestBody ContentWriteDTO contentDTO) {
		User writer = this.userRepo.findById(contentDTO.getWriterId())
							.orElseThrow(() -> new RuntimeException("존재하지 않는 사용자 id"));
		Board content = new Board(writer, contentDTO.getContent());
		
		this.boardRepo.save(content);
		
		return ResponseEntity.ok(Map.of("message", "content saved [id=" + content.getContentId() + "]"));
	}
	
	@GetMapping("/content/{contentId}")
	public ResponseEntity<?> selectContent(@PathVariable Long contentId) {
		Board content = this.boardRepo.findById(contentId).orElseThrow(() -> new RuntimeException("존재하지 않는 Board [id=" + contentId + "]"));
		
		return ResponseEntity.ok(Map.of(
				"contentId", contentId,
				"content", content.getContent(),
				"writerId", content.getWriter().getId(),
				"writerName", content.getWriter().getName(),
				"contentCreatedAt", content.getContentCreatedAt()
				));
	}
}
