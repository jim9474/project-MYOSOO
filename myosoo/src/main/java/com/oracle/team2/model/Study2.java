package com.oracle.team2.model;

import java.util.Date;

import lombok.Data;

@Data // 학습그룹
public class Study2 {
	private int study_key; // 학습그룹 일련번호(PK)
	private int game_key; // 게임콘텐츠 일련번호(FK)
	private int member_key; // 회원일련번호(FK)
	private int study_month; // 학습개월
	private String study_name; // 그룹명
	private String study_startdate; // 학습시작일자
	private String study_enddate; // 학습종료일자
	private int study_maxperson; // T/O(정원 수)
	private int study_maxlevel; // 최대레벨
	private String study_bigo; // 비고
	private int study_appperson; // 승인된 인원수

	// 조회용
	private String game_name; // 게임콘텐츠명
	private String totaldate; // 날짜 시작과 끝을 보여줌
	private int student_confirm; 
	private int studynum; // 승인 인원
	private int game_subpercount; // 구독가능인원
	private int mm;
	private String game_startdate; // 구독시작일자
	private String game_enddate; // 구독종료일자
	private int game_maxlevel; // 최대레벨
	
	// 조회용
	private String study_duration;
	
}
