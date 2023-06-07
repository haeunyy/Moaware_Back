package com.greedy.moaware.project.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicInsert;
import org.springframework.format.annotation.DateTimeFormat;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@DynamicInsert
@Table(name="TASK")
public class Task {

	@Id	
	@Column(name="TASK_CODE")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer taskCode;
	
	@Column(name="TASK_NAME")
	private String taskName;
	
	@Column(name="TASK_NOTICE")
	private String taskNotice;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Column(name="TASK_START_DATE")
	private Date startDate;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Column(name="TASK_END_DATE")
	private Date endDate;
	
	@Column(name="TASK_TYPE")
	private String type;		// 업무단계 - 기획, 설계, 테스트, 개발, 시연
	
	@Column(name="TASK_STAGE")
	private String stage;		// 진행단계 - 해야할일, 진행중, 완료
	
	@ManyToOne
	@JoinColumn(name="PROJ_CODE")
	private Project project;
	
	@ManyToOne
	@JoinColumn(name="TASK_AUTHOR")
	private ProjEmp author;
	
	@Column(name="TASK_STATUS")
	private String status;
	
	@Column(name="MODIFY_TIME")
	private Date modifyTime;
	
	@Column(name="TASK_PROGRESS")
	private Integer progress;
	
	public void update(String taskName, String taskNotice, String type, String stage, Date startDate, Date endDate, Date modifyTime) {
		this.taskName = taskName;
		this.taskNotice = taskNotice;
		this.type = type;
		this.stage = stage;
		this.startDate = startDate;
		this.endDate = endDate;
		this.modifyTime = new Date();
	}

}
