package com.greedy.moaware.project.dto;

import java.util.Date;
import java.util.List;

import com.greedy.moaware.employee.dto.AuthEmpDto;
import com.greedy.moaware.project.entity.ProjParticipant;

import lombok.Data;

@Data
public class CreateProjectDto {
	
	private Integer projCode;
	private String projName;
	private String projContent;
	private Date startDate;
	private Date endDate;
	private String projStatus;
	private AuthEmpDto employee;
	private CreateProjectEmpDto emp;
	private List<ProjParticipantDto> projMember;

		
	
	
}
