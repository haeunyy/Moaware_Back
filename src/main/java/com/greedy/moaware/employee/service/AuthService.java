package com.greedy.moaware.employee.service;

import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.greedy.moaware.employee.dto.AuthEmpDto;
import com.greedy.moaware.employee.dto.EmpDto;
import com.greedy.moaware.employee.dto.TokenDto;
import com.greedy.moaware.employee.entity.AuthEmp;
import com.greedy.moaware.employee.entity.Emp;
import com.greedy.moaware.employee.repository.AuthEmpRepository;
import com.greedy.moaware.employee.repository.EmpRepository;
import com.greedy.moaware.exception.LoginFailException;
import com.greedy.moaware.jwt.TokenProvider;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class AuthService {

	private final AuthEmpRepository authEmpRepository;
	private final ModelMapper medelMapper;
	private final TokenProvider tokenProvider;
	private final PasswordEncoder passwordEncoder;
	
	public AuthService(AuthEmpRepository authEmpRepository
			, ModelMapper medelMapper
			, TokenProvider tokenProvider
			, PasswordEncoder passwordEncoder) {
		this.authEmpRepository = authEmpRepository;
		this.medelMapper = medelMapper;
		this.passwordEncoder = passwordEncoder;
		this.tokenProvider = tokenProvider;
	}
	
	public TokenDto login(AuthEmpDto empDto) {
		
		log.info("[AuthService] login start ======================================");
		log.info("[AuthService] empDto : {}", empDto);

		/* 아이디 매칭 조회 */
		AuthEmp emp = authEmpRepository.findByEmpId(empDto.getEmpId())
				.orElseThrow(()-> new LoginFailException("아이디와 비밀번호를 다시 확인해주세요."));
		log.info("[AuthService]아이디 매칭 조회-----------------: {}", emp);
		log.info("[AuthService]아이디 매칭 조회-----------------: {}", emp.getRoleList().get(0));


		/* 비밀번호 매칭 확인 */
		if(!passwordEncoder.matches(empDto.getEmpPwd(), emp.getEmpPwd())) {
			throw new LoginFailException("아이디와 비밀번호를 다시 확인해주세요.");
		}
		log.info("[AuthService]비밀번호 매칭 조회-----------------");
		/* 토큰 발급 */
		log.info("[AuthService]토큰  매칭 조회-----------------: {}", emp.getEmpCode());
		TokenDto tokenDto = tokenProvider.generateTokenDto(medelMapper.map(emp, AuthEmpDto.class));
		log.info("[AuthService] tokenDto : {}", tokenDto);
		
		log.info("[AuthService] login end ======================================");
		return tokenDto;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
