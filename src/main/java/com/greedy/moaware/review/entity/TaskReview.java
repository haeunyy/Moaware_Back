package com.greedy.moaware.review.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.greedy.moaware.employee.entity.Emp;
import com.greedy.moaware.project.entity.Task;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Setter
@Getter
@Entity
@Table(name="REVIEW")
@SequenceGenerator(name="REVIEW_SEQ_GENERATOR", sequenceName="SEQ_REVIEW_CODE", initialValue=1, allocationSize=1)
public class TaskReview {
	
	@Id
	@Column(name="REVIEW_CODE")
	private Integer reviewCode;
	
	@Column(name="REVIEW_CONTENT")
	private String content;
	
	@ManyToOne
	@JoinColumn(name="REVIEW_AUTHOR")
	private Emp emp;
	
	@Column(name="REVIEW_DATE")
	private Date date;
	
	@Column(name="REVIEW_TYPE")
	private String type;
	
	@ManyToOne
	@JoinColumn(name="TASK_CODE")
	private Task task;
	
	@Column(name="STATUS")
	private String status;
	
	@Column(name="MODIFY_DATE")
	private Date modifyDate;

}
