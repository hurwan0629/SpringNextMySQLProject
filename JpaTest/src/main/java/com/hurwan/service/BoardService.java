package com.hurwan.service;

import org.hibernate.mapping.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.hurwan.jpaRepository.BoardRepo;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BoardService {
	
	@Autowired
	private BoardRepo boardRepo;
	
	@Tranzactional(readOnly=true)
	@Cacheable(value="boardContent", key="#contentId")
	public Map<String, Object> selectContent(Long contentId) {
		
	}
}
