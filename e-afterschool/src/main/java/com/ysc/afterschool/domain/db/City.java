package com.ysc.afterschool.domain.db;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.ysc.afterschool.domain.Domain;

import lombok.Data;

/**
 * 지역 테이블 도메인
 * 
 * @author hgko
 *
 */
@Entity
@Table(name = "tb_city")
@Data
public class City implements Domain {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	/** 이름 */
	@Column(nullable = false, length = 20)
	private String name;
	
	@Column(nullable = false, length = 20)
	private String link;
	
	@Column(nullable = false, length = 20)
	private String content;
	
	@Column(length = 45)
	private String campus;
	
	@Column(length = 20)
	private String logo;
	
	// 수강신청문의
	@Column(length = 20)
	private String tel1;
	
	// 기타운영문의
	@Column(length = 20)
	private String tel2;
	
	// SMS연락처
	@Column(length = 20)
	private String sms;
	
	// 설문조사 여부
	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private SurveyType survey;
	
	public enum SurveyType {
		ON, OFF;
	}
}
