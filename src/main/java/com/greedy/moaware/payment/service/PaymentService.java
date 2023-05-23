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
import com.greedy.moaware.payment.repository.FormRepository;
import com.greedy.moaware.payment.repository.PayAttachedFileRepository;
import com.greedy.moaware.payment.repository.PayEmpRepository;
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
	private final ModelMapper modelMapper;
	
	@Value("${image.image-dir}")
	private String IMAGE_DIR;
	
	public PaymentService(ModelMapper modelMapper, PaymentRepository paymentRepository, FormRepository formRepository
			, PayEmpRepository payEmpRepository, PayAttachedFileRepository payAttachedFileRepository) {
		this.modelMapper = modelMapper;
		this.paymentRepository = paymentRepository;
		this.formRepository = formRepository;
		this.payEmpRepository = payEmpRepository;
		this.payAttachedFileRepository = payAttachedFileRepository;
		
	}
	
	/* 기안서 전체 조회 */
	public List<PaymentDto> paymentList(Integer empCode) {
		
		log.info("[PaymentService] payMentList start ============================== ");
		
		
		PayEmp emp = payEmpRepository.findById(empCode).orElseThrow( () -> new IllegalArgumentException("해당 사원이 없습니다. empCode=" + empCode));
		
		log.info("이히힝 ");
		
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
		
		paymentRepository.save(pay);
		}
		
		
		log.info("[PaymentService] insertPayment end ============================== ");
		
	}
	
	/* 결재 대기 조회 */
	
	/* 결재 진행 조회 */
	
	/* 결재 완료 조회*/

	/* 반려 조회 */
	
	/* 임시 저장 조회 */
	
	/* 서명 조회 */

	


}
