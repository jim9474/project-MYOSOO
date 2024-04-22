package com.oracle.team2.dao.DY_Dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.oracle.team2.model.EcCode;
import com.oracle.team2.model.Member;
import com.oracle.team2.model.Payment;

import jakarta.mail.internet.MimeMessage;

public interface DY_Dao_Interface {

	void ecSave(EcCode ecCode);

	int confirmEmail(EcCode eccode);

	int memberIdChk(String member_id);

	int memberInsert(Member member);

	Member findById(int member_key);

	void updateMemberInfo(Member member);

	void deleteMemberInfo(int member_key);


	List<Payment> getMonthlySales(Map<String, Object> params);

	List<Payment> findPaymentsByDate(String selectedDate);

	List<Payment> findPaymentsByMonth(String selectedDate);

	int getSalesTotal(Payment payment);

	List<Payment> getPaymentSales(Payment payment);
	

	

	

	
	
	
	

}
