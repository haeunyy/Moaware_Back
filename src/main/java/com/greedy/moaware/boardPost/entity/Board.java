package com.greedy.moaware.boardPost.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name="BOARD")
public class Board {

	@Id
	@Column(name="BOARD_CODE")
	private Long boardCode;
	
	@Column(name="BOARD_NAME")
	private String boardName;
	
	
}
