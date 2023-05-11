package com.greedy.moaware.organization.dto;

import java.util.List;

import lombok.Data;

@Data
public class OrganizationDto {
	

	private Integer deptCode;
	private String deptName;
	private String refDeptCode;
	private HighOrganizationDto highDept;
	private List<OrganizationEmpDto> orgEmp;

}
