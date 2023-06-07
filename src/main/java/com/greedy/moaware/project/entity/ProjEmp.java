package com.greedy.moaware.project.entity;

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
@Table(name="EMPLOYEE")
public class ProjEmp {

	@Id
	@Column(name="EMP_CODE")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer empCode;

	@Column(name="EMP_NAME")
	private String empName;

	@Column(name="EMP_ID")
	private String empID;


}
