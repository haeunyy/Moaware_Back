package com.greedy.moaware.leave.service;

import java.util.Arrays;
import java.util.Date;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.greedy.moaware.leave.dto.LeaveDto;
import com.greedy.moaware.leave.dto.LeavePaymentDto;
import com.greedy.moaware.leave.entity.Leave;
import com.greedy.moaware.leave.entity.LeavePayment;
import com.greedy.moaware.leave.repository.LeavePaymentRepository;
import com.greedy.moaware.leave.repository.LeaveRepository;

@Service
public class LeaveService {
	private final ModelMapper modelMapper;
	private final LeaveRepository leaveRepository;
	private final LeavePaymentRepository leavePaymentRepository;
	
	public LeaveService(LeavePaymentRepository leavePaymentRepository, ModelMapper modelMapper, LeaveRepository leaveRepository) {
		this.leavePaymentRepository = leavePaymentRepository;
		this.modelMapper = modelMapper;
		this.leaveRepository = leaveRepository;
	}

	public Page<LeavePaymentDto> selectMyLeaveList(Integer empCode, int page) {
		
//		LeavePK leavePk = new LeavePK();
//		leavePk.setEmpCode(empCode);
		
//		Calendar calendar = Calendar.getInstance();
//	    calendar.setTime(leaveReqDate);
//	    calendar.set(Calendar.DAY_OF_MONTH, 1);
//	    Date startDate = calendar.getTime();
//
//	    calendar.add(Calendar.MONTH, 1);
//	    calendar.add(Calendar.DATE, -1);
//	    Date endDate = calendar.getTime();
		
		
		Pageable pageable = PageRequest.of(page - 1, 10, Sort.by("leaveCode").descending());
		Page<LeavePayment> leaveList = leavePaymentRepository.findByEmployeeEmpCodeAndlPayStatus(empCode, Arrays.asList("승인", "반려"),  pageable);
		
		Page<LeavePaymentDto> projDtoList = leaveList.map(proj -> modelMapper.map(proj, LeavePaymentDto.class));
		
		return projDtoList;
	}

//	public Page<LeaveDto> selectMyLeaveList2(Integer empCode, Integer leaveYear, int page) {
//		
//		Pageable pageable = PageRequest.of(page - 1, 10, Sort.by("leavePayment.leaveCode").descending());
////		Page<Leave> leaveList = leaveRepository.findByLeavePkEmpCodeAndLeavePkLeaveYear(empCode, leaveYear, pageable);
//		
//		Page<LeaveDto> leaveDtoList = leaveList.map(leave -> {
//			LeaveDto leaveDto = modelMapper.map(leave, LeaveDto.class);
//			leaveDto.setLeavePayment(modelMapper.map(leave.getLeavePayment(), LeavePaymentDto.class));
//			return leaveDto;
//		});
//			
//		return leaveDtoList;
//	}
	
	
}
