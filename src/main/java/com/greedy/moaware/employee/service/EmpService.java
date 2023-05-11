package com.greedy.moaware.employee.service;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.greedy.moaware.employee.dto.EmpDto;
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
	
	@Value("${image.image-url}")
	private String IMAGE_URL;
	
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
	
	public EmpDto selectEmpDetail(Integer empCode ) {
		
		log.info("[EmpService] selectEmpList start ============================== ");
		log.info("[EmpService] empCode : {}" , empCode);
		
		Emp emp = empRepository.findById(empCode)
				.orElseThrow( ()-> new IllegalArgumentException("해당 사번을 가진 사원이 없습니다. 사번 = " + empCode));
		
		
		
		log.info("[EmpService] selectEmpList end ================================ ");
		return null;
	}
	

}
