package com.oracle.team2.controller;


import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.oracle.team2.model.Deployment;
import com.oracle.team2.model.Homework;
import com.oracle.team2.model.Member;
import com.oracle.team2.model.Student;
import com.oracle.team2.model.Study1;
import com.oracle.team2.service.MW_Service.MW_Paging;
import com.oracle.team2.service.MW_Service.MW_Paging2;
import com.oracle.team2.service.MW_Service.MW_Service_Interface;

import ch.qos.logback.core.recovery.ResilientSyslogOutputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class MW_Controller {
	private final MW_Service_Interface mws;
	private final CommonController cc;
	
	
	// 숙제 등록 페이지 맵핑
	@RequestMapping(value = "/insertHomework")
	public String getInsertHomework(HttpSession session, Model model, Homework homework) {
		// 학습그룹정보 갖고오기
		System.out.println("그룹명들을 갖고와볼까???");
		int member_key = (int) session.getAttribute("member_key");
		System.out.println("세션의 멤버키를 갖고오나???"+member_key);
				List<Study1> groupList = mws.getGroupList(member_key);
				System.out.println("그룹리스트의 사이즈-->"+groupList.size());
						
				model.addAttribute("groupList", groupList);
				
				// 현재 년도부터 10년 후까지의 연도 목록 생성
		        List<Integer> years = new ArrayList<>();
		        int currentYear = LocalDate.now().getYear();
		        for (int i = currentYear; i <= currentYear + 10; i++) {
		            years.add(i);
		        }
		        model.addAttribute("years", years);

		        // 1월부터 12월까지의 월 목록 생성
		        List<Integer> months = new ArrayList<>();
		        for (int i = 1; i <= 12; i++) {
		            months.add(i);
		        }
		        model.addAttribute("months", months);
		        
		        // 숙제정보 가져와서 페이징처리
		        List<Homework> homeworkList = mws.getHomeworkList(member_key);
		        
		        int totalHomeworkCount = homeworkList.size();
		        System.out.println("숙제 총개수 -->"+totalHomeworkCount);
		        
		        MW_Paging page = new MW_Paging(totalHomeworkCount, homework.getCurrentPage());
		        homework.setStart(page.getStart());
		        homework.setEnd(page.getEnd());
		        homework.setMember_key(member_key);
		        
		        List<Homework> pageHomeworkList = mws.getHomeworkPage(homework);
		        
		        model.addAttribute("pageHomeworkList", pageHomeworkList);
		        model.addAttribute("totalHomeworkCount", totalHomeworkCount);
		        model.addAttribute("page", page);
		        
		return "MW_views/insertHomework";
	}

	// 숙제 생성
	@ResponseBody
	@PostMapping("/saveHomework")	
    public String saveHomework(@RequestBody Homework homework) {
        // request 객체를 통해 숙제 정보에 접근 가능
        System.out.println("숙제명: " + homework.getHomework_name());
        System.out.println("숙제 내용: " + homework.getHomework_content());
        // 학습 그룹, 학습 레벨, 제출 기한 등도 마찬가지로 접근 가능
        
        int result = mws.saveHomework(homework);
        if(result > 0) {
        	return "success";
        } else {
        	return "fail";
        }
    }
	
	// 숙제전송 페이지 로드
	@RequestMapping("homeworkSend")
	public String homeworkSend(HttpSession session, Model model) {
		int member_key = (int) session.getAttribute("member_key");
		System.out.println("세션의 멤버키를 갖고오나???"+member_key);
		List<Study1> groupList = mws.getGroupList(member_key);
		System.out.println("그룹리스트의 사이즈-->"+groupList.size());
						
		model.addAttribute("groupList", groupList);
		
		return "MW_views/homeWorkSend";
	}
	
	// 숙제전송 페이지의 그룹별 학생 리스트 조회
	@ResponseBody
	@PostMapping("/studentList")
	public String studentList(@RequestParam("study_key") int study_key, Model model) {
		System.out.println("홈웤샌드 컨트롤러 스터디키 갖고 오나??? "+study_key);
		List<Student> studentList = mws.getStudentList(study_key);
		System.out.println("학생리스트 사이즈----> "+studentList.size());
		model.addAttribute("studentList", studentList);
		System.out.println(model.getAttribute("studentList"));
		
		// ObjectMapper를 사용하여 객체를 JSON 문자열로 변환
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonResult = "";
        try {
            jsonResult = objectMapper.writeValueAsString(studentList);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            // 예외 처리
            return "error"; // 예외 발생 시 클라이언트에 에러 메시지를 반환할 수 있습니다.
        }

        // JSON 형식의 학습자 정보를 클라이언트에 반환
        return jsonResult;
	}
	
	// 숙제전송 페이지의 숙제 리스트 조회
	@ResponseBody
	@PostMapping("/homeworkList")
	public String getHomeworkList(@RequestParam("study_key") int study_key, HttpSession session) {
		System.out.println("홈웍리스트 뽑아오자 컨트롤러~~~");
		System.out.println("스터디키는 뽑아왔나???>>>"+study_key);
		int member_key = (int) session.getAttribute("member_key");
		List<Homework> homeworkList = null;
		if(study_key == 0) {
			homeworkList = mws.getHomeworkList(member_key);
		} else {
			homeworkList = mws.getHomeworkList2(study_key);
		}
		
		// ObjectMapper를 사용하여 객체를 JSON 문자열로 변환
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonResult = "";
		try {
			jsonResult = objectMapper.writeValueAsString(homeworkList);
		} catch(JsonProcessingException e) {
			e.printStackTrace();
			return "error";
		}
		// JSON 형식의 숙제리스트 정보를 클라이언트에 반환
        return jsonResult;
		
	}
	
	// 숙제 전송하기
	@ResponseBody
	@PostMapping("/sendHomework")
	public String sendHomework(@RequestBody Deployment deployment) {
		System.out.println("숙제전송 다왔따 체크박스값들 갖고오나??? "+Arrays.toString(deployment.getCheckboxValues()));
		System.out.println("숙제전송 다왔따 라디오값 갖고오나??? "+deployment.getRadioValue());
		
		int[] member_keys = deployment.getCheckboxValues();
		int result = 0;
		for (int i = 0; i < member_keys.length; i++) {
		    
		        deployment.setHomework_key(deployment.getRadioValue());
		        deployment.setMember_key(member_keys[i]);
		        result = mws.insertDeployment(deployment);
		}
		if(result > 0) {
        	return "success";
        } else {
        	return "fail";
        }
	}
	
	
	// 내 숙제조회 페이지 로드
	@RequestMapping("myHomework")
	public String myHomework(HttpSession session, Model model, Deployment deployment) {
		System.out.println("내숙제조회 컨트롤러~~~~~~~~");
		int member_key = (int)session.getAttribute("member_key");
		System.out.println("세션의 멤버키-->"+member_key);
		// 내숙제조회 페이징 처리
		int totalMyHomeworkCount = mws.getHomeworkCount(member_key);
		System.out.println("총 개수--->"+totalMyHomeworkCount);
		
		MW_Paging page = new MW_Paging(totalMyHomeworkCount, deployment.getCurrentPage());
		deployment.setStart(page.getStart());
		deployment.setEnd(page.getEnd());
		deployment.setMember_key(member_key);
		
		List<Deployment> myHomeworkList = mws.getMyHomework(deployment);
		
		
		model.addAttribute("myHomeworkList", myHomeworkList);
		model.addAttribute("totalMyHomeworkCount", totalMyHomeworkCount);
		model.addAttribute("page", page);
		
		return "MW_views/myHomework";
	}
	
	
	// 숙제 제출전 입력내용 저장
	@ResponseBody
	@PostMapping("/deploymentSave")
	public void deploymentSave(@RequestBody Deployment deployment, HttpSession session) {
		System.out.println("자 숙제를 풀고 제출전 임시저장하는 컨트롤러야");
		deployment.setMember_key((int)session.getAttribute("member_key"));
		mws.deploymentSave(deployment);
	}
	
	
	// 숙제 제출
	@ResponseBody
	@PostMapping("/deploymentSubmit")
	public void deploymentSubmit(@RequestParam("homework_key") int homework_key, HttpSession session) {
		System.out.println("숙제를 제출하는 컨트롤러~~~ 제출제출제출제출제출제출제출");
		System.out.println("홈웤키 갖고오나~~~끼얏호"+homework_key);
		Deployment deployment = new Deployment();
		deployment.setHomework_key(homework_key);
		deployment.setMember_key((int)session.getAttribute("member_key"));
		
		mws.deploymentSubmit(deployment);
		
	}
	
	// 숙제평가 페이지 로드
	@RequestMapping("homeworkGrade")
	public String homeworkGradePage(HttpSession session, Model model) {
		int member_key = (int) session.getAttribute("member_key");
		System.out.println("세션의 멤버키를 갖고오나???"+member_key);
		List<Study1> groupList = mws.getGroupList(member_key);
		System.out.println("그룹리스트의 사이즈-->"+groupList.size());
						
		model.addAttribute("groupList", groupList);
		
		
		return "MW_views/homeWorkEGrade";
	}
	
	
	// 전송한 숙제리스트 조회 
	@ResponseBody
	@PostMapping("/sendHomeworkList")
	public String getSendHomeworkList(@RequestParam("study_key") int study_key, HttpSession session) {
		System.out.println("홈웍리스트 뽑아오자 컨트롤러~~~");
		System.out.println("스터디키는 뽑아왔나???>>>"+study_key);
		int member_key = (int) session.getAttribute("member_key");
		List<Homework> sendHomeworkList = null;
		if(study_key == 0) {
			sendHomeworkList = mws.getSendHomeworkList1(member_key);
		} else {
			sendHomeworkList = mws.getSendHomeworkList2(study_key);
		}
		
		// ObjectMapper를 사용하여 객체를 JSON 문자열로 변환
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonResult = "";
        if(sendHomeworkList.size() > 0) {
        	try {
    			jsonResult = objectMapper.writeValueAsString(sendHomeworkList);
    		} catch(JsonProcessingException e) {
    			e.printStackTrace();
    			return "error";
    		}
        } else jsonResult = null;
		
		// JSON 형식의 숙제리스트 정보를 클라이언트에 반환
        return jsonResult;
		
	}
	
	// 숙제 제출내역 조회
	@ResponseBody
	@PostMapping("/submitStudentList")
	public String getSubmitStudentList(@RequestParam("homework_key") int homework_key, HttpSession session) {
		System.out.println("숙제 제출내역 조회조회조회조회조회조회조회조회조회조회조회조회조회 컨트롤러");
		System.out.println("홈워크 키--->"+homework_key);
		List<Deployment> submitStudentList = mws.getSubmitStudentList(homework_key);
		
		ObjectMapper objectMapper = new ObjectMapper();
		String jsonResult = "";
		if(submitStudentList.size() > 0) {
			try {
				jsonResult = objectMapper.writeValueAsString(submitStudentList);
			} catch(JsonProcessingException e) {
				e.printStackTrace();
				return "error";
			}
		} else jsonResult = null;	
		
		return jsonResult;
	}
	
	// 숙제 성적 저장
	@ResponseBody
	@PostMapping("/saveGrade")
	public String getSaveGrade(@RequestBody List<Deployment> deployment) {
		System.out.println("학생의 숙제를 평가해보자 컨트롤러~~~~~~~~~~~~~~");
		System.out.println("사이즈--->"+deployment.size());
		Deployment deploymentArray = null;
		for(int i=0; i<deployment.size(); i++) {
			deploymentArray = deployment.get(i);
			System.out.println(deploymentArray);
			
			mws.saveGrades(deploymentArray);
		}
		return null;
	}
	
	// 내 성적 조회 페이지 로드
	@RequestMapping("myHomeworkGrade")
	public String myHomeworkGrade(HttpSession session, Model model, Deployment deployment) {
		System.out.println("내 성적은 얼마나 나왔을까???????? 컨트롤러");
		int member_key = (int)session.getAttribute("member_key");
		int hwTotal = mws.getMyHomeworkGradeTotal(member_key);
		System.out.println("총개수---->"+hwTotal);
		
		MW_Paging page = new MW_Paging(hwTotal, deployment.getCurrentPage());
		deployment.setStart(page.getStart());
		deployment.setEnd(page.getEnd());
		deployment.setMember_key(member_key);
		
		List<Deployment> myGradeList = mws.getMyGradeList(deployment);
		System.out.println("ㅇㅇㄴㄴㅁㅇㄴㅁㅇㄴㅁ"+myGradeList.size());
		
		model.addAttribute("myGradeList", myGradeList);
		model.addAttribute("page", page);
		
		
		return "MW_views/myHomeworkGrade";
	}
	
	// 숙제 상세보기 페이지 로드
	@GetMapping("/detailHomework")
	public String showDetailHomework(@RequestParam("homeworkKey") int homeworkKey, Model model) {
		System.out.println("숙제 상세화면 컨트롤러~~~~~~~~~~~~~~~~");
		System.out.println(homeworkKey);
		int hk = homeworkKey;
		System.out.println("갖고와졌나 확인은 해볼까나??????"+hk);
		
		Homework homework = mws.getHomeworkDetail(hk);
		System.out.println(homework.getHomework_date());
		
		// homework 객체에서 날짜를 가져옴
		// String을 LocalDate로 변환
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-M-d");
	    LocalDate homeworkDate = LocalDate.parse(homework.getHomework_date(), formatter);
	    // 년, 월, 일로 분리
	    int year = homeworkDate.getYear();
	    int month = homeworkDate.getMonthValue();
	    int day = homeworkDate.getDayOfMonth();
	    System.out.println(day);
	    model.addAttribute("getYear", year);
	    model.addAttribute("getMonth", month);
	    model.addAttribute("getDay", day);
	    
	    // 현재 년도부터 10년 후까지의 연도 목록 생성
        List<Integer> years = new ArrayList<>();
        int currentYear = LocalDate.now().getYear();
        for (int i = currentYear; i <= currentYear + 10; i++) {
            years.add(i);
        }
        model.addAttribute("years", years);

        // 1월부터 12월까지의 월 목록 생성
        List<Integer> months = new ArrayList<>();
        for (int i = 1; i <= 12; i++) {
            months.add(i);
        }
        model.addAttribute("months", months);
		
		model.addAttribute("homework", homework);
		
	    return "MW_views/homeworkDetail";
	}
	
	// 숙제 수정
	@ResponseBody
	@PostMapping("/updateHomework")
	public String homeworkUpdate(@RequestBody Homework homework) {
		System.out.println("숙제를 수정해보자 컨트로오오오오오오러");
		int result = 0;
		result = mws.updateHomework(homework);
		if(result > 0) {
			return "success";
		} else if(result < 0) {
			return "send";
		} else return "fail";
	}
	
	// 숙제 삭제
	@ResponseBody
	@PostMapping("/deleteHomework")
	public String homeworkDelete(@RequestParam("homework_key") int homeWorkKey) {
		System.out.println("숙제를 삭제하는 컨트으으으으으으으올러");
		int result = 0;
		result = mws.deleteHomework(homeWorkKey);
		
		if(result > 0) {
			return "success";
		} else {
			return "fail";
		}
		
	}
	
	// 운영자의 회원관리 페이지 로드
	@RequestMapping("selectMember")
	public String selectMember(HttpSession session, Model model, Member member) {
		System.out.println("회원관리 페이지 컨트로로롤로로롤로노아조로");
		
//		if((int)session.getAttribute("member_mikey") == 40) {
//			int totalCount = mws.getMemberCount();
//			System.out.println(totalCount);
//			MW_Paging2 page = new MW_Paging2(totalCount, member.getCurrentPage());
//			member.setStart(page.getStart());
//			member.setEnd(page.getEnd());
//			
//			List<Member> memberList = mws.getMemberList(member);
//			model.addAttribute("memberList", memberList);
//			model.addAttribute("totalCount", totalCount);
//			model.addAttribute("page", page);
//		}
		
		
		return "MW_views/selectMember";
	}
	
	// 운영자의 회원관리 페이지에서 검색기능
	@ResponseBody
	@PostMapping("/search")
	public String searchMember(@RequestBody Member member, Model model, HttpSession session) {
		System.out.println("회원검색용 컨트로로로로로로롤로노아조로");
		System.out.println(member.getIsfree());
		System.out.println(member.getStartDate());
		System.out.println(member.getEndDate());
		System.out.println(member.getMikey());
		System.out.println(member.getOpt());
		System.out.println(member.getKeyword());
		
		
		List<Member> searchMem = mws.memberSearch(member);

		// ObjectMapper를 사용하여 객체를 JSON 문자열로 변환
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonResult = "";
        System.out.println("searchMem----->"+searchMem);
		if(searchMem.size() > 0) {
			try {
				jsonResult = objectMapper.writeValueAsString(searchMem);
				System.out.println("jsonResult---->"+jsonResult);
			} catch(JsonProcessingException e) {
				e.printStackTrace();
				return "error";
			}

		} else jsonResult = null;
		return jsonResult;
	}
	
	// 회원 상세보기 페이지 로드
	@GetMapping("/detailMember")
	public String detailMemberPage(@RequestParam("memberKey") int member_key, Model model) {
		
		System.out.println("회원상세보기 페이지로드 컨트롤로노아조로");
		System.out.println("멤버키 갖고오나????"+member_key);
		
		Member member = mws.getMemberDetail(member_key);
		System.out.println("회원 갖고오나???"+member);
		
		model.addAttribute("MemberDetail", member);
		
		
		return "MW_views/memberDetail";
	}
	
	
	
	
}//class 
