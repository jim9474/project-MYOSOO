package com.oracle.team2.controller;

import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.oracle.team2.model.Game2;
import com.oracle.team2.model.Member;
import com.oracle.team2.model.Payment;
import com.oracle.team2.service.MS_Service.MS_Service_Interface;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class MS_Controller {
   private final MS_Service_Interface mss;
   private final CommonController cc;

   @RequestMapping(value = "/badukContentView")
   public String badukContentView(Model model,HttpSession session) {
      Integer member_mikey = (Integer) session.getAttribute("member_mikey");
      System.out.println("miKey" + member_mikey);
      model.addAttribute("member_mikey", member_mikey);
      

      return "MS_views/badukContent";
   }

   @RequestMapping(value = "/siteGuideView")
   public String siteGuideView(Model model,HttpSession session) {
      Integer member_mikey = (Integer) session.getAttribute("member_mikey");
      System.out.println("miKey" + member_mikey);
      model.addAttribute("member_mikey", member_mikey);
      
      
      return "MS_views/siteGuide";
   }
   

   @RequestMapping(value = "/subscriptionApplicationListView")
   public String subscriptionApplicationListView(Model model, HttpSession session) {
      System.out.println("MS_Controller Start subscriptionApplicationListView");
      List<Game2> gameList = mss.gameList();
      System.out.println("MS_Controller gameList.size() -> " + gameList.size());
      Integer member_mikey = (Integer) session.getAttribute("member_mikey");
      System.out.println("miKey" + member_mikey);

      // 이미지를 Base64로 인코딩하여 모델에 추가
      for (Game2 game : gameList) {
         byte[] imgBytes = game.getGame_img();
         if (imgBytes != null) {
            String imgBase64 = Base64.getEncoder().encodeToString(imgBytes);
            game.setGame_imgEncod(imgBase64);
         }
      }


      model.addAttribute("gameList", gameList);
      
      model.addAttribute("member_mikey", member_mikey);

      // 게임 목록 출력을 위한 콘솔 출력
      for (Game2 game : gameList) {
         System.out.println("Game Name: " + game.getGame_name());
         System.out.println("Game Key: " + game.getGame_key());
         // 필요한 다른 정보들도 필요한 경우 여기에 출력 추가
      }

      return "MS_views/subscriptionApplicationList";
   }

   @GetMapping(value = "/gameList", produces = "application/json")
   @ResponseBody
   public List<Game2> gameList(Model model, HttpSession session) {
      System.out.println("MS_Controller Start subscriptionApplicationListView");
      Integer membermiKey = (Integer) session.getAttribute("member_mikey");
      System.out.println("miKey" + membermiKey);

      List<Game2> gameList = mss.gameList();
      System.out.println("MS_Controller gameList.size() -> " + gameList.size());
      System.out.println("MS_Controller gameList.size() -> " + gameList);

      model.addAttribute("member_mikey", membermiKey);
      // 이미지를 Base64로 인코딩하여 모델에 추가
      for (Game2 game : gameList) {
         byte[] imgBytes = game.getGame_img();
         if (imgBytes != null) {
            String imgBase64 = Base64.getEncoder().encodeToString(imgBytes);
            game.setGame_imgEncod(imgBase64);
         }
      }

      // 게임 목록 출력을 위한 콘솔 출력
      for (Game2 game : gameList) {
         System.out.println("Game Name: " + game.getGame_name());
         System.out.println("Game Key: " + game.getGame_key());
         // 필요한 다른 정보들도 필요한 경우 여기에 출력 추가
      }
      model.addAttribute("totalgameCount", gameList);
      model.addAttribute("totalgameCount", gameList.size());

      return gameList;
   }

   @RequestMapping(value = "/gameContentView")
   public String gameContentView(Model model, HttpSession session) {
      Integer membermiKey = (Integer) session.getAttribute("member_mikey");
      Integer memberKey = (Integer) session.getAttribute("member_key");
      System.out.println("mikey!!!!!!" + membermiKey);
      if (memberKey == null) {
         // Display alert message and redirect to login page
         return "redirect:/loginViews?alert=login_required";
      }
      // member_mikey가 40이 아닌 경우 처리
      if (membermiKey != 40) {
         model.addAttribute("message", "게임 등록 권한이 없습니다."); // 오류 메시지 추가
         return "MS_views/gameRegi";
         // 게임 등록 페이지로 반환
      }
      return "MS_views/gameContent";
   }

   @GetMapping(value = "/gameContentPayment")
   @ResponseBody
   public List<Payment> gameContentPayment(@RequestParam("gameKey") int gameKey) {

      System.out.println("MS_Controller Start gameContentPayment ... ");

      List<Payment> payment = mss.contentPayment(gameKey);

      System.out.println("MS_Controller payment info -> " + payment);

      return payment;
   }

   @RequestMapping(value = "/gameRegiView", method = RequestMethod.GET)
   public String gameRegiView(Member member, HttpSession session, Model model) {
      Integer member_mikey = (Integer) session.getAttribute("member_mikey");
      Integer memberKey = (Integer) session.getAttribute("member_key");

      System.out.println("mikey!!!!!!" + member_mikey);
      if (memberKey == null) {
         // 로그인 페이지로 경고 메시지와 함께 리다이렉트
         return "redirect:/loginViews";
      }
      // member_mikey가 40이 아닌 경우 처리
      if (member_mikey != 40) {
         model.addAttribute("message", "게임 등록 권한이 없습니다."); // 오류 메시지 추가
         return "MS_views/gameRegi";
         // 게임 등록 페이지로 반환
      }
      // member_mikey를 모델에 추가
      model.addAttribute("member_mikey", member_mikey);
      return "MS_views/gameRegi";
   }

   @PostMapping(value = "/gameRegi")
   public String gameRegiWrite(Model model, HttpSession session, Game2 game,
         @RequestParam(value = "file") MultipartFile file) {

      // 세션에서 로그인된 사용자 정보를 읽어옴
      String memberId = (String) session.getAttribute("member_id");
      Integer memberKey = (Integer) session.getAttribute("member_key");
      Integer membermiKey = (Integer) session.getAttribute("member_mikey");
      System.out.println("miKey" + membermiKey);
      System.out.println("MS_Controller gameRegiWrite Start... " + game);

      try {

         // 파일 업로드 처리
         if (!file.isEmpty()) {
            byte[] thumbnail = file.getBytes(); // 업로드된 파일을 바이트 배열로 변환
            game.setGame_img(thumbnail); // 게임 객체에 썸네일 데이터 설정
         }

         // 게임 등록 처리
         int insertResult = mss.insertgame(game, memberId);
         System.out.println("member_key!!!!!!!" + memberKey);
         if (insertResult > 0) {
            // 등록 성공 시 메시지 추가
            model.addAttribute("message", "게임이 성공적으로 등록되었습니다.");
         } else {
            // 등록 실패 시 메시지 추가
            model.addAttribute("message", "게임 등록에 실패했습니다.");
         }
      } catch (Exception e) {
         // 예외 발생 시 처리
         e.printStackTrace();
         // 오류 메시지 추가
         model.addAttribute("message", "게임 등록 중 오류가 발생했습니다.");
      }

      return "MS_views/gameRegi";
   }

   @RequestMapping(value = "/paymentView", method = RequestMethod.GET)
   public String paymentView(Model model, HttpSession session, Member member) {
      System.out.println("MS_Controller paymentView Start... ");
      // Check if member_key exists

      Integer member_key = (Integer) session.getAttribute("member_key");
      Integer member_mikey = (Integer) session.getAttribute("member_mikey");
      String member_name = (String) session.getAttribute("member_name");
      String member_tel = (String) session.getAttribute("member_tel");
      System.out.println("member_key!!!!!!!" + member_key);
      if (member_key == null) {
         // Display alert message and redirect to login page
         return "redirect:/loginViews?alert=login_required";
      }
      // member_mikey가 10 또는 30이 아닌 경우 게임 등록 페이지로 이동하지 않음
      if (member_mikey != 10 && member_mikey != 30) {
         return "redirect:/index?alert=insufficient_permission"; // 게임 등록 페이지로 이동하는 대신에 다른 페이지로 이동하도록 설정하세요.
      }

      member.setMember_key(member_key);
      member.setMember_key(member_mikey);
      member.setMember_name(member_name);
      member.setMember_tel(member_tel);
      model.addAttribute("member_mikey", member_mikey);
      System.out.println("MS_Controller paymentView Start... " + member);
      return "MS_views/payment";

   }

   @RequestMapping(value = "/getGamePrices", method = RequestMethod.POST)
   @ResponseBody
   public String getGamePrices(@RequestParam("gameKey") String gameKey) {
      System.out.println("MS_Controller getGamePrices Start... ");
      // 클라이언트로부터 전송된 gameKey 문자열을 파싱하여 게임 키 리스트로 변환합니다.
      List<Integer> gameKeyList = new ArrayList<>();
      String[] keyArray = gameKey.split(",");
      for (String key : keyArray) {
         gameKeyList.add(Integer.parseInt(key.trim())); // 공백 제거 후 정수로 변환하여 리스트에 추가
      }

      // 각 게임 키를 사용하여 데이터베이스 또는 다른 저장소에서 각 게임의 가격을 조회합니다.
      List<Integer> gamePrices = new ArrayList<>();

      for (Integer key : gameKeyList) {
         List<Game2> games = mss.getGamePrice(key);
         for (Game2 game : games) {
            int gamePrice = game.getGame_price();
            gamePrices.add(gamePrice);
         }
      }
      System.out.println("gamePrices---->" + gamePrices);
      ObjectMapper objectMapper = new ObjectMapper();
      String jsonResult = "";
      try {
         jsonResult = objectMapper.writeValueAsString(gamePrices);
      } catch (JsonProcessingException e) {
         e.printStackTrace();
      }

      return jsonResult;
   }

   @ResponseBody
   @RequestMapping(value = "/payment", method = RequestMethod.POST)
   public String paymentInsert(Model model, @RequestBody Payment paymentRequest, HttpSession session,
         HttpServletRequest request) {
      System.out.println("MS_Controller paymentInsert Start... ");
      Integer memberKey = (Integer) session.getAttribute("member_key");
      Integer membermiKey = (Integer) session.getAttribute("member_mikey");

      // membermiKey가 10 또는 30인 경우에만 구매 가능하도록 확인
      if (membermiKey != null && (membermiKey == 10 || membermiKey == 30)) {
         // member_key가 null인지 확인
         if (memberKey == null) {
            // 처리할 수 있는 방법에 따라 로그인 페이지로 리다이렉트 또는 에러 처리
            return "redirect:.loginViews"; // 예시로 "-1"을 반환하여 로그인이 필요하다는 것을 클라이언트에게 알립니다.
         }

         // paymentRequest에 회원 키(member_key) 설정
         paymentRequest.setMember_key(memberKey);

         // paymentRequest에 게임 키(game_key) 설정
         paymentRequest.setGame_key(paymentRequest.getGameKey());

         // paymentRequest에 결제 방법(payment_method) 설정
         paymentRequest.setPayment_method(paymentRequest.getPaymentMethod());

         // 게임 결제 처리
         int insertResult = mss.gamepayment(paymentRequest);

         if (insertResult <= 0) {
            // 저장 실패 시
            model.addAttribute("message", "Payment failed!"); // 모델에 메시지 추가
            return "fail"; // 결제 실패 페이지로 이동
         }
         model.addAttribute("member_mikey", membermiKey);

         // 결제 성공 시
         return "success"; // 성공 여부를 클라이언트에게 반환
      } else {
         // 구매할 수 없는 회원인 경우
         return "구매할 수 없습니다."; //
      }
   }

   @PostMapping("/memberFreeupdate")
   @ResponseBody
   public int MemebrFrUpdate(@RequestBody Member member, HttpSession session) {
      System.out.println("MS_Controller Start MenuReUpdate ... ");
      Integer memberKey = (Integer) session.getAttribute("member_key");
      member.setMember_key(memberKey);
      System.out.println("MS_Controller MenuReUpdate member " + member);
      int MemebrFrUpdate = mss.MemebrFrUpdate(member);
      System.out.println("MS_Controller  MemebrFrUpdate -> " + MemebrFrUpdate);
      return MemebrFrUpdate;
   }

   @RequestMapping(value = "/purchaseHistoryView", method = RequestMethod.GET)
   public String purchaseHistoryView(Payment payment, Model model, HttpSession session) {
      System.out.println("MS_Controller purchaseHistoryView Start... ");
      Integer memberKey = (Integer) session.getAttribute("member_key");

      // 세션에 회원 키가 없는 경우 로그인 페이지로 리다이렉트합니다.
      if (memberKey == null) {
         return "redirect:/loginViews";
      }
      // 회원 키를 사용하여 구매 내역을 가져옵니다.
      List<Payment> paymentList = mss.getAllPurchaseHistory(memberKey);
      /*
       * int totalPayment = mss.totalPayment(payment);
       * System.out.println("MS_Controller Start totalPayment -> " + totalPayment);
       * 
       * MS_Paging page = new MS_Paging(totalPayment, payment.getCurrentPage());
       * payment.setStart(page.getStart()); payment.setEnd(page.getEnd());
       */
      // 이미지를 Base64로 인코딩하여 모델에 추가
      for (Payment paymentlist : paymentList) {
         byte[] imgBytes = paymentlist.getGame_img();
         if (imgBytes != null) {
            String imgBase64 = Base64.getEncoder().encodeToString(imgBytes);
            paymentlist.setGame_imgEncod(imgBase64);
         }
      }

      // 가져온 구매 내역을 모델에 추가합니다.
      model.addAttribute("payment", paymentList);
       model.addAttribute("totalItemCount", paymentList.size());

      // 구매 내역을 표시할 뷰를 반환합니다.
      return "MS_views/purchaseHistory";
   }

// 등록된 게임 삭제
   @RequestMapping(value = "/CardDelete", method = RequestMethod.POST)
   @ResponseBody
   public String gameCardDelete(HttpServletRequest request, @RequestBody Game2 game, Model model, HttpSession session) {
      System.out.println("MS_Controller gameCardDelete Start... ");
      Integer membermiKey = (Integer) session.getAttribute("member_mikey");

      try {

         int deleteCard = mss.deleteCard(game.getGame_key());
         return "MS_views/gameContent";
      } catch (Exception e) {
         // 예외 발생 시 처리할 내용을 여기에 작성합니다.
         e.printStackTrace(); // 예외 내용을 콘솔에 출력합니다.
         return "error"; // 예외 발생 시 클라이언트에게 전달할 응답입니다.
      }
   }

   @PostMapping(value = "/CardUpdate")
   @ResponseBody
   public int CardUpdate(Model model, Game2 game, HttpSession session) {
      System.out.println("MS_Controller Start MenuReUpdate ... ");
      Integer memberKey = (Integer) session.getAttribute("member_key");
      game.setMember_key(memberKey);
      System.out.println("MS_Controller MenuReUpdate Game " + game);

      try {
         // 가격과 구독 기간 변환
         String priceString = String.valueOf(game.getGame_price()).replace("￦", "").trim();
         String submonthString = String.valueOf(game.getGame_submonth()).replaceAll("[^0-9]", "");

         // 문자열을 숫자로 변환
         int price = Integer.parseInt(priceString);
         int submonth = Integer.parseInt(submonthString);

         game.setGame_Price(price);
         game.setGame_submonth(submonth);

         // 게임 정보 업데이트
         int updateResult = mss.UpdateCard(game);
         System.out.println("MS_Controller UpdateCard -> " + updateResult);
         return updateResult;
      } catch (Exception e) {
         // 예외 발생 시 처리
         e.printStackTrace();
         return 0; // 업데이트 실패를 나타내는 값 반환
      }
   }



}
