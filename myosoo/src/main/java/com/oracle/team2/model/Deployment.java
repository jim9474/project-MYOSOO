package com.oracle.team2.model;

import java.util.Date;

import lombok.Data;

@Data // 배포이력
public class Deployment {
	private int homework_key; // 숙제 일련번호(FK)
	private int member_key; // 회원일련번호(FK)
	private Date deployment_senddate; // 교육자 전송일자
	private String deployment_content; // 제출내용
	private String deployment_question; // 추가질의내용
	private Date deployment_date; // 제출일자
	private String deployment_answer; // 교육자 질의답변
	private int deployment_grade; // 교육자 숙제평가
	private Date deployment_gradedate; // 교육자 평가일자
	private int deployment_submit;		// 학습자 제출여부
	
	// 기능 구현용
	private int radioValue;
	private int[] checkboxValues;
	
	// 조회용
	private String game_name;
	private String member_name;
	private String homework_name;
	private String homework_content;
	private int homework_level;
	private String homework_date;
	private int study_key;
	
	// 페이징용
	private String pageNum;
	private int start;
	private int end;
	private int rn;	
	private String currentPage;
}
