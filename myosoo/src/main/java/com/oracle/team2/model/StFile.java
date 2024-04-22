package com.oracle.team2.model;

import java.util.Date;

import lombok.Data;

@Data // 학습자료
public class StFile {
	private int stfile_key; // 학습자료 일련번호(PK)
	private int member_key; // 운영자회원일련번호(FK)
	private int stfile_makey; // 자료구분 대분류
	private int stfile_mikey; // 자료구분 소분류
	private int stfile_method; // 자료유형
	private String stfile_file; // 첨부파일명
	private String stfile_fileaddr; // 첨부파일주소
	private int stfilesv_makey; // 서비스구분 대분류
	private int stfilesv_mikey; // 서비스구분 소분류
	private String stfile_content; // 자료내용
	private byte[] stfile_img; // 썸네일(decode)
	private String stfile_imgEncod; // 썸네일(encode)
	private Date stfile_regdate; // 등록일자
	private String stfile_name; // 학습자료명

	// 조회용
		private String search1;
		private String search2;
		private String search3;
		private String keyword;
		private String pageNum;
		private int start;
		private int end;
			
		// Page 정보
		private String currentPage;
}
