package com.oracle.team2.controller;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.oracle.team2.model.EcCode;
import com.oracle.team2.model.Member;
import com.oracle.team2.service.JM_Service.JM_Service_Interface;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class JM_Controller {
	private final JM_Service_Interface  jms;
	private final CommonController cc;
	private final BCryptPasswordEncoder pe;	
	
	@RequestMapping(value = "loginViews")
	public String login() {
		return "JM_views/login";
	}
	
	@PostMapping("/login") // AJAX 요청을 처리하는 메소드에는 @PostMapping 어노테이션을 사용합니다.
    @ResponseBody // AJAX 요청에 대한 응답을 직접 작성하기 위해 @ResponseBody 어노테이션을 사용합니다.
    public String login(@RequestParam("username") String username, @RequestParam("password") String password,
    		Member member, HttpServletRequest request) {
	    System.out.println("로그인 컨트롤러 시작...");

	    // 사용자가 입력한 비밀번호를 암호화하여 저장
	    String encodedPassword = pe.encode(password);
	    System.out.println("encodedPassword--->"+encodedPassword);
	    // 입력된 아이디로 회원 정보를 조회
	    member = jms.login(username);

	    // 회원 정보가 조회되고, 입력된 암호화된 비밀번호와 DB에 저장된 암호화된 비밀번호가 일치하는지 확인
	    if (member != null && pe.matches(password, member.getMember_pw())) {
	        HttpSession session = request.getSession();

	        // 세션에 회원 정보 저장
	        session.setAttribute("member_key", member.getMember_key());
	        session.setAttribute("member_name", member.getMember_name());
	        session.setAttribute("member_email", member.getMember_email());
	        session.setAttribute("member_id", member.getMember_id());
	        session.setAttribute("member_tel", member.getMember_tel());
	        session.setAttribute("member_makey", member.getMember_makey());
	        session.setAttribute("member_mikey", member.getMember_mikey());
	        session.setMaxInactiveInterval(60 * 30);

	        return "success"; // 로그인 성공 시 'success' 문자열을 반환
	    } else {
	        System.out.println("로그인 실패!");
	        return "failure"; // 로그인 실패 시 'failure' 문자열을 반환
	    }
    }
	
	@RequestMapping("logout")
	public String logout(HttpServletRequest request) {
		HttpSession session = request.getSession();
		
		session.invalidate();
		return "index";
		
	}
	
	// 아이디 찾기 페이지로 이동하는 요청을 처리합니다.
    @RequestMapping("findIDView")
    public String findUsernameView() {
    	//Member member = new Member();
    	//member.getMember_id();
        return "JM_views/findID"; // 아이디 찾기 페이지로 이동합니다.
    }
    
    // 비밀번호 찾기 페이지로 이동하는 요청을 처리합니다.
    @RequestMapping("findPasswordView")
    public String findPasswordView() {
        return "JM_views/findPassword"; // 비밀번호 찾기 페이지로 이동합니다.
    }
    
    // 아이디 찾기 요청을 처리하는 메소드입니다.
    @PostMapping("/findID")
    @ResponseBody
    public String findID(@RequestParam("name") String name, @RequestParam("email") String email,
            Member member, Model model) {
    	System.out.println("아이디 찾기 컨트롤러 시작...");
    	 member = null;
         
         member = jms.findID(name, email);
         System.out.println("찾은 아이디를 컨트롤러 까지 갖고오나???"+member.getMember_id());
         if (member != null && member.getMember_name().equals(name) && member.getMember_email().equals(email)) {
             
        	 ObjectMapper objectMapper = new ObjectMapper();
        	 String jsonResult = "";
        	 try {
        		 jsonResult = objectMapper.writeValueAsString(member);
        	 } catch(JsonProcessingException e) {
        		 e.printStackTrace();
        		 return "error";
        	 }	
        	 
             return jsonResult; // 아이디찾기 성공 시 'success' 문자열을 반환합니다.
         } else {
             System.out.println("아이디찾기 실패!");
             return "failure"; // 아이디찾기 실패 시 'failure' 문자열을 반환합니다.
         }
     }
        // 여기에 아이디 찾기 로직을 추가합니다.
        // 예를 들어, 입력된 이름과 이메일 주소를 기반으로 아이디를 찾습니다.
        // 아이디를 찾은 경우 해당 아이디를 반환하고, 없는 경우 실패 메시지를 반환합니다.
        
        
        // 아이디를 찾지 못한 경우
        // return "failure"; // 아이디를 찾지 못한 경우에는 실패 메시지를 반환합니다.


    @ResponseBody
    @PostMapping("/findPassword")
    public String findPassword(@RequestBody Member member){
        return jms.findPassword(member);
    }

    @ResponseBody
    @PostMapping("/findPasswordConfirm")
    public String findPasswordConfirm(@RequestBody EcCode eccode){
        return jms.findPasswordConfirm(eccode);
    }

    @ResponseBody
    @PostMapping("/password")
    public String updatePassword(@RequestBody Member member, @RequestParam(name = "password") String password){
    	System.out.println("입력된 패스워드 그대로 들고오나???"+password);
        return jms.updatePassword(member, password);
    }
	
	
}
