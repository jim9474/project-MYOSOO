package com.oracle.team2.model;

import java.util.Date;

import lombok.Data;

@Data // 회원
public class Member {
	private int member_key; //회원일련번호
	private String member_name; //이름
	private String member_email; //이메일주소
	private String member_id; //아이디
	private String member_pw; //패스워드
	private String member_birth; //생년월일
	private String member_tel; //휴대폰번호
	private int member_makey; //회원구분대분류
	private int member_mikey; //회원구분소분류
	private String member_gender; //성별
	private Date member_regdate; //가입일자
	private Date member_update; //수정일자
	private int member_isfree; //자격(무료 0/ 무료 1)
	private int member_isdelete; //삭제여부(존재 0/ 탈퇴 1)
	
	
	// 페이징용
	private String pageNum;
	private int start;
	private int end;
	private int rn;
	private String currentPage;
	
	// 조회용
	private String formatted_regdate;
	private String startDate;
	private String endDate;
	private int opt;
	private int mikey;
	private int isfree;
	private String keyword;
	private int payment_count;
	private String member_birth_formatted;
	private String member_tel_formatted;
}
