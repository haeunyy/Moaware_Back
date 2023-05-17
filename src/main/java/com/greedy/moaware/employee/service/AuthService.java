package com.greedy.moaware.employee.service;

import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

//import com.greedy.moaware.common.configuration.MailSenderConfig;
import com.greedy.moaware.employee.dto.AuthEmpDto;
import com.greedy.moaware.employee.dto.TokenDto;
import com.greedy.moaware.employee.entity.AuthEmp;
import com.greedy.moaware.employee.repository.AuthEmpRepository;
import com.greedy.moaware.exception.FindMyAccoutException;
import com.greedy.moaware.exception.LoginFailedException;
import com.greedy.moaware.jwt.TokenProvider;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class AuthService {

	private final AuthEmpRepository authEmpRepository;
	private final ModelMapper medelMapper;
	private final TokenProvider tokenProvider;
	private final PasswordEncoder passwordEncoder;
	//private final MailSenderConfig mailSender;
	
	public AuthService(AuthEmpRepository authEmpRepository
			, ModelMapper medelMapper
			, TokenProvider tokenProvider
			, PasswordEncoder passwordEncoder
			) {
		this.authEmpRepository = authEmpRepository;
		this.medelMapper = medelMapper;
		this.passwordEncoder = passwordEncoder;
		this.tokenProvider = tokenProvider;
		//this.mailSender = mailSender;
	}
	
	public TokenDto login(AuthEmpDto empDto) {
		
		log.info("[AuthService] login start ======================================");
		log.info("[AuthService] empDto : {}", empDto);

		/* 아이디 매칭 조회 */
		AuthEmp emp = authEmpRepository.findByEmpId(empDto.getEmpId())
				.orElseThrow(()-> new LoginFailedException("아이디와 비밀번호를 다시 확인해주세요."));

		/* 비밀번호 매칭 확인 */
		if(!passwordEncoder.matches(empDto.getEmpPwd(), emp.getEmpPwd())) {
			throw new LoginFailedException("아이디와 비밀번호를 다시 확인해주세요.");
		}
		/* 토큰 발급 */
		TokenDto tokenDto = tokenProvider.generateTokenDto(medelMapper.map(emp, AuthEmpDto.class));
		
		log.info("[AuthService] login end ======================================");
		return tokenDto;
	}

	public String accountIdFind(AuthEmpDto emp) {

		log.info("[AuthService] accountIdFind start ======================================");
		log.info("[AuthService] empDto : {}", emp);
		
		AuthEmp employee = authEmpRepository.findById(emp.getEmpCode())
				.orElseThrow(()-> new FindMyAccoutException("입력하신 정보를 다시 확인해주세요."));
		
		if(!emp.getEmpName().equals(employee.getEmpName()) || !emp.getEmail().equals(employee.getEmail())) {
			throw new FindMyAccoutException("입력하신 정보를 다시 확인해주세요.");
		}
		
		log.info("[AuthService] accountIdFind end ======================================");
		return employee.getEmpId();
	}
	
	
	public void accountPwdFind(AuthEmpDto emp) {
		
		log.info("[AuthService] sendEmail start ======================================");
		log.info("[AuthService] emp : {}", emp);
		
		AuthEmp employee = authEmpRepository.findById(emp.getEmpCode())
				.orElseThrow(()-> new FindMyAccoutException("입력하신 정보를 다시 확인해주세요."));
		
		if(!emp.getEmpId().equals(employee.getEmpId()) || !emp.getEmail().equals(employee.getEmail())) {
			throw new FindMyAccoutException("입력하신 정보를 다시 확인해주세요.");
		}
		
		//mailSender.sendMail(employee.getEmail());
		
		log.info("[AuthService] sendEmail end ======================================");
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
