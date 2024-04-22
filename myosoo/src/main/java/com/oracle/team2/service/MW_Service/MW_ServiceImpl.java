package com.oracle.team2.service.MW_Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.oracle.team2.dao.MW_Dao.MW_Dao_Interface;
import com.oracle.team2.model.Deployment;
import com.oracle.team2.model.Homework;
import com.oracle.team2.model.Member;
import com.oracle.team2.model.Student;
import com.oracle.team2.model.Study1;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MW_ServiceImpl implements MW_Service_Interface {
	@Autowired
	private JavaMailSender mailSender;	// 이메일을 전송하는데 사용되는 객체

	private final MW_Dao_Interface mwd;

	@Override
	public List<Study1> getGroupList(int member_key) {
		System.out.println("서비스까지 왔나???");
		List<Study1> groupList = mwd.getGroupList(member_key);
		return groupList;
	}

	@Override
	public int saveHomework(Homework homework) {
		System.out.println("숙제저장 서비스다 이거");
		int result = mwd.saveHomework(homework);
		return result;
	}

	@Override
	public List<Homework> getHomeworkList(int member_key) {
		System.out.println("숙제리스트 서비스다 이거야");
		List<Homework> homeworkList = mwd.getHomeworkList(member_key);
		return homeworkList;
	}

	@Override
	public List<Homework> getHomeworkPage(Homework homework) {
		// 특정 페이지에 해당하는 숙제 목록을 DAO에서 가져옴
        return mwd.getHomeworkPage(homework);
	}

	@Override
	public List<Student> getStudentList(int study_key) {
		System.out.println("홈웤샌드 서비스다");
		System.out.println("여기까진 스터디키 갖고왔나--? "+study_key);
		List<Student> studentList = mwd.getStudentList(study_key);
		
		return studentList;
		
	}

	@Override
	public List<Homework> getHomeworkList2(int study_key) {
		System.out.println("홈웤리스트를 뽑아오는 다오다 임마");
		System.out.println("여기까진 스터디키 갖고왔나--? "+study_key);
		List<Homework> homeworkList = mwd.getHomeworkList2(study_key);
		
		return homeworkList;
	}

	@Override
	public int insertDeployment(Deployment deployment) {
		System.out.println("홈웤 디플로이에 인서트 떄려박는 서비스야");
		System.out.println("여기까지 파라미터들 갖고왔나 볼까???? "+deployment.getHomework_key()+", "+deployment.getMember_key());
		int studentMk = deployment.getMember_key();
		int result = 0;
		result = mwd.insertDeployment(deployment);
		if(result > 0) {
			Member member = new Member();
			member = mwd.getMemberEmail(studentMk);
			System.out.println(member.getMember_email());
			if(member.getMember_email() != null) {
				SimpleMailMessage message = new SimpleMailMessage();
				message.setTo(member.getMember_email());
				message.setSubject("묘수의 숙제가 도착했습니다.");
				message.setText("도착도착도착도착도착도착도착도착도착도착도착도착");
				mailSender.send(message);
			} else System.out.println("이메일이 없는데???");
		} else System.out.println("이미 숙제를 전송했습니다");
		
		return result;
	}

	@Override
	public List<Deployment> getMyHomework(Deployment deployment) {
		System.out.println("내숙제조회 서비스 작동하냐~~~~~~~~~~~~");
		System.out.println("서비스 파라미터 오냐????"+deployment.getMember_key());
		
		List<Deployment> myHomeworkList = mwd.getMyHomework(deployment);
		
		return myHomeworkList;
	}

	@Override
	public int getHomeworkCount(int member_key) {
		
		return mwd.getHomeworkCount(member_key);
	}

	@Override
	public void deploymentSave(Deployment deployment) {
		System.out.println("서비스까지 잘넘어왔나 볼까???");
		System.out.println("숙제키: "+deployment.getHomework_key());
		System.out.println("멤버키: "+deployment.getMember_key());
		System.out.println("제출내용: "+deployment.getDeployment_content());
		System.out.println("추가 질의: "+deployment.getDeployment_question());
		
		mwd.deploymentSave(deployment);
		
	}

	@Override
	public void deploymentSubmit(Deployment deployment) {
		System.out.println("숙제제출 서비스로 넘어왔으면 끼약호~~~~~~~~~~~~");
		mwd.deploymentSubmit(deployment);
		
	}

	@Override
	public List<Homework> getSendHomeworkList1(int member_key) {
		System.out.println("보낸 숙제리스트 서비스다 이거야");
		List<Homework> sendHomeworkList = mwd.getSendHomeworkList1(member_key);
		return sendHomeworkList;
	}

	@Override
	public List<Homework> getSendHomeworkList2(int study_key) {
		System.out.println("보낸 홈웤리스트를 뽑아오는 다오다 임마");
		System.out.println("여기까진 스터디키 갖고왔나--? "+study_key);
		List<Homework> sendHomeworkList = mwd.getSendHomeworkList2(study_key);
		return sendHomeworkList;
	}

	@Override
	public List<Deployment> getSubmitStudentList(int homework_key) {
		System.out.println("숙제 제출내역 확인하는 서비스");
		List<Deployment> submitStudentList = mwd.getSubmitStudentList(homework_key);
		System.out.println("제출학생 리스트 사이즈--->"+submitStudentList.size());
		
		return submitStudentList;
	}

	@Override
	public void saveGrades(Deployment deploymentArray) {
		System.out.println("서뷔스서뷔스서뷔스서뷔스서뷔스 학생들의 숙제를 저장해보자잇~~~~~~~~");
		mwd.saveGrades(deploymentArray);
		
	}

	@Override
	public int getMyHomeworkGradeTotal(int member_key) {
		System.out.println("총개수 갖고와보자잇 서비스~~~~~~~~~~~~~~~~~~~~~~");
		return mwd.getMyHomeworkGradeTotal(member_key);
	}

	@Override
	public List<Deployment> getMyGradeList(Deployment deployment) {
		System.out.println("내성적 조회하는 서비이이ㅣ이이이이이이ㅣ스");
		List<Deployment> myGradeList = mwd.getMyGradeList(deployment);
		return myGradeList;
	}

	@Override
	public Homework getHomeworkDetail(int hk) {
		System.out.println("숙제 상세화면 서비이이이ㅣ이이이이이ㅣ이이스");
		Homework homework = mwd.getHomeworkDetail(hk);
		return homework;
	}

	@Override
	public int updateHomework(Homework homework) {
		System.out.println("숙제 수정 서어어어어어어비스");
		int result = mwd.updateHomework(homework);
		return result;
	}

	@Override
	public int deleteHomework(int homeWorkKey) {
		System.out.println("숙제 삭제 서어어엉엉ㅇ어어어어비이이이이스으으으응으ㅡ");
		int result = mwd.deleteHomework(homeWorkKey);
		return result;
	}

	@Override
	public int getMemberCount() {
		System.out.println("멤버관리 페이지 로드 서어어어어어어비스");
		
		return mwd.getMemberCount();
	}

	@Override
	public List<Member> getMemberList(Member member) {
		System.out.println("총 회원 리스트 뽑는 서어어어어어어어어어엉어어어어ㅓ비스");
		return mwd.getMemberList(member);
	}

	@Override
	public List<Member> memberSearch(Member member) {
		System.out.println("검색 서어어어어어어어어어어ㅓㅇ비스");		
		return mwd.memberSearch(member);
	}

	@Override
	public Member getMemberDetail(int member_key) {
		System.out.println("회원상세보기 서어어어어어어어어어ㅓ비스");
		
		return mwd.getMemberDetail(member_key);
	}

}
