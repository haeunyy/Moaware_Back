package com.greedy.moaware.employee.dto;

import java.util.List;

import lombok.Data;

@Data
public class FileCategoryDto {
	
	private Integer fCategoryCode;
	private String fCategoryName;
	private String fCategoryType;
	private AttachedFileDto file;

}
