package com.oracle.team2.dao.MW_Dao;


import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.oracle.team2.model.Deployment;
import com.oracle.team2.model.Homework;
import com.oracle.team2.model.Member;
import com.oracle.team2.model.Student;
import com.oracle.team2.model.Study1;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class MW_DaoImpl implements MW_Dao_Interface {

	private final SqlSession session;

	@Override
	public List<Study1> getGroupList(int member_key) {
		System.out.println("다오까진 도달했나???");
		Study1 study = new Study1();
		study.setMember_key(member_key);
		System.out.println("여까지 값을 갖고 왔는지 확인-->"+member_key);
		List<Study1> groupList = null;
		try {
			groupList = session.selectList("mwGroup", member_key);
		} catch (Exception e) {
			System.out.println("익셉션 >>> "+e.getMessage());
		}
		
		return groupList;
	}

	@Override
	public int saveHomework(Homework homework) {
		System.out.println("숙제저장 다오야 이거");
		int result = 0;
		System.out.println("숙제입력레벨-->"+homework.getHomework_level());
		try {
			if(homework.getHomework_level() > 1) {
				result = session.selectOne("vsLevel", homework);
				if(result > 0) {
					result = session.insert("saveHomework", homework);
				} else result = 0;
			} else result = 0;							
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}

	@Override
	public List<Homework> getHomeworkList(int member_key) {
		System.out.println("숙제 리스트 다오다 이거야");
		List<Homework> homeworkList = null;
		try {
			homeworkList = session.selectList("selectHomework", member_key);
		} catch(Exception e) {
			System.out.println("익셉션--->"+e.getMessage());
		}
		return homeworkList;
	}

	@Override
	public List<Homework> getHomeworkPage(Homework homework) {
		
		List<Homework> homeworkPage = null;
		System.out.println("멤버키-->"+homework.getMember_key());
		System.out.println("스타트-->"+homework.getStart());
		System.out.println("마지막-->"+homework.getEnd());
		try {
			homeworkPage = session.selectList("getHomeworkPage", homework);
		} catch(Exception e) {
			System.out.println("익셉션--->"+e.getMessage());
			e.printStackTrace();
		}
		
		return homeworkPage;
	}

	@Override
	public List<Student> getStudentList(int study_key) {
		System.out.println("학생리스트 끌고오는 다오다");
		List<Student> studentList = null;
		try {
			studentList = session.selectList("studentList", study_key);
			System.out.println("학생리스트 사이즈--->"+studentList);
		} catch(Exception e) {
			System.out.println("익셉션--->"+e.getMessage());
		}
		
		return studentList;
		
	}

	@Override
	public List<Homework> getHomeworkList2(int study_key) {
		System.out.println("홈웤리스트 끌고오는 다오다 임마");
		List<Homework> homeworkList = null;
		try {
			homeworkList = session.selectList("homeworkList", study_key);
			System.out.println("홈웤리스트 사이즈--->"+homeworkList.size());
		} catch(Exception e) {
			System.out.println("익셉션----->"+e.getMessage());
		}
		return homeworkList;
	}

	@Override
	public int insertDeployment(Deployment deployment) {
		System.out.println("이제 다오까지 왔으니 얼마안남았따 흐에!!!");
		int result = 0;
		try {
			result = session.selectOne("vsStudy", deployment);
			System.out.println("리저트--->"+result);
			if(result > 0)
				result = session.insert("insertDeployment", deployment);
				
		} catch(Exception e) {
			System.out.println("익셉션----->"+e.getMessage());
			return 0;
		}
		return result;
	}

	@Override
	public List<Deployment> getMyHomework(Deployment deployment) {
		System.out.println("내숙제조회 다다다다다ㅏㄷ오오오ㅗ오오");
		List<Deployment> myHomeworkList = null;
		try {
			myHomeworkList = session.selectList("myHomeworkList", deployment);
			System.out.println("myHomeworkList의 사이즈---->"+myHomeworkList.size());
		} catch(Exception e) {
			System.out.println("익셉션으로 가나???"+e.getMessage());
		}
		return myHomeworkList;
	}

	@Override
	public int getHomeworkCount(int member_key) {
		System.out.println("숙제 카운트뽑기 다오");
		int result = 0;
		try {
			result = session.selectOne("hwCount", member_key);
		} catch(Exception e) {
			System.out.println("여기는 익셉션~~"+e.getMessage());
		}
		return result;
	}

	@Override
	public void deploymentSave(Deployment deployment) {
		System.out.println("호잇차 서비스에서 다오까지 왔으면 거의 다 왔어");
		try {
			session.update("deploymentSave", deployment);
		} catch(Exception e) {
			System.out.println("익셉션뜨면 ㅈ같은거고"+e.getMessage());
		}
		
	}

	@Override
	public void deploymentSubmit(Deployment deployment) {
		System.out.println("숙제제출 다오까지 와버렸어~~~~");
		try {
			session.update("deploymentSubmit", deployment);
		} catch(Exception e) {
			System.out.println("익셉션 ssibal ----->"+e.getMessage());
		}
		
	}

	@Override
	public List<Homework> getSendHomeworkList1(int member_key) {
		System.out.println("숙제 리스트 다오다 이거야");
		List<Homework> sendHomeworkList = null;
		try {
			sendHomeworkList = session.selectList("sendHomeworkListAll", member_key);
		} catch(Exception e) {
			System.out.println("익셉션--->"+e.getMessage());
		}
		return sendHomeworkList;
	}

	@Override
	public List<Homework> getSendHomeworkList2(int study_key) {
		System.out.println("홈웤리스트 끌고오는 다오다 임마");
		List<Homework> sendHomeworkList = null;
		try {
			sendHomeworkList = session.selectList("sendHomeworkListGroup", study_key);
			System.out.println("홈웤리스트 사이즈--->"+sendHomeworkList.size());
		} catch(Exception e) {
			System.out.println("익셉션----->"+e.getMessage());
		}
		return sendHomeworkList;
	}

	@Override
	public List<Deployment> getSubmitStudentList(int homework_key) {
		System.out.println("숙제 제출내역 확인하는 디아오");
		List<Deployment> submitStudentList = null;
		try {
			submitStudentList = session.selectList("submitStudentList", homework_key);
		} catch(Exception e) {
			System.out.println("익셉션 시벌련아--->"+e.getMessage());
		}
		return submitStudentList;
	}

	@Override
	public void saveGrades(Deployment deploymentArray) {
		System.out.println("크아다오크아다오크아다오크아다오크아다오 학생들의 숙제평가를 내려볼까????????");
		try {
			session.update("saveGrades", deploymentArray);
			int grade = deploymentArray.getDeployment_grade();
			if(grade > 0) {
				session.update("levelUP", deploymentArray);
			}
			
			
		} catch (Exception e) {
			System.out.println("익셉션 시불련아");
			e.printStackTrace();
		}
		
	}

	@Override
	public int getMyHomeworkGradeTotal(int member_key) {
		System.out.println("나는 다오다왇와도아도아돵돠와ㅏ와당");
		int result = 0;
		try {
			result = session.selectOne("totalHwCount", member_key);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public List<Deployment> getMyGradeList(Deployment deployment) {
		System.out.println("내 성적 조회하는 다아아아아아아아아ㅏ아오");
		List<Deployment> myGradeList = null;
		try {
			myGradeList = session.selectList("myGradeList", deployment);
			System.out.println("사아아아아아아아이즈---->"+myGradeList.size());
		} catch (Exception e) {
			System.out.println("익셉션이 떴네 시부레???");
			e.printStackTrace();
		}
			
		return myGradeList;
	}

	@Override
	public Homework getHomeworkDetail(int hk) {
		System.out.println("숙제 상세보기 다아아아아아아아앙오");
		Homework homework = null;
		try {
			homework = session.selectOne("detailHomework", hk);
		} catch (Exception e) {
			System.out.println("익셉션 시불럼아");
			e.printStackTrace();
		}
		return homework;
	}

	@Override
	public int updateHomework(Homework homework) {
		System.out.println("숙제 수정 다아아아아ㅏㅇ아ㅏ오ㄴㅇ무ㅠㅏㅓ뮤파ㅓㅁㅇ뉴ㅓ");
		int result = 0;
		try {
			List<Deployment> deployment = session.selectList("selectDeployment", homework);
			if(deployment.size() == 0) {
				result = session.update("updateHomework", homework);
			} else return -1;
			
		} catch (Exception e) {
			System.out.println("익셉션 시이이이이부레");
			e.printStackTrace();
		}
		System.out.println("리저트---->"+result);
		return result;
	}

	@Override
	public int deleteHomework(int homeWorkKey) {
		System.out.println("숙제 삭제 다아아아아아아아아아아아아아아아아아아아아아아오");
		int result = 0;
		try {
			result = session.delete("hwDelete", homeWorkKey);
		} catch (Exception e) {
			System.out.println("익셉션이네 시부레");
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public Member getMemberEmail(int studentMk) {
		System.out.println("이메일을 전송하기 위해 학습자의 이메일을 갖고오는 다아아아아아아아오");
		Member member = null;
		try {
			member = session.selectOne("selectEmail", studentMk);
		} catch (Exception e) {
			System.out.println("익셉션이네 시부레????");
			e.printStackTrace();
		}
		return member;
	}

	@Override
	public int getMemberCount() {
		System.out.println("멤버총카운트 다아ㅏ아아아아아ㅏㅇ오");
		int result = 0;
		try {
			result = session.selectOne("memberCount");
			System.out.println(result);
		} catch (Exception e) {
			System.out.println("익셉션 돔황쳐~~~~~~~~~~~~~~~~~~~~~~");
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public List<Member> getMemberList(Member member) {
		System.out.println("총 회원 리스트 뽑는 다아아아아아아아ㅏ앙오");
		List<Member> memberList = null;
		try {
			memberList = session.selectList("memberList", member);
			System.out.println("사이즈--->"+memberList.size());
		} catch (Exception e) {
			System.out.println("익셉션이야~~~~~~~~~~~~~~~~~~");
			e.printStackTrace();
		}
		return memberList;
	}

	@Override
	public List<Member> memberSearch(Member member) {
		System.out.println("검색 다아아아아아아아아아아오");
		List<Member> searchMem = null;
		try {
			searchMem = session.selectList("searchMembers", member);
			System.out.println("사이즈------------->"+searchMem.size());
		} catch (Exception e) {
			System.out.println("시이이이이이이이부레 익셉션");
			e.printStackTrace();
		}
		return searchMem;
	}

	@Override
	public Member getMemberDetail(int member_key) {
		System.out.println("회원상세보기 다아아아아아아앙아오");
		Member member = null;
		try {
			member = session.selectOne("memberDetail", member_key);
		} catch (Exception e) {
			System.out.println("익셉션이야~~~ 돔황쳐~~~");
			e.printStackTrace();
		}
		return member;
	}


}// class
