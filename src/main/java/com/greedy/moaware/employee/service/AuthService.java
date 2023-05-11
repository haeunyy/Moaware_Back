package com.greedy.moaware.employee.service;

import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.greedy.moaware.employee.dto.EmpDto;
import com.greedy.moaware.employee.dto.TokenDto;
import com.greedy.moaware.employee.entity.Emp;
import com.greedy.moaware.employee.repository.EmpRepository;
import com.greedy.moaware.exception.LoginFailException;
import com.greedy.moaware.jwt.TokenProvider;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class AuthService {

	private final EmpRepository empRepository;
	private final ModelMapper medelMapper;
	private final TokenProvider tokenProvider;
	private final PasswordEncoder passwordEncoder;
	
	public AuthService(EmpRepository empRepository
			, ModelMapper medelMapper
			, TokenProvider tokenProvider
			, PasswordEncoder passwordEncoder) {
		this.empRepository = empRepository;
		this.medelMapper = medelMapper;
		this.passwordEncoder = passwordEncoder;
		this.tokenProvider = tokenProvider;
	}
	
	public TokenDto login(EmpDto empDto) {
		
		log.info("[AuthService] login start ======================================");
		log.info("[AuthService] empDto : {}", empDto);

		/* 아이디 확인 */
		Emp emp = empRepository.findByEmpId(empDto.getEmpId())
				.orElseThrow(()-> new LoginFailException("아이디와 비밀번호를 다시 확인해주세요."));
		
		/* 비밀번호 확인 */
		if(!passwordEncoder.matches(empDto.getEmpPwd(), emp.getEmpPwd())) {
			throw new LoginFailException("아이디와 비밀번호를 다시 확인해주세요.");
		}
		
		TokenDto tokenDto = tokenProvider.generateTokenDto(medelMapper.map(emp, EmpDto.class));
		log.info("[AuthService] tokenDto : {}", tokenDto);
		
		log.info("[AuthService] login end ======================================");
		return tokenDto;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
