package com.greedy.moaware.payment.dto;

import java.util.Date;

import lombok.Data;

@Data
public class PaymentDto {
	

	private Integer payCode;
	private Date draftDate;
	private String draftTitle;
	private String draftContent;
	private FormDto form;
	private String payStatus;

}
