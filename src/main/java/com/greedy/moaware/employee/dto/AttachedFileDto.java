package com.greedy.moaware.employee.dto;

import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Data
public class AttachedFileDto {
	
	private Integer fileCode;
	private String originalFileName;
	private String filePath;
	private String savedFileName;
	
	@JsonIgnore
	private FileCategoryDto fileCategory;
	
	@JsonIgnore
	private MultipartFile fileInfo;

	
}
