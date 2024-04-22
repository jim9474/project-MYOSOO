package com.oracle.team2.model;

import java.util.Date;

import lombok.Data;

@Data // 숙제
public class Homework {
	private int homework_key; // 숙제 일련번호(PK)
	private int study_key; // 학습그룹 일련번호(FK)
	private String homework_name; // 숙제명
	private String homework_content; // 숙제내용
	private int homework_level; // 학습레벨
	private String homework_date; // 제출기한
	private Date homework_regdate; // 등록일
	private Date homework_update; // 수정일

	// 조인용 컬럼
	private String study_name;
	private int member_key;
	private Date deployment_senddate;
	private String formatted_senddate;
	
	// 페이징용
	private String pageNum;
	private int start;
	private int end;
	private int rn;
	private String currentPage;
}
