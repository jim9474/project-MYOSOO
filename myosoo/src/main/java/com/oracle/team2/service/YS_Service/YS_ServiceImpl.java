package com.oracle.team2.service.YS_Service;



import java.util.List;

import org.springframework.stereotype.Service;


import com.oracle.team2.dao.YS_Dao.YS_Dao_Interface;
import com.oracle.team2.model.Game1;
import com.oracle.team2.model.Student;
import com.oracle.team2.model.Study2;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class YS_ServiceImpl implements YS_Service_Interface {
	private final YS_Dao_Interface ysd;

	@Override
	public List<Game1> getSelectBox(int member_key) {
		List<Game1>selectBoxList = ysd.getSelectBox(member_key);
		return selectBoxList;
	}

	@Override
	public List<Game1> getSelectBoxDetail(int member_key) {
		List<Game1>selectBoxDetailList = ysd.getSelectBoxDetail(member_key);
		return selectBoxDetailList;
	}



	@Override
	public Game1 getContentsName(Game1 game) {
		System.out.println("YS_ServiceImpl getContentsName start");
		Game1 resultGame = ysd.getContentsName(game);
		return resultGame;
	}

	@Override
	public void studyGroupInsert(Study2 study) {
		System.out.println("YS_ServiceImpl studyGroupInsert start");
		
		ysd.studyGroupInsert(study);
		
	}



	@Override
	public List<Study2> getStudyGroupSelect(int member_key) {
		List<Study2>resultList =null;
		resultList = ysd.getStudyGroupSelect(member_key);
		return resultList;
	}

	@Override
	public Study2 studyGroupSelectUpdateForm(Study2 study) {
		Study2 result = null;
		result = ysd.studyGroupSelectUpdateForm(study);
		return result;
	}

	@Override
	public Study2 getUpateName(Study2 study) {
		Study2 result = null;
		result = ysd.getUpateName(study);
		return result;
	}

	@Override
	public int studyGroupUpdateAll(Study2 study) {
		int result = 0;
		result = ysd.studyGroupUpdateAll(study);
		return result;
	}

	@Override
	public int studyGroupDelete(Study2 study) {
		int result= 0;
		result =ysd.studyGroupDelete(study);
		return result;
	}

	@Override
	public List<Study2> studyDetailselectBoxList(Study2 study) {
		List<Study2>result = null;
		result = ysd.studyDetailselectBoxList(study);
		return result;
	}

	@Override
	public Study2 getstudyGroupOne(Study2 study) {
		Study2 result = ysd.getstudyGroupOne(study);
		return result;
	}

	@Override
	public int getTotaCount(Student student) {
		int totalCnt = ysd.getTotaCount(student);
		return totalCnt;
	}

	@Override
	public List<Student> getStudyGoupMemberList(Student student) {
		List<Student>resultList = ysd.getStudyGoupMemberList(student);
		return resultList;
	}




	


}
