package com.oracle.team2.dao.YS_Dao;

import java.util.List;

import com.oracle.team2.model.Game1;
import com.oracle.team2.model.Student;
import com.oracle.team2.model.Study2;

public interface YS_Dao_Interface {

	List<Game1> getSelectBox(int member_key);

	List<Game1> getSelectBoxDetail(int member_key);

	Game1 getContentsName(Game1 game);

	void studyGroupInsert(Study2 study);

	List<Study2> getStudyGroupSelect(int member_key);

	Study2 studyGroupSelectUpdateForm(Study2 study);

	Study2 getUpateName(Study2 study);

	int studyGroupUpdateAll(Study2 study);

	int studyGroupDelete(Study2 study);

	List<Study2> studyDetailselectBoxList(Study2 study);

	Study2 getstudyGroupOne(Study2 study);

	int getTotaCount(Student student);

	List<Student> getStudyGoupMemberList(Student student);
	
	

}
