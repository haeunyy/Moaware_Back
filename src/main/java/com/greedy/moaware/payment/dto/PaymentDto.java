package com.greedy.moaware.payment.dto;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PaymentDto {
	

	private Integer payCode;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date draftDate;
	private String draftTitle;
	private String draftContent;
	private FormDto form;
	private String payStatus;
	private PayEmpDto emp;
	private PayFileCategoryDto payFileCategory;

}
