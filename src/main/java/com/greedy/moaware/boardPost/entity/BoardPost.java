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


import lombok.Getter;
import lombok.Setter;



@Setter
@Getter
@Entity
@Table(name="BOARD_POST")
@SequenceGenerator(name="BOARD_POST_SEQ_GENERATOR",
		sequenceName="SEQ_POST_CODE",
		initialValue=1, allocationSize=1)

public class BoardPost {
	
	@Id
	@Column(name="POST_CODE")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="PRODUCT_SEQ_GENERATOR")
	private Long postCode;
	
	/* cascade = CascadeType.PERSIST : 
	 * 영속성 전이 설정을 넣으면 Category에 새로운 값이 있을 경우 insert 될 수 있음 */
	@ManyToOne
	@JoinColumn(name = "BOARD_CODE")
	private Board boardCode;
	
	@Column(name="POST_CATEGORY")
	private String postCategory;
	
	@Column(name="POST_TITLE")
	private String postTitle;
	
	@Column(name="POST_CONTENT")
	private String postContent;
	
	@Column(name="CREATE_DATE")
	private Date createDate;
	
	@Column(name="MODIFY_DATE")
	private Date modifyDate;
	
	@Column(name="STATUS")
	private String status;
	
	@Column(name="VIEWS")
	private Long views;
	
	@Column(name="EMP_CODE")
	private Long empCode;
	
	
	/* BoardPost entity 수정 용도의 메소드를 별도로 정의 */
	public void update(Long postCode, Board boardCode, String postCategory, 
			String postTitle, String postContent, Date modifyDate, String status, 
			Long views, Long empCode ) {
		
		this.postCode = postCode;
		this.boardCode = boardCode;
		this.postCategory = postCategory;
		this.postTitle = postTitle;
		this.postContent = postContent;
		this.modifyDate = modifyDate;
		this.status = status;
		this.views = views;
		this.empCode = empCode;
	
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
}



