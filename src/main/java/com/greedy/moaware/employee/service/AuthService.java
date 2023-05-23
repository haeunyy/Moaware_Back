package com.greedy.moaware.employee.service;

import java.util.Date;
import java.util.Random;

import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import com.greedy.moaware.common.configuration.MailSenderConfig;
import com.greedy.moaware.employee.dto.AuthEmpDto;
import com.greedy.moaware.employee.dto.TokenDto;
import com.greedy.moaware.employee.entity.AuthEmp;
import com.greedy.moaware.employee.repository.AuthEmpRepository;
import com.greedy.moaware.employee.repository.AuthRepository;
import com.greedy.moaware.employee.repository.EmpRepository;
import com.greedy.moaware.exception.FindMyAccoutException;
import com.greedy.moaware.exception.LoginFailedException;
import com.greedy.moaware.jwt.TokenProvider;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class AuthService {

	private final AuthEmpRepository authEmpRepository;
	private final ModelMapper modelMapper;
	private final TokenProvider tokenProvider;
	private final PasswordEncoder passwordEncoder;
	private final MailSenderConfig mailSender;
	private final EmpRepository empRepository;
	
	public AuthService(AuthEmpRepository authEmpRepository
			, ModelMapper modelMapper
			, TokenProvider tokenProvider
			, PasswordEncoder passwordEncoder
			, MailSenderConfig mailSender
			, EmpRepository empRepository
			) {
		this.authEmpRepository = authEmpRepository;
		this.modelMapper = modelMapper;
		this.passwordEncoder = passwordEncoder;
		this.tokenProvider = tokenProvider;
		this.mailSender = mailSender;
		this.empRepository = empRepository;
	}
	
	/* 로그인 */
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
		TokenDto tokenDto = tokenProvider.generateTokenDto(modelMapper.map(emp, AuthEmpDto.class));
		
		log.info("[AuthService] login end ======================================");
		return tokenDto;
	}

	/* 아이디 찾기 */
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
	
	
	/* 비밀번호 찾기 - mailSender 통하여 임시 비밀번호 전송 및 비밀번호 설정 */
	@Transactional
	public void accountPwdFind(AuthEmpDto empDto) {
		
		log.info("[AuthService] accountPwdFind start ======================================");
		log.info("[AuthService] emp : {}", empDto);
		
		AuthEmp employee = authEmpRepository.findById(empDto.getEmpCode())
				.orElseThrow(()-> new FindMyAccoutException("입력하신 정보를 다시 확인해주세요."));
		
		if(!empDto.getEmpId().equals(employee.getEmpId()) || !empDto.getEmail().equals(employee.getEmail())) {
			throw new FindMyAccoutException("입력하신 정보를 다시 확인해주세요.");
		}
		

		String newPwd = randomPassword();
		
		employee.setEmpPwd(passwordEncoder.encode(newPwd));

		authEmpRepository.save(employee);
		
		mailSender.sendMail(employee.getEmail(), newPwd);
		
		log.info("[AuthService] sendEmail end ======================================");
	}
	
	
	/* 임시 비밀번호 생성 */
	public String randomPassword() {
		
		char[] charSet = new char[] {
				'0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
			    'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M',
			    'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z',
			    'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm',
			    'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'
		};
		
		StringBuilder builder = new StringBuilder();
		
		Random random = new Random(new Date().getTime());
		
		int length = charSet.length;
		
		for (int i = 0; i < 12; i++) {
			builder.append(charSet[random.nextInt(length)]);
		}
		
		log.info("임시 비밀번호 : {}", builder.toString());
		
		return builder.toString();
	}

	
	/* 회원 정보 수정 재로그인 */
	public AuthEmpDto memberCheck(AuthEmpDto empDto, AuthEmpDto empPwd) {
		
		log.info("[AuthService] memberInfo start ======================================");
		log.info("[AuthService] empPwd : {}",empDto);
		log.info("[AuthService] empPwd : {}",empPwd);
		
		AuthEmp emp = authEmpRepository.findByEmpId(empDto.getEmpId())
				.orElseThrow(()-> new LoginFailedException("비밀번호를 다시 확인해주세요."));

		if(!passwordEncoder.matches(empPwd.getEmpPwd(), emp.getEmpPwd())) {
			throw new LoginFailedException("비밀번호를 다시 확인해주세요.");
		}
		
		log.info("[AuthService] memberInfo end ======================================");

		return modelMapper.map(emp, AuthEmpDto.class);
	}


	/* 회원정보 수정 */
	@Transactional
	public void infoModify(@AuthenticationPrincipal AuthEmpDto emp, @RequestBody AuthEmpDto putEmp) {

		log.info("[AuthService] infoModify start ======================================");
		log.info("emp : {}", emp);
		log.info("newEmp : {}", putEmp);
		
		AuthEmpDto newEmp = modelMapper.map(authEmpRepository.findById(emp.getEmpCode()), AuthEmpDto.class);
		
		// 수정 정보 입력
		newEmp.setEmpPwd(putEmp.getEmpPwd());
		newEmp.setEmail(putEmp.getEmail());
		newEmp.setPhone(putEmp.getPhone());
		newEmp.setExtensionNum(putEmp.getExtensionNum());
		
		authEmpRepository.save(modelMapper.map(newEmp, AuthEmp.class));
		
		log.info("[AuthService] infoModify end ======================================");
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
}
