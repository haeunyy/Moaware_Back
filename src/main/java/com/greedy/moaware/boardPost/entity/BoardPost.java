package com.greedy.moaware.boardPost.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicInsert;
import org.springframework.format.annotation.DateTimeFormat;

import com.greedy.moaware.employee.entity.Emp;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


@DynamicInsert
@Setter
@Getter
@ToString
@Entity
@Table(name="BOARD_POST")
@SequenceGenerator(name="BOARD_POST_SEQ_GENERATOR",
		sequenceName="SEQ_POST_CODE",
		initialValue=1, allocationSize=1)

public class BoardPost {
	
	@Id
	@Column(name="POST_CODE")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="BOARD_POST_SEQ_GENERATOR")
	private Long postCode;
	
	/* cascade = CascadeType.PERSIST : 
	 * 영속성 전이 설정을 넣으면 BoardCode에 새로운 값이 있을 경우 insert 될 수 있음 */
//@ManyToOne(cascade= CascadeType.PERSIST)
	@ManyToOne
	@JoinColumn(name="BOARD_CODE")
	private Board board;
	
	@Column(name="POST_CATEGORY")
	private String postCategory;
	
	@Column(name="POST_TITLE")
	private String postTitle;
	
	@Column(name="POST_CONTENT")
	private String postContent;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Column(name="CREATE_DATE")
	private Date createDate;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Column(name="MODIFY_DATE")
	private Date modifyDate;
	
	
	@Column(name="STATUS")
	private String status;
	
	@Column(name="VIEWS")
	private Long views;
	
	@ManyToOne
	@JoinColumn(name="EMP_CODE")
	private Emp writer;
	
	
	/* BoardPost entity 수정 용도의 메소드를 별도로 정의 */
	public void update(Long postCode, Board board, String postCategory, 
			String postTitle, String postContent, Date createDate, Date modifyDate, String status, 
			Long views, Emp writer ) {
		
		this.postCode = postCode;
		this.board = board;
		this.postCategory = postCategory;
		this.postTitle = postTitle;
		this.postContent = postContent;
		this.modifyDate = createDate;
		this.modifyDate = modifyDate;
		this.status = status;
		this.views = views;
		this.writer = writer;
	
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
}


