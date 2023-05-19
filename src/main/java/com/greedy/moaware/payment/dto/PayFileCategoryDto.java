package com.greedy.moaware.payment.dto;

import lombok.Data;

@Data
public class PayFileCategoryDto {
	
	private Integer fCategoryCode;
	private String fCategoryName;
	private String fCategoryType;
    private PayEmpDto emp;
    private PayAttachedFileDto file;

}
