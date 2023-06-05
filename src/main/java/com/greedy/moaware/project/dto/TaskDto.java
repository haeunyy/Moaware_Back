package com.greedy.moaware.project.dto;

import java.util.Date;

import lombok.Data;

@Data
public class TaskDto {
	
	private Integer taskCode;
	private String taskName;
	private String taskNotice;
	private Date startDate;
	private Date endDate;
	private String type;
	private String stage;
	private ProjectDto project;
	private ProjEmpDto author;
	private String status;
	private Date modifyTime;
	private Integer progress;
	
	
	
	
	
	
}
