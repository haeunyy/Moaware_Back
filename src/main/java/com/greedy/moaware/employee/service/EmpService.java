package com.greedy.moaware.employee.service;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.greedy.moaware.employee.dto.DeptDto;
import com.greedy.moaware.employee.dto.EmpDto;
import com.greedy.moaware.employee.entity.Dept;
import com.greedy.moaware.employee.entity.Emp;
import com.greedy.moaware.employee.repository.DeptRepository;
import com.greedy.moaware.employee.repository.EmpRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class EmpService {
	
	private final EmpRepository empRepository;
	private final DeptRepository deptRepository;
	private final ModelMapper modelMapper;
	
	public EmpService( EmpRepository empRepository, ModelMapper modelMapper
			, DeptRepository deptRepository) {
		this.empRepository = empRepository;
		this.deptRepository = deptRepository;
		this.modelMapper = modelMapper;
	}
	
	public List<EmpDto> selectEmpList() {
		
		log.info("[EmpService] selectEmpList start ============================== ");
		
		List<Emp> empList = empRepository.findAll();
		
		log.info("{}", empList.get(0).getDept());
		
		List<EmpDto>empDtoList = empList.stream().map( emp -> modelMapper.map(emp, EmpDto.class)).collect(Collectors.toList());
		
		
		log.info("[EmpService] selectEmpList end ================================ ");
		
		return empDtoList;
		
	}
	
	public List<DeptDto> selectDeptList() {
		
		log.info("[EmpService] selectDeptList start ============================== ");
		
		List<Dept> DeptList = deptRepository.findAll();
		
		log.info("{}", DeptList.get(0).getDeptCode());
		
		List<DeptDto> DeptDtoList = DeptList.stream().map(dept -> modelMapper.map(dept, DeptDto.class)).collect(Collectors.toList());
		
		
		log.info("[EmpService] selectEmpList end ================================ ");
		
		return DeptDtoList;
		
	}

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	//retireYn
	
	
	
	public Page<EmpDto> findByEmpName(String empName, int page) {
		
		Pageable pageable = PageRequest.of(page - 1, 10, Sort.by("empCode").descending());
		
		Page<Emp> empList = empRepository.findByEmpName(empName, pageable);
		
		
		
		Page<EmpDto> empDtoList = empList.map(emp -> modelMapper.map(emp, EmpDto.class));
		
		return empDtoList;
	}

}
