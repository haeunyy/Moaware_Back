package com.greedy.moaware.employee.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Data
public class FileCategoryDto {
	
	private Integer fCategoryCode;
	private String fCategoryName;
	private String fCategoryType;
	private AttachedFileDto file;
	
	@JsonIgnore
	private EmpDto emp;
    
}
