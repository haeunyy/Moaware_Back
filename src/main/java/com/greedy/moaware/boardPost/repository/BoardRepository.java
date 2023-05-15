package com.greedy.moaware.boardPost.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.greedy.moaware.boardPost.entity.Board;

public interface BoardRepository extends JpaRepository <Board, Long>{

}
