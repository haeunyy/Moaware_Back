package com.greedy.moaware.organization.dto;

import java.util.List;

import lombok.Data;

@Data
public class SearchOrganizationDto {
	
	
	private List<SearchOrgDto> highDept;
	private List<SearchOrgDto> subDept;
	private List<OrganizationEmpDto> orgEmp;

}
