package com.greedy.moaware.project.dto;

import com.greedy.moaware.employee.dto.DeptDto;
import com.greedy.moaware.employee.dto.JobDto;

import lombok.Data;

@Data
public class CreateProjectEmpDto {

	private Integer empCode;
	private String empName;
	private String empID;
	private String email;
	private JobDto job;
	private DeptDto dept;
}
