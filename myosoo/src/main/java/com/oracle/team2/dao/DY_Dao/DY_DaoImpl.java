package com.oracle.team2.dao.DY_Dao;


import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.catalina.User;
import org.apache.ibatis.session.SqlSession;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;

import com.oracle.team2.model.EcCode;
import com.oracle.team2.model.Member;
import com.oracle.team2.model.Payment;

import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class DY_DaoImpl implements DY_Dao_Interface {

   private final SqlSession session;
   private final BCryptPasswordEncoder pe;

   @Override
   public void ecSave(EcCode ecCode) {
      session.insert("dyEcSave", ecCode);
      
   }
   

   @Override
   public int confirmEmail(EcCode eccode) {
       System.out.println("DY_DaoImpl confirmEmail start...");
       int result = 0;
       try {
           eccode = session.selectOne("dyConfirmEmail", eccode);
           System.out.println(eccode);
           if (eccode != null) {
               result = session.update("dyUpdateEcCode", eccode);
               
           } else {
               // 인증 실패
               
           }
       } catch (Exception e) {
           e.printStackTrace();
           // 예외 발생 시 인증 실패로 처리
           
       }
       return result;
   }


   @Override
   public int memberIdChk(String member_id) {
      System.out.println("DY_DaoImpl memberIdChk start...");
      int chkIdresult = 0;
      try {
         Member result1 = session.selectOne("dyChkId",member_id);
          System.out.println("DY_DaoImpl memberIdChk  "+result1);
          if(result1 != null) {//아이디 중복있음
             chkIdresult = 1;
          }
      } catch (Exception e) {
         System.out.println("DY_DaoImpl memberIdChk e.getMessage()"+e.getMessage());
         e.printStackTrace();
      }
      System.out.println("DY_DaoImpl memberIdChk chkIdresult..."+chkIdresult);
      return chkIdresult;
   }


   @Override
   public int memberInsert(Member member) {
      
      // 비밀번호 암호화
       String encodedPassword = pe.encode(member.getMember_pw());
       member.setMember_pw(encodedPassword);
      int result = 0;
      
      result = session.insert("DYMemberInsert", member);
      return result;
   }


   @Override
   public Member findById(int member_key) {
      
      return session.selectOne("dyFindById", member_key);
   }


   @Override
   public void updateMemberInfo(Member member) {
      session.update("dyUpdateMemberInfo", member);      
      session.selectOne("dyFindById", member.getMember_key());
      
   }


   @Override
   public void deleteMemberInfo(int member_key) {
      session.update("dyDeleteMemberInfo", member_key);
      
   }



   @Override
   public List<Payment> getMonthlySales(Map<String, Object> params) {
      
      return session.selectList("dyGetMonthlySales", params);
      
   }


   @Override
   public List<Payment> findPaymentsByDate(String selectedDate) {
      List<Payment> payments = session.selectList("dyFindPaymentsByDate", selectedDate);
      return payments;
   }


   @Override
   public List<Payment> findPaymentsByMonth(String selectedDate) {
      List<Payment> payments = session.selectList("dyFindPaymentsByMonth", selectedDate);
      return payments;
   }


   @Override
   public int getSalesTotal(Payment payment) {
      System.out.println("DY_DaoImpl getSalesTotal start...");
      int salesTotal =0;
      try {
         if(payment.getDayOrMonth().equals("daily")) {
            salesTotal= session.selectOne("dyGetDailySalesTotal",payment);
         
         } else if (payment.getDayOrMonth().equals("monthly")) {
            salesTotal= session.selectOne("dyGetMonthlySalesTotal",payment);
            
         }
      } catch (Exception e) {
         e.printStackTrace();
      }
      
      return salesTotal;
   }

   
   @Override
   public List<Payment> getPaymentSales(Payment payment) {
      List<Payment> paymentList =null;
      System.out.println("DY_DaoImpl getDailySales payment..." + payment);
      try {
         if(payment.getDayOrMonth().equals("daily")) {
            paymentList = session.selectList("dyGetDailySales", payment);
            System.out.println("DY_DaoImpl getDailySales paymentList.size()..."+paymentList.size());
         
         } else if (payment.getDayOrMonth().equals("monthly")) {
            paymentList = session.selectList("dyGetMonthlySales", payment);
            System.out.println("DY_DaoImpl getDailySales paymentList.size()..."+paymentList.size());
            
         }
         
      } catch (Exception e) {
         e.printStackTrace();
      }
      return paymentList;
   }

   


   

   


   

   

}// class