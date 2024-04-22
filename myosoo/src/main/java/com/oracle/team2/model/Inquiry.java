package com.oracle.team2.model;

import java.util.Date;

import lombok.Data;

@Data // 문의답변
public class Inquiry {
	private int board_key; // 게시판 일련번호(PK)(FK)
	private String inquiry_content; // 내용
	private Date inquiry_regdate; // 등록일자

}
