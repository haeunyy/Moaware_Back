package com.greedy.moaware.payment.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name="PAYMENT_MEMBER")
public class PaymentMember {
	
	@EmbeddedId
	private PaymentMemberPk paymentMemberPk;
	
	@Column(name="PAY_TOTAL_YN")
	private String payTotalYn;
	
	@Column(name="PAY_FINAL_YN")
	private String payFinalYn;
	
	@Column(name="PAY_DATE")
	private Date payDate;
	
	@Column(name="CANCLE_REASON")
	private String cancleReason;
	
	@Column(name="payRank")
	private Integer payRank;
	
	

}
