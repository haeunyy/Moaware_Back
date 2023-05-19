package com.greedy.moaware.project.dto;

import java.util.Date;

import com.greedy.moaware.employee.dto.AuthEmpDto;
import com.greedy.moaware.employee.entity.AuthEmp;

import lombok.Data;

@Data
public class CreateProjectEmpDto {
	
	private Integer projCode;
	private String projName;
	private String projContent;
	private Date startDate;
	private Date endDate;
	private String projStatus;
	private AuthEmpDto employee;

		
	
	
}
