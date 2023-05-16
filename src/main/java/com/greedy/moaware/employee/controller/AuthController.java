package com.greedy.moaware.employee.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.greedy.moaware.common.ResponseDto;
import com.greedy.moaware.employee.dto.AuthEmpDto;
import com.greedy.moaware.employee.dto.EmpDto;
import com.greedy.moaware.employee.service.AuthService;

@RestController	
@RequestMapping("/auth")
public class AuthController {

	private final AuthService authService;
	
	public AuthController(AuthService authService) {
		this.authService = authService;
	}
	
	
	/* 로그인 하기 */
	@PostMapping("/login")
	public ResponseEntity<ResponseDto> login(@RequestBody AuthEmpDto emp){
		
		return ResponseEntity.ok().body(new ResponseDto(HttpStatus.OK, "로그인 완료", authService.login(emp)));
	}
	
	
	/* 아이디 찾기 */
	@PostMapping("/idfind")
	public ResponseEntity<ResponseDto> accountIdFind(@RequestBody AuthEmpDto emp){
		
		return ResponseEntity.ok().body(new ResponseDto(HttpStatus.OK,"아이디 찾기 완료", authService.accountIdFind(emp)));
	}
	
	
	
	
}
