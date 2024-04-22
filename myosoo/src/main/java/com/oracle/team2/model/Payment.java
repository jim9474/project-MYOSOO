package com.oracle.team2.model;

import java.util.Date;
import java.util.List;

import lombok.Data;

@Data // 구매이력
public class Payment {
	private int game_key; // 게임콘텐츠 일련번호(PK)(FK)
	private int member_key; // 회원일련번호(PK)(FK)
	private Date payment_date; // 구매일자
	private int payment_price; // 결제금액
	private int payment_method; // 결제방법 (카카오페이 0 / 계좌이체 1 / 무통장입금 2) 

	//DY 페이징용
	private String startDate;
	private String endDate;
	private String dayOrMonth; // 일별 혹은 월별
	private int total_price;
	private int total_count;
	private String payment_month;
	private String member_id;
	private String game_name;
	private String currentPage;
	private Integer start;
	private Integer end;	

	// 조회용
	private int member_isfree;
	private int gameKey;
	private List<Integer> gameKeys; // 여러 개의 게임 키를 저장하는 리스트
	private int memberKey; // 회원 일련번호
	private String study_name;
	private Date game_startdate;
	private Date game_enddate;
	private Date paymentDate; // 결제 일자
	private int paymentPrice; // 결제 금액
	private int paymentMethod; // 결제 방법 (카카오페이 0 / 계좌이체 1 / 무통장입금 2)
	private byte[] game_img;
	private String game_imgEncod;
	private int member_mikey;
	
	private int[] paymentPrices;

	private List<Payment> payments;

	public List<Payment> getPayments() {
		return payments;
	}

	public void setPayments(List<Payment> payments) {
		this.payments = payments;

	}

}
