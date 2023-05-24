package com.greedy.moaware.leave.dto;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.greedy.moaware.employee.dto.EmpDto;
import com.greedy.moaware.employee.entity.Auth;
import com.greedy.moaware.leave.entity.Leave;

import lombok.Data;

@Data
public class LeavePaymentDto {

	private Integer leaveCode;
	
	private Date leaveStartDay;
	
	private String leaveType;
	
//	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date leaveReqDate;
	
	private Auth employee;
	
	private String lPayStatus;
	
	private EmpDto payEmp;
	
	private Date lPayDate;
	
	private Date lFinalPayDate;
	
	private EmpDto payFinalEmp;
	
	private Date leaveEndDate;
	
	private Leave leave;
}
