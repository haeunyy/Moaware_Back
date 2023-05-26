package com.greedy.moaware.schedule.service;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.greedy.moaware.employee.entity.AuthEmp;
import com.greedy.moaware.employee.entity.Emp;
import com.greedy.moaware.employee.repository.EmpRepository;
import com.greedy.moaware.exception.ScheduleNotFoundException;
import com.greedy.moaware.exception.UserNotFoundException;
import com.greedy.moaware.schedule.dto.ScheduleDto;
import com.greedy.moaware.schedule.entity.Schedule;
import com.greedy.moaware.schedule.repository.SchRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class SchService {

    private final SchRepository schRepository;
    private final EmpRepository empRepository;
    private ModelMapper modelMapper;
    
    public SchService(SchRepository schRepository, EmpRepository empRepository, ModelMapper modelMapper) {
        this.schRepository = schRepository;
        this.empRepository = empRepository;
        this.modelMapper = modelMapper;
    }

    public List<ScheduleDto> getScheduleListByUser(Integer emp) {
    	
        Emp employee = empRepository.findById(emp)
                .orElseThrow(() -> new UserNotFoundException(emp + "번의 사원을 찾을 둘수 없습니다."));
        
        List<Schedule> schedules = schRepository.findBySchAuthor(employee);
        
        log.info("getScheduleListByUser: {}", schedules);
        
        List<ScheduleDto> scheduleDto = schedules.stream()
                .map(schedule -> modelMapper.map(schedule, ScheduleDto.class))
                .collect(Collectors.toList());
        
        return scheduleDto;
    }

	public ScheduleDto getScheduleByCodeAndUser(Integer schCode, Integer emp) {
		
		Emp employee = empRepository.findById(emp)
	            .orElseThrow(() -> new UserNotFoundException(emp + "번의 사원을 찾을 수 없습니다."));
		
		Schedule schedule = schRepository.findBySchCodeAndSchAuthor(schCode, employee)
	            .orElseThrow(() -> new ScheduleNotFoundException(schCode + "번의 일정을 찾을 수 없습니다."));

	    log.info("getScheduleByCodeAndUser: {}", schedule);

	    return modelMapper.map(schedule, ScheduleDto.class);
	}

}

