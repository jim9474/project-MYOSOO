package com.oracle.team2.model;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@Data
public class Game2 {
	private int game_key; // 게임콘텐츠 일련번호
	private int member_key; // 운영자 회원번호
	private String game_name; // 게임콘텐츠명
	private int game_maxlevel; // 최대레벨
	private int game_submonth; // 구독개월

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date game_startdate; // 구독시작일자

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date game_enddate; // 구독종료일자

	private int game_subpercount; // 구독가능인원
	private int game_price; // int로 유지 판매가
	private String game_content; // 게임내용
	private byte[] game_img; // byte 배열로 변경 썸네일
	private String game_imgEncod; // 게임 썸네일(encode)
	private String game_file; // 첨부파일명
	private String game_fileaddr; // 첨부파일주소
	private Date game_regdate; // 등록일자
	private Date game_update; // 수정일자
	private int member_mikey;

	// game_price에 쉼표를 제거한 후 int로 변환하여 저장
	public void setGame_price(String game_price) {
		// 쉼표 제거
		String priceWithoutComma = game_price.replaceAll(",", "");
		// 숫자만 추출하여 저장
		this.game_price = Integer.parseInt(priceWithoutComma);
	}

	public void setGame_Price(int price) {
		this.game_price = price;
	}
}
