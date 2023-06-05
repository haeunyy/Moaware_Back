package com.greedy.moaware.boardPost.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.greedy.moaware.boardPost.entity.Board;
import com.greedy.moaware.boardPost.entity.BoardPost;

@Repository
public interface BoardRepository extends JpaRepository <Board, Long>{

	/* 게시 판 목록 조회 (사용자용) */
	Page<Board> findByStatus(Pageable pageable, String string);
	
	/* 게시 판 목록 조회 (관리자용) */
	Page<Board> findAll(Pageable pageable);

	
}

	






