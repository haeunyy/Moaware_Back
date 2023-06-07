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
import com.greedy.moaware.project.entity.CreateProject;

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

	
	// 테이블명 BoardPost / 솔트 바이 postCode임 착각하지 말자~
	/* 1. 게시글 목록 조회 - 페이징, 조회 불가 게시글 제외(사용자) */
	public Page<BoardPostDto> selectBoardPostList(int page) {

		Pageable pageable = PageRequest.of(page - 1, 10, Sort.by("postCode").descending());

		Page<BoardPost> boardPostList = boardPostRepository.findByStatus(pageable, "Y");
		Page<BoardPostDto> boardPostDtoList = boardPostList
				.map(boardPost -> modelMapper.map(boardPost, BoardPostDto.class));


		

		return boardPostDtoList;
	}
	

	
	/* 1-1. 게시글 목록 조회 - 페이징, 조회 불가 게시글 포함(관리자) */
	public Page<BoardPostDto> selectBoardPostListForAdmin(int page) {

		Pageable pageable = PageRequest.of(page - 1, 10, Sort.by("postCode").descending());

		Page<BoardPost> boardPostList = boardPostRepository.findAll(pageable);

		Page<BoardPostDto> boardPostDtoList = boardPostList.map(boardPost -> modelMapper.map(boardPost, BoardPostDto.class));

		return boardPostDtoList;
	}
	//-------------------------------------------------------------------------------------------------------------
	
	/* 2. 게시글 목록 조회 - 게시`판`코드 기준, 페이징, 조회 불가 게시물 제외(사용자) */
	public Page<BoardPostDto> selectBoardPostListByBoard(int page, Long boardCode) {


		Pageable pageable = PageRequest.of(page - 1, 10, Sort.by("postCode").descending());


		Board findBoard = boardRepository.findById(boardCode)
				.orElseThrow(() -> new IllegalArgumentException("해당 게시판이 없습니다. boardCode = " + boardCode));

		Page<BoardPost> boardPostList = boardPostRepository.findByBoardAndStatus(pageable, findBoard, "Y");
		Page<BoardPostDto> boardPostDtoList = boardPostList
				.map(boardPost -> modelMapper.map(boardPost, BoardPostDto.class));

		return boardPostDtoList;

	}
	
	
	/* 2-1. 게시글 목록 조회 - 게시`판`코드 기준, 페이징, 조회 불가 게시물 포함(사용자) */

	public Page<BoardPostDto> selectBoardPostListByBoardForAdmin(int page, Long boardCode) {
		
		Pageable pageable = PageRequest.of(page - 1, 10, Sort.by("postCode").descending());


		Board findBoard = boardRepository.findById(boardCode)
				.orElseThrow(() -> new IllegalArgumentException("해당 게시판이 없습니다. boardCode = " + boardCode));

		Page<BoardPost> boardPostList = boardPostRepository.findByBoard(pageable, findBoard);
		Page<BoardPostDto> boardPostDtoList = boardPostList
				.map(boardPost -> modelMapper.map(boardPost, BoardPostDto.class));

		return boardPostDtoList;
		
	}
	//-------------------------------------------------------------------------------------------------------------

	/* 3. 게시글 목록 조회 - 게시글제목 검색 기준, 페이징, 조회 불가 게시물 제외(사용자) */
	public Page<BoardPostDto> selectBoardPostListByPostTitle(int page, String postTitle) {


		Pageable pageable = PageRequest.of(page - 1, 10, Sort.by("postCode").descending());

		Page<BoardPost> boardPostList = boardPostRepository.findByPostTitleContainsAndStatus(pageable, postTitle, "Y");
		Page<BoardPostDto> boardPostDtoList = boardPostList
				.map(boardPost -> modelMapper.map(boardPost, BoardPostDto.class));


		return boardPostDtoList;
	}

	/* 4. 게시글 상세 조회 - postCode로 게시글 1개 조회, 조회 불가 게시물 제외(사용자) */
	public BoardPostDto selectBoardPost(Long postCode) {
		

		BoardPost boardPost = boardPostRepository.findByPostCode(postCode)
				.orElseThrow(() -> new IllegalArgumentException("해당 코드의 게시물이 없습니다. postCode=" + postCode));

		BoardPostDto boardPostDto = modelMapper.map(boardPost, BoardPostDto.class);
	
		return boardPostDto;
	}

	/* 4-1. 게시글 상세 조회 - postCode로 게시글 1개 조회, 조회 불가 게시글 포함(관리자) => findById 메소드 사용 */
	public BoardPostDto selectBoardPostForAdmin(Long postCode) {


		BoardPost boardPost = boardPostRepository.findById(postCode)
				.orElseThrow(() -> new IllegalArgumentException("해당 코드의 게시물이 없습니다. postCode=" + postCode));

		BoardPostDto boardPostDto = modelMapper.map(boardPost, BoardPostDto.class);
	
		return boardPostDto;
	}

	/* 5. 게시글 작성 */
	@Transactional
	public void insertBoardPost(Integer empCode, BoardPostDto boardPostDto) {

	

	

			boardPostRepository.save(modelMapper.map(boardPostDto, BoardPost.class));

	}	

	/* 6. 게시물 수정 */

	@Transactional
	public void updateBoardPost(Integer empCode, BoardPostDto boardPostDto) {


	

			BoardPost originBoardPost = boardPostRepository.findById(boardPostDto.getPostCode())
					.orElseThrow(() -> new IllegalArgumentException("해당 코드의 게시물이 없습니다. postCode=" + boardPostDto.getPostCode()));

			/* 조회했던 기존 엔티티의 내용을 수정 -> 별도의 수정 메소드를 정의해서 사용하면 다른 방식의 수정을 막을 수 있다. */
			originBoardPost.update(
									boardPostDto.getPostCode(), 
									modelMapper.map(boardPostDto.getBoard(), Board.class),
									//modelMapper.map(boardPostDto.getWriter(), Emp.class), //엔티티로 값을 바꿔서 처리하는 과정
								        boardPostDto.getPostTitle(),
										boardPostDto.getStatus(),
									    boardPostDto.getPostContent()
								    );
	
	
	}

	

	/* 7. 게시물 삭제 */

	@Transactional
	public void deleteBoardPost(BoardPostDto boardPostDto) {
	   
		
			try {
				BoardPost findBoardPost = boardPostRepository.findById(boardPostDto.getPostCode())
						.orElseThrow(() -> new IllegalArgumentException("해당 게시물의 정보가 없습니다. findBoardPost=" + boardPostDto.getPostCode()));
				
				
				boardPostDto.setStatus("N");
				
			    findBoardPost.update(
						boardPostDto.getStatus()
								);
				
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			}			
		
	
		}


	
	}
