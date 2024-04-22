package com.oracle.team2.dao.MW_Dao;

import java.util.List;

import com.oracle.team2.model.Deployment;
import com.oracle.team2.model.Homework;
import com.oracle.team2.model.Member;
import com.oracle.team2.model.Student;
import com.oracle.team2.model.Study1;

public interface MW_Dao_Interface {

	List<Study1> getGroupList(int member_key);

	int saveHomework(Homework homework);

	List<Homework> getHomeworkList(int member_key);

	List<Homework> getHomeworkPage(Homework homework);

	List<Student> getStudentList(int study_key);

	List<Homework> getHomeworkList2(int study_key);

	int insertDeployment(Deployment deployment);

	List<Deployment> getMyHomework(Deployment deployment);

	int getHomeworkCount(int member_key);

	void deploymentSave(Deployment deployment);

	void deploymentSubmit(Deployment deployment);

	List<Homework> getSendHomeworkList1(int member_key);

	List<Homework> getSendHomeworkList2(int study_key);

	List<Deployment> getSubmitStudentList(int homework_key);

	void saveGrades(Deployment deploymentArray);

	int getMyHomeworkGradeTotal(int member_key);

	List<Deployment> getMyGradeList(Deployment deployment);

	Homework getHomeworkDetail(int hk);

	int updateHomework(Homework homework);

	int deleteHomework(int homeWorkKey);

	Member getMemberEmail(int studentMk);

	int getMemberCount();

	List<Member> getMemberList(Member member);

	List<Member> memberSearch(Member member);

	Member getMemberDetail(int member_key);

	

}
