package com.greedy.moaware.board.service;

import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.greedy.moaware.board.dto.BoardDto;
import com.greedy.moaware.boardPost.entity.Board;
import com.greedy.moaware.boardPost.repository.BoardPostRepository;
import com.greedy.moaware.boardPost.repository.BoardRepository;
import com.greedy.moaware.employee.entity.Emp;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class BoardService {

	private final BoardPostRepository boardPostRepository;
	private final BoardRepository boardRepository;
	private final ModelMapper modelMapper;

	public BoardService(BoardPostRepository boardPostRepository, BoardRepository boardRepository,
			ModelMapper modelMapper) {
		this.boardPostRepository = boardPostRepository;
		this.boardRepository = boardRepository;
		this.modelMapper = modelMapper;
	}

	
	/* 1.게시 판 목록 조회 - 페이징, 조회불가 게시판 제외(사용자) */
	public Page<BoardDto> selectBoardList(int page) {


		Pageable pageable = PageRequest.of(page - 1, 10, Sort.by("boardCode").ascending());

		Page<Board> boardList = boardRepository.findByStatus(pageable, "Y");
		Page<BoardDto> boardDtoList = boardList
				.map(board -> modelMapper.map(board, BoardDto.class));




		return boardDtoList;
	}

	
	

	/* 2.게시 판 목록 조회 - 페이징(관리자) */
	public Page<BoardDto> selectBoardListForAdmin(int page) {


		Pageable pageable = PageRequest.of(page - 1, 10, Sort.by("boardCode").ascending());

		Page<Board> boardList = boardRepository.findAll(pageable);
		// 페이저블,
		Page<BoardDto> boardDtoList = boardList
				.map(board -> modelMapper.map(board, BoardDto.class));



		return boardDtoList;
	}


	
	
	
	/* 게시판 작성 */
	public void insertBoard(BoardDto boardDto) {
		
		boardRepository.save(modelMapper.map(boardDto, Board.class));
	
	}



	/* 게시판 수정 */
	@Transactional
	public void updateBoard(Integer empCode, BoardDto boardDto) {
		
		
		
	

			Board originBoard = boardRepository.findById(boardDto.getBoardCode())
					.orElseThrow(() -> new IllegalArgumentException("해당 코드의 게시판이 없습니다. boardCode=" + boardDto.getBoardCode()));

			/* 조회했던 기존 엔티티의 내용을 수정 -> 별도의 수정 메소드를 정의해서 사용하면 다른 방식의 수정을 막을 수 있다. */
			originBoard.update(
									boardDto.getBoardCode(), 
									boardDto.getBoardName(),
									boardDto.getStatus()
									       
								    );
	
		
		
	}

	

	

	
	
	
	
	
	
	
	
	/* 게시판 삭제 */
	
	
	@Transactional
	public void deleteBoard(BoardDto boardDto)  {


		try {
	
	
		Board findBoard = boardRepository.findById(boardDto.getBoardCode())		
				
				.orElseThrow(() -> new IllegalArgumentException("해당 게시판의 정보가 없습니다. findBoard=" + boardDto.getBoardCode()));
				boardDto.setStatus("N");
		
	    findBoard.update(
				boardDto.getStatus()
						);
	
	} catch (IllegalArgumentException e) {
		e.printStackTrace();
	}		
	
	
	
	}
	}




	
	
	
	
	
	
	

