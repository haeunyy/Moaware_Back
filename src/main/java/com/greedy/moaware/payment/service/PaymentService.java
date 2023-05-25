package com.greedy.moaware.payment.service;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import org.apache.commons.io.FilenameUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.greedy.moaware.payment.dto.FormDto;
import com.greedy.moaware.payment.dto.PayAttachedFileDto;
import com.greedy.moaware.payment.dto.PayEmpDto;
import com.greedy.moaware.payment.dto.PaymentDto;
import com.greedy.moaware.payment.entity.Form;
import com.greedy.moaware.payment.entity.PayAttachedFile;
import com.greedy.moaware.payment.entity.PayEmp;
import com.greedy.moaware.payment.entity.Payment;
import com.greedy.moaware.payment.entity.PaymentMember;
import com.greedy.moaware.payment.entity.PaymentMemberPk;
import com.greedy.moaware.payment.repository.FormRepository;
import com.greedy.moaware.payment.repository.PayAttachedFileRepository;
import com.greedy.moaware.payment.repository.PayEmpRepository;
import com.greedy.moaware.payment.repository.PaymentMemberRepository;
import com.greedy.moaware.payment.repository.PaymentRepository;
import com.greedy.moaware.util.FileUploadUtils;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class PaymentService {
	
	private final PaymentRepository paymentRepository;
	private final FormRepository formRepository;
	private final PayEmpRepository payEmpRepository;
	private final PayAttachedFileRepository payAttachedFileRepository;
	private final PaymentMemberRepository paymentMemberRepository;
	private final ModelMapper modelMapper;
	
	@Value("${image.image-dir}")
	private String IMAGE_DIR;
	
	public PaymentService(ModelMapper modelMapper, PaymentRepository paymentRepository, FormRepository formRepository
			, PayEmpRepository payEmpRepository, PayAttachedFileRepository payAttachedFileRepository, PaymentMemberRepository paymentMemberRepository) {
		this.modelMapper = modelMapper;
		this.paymentRepository = paymentRepository;
		this.formRepository = formRepository;
		this.payEmpRepository = payEmpRepository;
		this.payAttachedFileRepository = payAttachedFileRepository;
		this.paymentMemberRepository = paymentMemberRepository;
		
	}
	
	/* 기안자 기안문 전체 조회 */
	public List<PaymentDto> paymentAllList(Integer empCode) {
		
		log.info("[PaymentService] payMentList start ============================== ");
		
		
		PayEmp emp = payEmpRepository.findById(empCode).orElseThrow( () -> new IllegalArgumentException("해당 사원이 없습니다. empCode=" + empCode));
		
		
		List<Payment> payList = paymentRepository.findByEmp(emp);

		
		log.info("[PaymentService] payMentList payList : {}" , payList);
		
		List<PaymentDto> paysDto = payList.stream().map( pay -> modelMapper.map(pay, PaymentDto.class)).collect(Collectors.toList());
		
		
		
		log.info("[PaymentService] payMentList paysDto : {}" , paysDto);
		
		log.info("[PaymentService] payMentList end ============================== ");
		
		return paysDto;
	}
	

	
	/* 기안문 조회 */
	public Map<String, Object> formSelect(Integer empCode) {
		
		log.info("[PaymentService] formSelect start ============================== ");
		log.info("[PaymentService] formSelect EmpCode : {}" , empCode);
		
		List<Form> formList = formRepository.findAll();
		
		PayEmp emp = payEmpRepository.findById(empCode).orElseThrow( () -> new IllegalArgumentException("해당 사원이 없습니다. empCode=" + empCode));
		
		
		log.info("[PaymentService] formSelect formList : {}" , formList);
		
		
		List<FormDto> formDtoList = formList.stream().map( form-> modelMapper.map(form, FormDto.class)).collect(Collectors.toList());
		
		PayEmpDto empDto = modelMapper.map(emp, PayEmpDto.class);
		
		log.info("[PaymentService] formSelect formDtoList : {}" , formDtoList);
		log.info("[PaymentService] formSelect empDto : {}" , empDto);
		
		Map<String, Object> result = new HashMap<>();
		
		result.put("payForm", formDtoList);
		result.put("payEmp", empDto);
		
		
		log.info("[PaymentService] formSelect end ============================== ");
		
		return result;
		
	}
	
	/* 기안문 저장*/
	@Transactional
	public void insertPayment(Integer empCode, PayAttachedFileDto payAttachedFile) {
		log.info("[PaymentService] insertPayment start ============================== ");
		log.info("[PaymentService] insertPayment payAttachedFile : {}" , payAttachedFile);
		
		PayEmp emp = payEmpRepository.findById(empCode).orElseThrow( () -> new IllegalArgumentException("해당 사원이 없습니다. empCode=" + empCode));
		
		if(payAttachedFile.getFileInfo() != null) {
		
		String fileName = UUID.randomUUID().toString().replace("-","");
		
		String savedName = fileName + "." + FilenameUtils.getExtension(payAttachedFile.getOriginalFileName());
		
		payAttachedFile.setSavedFileName(savedName);
		
		try {
			
			String uploadDir = IMAGE_DIR+"/payment";
			
			log.info("업로드 dir  : {}", uploadDir);
			
			String replaceFileName = FileUploadUtils.saveFile(uploadDir, fileName, payAttachedFile.getFileInfo());
			String path = "/payment/" + replaceFileName;
			
			log.info("업로드 path  : {}", path);
			payAttachedFile.setFilePath(path);
			
		} catch (IOException e) {
			e.printStackTrace();
			
		} 
		
		PayAttachedFile file = modelMapper.map(payAttachedFile, PayAttachedFile.class);
		
		file.getPayFileCategory().getPay().setEmp(emp);
		
		
		log.info("[PaymentService] insertPayment file : {} ", file);
		
		payAttachedFileRepository.save(file);
				
		}
		else {
		Payment pay = modelMapper.map(payAttachedFile.getPayFileCategory().getPay(), Payment.class);
		
		pay.setEmp(emp);
		
		log.info("[PaymentService] insertPayment pay : {} ", pay);
		
		paymentRepository.save(pay);
		
		
		}
		
		
		log.info("[PaymentService] insertPayment end ============================== ");
		
	}
	
	/* 결재 대기 조회 */
	public Page<PaymentDto> paymentMemberList(Integer empCode, int page) {
		log.info("[PaymentService] paymentMemberList start ============================== ");
		
		Pageable pageable = PageRequest.of(page -1, 10, Sort.by("payCode").descending());
		
		log.info("[PaymentService] paymentMemberList empCode : {} ", empCode);
		PaymentMemberPk paymentMemberPk = new PaymentMemberPk();
		
		paymentMemberPk.setEmpCode(empCode);
		
		List<PaymentMember> paymentMemberList = paymentMemberRepository.findByPaymentMemberPkEmpCode(empCode);
		
		log.info("[PaymentService] paymentMemberList paymentMemberList : {} ", paymentMemberList);
		
		Page<Payment> paymentList = paymentRepository.findByPaymentMemberInOrderByPayCode(pageable, paymentMemberList);
				
		log.info("[PaymentService] paymentMemberList paymentList : {} ", paymentList);
		
		Page<PaymentDto> paymentListDto = paymentList.map( pay -> modelMapper.map(pay, PaymentDto.class));
		
		log.info("[PaymentService] paymentMemberList end ============================== ");
		
		return paymentListDto;
	}
	
	/* 기안자로 기안문 진행중 조회 */
	public Page<PaymentDto> paymentList(Integer empCode, int page) {
		
		log.info("[PaymentService] payMentList start ============================== ");
		
		Pageable pageable = PageRequest.of(page -1, 10, Sort.by("payCode").descending());
		
		PayEmp emp = payEmpRepository.findById(empCode).orElseThrow( () -> new IllegalArgumentException("해당 사원이 없습니다. empCode=" + empCode));
		
		String payStatus = "진행중";
		
		Page<Payment> payList = paymentRepository.findByEmpAndPayStatus(pageable, emp, payStatus);
		
		log.info("[PaymentService] payMentList payList : {}" , payList);
		
		Page<PaymentDto> payDtoList = payList.map( pay -> modelMapper.map(pay, PaymentDto.class));
			
		
		log.info("[PaymentService] payMentList payDtoList : {}" , payDtoList);
		
		log.info("[PaymentService] payMentList end ============================== ");
		
		return payDtoList;
	}


	
	/* 결재 진행 조회 */
	
	/* 결재 완료 조회*/

	/* 반려 조회 */
	
	/* 임시 저장 조회 */
	
	/* 서명 조회 */

	


}
