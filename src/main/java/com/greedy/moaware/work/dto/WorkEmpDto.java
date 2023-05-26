package com.greedy.moaware.work.dto;

import java.util.Date;

import com.greedy.moaware.employee.entity.Dept;
import com.greedy.moaware.employee.entity.FileCategory;
import com.greedy.moaware.employee.entity.Job;

import lombok.Data;

@Data
public class WorkEmpDto {
	
	private Integer empCode;
	private Job job;
	private Dept dept;
	private Date hireDate;
}
