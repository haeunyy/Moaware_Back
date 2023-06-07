package com.greedy.moaware.employee.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@Entity
@Table(name="FILE_CATEGORY")
public class FileCategory {
	
	@Id
	@Column(name="F_CATEGORY_CODE")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer fCategoryCode;
	
	@Column(name="F_CATEGORY_NAME")
	private String fCategoryName;
	
	@Column(name="F_CATEGORY_TYPE")
	private String fCategoryType;
	
	@OneToOne(mappedBy="fileCategory")
    private AttachedFile file;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="EMP_CODE")
    private Emp emp;

	@Override
	public String toString() {
		return "FileCategory [fCategoryCode=" + fCategoryCode + ", fCategoryName=" + fCategoryName + ", fCategoryType="
				+ fCategoryType + ", file=" + file + "]";
	}
    
    
	
	
}

























