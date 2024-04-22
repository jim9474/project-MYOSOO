package com.oracle.team2.controller;


import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.oracle.team2.model.Member;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
public class CommonController {
	
	HttpSession session = null;
	
	// 시작화면
	@RequestMapping(value = "index")
	public String index() {
		return "index";
	}

	
	
	// 진형: 페이지에서 로그인 되었는지 확인 후 로그인 페이지로 이동할 때
	/**
	 * 로그인 되어있는지 체크하는 메소드
	 * @param request Controller의 request
	 * @param afterLoginPage 로그인 성공 후 들어갈 페이지, "": index페이지로 이동
	 * @return true: 로그인 되어있음, false: 로그인 되어있지 않음
	 */
//	public boolean loginCheck(HttpServletRequest request, String afterLoginPage) {
//		System.out.println("CommonController loginCheck start...");
//		HttpSession session = request.getSession(true); // 세션이 있으면 받아오고, 없으면 새로 만듬
//		
//		if(session.getAttribute("userId")==null) {
//			// 로그인 되지 않았으면 요청한 페이지 url을 가지고 로그인 페이지로 이동
//			if(afterLoginPage == null) {
//				afterLoginPage = "";
//			}
//			session.setAttribute("prePage", afterLoginPage);
//			return false;
//		} else {
//			// 로그인 되어있을 때
//			session.setMaxInactiveInterval(1800);	// 세션 유지시간 재설정
//			return true;
//		}
//	}
//	
//	public void setUserSession(HttpSession session, Member member) {
//		
//		session.setAttribute("memberId", member.getMember_id());	//세션에 userId라는 Key로 아이디 담음
//		session.setAttribute("memberName", member.getMember_name());
//		session.setMaxInactiveInterval(1800);	//세션유지시간
//		System.out.println(session.getAttribute("memberId")); //세션에 담긴 아이디 확인
//	}
//	
//	public void deleteSession(HttpServletRequest request) {
//		System.out.println("CommonController deleteSession start");
//		session = request.getSession(true);
//		if(session.getAttribute("userId") != null) {
//			session.invalidate();
//		}
//	}
//	
//	public String getUserIdInSession(HttpServletRequest request) {
//		HttpSession session = request.getSession(false);
//		if(session == null || session.getAttribute("userId") == null)
//			return "";
//		String userId = session.getAttribute("userId").toString();
//		System.out.println("CommonController getUserIdInsession userId->"+ userId);
//		return userId; 
//	}
//	
//	public String getPrePage(HttpServletRequest request) {
//	      String prePage = "";
//	      HttpSession session = request.getSession(true);
//	      if(session.getAttribute("prePage") != null)
//	         prePage = session.getAttribute("prePage").toString();
//	      return prePage;
//	   }

}
