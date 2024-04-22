package com.oracle.team2.dao.JY_Dao;

import java.util.List;

import com.oracle.team2.model.StFile;
import com.oracle.team2.model.Student;
import com.oracle.team2.model.Study1;

public interface JY_Dao_Interface {

	int condTotalStudy(Study1 study);

	List<Study1> studyGroupAppSearch(Study1 study);

	int totalStFile(StFile stFile);

	List<StFile> stFileList(StFile stFile);

	int stFileInsert(StFile stFile);

	StFile stFileDetail(int stfile_key);

	int stFileDelete(int stfile_key);

	int stFileUpdate(StFile stFile);

	int studyGroupApp(Student student);

	boolean searchMyApp(Student student);

	List<Study1> studyJoinApproval(Study1 study);

	List<Study1> studyJoinAppForm(Study1 study);

	int approveJoin(Student student);

	Student searchMxPerson(Student student);
	
}
