package com.greedy.moaware.payment.entity;

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
@Table(name="JOB")
public class PayJob {
	
	@Id
	@Column(name="JOB_CODE")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer jobCode;
	
	@Column(name="JOB_NAME")
	private String jobName;

}
