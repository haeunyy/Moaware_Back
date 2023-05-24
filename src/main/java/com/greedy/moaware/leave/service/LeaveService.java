package com.greedy.moaware.leave.service;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.greedy.moaware.leave.dto.LeavePaymentDto;
import com.greedy.moaware.leave.entity.LeavePayment;
import com.greedy.moaware.leave.repository.LeavePaymentRepository;

@Service
public class LeaveService {
	private final ModelMapper modelMapper;
	private final LeavePaymentRepository leavePaymentRepository;
	
	public LeaveService(LeavePaymentRepository leavePaymentRepository, ModelMapper modelMapper) {
		this.leavePaymentRepository = leavePaymentRepository;
		this.modelMapper = modelMapper;
	}

	public Page<LeavePaymentDto> selectMyLeaveList(Integer empCode, Date leaveReqDate, int page) {
		
//		LeavePK leavePk = new LeavePK();
//		leavePk.setEmpCode(empCode);
		
		Calendar calendar = Calendar.getInstance();
	    calendar.setTime(leaveReqDate);
	    calendar.set(Calendar.DAY_OF_MONTH, 1);
	    Date startDate = calendar.getTime();

	    calendar.add(Calendar.MONTH, 1);
	    calendar.add(Calendar.DATE, -1);
	    Date endDate = calendar.getTime();
		
		
		Pageable pageable = PageRequest.of(page - 1, 10, Sort.by("leaveCode").descending());
		Page<LeavePayment> leaveList = leavePaymentRepository.findByEmployeeEmpCodeAndlPayStatusAndLeaveReqDateBetween(empCode, Arrays.asList("승인", "반려") , startDate, endDate, pageable);
		
		Page<LeavePaymentDto> projDtoList = leaveList.map(proj -> modelMapper.map(proj, LeavePaymentDto.class));
		
		return projDtoList;
	}
	
	
}
