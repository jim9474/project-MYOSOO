package com.oracle.team2.service.DY_Service;

import java.util.List;

import com.oracle.team2.model.EcCode;
import com.oracle.team2.model.Member;
import com.oracle.team2.model.Payment;

public interface DY_Service_Interface {
	
	// 이메일 인증
	int sendMail(String member_email);
	
	// 이메일 인증번호 비교
	int confirmEmail(EcCode eccode);

	// ajax 아이디 중복 체크
	int memberIdChk(String member_id);
	
	// 회원 insert
	int memberInsert(Member member);
	
	// 회원 정보조회
	Member findById(int member_key);
	
	// 회원 정보수정
	void updateMemberInfo(Member member);
	
	// 회원 정보삭제
	void deleteMemberInfo(int member_key);

	List<Payment> getMonthlySales(String startDate, String endDate, int startIndex, int pageSize);

	List<Payment> getPaymentsByDateAndMonth(String selectedDate, String dayOrMonth);

	int getSalesTotal(Payment payment);

	List<Payment> getPaymentSales(Payment payment);

	

	
	
	
	

	

	
}
