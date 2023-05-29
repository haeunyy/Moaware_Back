package com.greedy.moaware.review.dto;

import java.util.Date;

import com.greedy.moaware.project.entity.Task;
import com.greedy.moaware.review.entity.ReviewEmp;

import lombok.Data;

@Data
public class TaskReviewDto {
	
	private Integer reviewCode;
	private String content;
	private ReviewEmp emp;
	private Date date;
	private String type;
	private Task task;
	private String status;
	private Date modifyDate;

}
