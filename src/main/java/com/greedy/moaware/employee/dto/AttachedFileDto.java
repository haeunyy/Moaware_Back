package com.greedy.moaware.employee.dto;

import lombok.Data;

@Data
public class AttachedFileDto {
	
	private Integer fileCode;
	private String originalFileName;
	private String filePath;
	private String savedFileName;

}
