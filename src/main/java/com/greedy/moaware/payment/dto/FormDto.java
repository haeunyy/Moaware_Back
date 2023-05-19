package com.greedy.moaware.payment.dto;

import lombok.Data;

@Data
public class FormDto {
	
	private Integer formCode;
	private String formTitle;
	private String formPath;
	private String formOriginalFileName;
	private String formSavedFileName;

}
