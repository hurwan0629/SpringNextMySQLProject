package com.hurwan.jpaRepository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hurwan.jpaEntity.Board;

public interface BoardRepo extends JpaRepository <Board, Long>{

}
