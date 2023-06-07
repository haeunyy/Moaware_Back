package com.greedy.moaware.employee.entity;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.greedy.moaware.project.entity.CreateProjectEmp;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "DEPARTMENT")
public class Pdept {

	@Id
	@Column(name = "DEPT_CODE")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer deptCode;

	@Column(name = "DEPT_NAME")
	private String deptName;

	@Column(name = "REF_DEPT_CODE")
	private String refDeptCode;
	
	@OneToMany
	@JoinColumn(name="DEPT_CODE")
    private List<CreateProjectEmp> emp;
}
	

