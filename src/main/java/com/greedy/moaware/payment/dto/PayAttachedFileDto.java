package com.greedy.moaware.payment.dto;

import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Data
public class PayAttachedFileDto {
	
	private Integer fileCode;
	private String originalFileName;
	private String filePath;
	private String savedFileName;
	
	@JsonIgnore
	private MultipartFile fileInfo;

}
