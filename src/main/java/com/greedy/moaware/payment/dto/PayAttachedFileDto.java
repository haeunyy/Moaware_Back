package com.greedy.moaware.payment.dto;

import lombok.Data;

@Data
public class PayAttachedFileDto {
	
	private Integer fileCode;
	private String originalFileName;
	private String filePath;
	private String savedFileName;

}
