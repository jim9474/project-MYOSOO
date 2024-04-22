package com.oracle.team2.controller;


import java.util.Arrays;
import java.util.Base64;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.oracle.team2.model.StFile;
import com.oracle.team2.model.Student;
import com.oracle.team2.model.Study1;
import com.oracle.team2.service.JY_Service.JY_Paging;
import com.oracle.team2.service.JY_Service.JY_Service_Interface;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class JY_Controller {
	private final JY_Service_Interface  jys;
	private final CommonController cc;
	
	/** 학습자료 등록 폼 -관리자- */
	@RequestMapping(value = "stFileInsertView")
	public String stFileInsertForm() {
		
		return "JY_views/stFile";
	}
	
	/** 학습자료 등록하기 -관리자- */
	@PostMapping(value = "stFileInsert")
	public String stFileInsert(StFile stFile, Model model, HttpSession session, 
	        @RequestParam(value = "file") MultipartFile file) {    
	    System.out.println("JY_Controller stFileInsert start...");
	    try {
	        // 파일 업로드 처리
	        if (!file.isEmpty()) {
	            byte[] thumbnail = file.getBytes(); // 업로드된 파일을 바이트 배열로 변환
	            stFile.setStfile_img(thumbnail); // 썸네일 데이터 설정
	        }
	        
	        int member_key = (int) session.getAttribute("member_key");
	        stFile.setMember_key(member_key);
	        System.out.println("JY_Controller stFileInsert stFile -> " + stFile);
	        
	        int insertStFile = jys.stFileInsert(stFile);
	        if (insertStFile < 0) {
	        	model.addAttribute("msg", "등록실패 다시 확인해주세요");            
	        } 
	    } catch (Exception e) {
	        // 예외 발생 시 처리
	        System.out.println("JY_Controller stFileInsert Exception -> " + e.getMessage());
	    }
	    
	    return "forward:stFileInsertView";
	}
	
	/** 학습자료 리스트 -관리자- */
	@RequestMapping(value = "stFileListView")
	public String stFileList(StFile stFile, Model model) {
		System.out.println("JY_Controller stFileList Start...");
		
		int totalStFile = jys.totalStFile(stFile);
		System.out.println("JY_Controller Start totalStFile -> " + totalStFile);
		
		JY_Paging page = new JY_Paging(totalStFile, stFile.getCurrentPage());
		stFile.setStart(page.getStart());
		stFile.setEnd(page.getEnd());
				
		List<StFile> listStFile = jys.stFileList(stFile);
		System.out.println("JY_Controller listStFile.size() -> " + listStFile.size());
		
		// 이미지를 Base64로 인코딩하여 모델에 추가
	    for (StFile stfile : listStFile) {
	        byte[] imgBytes = stfile.getStfile_img();
	        if (imgBytes != null) {
	            String imgBase64 = Base64.getEncoder().encodeToString(imgBytes);
	            stfile.setStfile_imgEncod(imgBase64);
	        }
	    }
		
		model.addAttribute("totalStFile", totalStFile);
		model.addAttribute("listStFile", listStFile);
		model.addAttribute("stFile", stFile);
		model.addAttribute("page", page);
		
		return "JY_views/stFileList";
	}
	
	/** 학습자료 상세보기 -관리자- */
	@GetMapping(value = "stFileDetailView")
	public String stFileDetail(StFile stFile, Model model) {
		System.out.println("JY_Controller stFileDetail start...");
		System.out.println("JY_Controller stFileDetail stFile -> " + stFile);
		StFile detailStFile = jys.stFileDetail(stFile.getStfile_key());
		
		if (detailStFile != null) {
	        byte[] imgBytes = detailStFile.getStfile_img();
	        if (imgBytes != null) {
	            String imgBase64 = Base64.getEncoder().encodeToString(imgBytes);
	            detailStFile.setStfile_imgEncod(imgBase64);
	        }
	    }
		
		model.addAttribute("detailStFile", detailStFile);
		
		return "JY_views/stFileDetail";
	}
	
	/** 학습자료 지우기 -관리자- */
	@RequestMapping(value = "stFileDelete")
	public String stFileDelete(StFile stFile) {
		System.out.println("JY_Controller stFileDelete start...");
		int deleteStFile = jys.stFileDelete(stFile.getStfile_key());
		
		return "redirect:stFileListView";
	}
	
	@GetMapping(value = "stFileUpdateForm")
	public String stFileUpdateForm(StFile stFile, Model model) {
		System.out.println("JY_Controller stFileUpdateForm start...");
		System.out.println("JY_Controller stFileUpdateForm stFile -> " + stFile);
		StFile updateFormStFile = jys.stFileDetail(stFile.getStfile_key());
		
		model.addAttribute("updateFormStFile", updateFormStFile);
		
		return "JY_views/stFileUpdate";
	}
	
	/** 학습자료 수정하기 -관리자- */
	@PostMapping(value = "stFileUpdate")
	public String stFileUpdate(StFile stFile, Model model, HttpSession session, 
	        @RequestParam(value = "file") MultipartFile file) {
		System.out.println("JY_Controller stFileUpdate start...");
		try {
	        // 파일 업로드 처리
	        if (!file.isEmpty()) {
	            byte[] thumbnail = file.getBytes(); // 업로드된 파일을 바이트 배열로 변환
	            stFile.setStfile_img(thumbnail); // 썸네일 데이터 설정
	        }
	        System.out.println("JY_Controller stFileUpdate stFile -> " + stFile);
	        int updateStFile = jys.stFileUpdate(stFile);
	        if (updateStFile < 0) {
	        	model.addAttribute("msg", "등록실패 다시 확인해주세요");            
	        }
	    } catch (Exception e) {
	        // 예외 발생 시 처리
	        System.out.println("JY_Controller stFileInsert Exception -> " + e.getMessage());
	    }
		
		return "forward:stFileListView";
	}
	
	/** 학습그룹 리스트 -학습자- */
	@RequestMapping(value = "studyGroupAppSearch")
	public String studyGroupNameSearch(Study1 study, Model model) {
		System.out.println("JY_Controller studyGroupNameSearch Start...");
		System.out.println("JY_Controller Start study -> " + study);
	    if (study.getKeyword1() == null && study.getKeyword2() == null) {
	    	study.setCondition(0);
	    } else if (study.getKeyword1() != null && study.getKeyword2() == null) {
	    	study.setCondition(1);
	    } else if (study.getKeyword1() == null && study.getKeyword2() != null) {
	    	study.setCondition(2);
		} else {
	    	study.setCondition(3);
		}
		
		int totalStudy = jys.condTotalStudy(study);
		System.out.println("JY_Controller Start totalStudy -> " + totalStudy);
		
		JY_Paging page = new JY_Paging(totalStudy, study.getCurrentPage());
		study.setStart(page.getStart());
		study.setEnd(page.getEnd());
				
		List<Study1> searchStudyGroupApp = jys.studyGroupAppSearch(study);
		System.out.println("JY_Controller searchStudyGroupApp.size() -> " + searchStudyGroupApp.size());
		
		model.addAttribute("totalStudy", totalStudy);
		model.addAttribute("listStudyGroupApp", searchStudyGroupApp);
		model.addAttribute("study", study);
		model.addAttribute("page", page);
		
		return "JY_views/studyGroupApplication";
	}
	
	/** 학습그룹 신청하기 -학습자- */
	   @RequestMapping(value = "studyGroupApp")
	   public String studyGroupApp(Student student, HttpSession session , Model model) {
	      System.out.println("JY_Controller stFileApp start...");

	      int member_key = (int) session.getAttribute("member_key");
	        student.setMember_key(member_key);
	        System.out.println("JY_Controller stFileApp stFile -> " + student);
	        
	        boolean myAppSearch = jys.searchMyApp(student);
	        Student mxPersonSearch = jys.searchMxPerson(student);
	        String resultMsg = null;
	        System.out.println("JY_Controller stFileApp myAppSearch -> " + myAppSearch);
	        if (myAppSearch) {
	           model.addAttribute("resultMsg" , "이미 가입신청한 학습그룹입니다.");
	           return "forward:studyGroupAppSearch";
	        } else if (mxPersonSearch == null || mxPersonSearch.getStudy_maxperson() <= 0) {
	           model.addAttribute("resultMsg" , "TO가 없습니다.");
	           return "forward:studyGroupAppSearch";
	        } else {
	           int appStudyGroup = jys.studyGroupApp(student);
	          System.out.println("JY_Controller stFileApp appStudyGroup -> " + appStudyGroup);
	          model.addAttribute("resultMsg" , "학습그룹 가입신청이 완료되었습니다.");
	        }

	      return "forward:studyGroupAppSearch";
	   }
	
	/** 학습그룹 승인하기 폼 -교육자- */
	@RequestMapping(value = "studyJoinAppForm")
	public String studyJoinAppForm(Study1 study, Model model, HttpSession session) {
		System.out.println("JY_Controller studyJoinAppForm start...");
		
		int member_key = (int) session.getAttribute("member_key");
		study.setMember_key(member_key);
        System.out.println("JY_Controller stFileApp stFile -> " + study);
		
		List<Study1> sjaForm = jys.studyJoinAppForm(study);
		System.out.println("JY_Controller sjaForm.size() -> " + sjaForm.size());
		
		model.addAttribute("sjaForm", sjaForm);
		model.addAttribute("study", study);
		model.addAttribute("page");
		
		return "JY_views/groupAgree";
	}
	
	/** 내 학습그룹 신청자 리스트 -교육자- */
	@RequestMapping(value = "studyJoinApproval")
	public String studyJoinApproval(Study1 study, Model model, HttpSession session) {
		System.out.println("JY_Controller studyJoinApproval start...");
		
		int member_key = (int) session.getAttribute("member_key");
		study.setMember_key(member_key);
        System.out.println("JY_Controller stFileApp stFile -> " + study);
        
        List<Study1> joinApprovalStudy = jys.studyJoinApproval(study);
		System.out.println("JY_Controller joinApprovalStudy.size() -> " + joinApprovalStudy.size());
		
		if(joinApprovalStudy.isEmpty()) {
	        model.addAttribute("msg", "가입신청이 없습니다.");
	    } else {
	    	study.setGame_name(joinApprovalStudy.get(0).getGame_name());
			study.setStudy_maxperson(joinApprovalStudy.get(0).getStudy_maxperson());
			study.setStudy_appperson(joinApprovalStudy.get(0).getStudy_appperson());
	    }
		
		List<Study1> sjaForm = jys.studyJoinAppForm(study);
		System.out.println("JY_Controller sjaForm.size() -> " + sjaForm.size());

		model.addAttribute("joinApprovalStudy", joinApprovalStudy);
		model.addAttribute("study", study);
		model.addAttribute("sjaForm", sjaForm);
		
		return "JY_views/groupAgree";
	}
	
	/** 내 학습그룹 신청자 승인 -교육자- */
	@RequestMapping(value = "approveJoin")
	public String approveJoin(Student student, Model model) {
	    System.out.println("JY_Controller approveJoin start...");
	    System.out.println("JY_Controller approveJoin student -> " + student);
	    System.out.println("JY_Controller approveJoin checkboxes ->" + Arrays.toString(student.getCheckboxes()));
	    
	    int[] sttmember_key = student.getCheckboxes();
	    int joinApprove = 0;     
	    
	    Student mxPersonSearch = jys.searchMxPerson(student);
	    String msg = null;
	    if (mxPersonSearch == null || mxPersonSearch.getStudy_maxperson() <= 0) {
	        model.addAttribute("msg", "승인가능 인원수를 초과했습니다.");
	    } else {
	        for (int i = 0; i < sttmember_key.length; i++) {
	            student.setMember_key(sttmember_key[i]);
	            student.setStudy_key(student.getStudy_key());
	            joinApprove = jys.approveJoin(student);
	            
	            model.addAttribute("msg", "승인이 성공적으로 완료되었습니다.");
	        }
	    }

	    return "forward:studyJoinApproval";
	}
} // class
