package com.oracle.team2.model;

import java.util.Date;

import lombok.Data;

@Data // 공지/문의/FAQ
public class Board {
	private int board_key; // 게시판 일련번호(PK)
	private int member_key; // 외래키
	private int board_makey; // 게시판 대분류
	private int board_mikey; // 게시판 소분류
	private String board_title; // 제목
	private String board_writer; // 작성자
	private String board_content; // 작성내용
	private String board_file; // 첨부파일
	private String board_fileaddr; // 첨부파일
	private int board_see; // 조회수
	private int board_comcount; // 댓글수
	private Date board_regdate; // 등록일자
	private Date board_update; // 수정일자
	private int board_iscomment; // 답변여부(없음 0 / 있음 1)
	
	// 페이징용
	private String pageNum;
	private int start;
	private int end;
	private int rn;
	private String currentPage;	

	





	
	

}
