package com.oracle.team2.controller;

import java.security.SecureRandom;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.fasterxml.jackson.annotation.JacksonInject.Value;
import com.oracle.team2.model.EcCode;
import com.oracle.team2.model.Member;
import com.oracle.team2.model.Payment;
import com.oracle.team2.service.DY_Service.DY_Paging;
import com.oracle.team2.service.DY_Service.DY_Service_Interface;
import com.oracle.team2.service.JM_Service.JM_Service_Interface;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class DY_Controller {

   private final DY_Service_Interface dys;
   private final CommonController cc;
   private final JavaMailSender mailSender;
   private final JM_Service_Interface jms;
   private final BCryptPasswordEncoder pe;


   // 본인인증
   @RequestMapping(value = "dyCreateUserView")
   public String memberjoin() {
      return "DY_views/signup1";
   }

   // 본인인증 시 이메일 전송
   @ResponseBody
   @RequestMapping(value = "sendEmail", method = RequestMethod.POST)
   public String sendEmail(@RequestParam("member_email") String member_email) {
      System.out.println("DY_Controller sendEmail Start!");
      System.out.println("member_email -> " + member_email);
      int number = dys.sendMail(member_email); // 인증번호

      String num = "" + number;
      return num;
   }

   // 인증 확인 요청 처리
   @ResponseBody
   @PostMapping("/confirmEmail")
   public String confirmEmail(@RequestBody() EcCode eccode) {
      // 서비스 메서드 호출하여 인증 확인 수행
      System.out.println("email eccode ->" + eccode);

      int resultEcCode = dys.confirmEmail(eccode);
      System.out.println("DY_Controller confirmEmail resultEcCode..." + resultEcCode);
      if (resultEcCode > 0) {
         return "success"; // 성공 시 응답
      } else {
         return "fail"; // 실패 시 응답
      }
   }

   // 인증받은 이메일 이름 가지고 넘어가기
   @RequestMapping("/signup2")
   public String signUp2(Model model, Member member) {
      System.out.println("DY_Controller signUp2 start...");
      // Member 객체로 매개변수 받기
      String member_email = member.getMember_email();
      String member_name = member.getMember_name();
      member.setMember_email(member_email);
      member.setMember_name(member_name);

      System.out.println("$$$$$$" + member);
      model.addAttribute("member_name", member_name);
      model.addAttribute("member_email", member_email);

      return "DY_views/signup2"; // 또는 실패 시에는 "failure" 등의 문자열을 반환할 수 있습니다.
   }

   // 아이디 중복체크
   @ResponseBody
   @RequestMapping("/signupIdCheck")
   public int memberIdChk(Model model, @RequestParam("member_id") String member_id) {
      System.out.println("DY_Controller memberIdChk start...");
      System.out.println("DY_Controller memberIdChk member_id   : " + member_id);

      int result = 0;// 아이디 중복없음 : 0
      result = dys.memberIdChk(member_id);
      System.out.println("DY_Controller memberIdChk result   : " + result);

      return result;
   }

   // 회원가입 insert
   @PostMapping("/memberJoin")
   public String memberInsert(Member member) {
      System.out.println("DY_Controller memberInsert start...");

      int memberResult = dys.memberInsert(member);

      return "JM_views/login";
   }

   // 회원정보 조회
   @GetMapping(value = "/memberInfo")
   public String memberInfo(Model model, HttpSession session) {
      System.out.println("DY_Controller memberInfo start...");

      int member_key = (Integer) session.getAttribute("member_key");
      Member member = dys.findById(member_key);
      model.addAttribute("member", member);

      return "DY_views/memberInfo";
   }

   // 회원정보 수정폼 이동
   @RequestMapping(value = "/updateMemberForm")
   public String updateMemberForm(Model model, HttpSession session) {
      System.out.println("DY_Controller updateMemberInfo start...");

      int member_key = (Integer) session.getAttribute("member_key");
      Member member = dys.findById(member_key);
      model.addAttribute("member", member);

      return "DY_views/updateMemberForm";
   }

   // 회원정보 수정
   @PostMapping(value = "/updateMemberInfo")
   public String updateMemberInfo(Member member, HttpSession session) {
      System.out.println("DY_Controller updateMemberInfo start...");
      System.out.println("과연 멤버를 갖고올까?????? " + member);
      
      if (member.getMember_pw() != null && !member.getMember_pw().isEmpty()) {
         // 암호화
         String encodedPassword = pe.encode(member.getMember_pw());
         member.setMember_pw(encodedPassword);
      }
      member.setMember_key((int) session.getAttribute("member_key"));
      dys.updateMemberInfo(member);

      return "redirect:/memberInfo";

   }

   // 회원정보 삭제
   @ResponseBody
   @RequestMapping(value = "/deleteMemberInfo")
   public String deleteMemberInfo(HttpSession session) {
      System.out.println("DY_Controller deleteMemberInfo start...");
      int member_key = (int) session.getAttribute("member_key");
      if (member_key > 0) {
         try {
            dys.deleteMemberInfo(member_key);
            session.invalidate(); // 세션 무효화
            return "success";
         } catch (Exception e) {
            e.printStackTrace();
            return "error";
         }

      }
      return "invalid";
   }

   // 운영자 매출조회폼
   @RequestMapping(value = "/salesManagement")
   public String salesManagement(HttpSession session) {
      System.out.println("DY_Controller salesManagement start...");

      return "DY_views/salesManagement";
   }
   
   // 매출조회
   @GetMapping("/searchSales")
       public String searchSales(Payment payment, Model model) {
         System.out.println("DY_Controller searchSales payment..." + payment);
         List<Payment> paymentList = null;
         DY_Paging dPaging =null;
         System.out.println("DY_Controller searchSales payment.getCurrentPage()..." + payment.getCurrentPage());
         System.out.println("DY_Controller searchSales dPaging..." + dPaging);
         
          if ("daily".equals(payment.getDayOrMonth())) {
             int dailySalesTotal = dys.getSalesTotal(payment);
             dPaging =new DY_Paging(dailySalesTotal, payment.getCurrentPage());
             System.out.println("DY_Controller searchSales dPaging..." + dPaging);
             System.out.println("DY_Controller searchSales dailySalesTotal..." + dailySalesTotal);
             
             payment.setEnd(dPaging.getEnd());
             payment.setStart(dPaging.getStart());
             paymentList = dys.getPaymentSales(payment);
             
          } else if ("monthly".equals(payment.getDayOrMonth())) {
             int monthlySalesTotal = dys.getSalesTotal(payment);
             dPaging =new DY_Paging(monthlySalesTotal, payment.getCurrentPage());
             System.out.println("DY_Controller searchSales dPaging..." + dPaging);
             System.out.println("DY_Controller searchSales monthlySalesTotal..." + monthlySalesTotal);

             payment.setEnd(dPaging.getEnd());
             payment.setStart(dPaging.getStart());
             paymentList = dys.getPaymentSales(payment);
             
          } else {
              throw new IllegalArgumentException("Invalid value for 'dayOrMonth'");
          }

          model.addAttribute("listPayment", paymentList);
          model.addAttribute("dPaging", dPaging);

          return "DY_views/salesManagement::#t_body";
       }
   
   // 매출 상세조회
   @GetMapping("/detail")
   public String showDetailPage(@RequestParam("selectedDate") String selectedDate,
                                @RequestParam("dayOrMonth") String dayOrMonth,
                                Model model) {
	   System.out.println("selectedDate--->"+selectedDate);
      List<Payment> payments = dys.getPaymentsByDateAndMonth(selectedDate, dayOrMonth);
        model.addAttribute("payments", payments);
       return "DY_views/salesDetail";
   }
   
}