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
import com.greedy.moaware.boardPost.entity.Board;
import com.greedy.moaware.boardPost.entity.BoardPost;
import com.greedy.moaware.employee.dto.EmpDto;
import com.greedy.moaware.employee.entity.Dept;
import com.greedy.moaware.employee.entity.Emp;
import com.greedy.moaware.employee.entity.Job;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class AdminEmpService {

	private final AdminEmpRepository adminEmpRepository;
//	private final DeptRepository deptRepository;
	private final ModelMapper modelMapper;
//	
//	@Value("${image.image-url}")
//	private String IMAGE_URL;
//	
//	@Value("${image.image-dir}")
//	private String IMAGE_DIR;
	
	public AdminEmpService( AdminEmpRepository adminEmpRepository, ModelMapper modelMapper)
//			DeptRepository deptRepository 
	{
		this.adminEmpRepository = adminEmpRepository;
		//this.deptRepository = deptRepository;
		this.modelMapper = modelMapper;
	}
	
	
	
	/* 계정(회원) 전체 조회*/
	public Page<AdminEmpDto> selectAdminEmpList(int page) {
		
		log.info("[AdminEmpService] selectAdminEmpList start ============================== ");

		Pageable pageable = PageRequest.of(page - 1, 10, Sort.by("empCode").descending());

		
		Page<AdminEmp> adminEmpList = adminEmpRepository.findAll(pageable);
		
		
		Page<AdminEmpDto> adminEmpDtoList = adminEmpList
				.map(adminEmp -> modelMapper.map(adminEmp, AdminEmpDto.class));
		
		
		
		return adminEmpDtoList;
		
	}
	
	/*계정(회원) 상세 조회*/
	public AdminEmpDto selectAdminEmpDetail(Integer empCode) {
		log.info("[AdminEmpService] selectAdminEmpDetail start ============================== ");
		log.info("[AdminEmpService] empCode : {}" , empCode);
		
		AdminEmp adminEmpList = adminEmpRepository.findById(empCode)
				.orElseThrow( ()-> new IllegalArgumentException("해당 사번을 가진 사원이 없습니다. 사번 = " + empCode));
		log.info("[EmpService] empList : {}" , adminEmpList);
		
		AdminEmpDto adminEmpDto = modelMapper.map(adminEmpList, AdminEmpDto.class);
		
		//empDto.getFileCategory().getFile().setFilePath(IMAGE_URL+empDto.getFileCategory().getFile().getFilePath());
		
		log.info("[AdminEmpService] empDto : {}" , adminEmpDto);
		
		log.info("[AdminEmpService] selectAdminEmpDetail end ================================ ");
		return adminEmpDto;
		
	}

	
	
	/*계정(회원)등록*/

	public void insertAdminEmp(AdminEmpDto adminEmpDto) {
		
		log.info("[AdminEmpService] insertAdminEmp Start ===================================");
		log.info("[AdminEmpService] adminEmpDto : {}", adminEmpDto);
		
		adminEmpRepository.save(modelMapper.map(adminEmpDto, AdminEmp.class));

		log.info("[AdminEmpService] insertAdminEmp End ==============================");

		
		
	}


	/* 계정(사원) 퇴직 처리 */
	@Transactional
	public void deleteAdminEmp(AdminEmpDto adminEmpDto) {
		
		
		try {
		AdminEmp findAdminEmp = adminEmpRepository.findById(adminEmpDto.getEmpCode())
				.orElseThrow(() -> new IllegalArgumentException("해당 계정의 정보가 없습니다. findAdminEmp=" + adminEmpDto.getEmpCode()));
		
		adminEmpDto.setRetireYn("Y");
		
	    findAdminEmp.update(
	    		adminEmpDto.getRetireYn()
						);
		

					
				} catch (IllegalArgumentException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}			
	}


	/* 계정(사원) 수정 */
	@Transactional
	public void updateAdminEmp(Integer empCode, AdminEmpDto adminEmpDto) {
	
		log.info("[AdminEmpService] updateAdminEmp start ============================== ");
		log.info("[AdminEmpService] adminEmpDto : {}", adminEmpDto);

		if (empCode == 1) {

			AdminEmp originAdminEmp = adminEmpRepository.findById(adminEmpDto.getEmpCode())
					.orElseThrow(() -> new IllegalArgumentException("해당 코드의 계정이 없습니다. empCode=" + adminEmpDto.getEmpCode()));

			/* 조회했던 기존 엔티티의 내용을 수정 -> 별도의 수정 메소드를 정의해서 사용하면 다른 방식의 수정을 막을 수 있다. */
			originAdminEmp.update(
						adminEmpDto.getEmpCode(),
						adminEmpDto.getEmpName(),
						adminEmpDto.getPhone(),
						adminEmpDto.getEmpID(),
						adminEmpDto.getEmpPwd(),
						modelMapper.map(adminEmpDto.getJob(), Job.class),
						modelMapper.map(adminEmpDto.getDept(), Dept.class)

						
						

					
					);
	
			
		} else {
			throw new IllegalArgumentException("수정 권한이 없습니다.");
		}
			log.info("[BoardPostService] updateBoardPost end ============================== ");
	}
		
		
	}

	
	
	
	
	
	