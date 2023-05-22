package com.greedy.moaware.boardPost.service;

import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.greedy.moaware.boardPost.dto.BoardPostDto;
import com.greedy.moaware.boardPost.entity.Board;
import com.greedy.moaware.boardPost.entity.BoardPost;
import com.greedy.moaware.boardPost.repository.BoardPostRepository;
import com.greedy.moaware.boardPost.repository.BoardRepository;
import com.greedy.moaware.employee.entity.Emp;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class BoardPostService {

	private final BoardPostRepository boardPostRepository;
	private final BoardRepository boardRepository;
	private final ModelMapper modelMapper;

	public BoardPostService(BoardPostRepository boardPostRepository, BoardRepository boardRepository,
			ModelMapper modelMapper) {
		this.boardPostRepository = boardPostRepository;
		this.boardRepository = boardRepository;
		this.modelMapper = modelMapper;
	}

	//테이블명 BoardPost / 솔트 바이 postCode임 착각하지 말자~
	/* 1. 게시글 목록 조회 - 페이징, 조회 불가 게시글 제외(사용자) */
	public Page<BoardPostDto> selectBoardPostList(int page) {

		log.info("[BoardPostService] selectBoardPostList start ============================== ");

		Pageable pageable = PageRequest.of(page - 1, 10, Sort.by("postCode").descending());

		Page<BoardPost> boardPostList = boardPostRepository.findByStatus(pageable, "Y ");
		// 페이저블, status y
		Page<BoardPostDto> boardPostDtoList = boardPostList
				.map(boardPost -> modelMapper.map(boardPost, BoardPostDto.class));

		/* 클라이언트 측에서 서버에 저장 된 이미지 요청 시 필요한 주소로 가공 */

		log.info("[BoardPostService] boardPostDtoList.getContent() : {}", boardPostDtoList.getContent());

		log.info("[BoardPostService] selectBoardPostList end ============================== ");

		return boardPostDtoList;
	}

	/* 2. 게시글 목록 조회 - 페이징, 조회 불가 게시글 포함(관리자) */
	public Page<BoardPostDto> selectBoardPostListForAdmin(int page) {

		log.info("[BoardPostService] selectBoardPostListForAdmin start ============================== ");

		Pageable pageable = PageRequest.of(page - 1, 10, Sort.by("postCode").descending());

		Page<BoardPost> boardPostList = boardPostRepository.findAll(pageable);
		// 페이저블, status y
		Page<BoardPostDto> boardPostDtoList = boardPostList
				.map(boardPost -> modelMapper.map(boardPost, BoardPostDto.class));

		/* 클라이언트 측에서 서버에 저장 된 이미지 요청 시 필요한 주소로 가공 */

		log.info("[BoardPostService] boardPostDtoList.getContent() : {}", boardPostDtoList.getContent());

		log.info("[BoardPostService] selectBoardPostListForAdmin end ============================== ");

		return boardPostDtoList;
	}

	/* 3. 상품 목록 조회 - 게시`판`코드 기준, 페이징, 조회 불가 게시물 제외(사용자) */
	public Page<BoardPostDto> selectBoardPostListByBoard(int page, Long boardCode) {

		log.info("[BoardPostService] selectBoardPostListByBoard start ============================== ");

		Pageable pageable = PageRequest.of(page - 1, 10, Sort.by("postCode").descending());

		/* 전달할 게시판코드 엔티티를 먼저 조회한다. */
		
		  Board findBoard = boardRepository.findById(boardCode)
				  .orElseThrow(() -> new IllegalArgumentException("해당 게시판이 없습니다. boardCode = "+ boardCode));
		 

		Page<BoardPost> boardPostList = boardPostRepository.findByBoardAndStatus(pageable, findBoard, "Y ");
		//보드코드 엔티티 단위로 다뤄야 하는 것에 주의
		Page<BoardPostDto> boardPostDtoList = boardPostList.map(boardPost -> modelMapper.map(boardPost, BoardPostDto.class));

		/* 클라이언트 측에서 서버에 저장 된 이미지 요청 시 필요한 주소로 가공 */
		//boardPostDtoList.forEach(boardPost -> boardPost.setBoardPostImgUrl(IMAGE_URL + boardPost.getBoardPostImgUrl()));
		
		log.info("[BoardPostService] boardPostDtoList.getContent() : {}", boardPostDtoList.getContent());
		
		log.info("[BoardPostService] selectBoardPostListByStatus end ============================== ");
		
		return boardPostDtoList;

	}

	
	/* 4. 게시글 목록 조회 - 게시글제목 검색 기준, 페이징, 조회 불가 게시물 제외(사용자) */
	public Page<BoardPostDto> selectBoardPostListByPostTitle(int page, String postTitle) {
		
		log.info("[BoardPostService] selectBoardPostListByPostTitle start ============================== ");	
		
		Pageable pageable = PageRequest.of(page - 1, 10, Sort.by("postCode").descending());

		Page<BoardPost> boardPostList = boardPostRepository.findByPostTitleContainsAndStatus (pageable, postTitle, "Y ");
		Page<BoardPostDto> boardPostDtoList = boardPostList.map(boardPost -> modelMapper.map(boardPost,BoardPostDto.class));
		
		/* 클라이언트 측에서 서버에 저장 된 이미지 요청 시 필요한 주소로 가공 */
		//productDtoList.forEach(product -> product.setProductImgUrl(IMAGE_URL + product.getProductImgUrl()));
		
		log.info("[BoardPostService] boardPostDtoList.getContent() : {}", boardPostDtoList.getContent());
		
		log.info("[BoardPostService] selectBoardPostListByPostTitle end ============================== ");
		
		return boardPostDtoList;
	}

	/* 5. 게시글 상세 조회 - postCode로 게시글 1개 조회, 조회 불가 게시물 제외(사용자) */
	public BoardPostDto selectBoardPost(Long postCode) {
		//파라미터 값 1개가 넘어오고-> DTO 형태로 구현
		log.info("[BoardPostService] selectBoardPost start ============================== ");
		log.info("[BoardPostService] postCode : {}", postCode);
		
		BoardPost boardPost = boardPostRepository.findByPostCode(postCode)
				.orElseThrow(() -> new IllegalArgumentException("해당 코드의 상품이 없습니다. postCode=" + postCode));
		
		BoardPostDto boardPostDto = modelMapper.map(boardPost, BoardPostDto.class);
		//boardPostDto.setProductImgUrl(IMAGE_URL + boardPostDto.getBoardPostImgUrl());
		
		log.info("[BoardPostService] boardPostDto : {}", boardPostDto);
		log.info("[BoardPostService] selectBoardPost end ============================== ");
		
		return boardPostDto;
	}
	
	/* 6. 게시글 상세 조회 - postCode로 게시글 1개 조회, 조회 불가 게시글 포함(관리자) => findById 메소드 사용 */
	public BoardPostDto selectBoardPostForAdmin(Long postCode) {
		
		log.info("[BoardPostService] selectBoardPostForAdmin start ============================== ");
		log.info("[BoardPostService] postCode : {}", postCode);
		
		BoardPost boardPost = boardPostRepository.findById(postCode)
				.orElseThrow(() -> new IllegalArgumentException("해당 코드의 상품이 없습니다. postCode=" + postCode));
		
		BoardPostDto boardPostDto = modelMapper.map(boardPost, BoardPostDto.class);
		//boardPostDto.setProductImgUrl(IMAGE_URL + productDto.getProductImgUrl());
		
		log.info("[BoardPostService] boardPostDto : {}", boardPostDto);
		log.info("[BoardPostService] selectBoardPostForAdmin end ============================== ");
		
		return boardPostDto;
	}

	/* 7. 게시글 작성 */
	@Transactional
	public void insertBoardPost(BoardPostDto boardPostDto) {
		
		log.info("[BoardPostService] insertBoardPost Start ===================================");
		log.info("[BoardPostService] boardPostDto : {}", boardPostDto);
		
		boardPostRepository.save(modelMapper.map(boardPostDto, BoardPost.class));
	
		log.info("[BoardPostService] insertBoardPost End ==============================");
	}

	/* 8. 게시물 수정 */

	@Transactional
	public void updateBoardPost(BoardPostDto boardPostDto) {
		
		log.info("[BoardPostService] insertBoardPost start ============================== ");
		log.info("[BoardPostService] boardPostDto : {}", boardPostDto);
		
		BoardPost originBoardPost = boardPostRepository.findById(boardPostDto.getPostCode())
				.orElseThrow(() -> new IllegalArgumentException("해당 코드의 게시물이 없습니다. postCode=" + boardPostDto.getPostCode()));
		
//		try {
//			/* 이미지를 변경하는 경우 */
//			if(productDto.getProductImage() != null) {
//				
//				/* 새로 입력 된 이미지 저장 */
//				String imageName = UUID.randomUUID().toString().replace("-", "");
//				String replaceFileName = FileUploadUtils.saveFile(IMAGE_DIR, imageName, productDto.getProductImage());
//				
//				/* 기존에 저장 된 이미지 삭제 */
//				FileUploadUtils.deleteFile(IMAGE_DIR, originProduct.getProductImgUrl());
//				
//				/* DB에 저장 될 imageUrl 값을 수정 */
//				originProduct.setProductImgUrl(replaceFileName);
//			}
			
			/* 이미지를 변경하지 않는 경우에는 
			 * 별도의 처리가 필요 없음 = 할 일이 없고 코드가 흘러가게 처리 */
			
			/* 조회했던 기존 엔티티의 내용을 수정 -> 별도의 수정 메소드를 정의해서 사용하면 다른 방식의 수정을 막을 수 있다. */
			originBoardPost.update(
					boardPostDto.getPostCode(),
					modelMapper.map(boardPostDto.getBoard(), Board.class),
					//엔티티로 값을 바꿔서 처리하는 과정
					boardPostDto.getPostCategory(),
					boardPostDto.getPostTitle(),
					boardPostDto.getPostContent(),
					boardPostDto.getCreateDate(),
					boardPostDto.getModifyDate(),
					boardPostDto.getStatus(),
					boardPostDto.getViews(),
					modelMapper.map(boardPostDto.getWriter(), Emp.class)
					//엔티티로 값을 바꿔서 처리하는 과정
			);
		
//		} catch (IOException e) {
//			e.printStackTrace();
		
		
		log.info("[BoardPostService] insertBoardPost end ============================== ");
	}
	
	
	
	
	
	
}
