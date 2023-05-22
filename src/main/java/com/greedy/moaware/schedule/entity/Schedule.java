package com.greedy.moaware.schedule.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name="SCHEDULE")
@SequenceGenerator(name="SCH_SEQ_GENERATOR", sequenceName="SEQ_SCH_CODE", initialValue=1, allocationSize=1)
public class Schedule {

	@Id
	@Column(name="SCH_CODE")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="SCH_SEQ_GENERATOR")
	private Integer schCode;
	
	@Column(name="SCH_TYPE")
	private Integer schType;
	
	@Column(name="SCH_NAME")
	private String schName;
	
	@Column(name="SCH_CONTENT")
	private String schContent;
	
	@Column(name="SCH_DATE")
	private Date schDate;
	
	@Column(name="SCH_END_DATE")
	private Date schEndDate;
	
	@Column(name="SCH_AUTHOR")
	private Integer schAuthor;
	
}
