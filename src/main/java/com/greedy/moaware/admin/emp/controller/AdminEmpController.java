package com.greedy.moaware.admin.emp.controller;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.greedy.moaware.admin.emp.dto.AdminEmpDto;
import com.greedy.moaware.admin.emp.service.AdminEmpService;
import com.greedy.moaware.common.ResponseDto;

import com.greedy.moaware.common.paging.Pagenation;
import com.greedy.moaware.common.paging.PagingButtonInfo;
import com.greedy.moaware.common.paging.ResponseDtoWithPaging;
import com.greedy.moaware.employee.dto.AuthEmpDto;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/admin/emp")
public class AdminEmpController {
	
	private AdminEmpService adminEmpService;
	
	public AdminEmpController(AdminEmpService adminEmpService) {
		this.adminEmpService = adminEmpService;
	}
	
	
	/* 회원탈퇴, 수정 추가하기 그리고 태스크 옮기기*/
	/* 계정(회원) 전체 조회 */
	

	@GetMapping("/list")
	public ResponseEntity<ResponseDto> selectAdminEmpList(@RequestParam(name="page", defaultValue="1") int page){
		
		//delete
		int a = page;
		System.out.println(a);
		// delete
		Page<AdminEmpDto> adminEmpDtoList =  adminEmpService.selectAdminEmpList(page);
		PagingButtonInfo pageInfo = Pagenation.getPagingButtonInfo(adminEmpDtoList);
		
		ResponseDtoWithPaging responseDtoWithPaging = new ResponseDtoWithPaging();
		responseDtoWithPaging.setPageInfo(pageInfo);

		responseDtoWithPaging.setData(adminEmpDtoList.getContent()); //페이지 안에 있는 것을 그대로 보내는 것이 아니라 컨탠츠로 꺼내어 넣어 보낸다
		
		return ResponseEntity.ok().body(new ResponseDto(HttpStatus.OK, "조회 성공", responseDtoWithPaging ));
		
}
	
	/* 계정(회원) 상세 조회*/
	@GetMapping("/list/{empCode}")
	public ResponseEntity<ResponseDto> selectAdminEmpDetail(@PathVariable Integer empCode){
		
		
		
		return ResponseEntity.ok().body(new ResponseDto(HttpStatus.OK, "조회 성공", adminEmpService.selectAdminEmpDetail(empCode) ));
	}	
	
	/* 계정(회원) 등록 */
	@PostMapping("/regist")
	public ResponseEntity<ResponseDto> insertAdminEmp(@ModelAttribute AdminEmpDto adminEmpDto) {
		adminEmpService.insertAdminEmp(adminEmpDto);
		
		return ResponseEntity
				.ok()
				.body(new ResponseDto(HttpStatus.OK, "계정 등록 성공"));
}
	
	
	/* 계정 수정 */
	@PutMapping("/modify")
	public ResponseEntity<ResponseDto> updateAdminEmp(@AuthenticationPrincipal AuthEmpDto authEmpDto, @ModelAttribute AdminEmpDto adminEmpDto) {
		adminEmpService.updateAdminEmp(adminEmpDto.getEmpCode(), adminEmpDto);
		
		return ResponseEntity
				.ok()
				.body(new ResponseDto(HttpStatus.OK, "계정 수정 성공"));
		
	}
	
	
	/* 계정(회원) 퇴직 처리 retire Y(삭제) */
	@PutMapping("/delete/{empCode}")
	public ResponseEntity<ResponseDto> deleteAdminEmp(@PathVariable(name="empCode") Integer empCode) {

		AdminEmpDto adminEmpDto = new AdminEmpDto();

		adminEmpDto.setEmpCode(empCode);
	
		adminEmpService.deleteAdminEmp(adminEmpDto);

		
		return ResponseEntity
				.ok()
				.body(new ResponseDto(HttpStatus.OK, "퇴직 처리 완료"));
		
	}
	
	
	
//	
//	
//    /* 이름으로 조회 */
//    @GetMapping("/{name}")
//    public ResponseEntity<ResponseDto> getEmployeeByName(@PathVariable String name,
//    		 @RequestParam(name = "page", defaultValue = "1") int page) {
//    	Page<EmpDto> empDtoList =  empService.findByEmpName(name, page);
//    	
//    	PagingButtonInfo pageInfo = Pagenation.getPagingButtonInfo(empDtoList);
//	
//		ResponseDtoWithPaging responseDtoWithPaging = new ResponseDtoWithPaging();
//		responseDtoWithPaging.setPageInfo(pageInfo);
//		responseDtoWithPaging.setData(empDtoList.getContent());
//    	
//    	
//		return ResponseEntity.ok().body(new ResponseDto(HttpStatus.OK, "조회성공", responseDtoWithPaging));   
//    }
//    
//	
//	/* 회원 정보 조회 */
//	@GetMapping("/member/info")
//	public ResponseEntity<ResponseDto> selectMemberInfo(@AuthenticationPrincipal AuthEmpDto empDto){
//		
//		return ResponseEntity.ok().body(new ResponseDto(HttpStatus.OK, "회원정보 조회 완료", empService.selectEmpDetail(empDto.getEmpCode())));
//	}
//	
//	
//	/* 회원 정보 수정 */
//	@PutMapping("/modify")
//	public ResponseEntity<ResponseDto> infoModify(@RequestBody AuthEmpDto empDto){
//		
//		return ResponseEntity.ok().body(new ResponseDto(HttpStatus.OK, "회원정보 수정 완료", empService.infoModify(empDto)));
//	}
//	

}