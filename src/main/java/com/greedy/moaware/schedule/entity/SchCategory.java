package com.greedy.moaware.schedule.entity;

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
@Table(name="SCHEDULE_CATEGORY")
public class SchCategory {

	@Id
	@Column(name="SCH_CATEGORY_CODE")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer schCategoryCode;
	
	@Column(name="SCH_CATEGORY_NAME")
	private String schCategoryName;
	
}