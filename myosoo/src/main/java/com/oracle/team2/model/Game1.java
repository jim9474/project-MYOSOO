package com.oracle.team2.model;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@Data // 게임콘텐츠
public class Game1 {
	private int game_key; // 게임콘텐츠 일련번호
	private int member_key; // 운영자 회원번호
	private String game_name; // 게임콘텐츠명
	private int game_maxlevel; // 최대레벨
	private int game_submonth; // 구독개월

	private String game_startdate; // 구독시작일자
	private String game_enddate; // 구독종료일자

	private int game_subpercount; // 구독가능인원
	private int game_price; // int로 유지 판매가
	private String game_content; // 게임내용
	private byte[] game_img; // byte 배열로 변경 썸네일
	private String game_file; // 첨부파일명
	private String game_fileaddr; // 첨부파일주소
	private Date game_regdate; // 등록일자
	private Date game_update; // 수정일자

	private String totaldate; // 날짜 시작과 끝을 보여줌
	private int study_key;
	private int student_confirm; 
	private int studynum; // 승인 인원
	private int mm;
	private String member_name;
	
	// 페이지 조회용
	// page 정보 카드상세페이지 댓글용
	private String pageNum;
	private int start; private int end;
	private String currentPage;	

	
}
