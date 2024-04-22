package com.oracle.team2.model;

import java.util.Date;

import lombok.Data;

@Data // 학습그룹
public class Study1 {
	private int study_key; // 학습그룹 일련번호(PK)
	private int game_key; // 게임콘텐츠 일련번호(FK)
	private int member_key; // 회원일련번호(FK)
	private int study_month; // 학습개월
	private String study_name; // 그룹명
	private Date study_startdate; // 학습시작일자
	private Date study_enddate; // 학습종료일자
	private int study_maxperson; // T/O(정원 수)
	private int study_maxlevel; // 최대레벨
	private String study_bigo; // 비고
	private int study_appperson; // 승인된 인원수
	
	// 조회용
	private int sttmember_key; // member table : 학생회원 일련번호
	private String member_name; // member table : 학생이름
	private String member_tel; // member table : 휴대폰번호
	private String game_name; // game table : 게임콘텐츠명
	private Date student_date; // student table : 신청일자
	private int student_confirm; // student table : 승인여부 (미승인 0 / 승인 1)
	private String totaldate; // 날짜 시작과 끝을 보여줌
	private int studynum; // 승인 인원
	private int game_subpercount; // 구독가능인원
	private int mm;
	private String game_startdate; // 구독시작일자
	private String game_enddate; // 구독종료일자
	private int game_maxlevel; // 최대레벨
	
	// 조회용
	private String study_duration;
	
	// 조회용
	private String search;
	private String keyword1;
	private String keyword2;
	private int      condition;


	// Page 정보
	private int rownum; // 숫자출력	
	private String currentPage;	
	private String pageNum;
	private int start;
	private int end;	
	
	
}
