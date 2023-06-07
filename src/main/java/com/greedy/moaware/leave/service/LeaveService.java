package com.greedy.moaware.leave.service;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.greedy.moaware.employee.dto.AuthEmpDto;
import com.greedy.moaware.employee.entity.AuthEmp;
import com.greedy.moaware.employee.repository.AuthEmpRepository;
import com.greedy.moaware.exception.UserNotFoundException;
import com.greedy.moaware.leave.dto.LeaveDto;
import com.greedy.moaware.leave.dto.LeavePaymentDto;
import com.greedy.moaware.leave.entity.Leave;
import com.greedy.moaware.leave.entity.LeavePayment;
import com.greedy.moaware.leave.entity.LeavePk;
import com.greedy.moaware.leave.repository.LeavePaymentRepository;
import com.greedy.moaware.leave.repository.LeaveRepository;
import com.greedy.moaware.project.dto.CreateProjectEmpDto;
import com.greedy.moaware.project.entity.CreateProjectEmp;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class LeaveService {
	private final ModelMapper modelMapper;
	private final LeaveRepository leaveRepository;
	private final LeavePaymentRepository leavePaymentRepository;
	private final AuthEmpRepository authEmpRepository;
	
	public LeaveService(LeavePaymentRepository leavePaymentRepository, ModelMapper modelMapper, LeaveRepository leaveRepository, AuthEmpRepository authEmpRepository) {
		this.leavePaymentRepository = leavePaymentRepository;
		this.modelMapper = modelMapper;
		this.leaveRepository = leaveRepository;
		this.authEmpRepository = authEmpRepository;
	}

	public Page<LeavePaymentDto> myLeaveList(Integer empCode, int page) {
		
		
		Pageable pageable = PageRequest.of(page - 1, 10, Sort.by("leaveCode").descending());
		Page<LeavePayment> leaveList = leavePaymentRepository.findByEmployeeEmpCodeAndlPayStatus(empCode, Arrays.asList("승인", "반려"),  pageable);
		
		Page<LeavePaymentDto> leaveDtoList = leaveList.map(proj -> modelMapper.map(proj, LeavePaymentDto.class));
		
		return leaveDtoList;
	}

	public LeaveDto selectMyLeaveYearList(Integer empCode, Integer leaveYear) {
		
		Leave leave = leaveRepository.findByLeavePkEmpCodeAndLeavePkLeaveYear(empCode, leaveYear)
				.orElseThrow(() -> new IllegalArgumentException("해당코드의 리뷰가 없습니다. reviewCode=" + empCode));
		
		LeaveDto leaveDto = modelMapper.map(leave, LeaveDto.class);
		
		return leaveDto;
	}

	public Page<LeavePaymentDto> selectMyLeaveList(Integer empCode, Date workDate, int page) {
		
		Optional<AuthEmp> empOptional = authEmpRepository.findById(empCode);
		AuthEmp emp = empOptional.orElseThrow(() -> new UserNotFoundException("해당 사원이 없습니다."));
		
	    Calendar calendar = Calendar.getInstance();
	    calendar.setTime(workDate);
	    calendar.set(Calendar.DAY_OF_MONTH, 1);
	    Date startDate = calendar.getTime();

	
	    calendar.add(Calendar.MONTH, 1);
	    calendar.add(Calendar.DATE, -1);
	    Date endDate = calendar.getTime();
		
	    Pageable pageable = PageRequest.of(page - 1, 10, Sort.by("leaveCode").descending());
		Page<LeavePayment> leaveList = leavePaymentRepository.findByEmployeeEmpCodeAndlPayStatusAndLeaveReqDate(empCode, Arrays.asList("승인", "반려"), startDate, endDate, pageable);
		
		Page<LeavePaymentDto> leaveDtoList = leaveList.map(proj -> modelMapper.map(proj, LeavePaymentDto.class));
		
		return leaveDtoList;
	}

	@Transactional
	public void insertLeave(Leave leave, AuthEmpDto emp) {
		
		Leave findLeave = leaveRepository.findByLeavePkEmpCodeAndLeavePkLeaveYear(leave.getLeavePk().getEmpCode(), leave.getLeavePk().getLeaveYear())
				.orElse(null);  // 예외를 던지지 않고 null 반환 
		
		Leave findLeave2 = leaveRepository.findByLeavePkEmpCodeAndLeavePkLeaveYear(leave.getLeavePk().getEmpCode(), leave.getLeavePk().getLeaveYear() +1 )
				.orElse(null);
		
		Date hireDate = emp.getHireDate();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(hireDate);
		
		Calendar calendar2 = Calendar.getInstance();
		
		LeavePk leavePk = new LeavePk();
		
		//입사년도 
		int year = calendar.get(Calendar.YEAR);
		//입사월 
		int month = calendar.get(Calendar.MONTH);
		int yearMonth = year + month;
	
		//현재년도
		Integer nowYear = leave.getLeavePk().getLeaveYear();
		int deCount = 15;  
		int deCount2 = 15;
		int maxDeCount = 25;
		
		if(findLeave == null) {
			if(year == nowYear) {
				leave.setLeaveTotalDay("15");
				leave.setLeaveUseDay("0");
				leaveRepository.save(leave);
			} else if(nowYear > year) {
				int count = nowYear - year;
				for(int i = 0; i < count; i++) {
					if(deCount < maxDeCount) {
						deCount++;						
					}
				}
				leave.setLeaveUseDay("0");
				leave.setLeaveTotalDay(Integer.toString(deCount));
				leaveRepository.save(leave);		
			}
		} else {				
		}
		
		if(findLeave2 == null) {
			if(nowYear <= leave.getLeavePk().getLeaveYear() +1) {
				int count = nowYear+1 - year;
				for(int i = 0; i < count; i++) {
					if(deCount2 < maxDeCount) {
						deCount2++;						
					}
					
				}
				leavePk.setEmpCode(emp.getEmpCode());
				leavePk.setLeaveYear(nowYear+1);
				leave.setLeavePk(leavePk);
				leave.setLeaveUseDay("0");
				leave.setLeaveTotalDay(Integer.toString(deCount2));
				leaveRepository.save(leave);
			}
		}
		
		
	}
	
	@Transactional
	public void insertLeaveRequest(LeavePaymentDto leavePayDto, AuthEmpDto emp) {
		
	
		leavePayDto.setEmployee(emp);
		leavePayDto.setLPayStatus("대기중");
		
		leavePaymentRepository.save(modelMapper.map(leavePayDto, LeavePayment.class));
		
	}

	public Page<LeavePaymentDto> myLeaveRequestList(Integer empCode, int page) {
		
		Pageable pageable = PageRequest.of(page - 1, 10, Sort.by("leaveCode").descending());
		Page<LeavePayment> leaveList = leavePaymentRepository.findByEmployeeEmpCodeAndlPayStatus(empCode, "대기중",  pageable);
		
		Page<LeavePaymentDto> leaveDtoList = leaveList.map(proj -> modelMapper.map(proj, LeavePaymentDto.class));
		
		return leaveDtoList;
	}

	public LeavePaymentDto selectLeaveDetail(Integer leaveCode) {
		
		LeavePayment leave = leavePaymentRepository.findByLeaveCode(leaveCode)
				.orElseThrow(() -> new IllegalArgumentException("해당코드의 연차가 없습니다. leaveCode=" + leaveCode));
		
		LeavePaymentDto leavePaymentDto = modelMapper.map(leave, LeavePaymentDto.class);
		
		return leavePaymentDto;
	}


	
}
