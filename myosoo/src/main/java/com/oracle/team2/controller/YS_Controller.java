package com.oracle.team2.controller;


import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.oracle.team2.model.Game1;
import com.oracle.team2.model.Member;
import com.oracle.team2.model.Student;
import com.oracle.team2.model.Study2;
import com.oracle.team2.model.Study2;
import com.oracle.team2.service.YS_Service.Paging;
import com.oracle.team2.service.YS_Service.YS_Service_Interface;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class YS_Controller {
	private final YS_Service_Interface yss;
	private final CommonController cc;
	
	// 학습 그룹등록(게임 콘텐츠 검색에 보이는 셀렉트 박스)
	@RequestMapping(value = "/studyGroupView")
	public String studyGroupView(Model model) {
		System.out.println("YS_Controller StudyGroupView start");

		return "YS_views/studyGroup";
	}
	
	// 학습 그룹 리스트 
	@RequestMapping(value = "/studyGroup")
	public String studyGroup(Model model,@RequestBody Game1 game,HttpSession session) {
		System.out.println("YS_Controller studyGroup start");
		System.out.println("값넘어는 값 : "+game.getGame_key());
		int member_key = (int) session.getAttribute("member_key");
		game.setMember_key(member_key);
		List<Game1> gameGroupList = yss.getSelectBoxDetail(member_key);
		
		model.addAttribute("gameGroupListDetail", gameGroupList);
		return "YS_views/studyGroup::#tableid";
	}


	@RequestMapping("/studyGroupInView")
//	public String studyGroupInView(@RequestParam("game_key")String game_key, Model model,HttpSession session) {
	public String studyGroupInView(Model model,Game1 paramGame,HttpSession session) {
		System.out.println("YS_Controller studyGroupInView start");
		System.out.println("YS_Controller studyGroupInView game: "+paramGame);
		System.out.println("YS_Controller studyGroupInView game_key " + paramGame.getGame_key());
		int member_key = (int) session.getAttribute("member_key");
		paramGame.setMember_key(member_key);
		

		Game1 resultGame = yss.getContentsName(paramGame);
		System.out.println("resultGame: "+resultGame);
		
		model.addAttribute("studyGroupIn", resultGame);
		
		
		return "YS_views/studyGroupIn";
	}
	
	@RequestMapping("/studyGroupIn")
	public String studyGroupIn(Study2 study, HttpSession session) {
		System.out.println("YS_Controller studyGroupIn start");
		System.out.println("YS_Controller studyGroupIn 의 값" +study );
		yss.studyGroupInsert(study);
		
		return"YS_views/studyGroupSelect";
	}
	@RequestMapping("/studyGroupSelectView")
	public String studyGroupSelectView(Study2 study) {
		
		
		System.out.println(study.getMember_key());

		return"YS_views/studyGroupSelect";
	}
	
	
	@RequestMapping("studyGroupSelect")
	public String studyGroupSelect(@RequestBody Study2 study, Model model,HttpSession session) {
		System.out.println("YS_Controller studyGroupSelect start");
		System.out.println("YS_Controller studyGroupSelect study : "+study);
		List<Study2> selectList = null;
		
		int member_key = (int) session.getAttribute("member_key");
		study.setMember_key(member_key);
		selectList = yss.getStudyGroupSelect(member_key);
		model.addAttribute("getSelectGroupList", selectList);

		

		return"YS_views/studyGroupSelect::#tableid";
	}
	
	@ResponseBody
	@RequestMapping("studyGroupSelectUpdateForm")
	public String studyGroupSelectUpdate(@RequestBody Study2 study,HttpSession session) {
		System.out.println("YS_Controller studyGroupSelectUpdate start");
		Study2 result = null;
		int member_key = (int) session.getAttribute("member_key");
		study.setMember_key(member_key);
		study.setStudy_key(study.getStudy_key());
		
		System.out.println("YS_Controller studyGroupSelectUpdate study start" + study);
		
		result = yss.studyGroupSelectUpdateForm(study);
		System.out.println("스터디넘"+result.getStudynum());
		
		if (result.getStudynum() == 0)  {
			System.out.println("넘어오는가1 : "+result.getStudynum());
			System.out.println("넘어오는가1 : "+result.getStudy_key());
			return "all";
		}else {
			System.out.println("넘어오는가2 : "  +result.getStudynum());
			System.out.println("넘어오는가2 : "  +result.getStudy_key());
			return "notall";
		}
	}
	
	
	@RequestMapping("studyGroupUpdateAllView")
	public String studyGroupUpdateAllView(Model model, Study2 study,HttpSession session) {
		System.out.println("YS_Controller studyGroupUpdateAllView start" );
		System.out.println("YS_Controller studyGroupUpdateAllView study : " +study );
		
		int member_key = (int) session.getAttribute("member_key");
		study.setMember_key(member_key);
		System.out.println(study.getMember_key());

		
		Study2 resultStudy = yss.getUpateName(study);
		System.out.println("YS_Controller studyGroupUpdateAllView resultStudy start" + resultStudy);
		
		
		model.addAttribute("study", resultStudy);
		
		
		return"YS_views/studyGroupUpdateAll";
	}
	
	@RequestMapping("studyGroupUpdateAll")
	public String studyGroupUpdateAll(Study2 study) {
		System.out.println("YS_Controller studyGroupUpdateAll start");
		System.out.println("YS_Controller studyGroupUpdateAll study start" + study);
		
		int result = 0;
		result = yss.studyGroupUpdateAll(study);
		return "YS_views/studyGroupSelect";
	}
	@ResponseBody
	@RequestMapping("studyGroupDelete")
	public String studyGroupDelete(Study2 study) {
		System.out.println("YS_Controller studyGroupDelete start");
		int result = 0;
		result = yss.studyGroupDelete(study);
		if(result !=0) {
			return "true";
		}
			return "false";
	}
	
	@RequestMapping("/studyGroupSelectDetailView")
	public String studyGroupSelectDetailView(Model model,Study2 study) {
		System.out.println("YS_Controller studyGroupSelectDetailView Hello");
		System.out.println("YS_Controller studyGroupSelectDetailView study start" + study);
		
		
		List<Study2> result = null;
		
		result =yss.studyDetailselectBoxList(study);
		model.addAttribute("studyDetailSelect", result);
		return"YS_views/studyGroupSelectDetail";
	}
	
	@RequestMapping("/studyGroupSelectDetail")
	public String studyGroupSelectDetail(Model model,HttpSession session,Study2 study) {
		System.out.println("YS_Controller studyGroupSelectDetail start");
		System.out.println("YS_Controller studyGroupSelectDetail study" + study);
		
		 
		int member_key = (int) session.getAttribute("member_key");
		study.setMember_key(member_key);
		Study2 result = yss.getstudyGroupOne(study);
		
		System.out.println("YS_Controller studyGroupSelectDetail result 시작 : " + result);
		model.addAttribute("studyGroupOne", result);
		return "YS_views/studyGroupSelectDetail::#tableid";
	}
	@RequestMapping("/studyGroupSelectDetail1")
	public String studyGroupSelectDetail1(Model model,HttpSession session,Student student) {
		System.out.println("YS_Controller studyGroupSelectDetail1 start");
		int totalCnt = yss.getTotaCount(student);
		
		System.out.println("YS_Controller studyGroupSelectDetail1 인원 체크 : " + totalCnt);
		Paging paging = new Paging(totalCnt, student.getCurrentPage());
		student.setStart(paging.getStart());
		student.setEnd(paging.getEnd());
		
		List<Student>groupMemberList = yss.getStudyGoupMemberList(student);
		
		System.out.println("YS_Controller studyGroupSelectDetail1 groupMemberList start" + groupMemberList);
		model.addAttribute("groupMemberList", groupMemberList);
		model.addAttribute("paging", paging);
		model.addAttribute("totalCnt", totalCnt);
		System.out.println(paging);
		System.out.println(totalCnt);
		return"YS_views/studyGroupSelectDetail::#ajaxTbl";
	}
	@RequestMapping("/onload")
	public String onload(Model model,HttpSession session,Student student) {
		System.out.println("YS_Controller onload start");
		int totalCnt = yss.getTotaCount(student);
		
		System.out.println("YS_Controller studyGroupSelectDetail1 인원 체크 : " + totalCnt);
		Paging paging = new Paging(totalCnt, student.getCurrentPage());
		student.setStart(paging.getStart());
		student.setEnd(paging.getEnd());
		
		List<Student>groupMemberList = yss.getStudyGoupMemberList(student);
		
		System.out.println("YS_Controller studyGroupSelectDetail1 groupMemberList start" + groupMemberList);
		model.addAttribute("groupMemberList", groupMemberList);
		model.addAttribute("paging", paging);
		model.addAttribute("totalCnt", totalCnt);
		return"YS_views/studyGroupSelectDetail::#ajaxTbl";
	}
	
	
}//class 
