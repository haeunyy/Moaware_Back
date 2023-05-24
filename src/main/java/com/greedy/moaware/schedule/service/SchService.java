package com.greedy.moaware.schedule.service;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.greedy.moaware.employee.entity.AuthEmp;
import com.greedy.moaware.employee.repository.AuthEmpRepository;
import com.greedy.moaware.exception.UserNotFoundException;
import com.greedy.moaware.schedule.dto.ScheduleDto;
import com.greedy.moaware.schedule.entity.Schedule;
import com.greedy.moaware.schedule.repository.SchRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class SchService {

    private final SchRepository schRepository;
    private final AuthEmpRepository authEmpRepository;
    private ModelMapper modelMapper;
    
    public SchService(SchRepository schRepository, AuthEmpRepository authEmpRepository, ModelMapper modelMapper) {
        this.schRepository = schRepository;
        this.authEmpRepository = authEmpRepository;
        this.modelMapper = modelMapper;
    }

    public List<ScheduleDto> getScheduleListByUser(Integer emp) {
    	
        AuthEmp employee = authEmpRepository.findById(emp)
                .orElseThrow(() -> new UserNotFoundException(emp + "번의 해당하는 사원을 찾을 수 없습니다."));
        
        List<Schedule> schedules = schRepository.findBySchAuthor(emp);
        
        log.info("getScheduleListByUser: {}", schedules);
        
        List<ScheduleDto> scheduleDto = schedules.stream()
                .map(schedule -> modelMapper.map(schedule, ScheduleDto.class))
                .collect(Collectors.toList());
        
        return scheduleDto;
    }

}