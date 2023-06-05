package com.greedy.moaware.admin.emp.service;

import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.stereotype.Service;

import com.greedy.moaware.admin.emp.dto.AdminEmpDto;
import com.greedy.moaware.admin.emp.entity.AdminEmp;
import com.greedy.moaware.admin.emp.repository.AdminEmpRepository;
import com.greedy.moaware.employee.dto.EmpDto;
import com.greedy.moaware.employee.entity.Dept;
import com.greedy.moaware.employee.entity.Job;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class AdminEmpService {

	private final AdminEmpRepository adminEmpRepository;
	private final ModelMapper modelMapper;


	public AdminEmpService(AdminEmpRepository adminEmpRepository, ModelMapper modelMapper)
	{
		this.adminEmpRepository = adminEmpRepository;
		this.modelMapper = modelMapper;
	}

	/* 계정(회원) 전체 조회 */
	public Page<AdminEmpDto> selectAdminEmpList(int page) {


		Pageable pageable = PageRequest.of(page - 1, 10, Sort.by("empCode").descending());

		Page<AdminEmp> adminEmpList = adminEmpRepository.findAll(pageable);

		Page<AdminEmpDto> adminEmpDtoList = adminEmpList.map(adminEmp -> modelMapper.map(adminEmp, AdminEmpDto.class));

		return adminEmpDtoList;

	}

	/* 계정(회원) 상세 조회 */
	public AdminEmpDto selectAdminEmpDetail(Integer empCode) {
		AdminEmp adminEmpList = adminEmpRepository.findById(empCode)
				.orElseThrow(() -> new IllegalArgumentException("해당 사번을 가진 사원이 없습니다. 사번 = " + empCode));

		AdminEmpDto adminEmpDto = modelMapper.map(adminEmpList, AdminEmpDto.class);

		return adminEmpDto;

	}

	/* 계정(회원)등록 */

	public void insertAdminEmp(AdminEmpDto adminEmpDto) {

		adminEmpRepository.save(modelMapper.map(adminEmpDto, AdminEmp.class));

	}

	/* 계정(사원) 퇴직 처리 */
	@Transactional
	public void deleteAdminEmp(AdminEmpDto adminEmpDto) {

		try {
			AdminEmp findAdminEmp = adminEmpRepository.findById(adminEmpDto.getEmpCode()).orElseThrow(
					() -> new IllegalArgumentException("해당 계정의 정보가 없습니다. findAdminEmp=" + adminEmpDto.getEmpCode()));

			adminEmpDto.setRetireYn("Y");

			findAdminEmp.update(adminEmpDto.getRetireYn());

		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		}
	}

	/* 계정(사원) 수정 */
	@Transactional
	public void updateAdminEmp(Integer empCode, AdminEmpDto adminEmpDto) {

		AdminEmp originAdminEmp = adminEmpRepository.findById(adminEmpDto.getEmpCode()).orElseThrow(
				() -> new IllegalArgumentException("해당 코드의 계정이 없습니다. empCode=" + adminEmpDto.getEmpCode()));
		originAdminEmp.update(adminEmpDto.getEmpCode(), adminEmpDto.getEmpName(), adminEmpDto.getPhone(),
				adminEmpDto.getEmpID(), modelMapper.map(adminEmpDto.getJob(), Job.class),
				modelMapper.map(adminEmpDto.getDept(), Dept.class));
	}
}
