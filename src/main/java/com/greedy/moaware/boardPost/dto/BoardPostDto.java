package com.greedy.moaware.boardPost.dto;



import java.util.Date;

import com.greedy.moaware.boardPost.entity.Board;

import lombok.Data;


@Data
public class BoardPostDto {

	
	private Long postCode;
	private BoardDTO board;
	private String postCategory;
	private String postTitle;
	private String postContent;
	private Date createDate;
	private Date modifyDate;
	private String status;
	private Long views;
	private Long empCode;
	
	
	
		
	}
	
