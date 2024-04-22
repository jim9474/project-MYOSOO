package com.oracle.team2.service.DY_Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.oracle.team2.dao.DY_Dao.DY_Dao_Interface;
import com.oracle.team2.model.EcCode;
import com.oracle.team2.model.Member;
import com.oracle.team2.model.Payment;

import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DY_ServiceImpl implements DY_Service_Interface {

   private final DY_Dao_Interface dyd;
   private final JavaMailSender mailSender;
   private static int authNumber;

   // 이메일 인증
   @Override
   public int sendMail(String member_email) {
      System.out.println("DY_ServiceImpl sendMail Start!");
      System.out.println(member_email);
      String setfrom = "dladyd1119@gmail.com"; // 보내는사람 이메일
      String title = "MYOSOO 인증 이메일입니다"; // 제목

      try {
         // Mime : 전자우편 인터넷 표준 format
         MimeMessage message = mailSender.createMimeMessage();
         MimeMessageHelper messageHelper = new MimeMessageHelper(message, true, "UTF-8");
         messageHelper.setFrom(setfrom); // 보내는사람 생략하면 정상작동하지 않음
         messageHelper.setTo(member_email); // 받는사람 이메일
         messageHelper.setSubject(title); // 메일제목 생략가능
         authNumber = (int) (Math.random() * 999999) + 1; // 6자리 난수

         messageHelper.setText("MYOSOO 인증번호입니다.\n" + authNumber); // 메일 내용

         System.out.println("인증번호 : " + authNumber);
         mailSender.send(message);

         EcCode ecCode = new EcCode();
         ecCode.setEccode_email(member_email);
         ecCode.setEccode_code(String.valueOf(authNumber));

         dyd.ecSave(ecCode);

         System.out.println("ecCode " + ecCode);

      } catch (Exception e) {
         e.printStackTrace();
      }

      return authNumber;
   }

   // 이메일 인증번호 확인
   @Override
   public int confirmEmail(EcCode eccode) {
      System.out.println("DY_ServiceImpl confirmEmail start...");
      int resultEccode = dyd.confirmEmail(eccode);

      return resultEccode;
   }

   // 아이디 중복확인
   @Override
   public int memberIdChk(String member_id) {
      int result = dyd.memberIdChk(member_id);
      return result;
   }

   // 회원등록
   @Override
   public int memberInsert(Member member) {
      System.out.println("DY_ServiceImpl memberInsert start...");
      int result = 0;
      result = dyd.memberInsert(member);
      return result;
   }

   @Override
   public Member findById(int member_key) {
      System.out.println("DY_ServiceImpl findById start...");
      return dyd.findById(member_key);
   }

   @Override
   public void updateMemberInfo(Member member) {
      System.out.println("DY_ServiceImpl updateMemberInfo start...");
      dyd.updateMemberInfo(member);

   }

   @Override
   public void deleteMemberInfo(int member_key) {
      System.out.println("DY_ServiceImpl deleteMemberInfo start...");
      dyd.deleteMemberInfo(member_key);
   }


   @Override
   public List<Payment> getMonthlySales(String startDate, String endDate, int startIndex, int pageSize) {
       Map<String, Object> params = new HashMap<>();
       params.put("startDate", startDate);
       params.put("endDate", endDate);
       params.put("startIndex", startIndex);
       params.put("pageSize", pageSize);
       return dyd.getMonthlySales(params);
   }

   @Override
   public List<Payment> getPaymentsByDateAndMonth(String selectedDate, String dayOrMonth) {
      List<Payment> payments = null;
       if ("day".equals(dayOrMonth)) {
           // 일별 매출 조회 쿼리 실행
           payments = dyd.findPaymentsByDate(selectedDate);
       } else if ("month".equals(dayOrMonth)) {
           // 월별 매출 조회 쿼리 실행
           payments = dyd.findPaymentsByMonth(selectedDate);
       } else {
           throw new IllegalArgumentException("Invalid value for 'dayOrMonth'");
       }
       return payments;
   }

   @Override
   public int getSalesTotal(Payment payment) {
      System.out.println("DY_ServiceImpl getDailySalesTotal start...");
      int salesTotal = dyd.getSalesTotal(payment);
      
      return salesTotal;
   }

   @Override
   public List<Payment> getPaymentSales(Payment payment) {
      System.out.println("DY_ServiceImpl getDailySales start...");
      List<Payment> dailyPaymentList = dyd.getPaymentSales(payment);
      
      return dailyPaymentList;
   }

   

   

   
}