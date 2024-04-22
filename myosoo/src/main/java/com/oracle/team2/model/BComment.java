package com.oracle.team2.model;

import java.util.Date;

import lombok.Data;

@Data // 공지댓글
public class BComment {
	private int board_key; // 게시판 일련번호(PK)(FK)
	private int member_key; // 회원일련번호(PK)(FK)
	private int bcomment_key; // 댓글 일련번호(PK)
	private String bcomment_writer; // 작성자
	private String bcomment_content; // 내용
	private Date bcomment_regdate; // 등록일자

}
