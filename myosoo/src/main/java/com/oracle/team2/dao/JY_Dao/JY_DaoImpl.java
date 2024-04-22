package com.oracle.team2.dao.JY_Dao;


import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.oracle.team2.model.StFile;
import com.oracle.team2.model.Student;
import com.oracle.team2.model.Study1;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class JY_DaoImpl implements JY_Dao_Interface {

	private final SqlSession session;

	@Override
	public int condTotalStudy(Study1 study) {
		int totalStudyCount = 0;
		System.out.println("JY_DaoImpl condTotalStudy Start...");
		System.out.println("JY_DaoImpl Start study -> " + study);
		try {
			totalStudyCount = session.selectOne("jyCondTotalStudy", study);
			System.out.println("JY_DaoImpl condTotalStudy totalStudyCount -> " + totalStudyCount);
		} catch (Exception e) {
			System.out.println("JY_DaoImpl condTotalStudy Exception -> " + e.getMessage());
		}
		
		return totalStudyCount;
	}

	@Override
	public List<Study1> studyGroupAppSearch(Study1 study) {
		List<Study1> searchStudyGroupApp = null;
		System.out.println("JY_DaoImpl studyGroupAppSearch Start...");
		System.out.println("JY_DaoImpl studyGroupAppSearch study -> " + study);
		try {
			searchStudyGroupApp = session.selectList("jyStudyGroupAppSearch", study);
		} catch (Exception e) {
			System.out.println("JY_DaoImpl studyGroupAppSearch Exception -> " + e.getMessage());
		}
		
		return searchStudyGroupApp;
	}

	@Override
	public int totalStFile(StFile stFile) {
		int totalStFileCnt = 0;
		System.out.println("JY_DaoImpl totalStFile Start");
		
		try {
			totalStFileCnt = session.selectOne("jyTotalStFile", stFile);
			System.out.println("JY_DaoImpl totalStFile totalStFileCnt -> " + totalStFileCnt);
		} catch (Exception e) {
			System.out.println("JY_DaoImpl totalStFile Exception -> " + e.getMessage());
		}
		
		return totalStFileCnt;
	}

	@Override
	public List<StFile> stFileList(StFile stFile) {
		List<StFile> listStFile = null;
		System.out.println("JY_DaoImpl stFileList start...");
		if (stFile.getKeyword() == null) stFile.setKeyword("");
		
		try {
			listStFile = session.selectList("jyStFileList", stFile);
			System.out.println("JY_DaoImpl listStFile.size() -> " + listStFile.size());
		} catch (Exception e) {
			System.out.println("JY_DaoImpl stFileList Exception -> " + e.getMessage());
		}
		
		return listStFile;
	}

	@Override
	public int stFileInsert(StFile stFile) {
		int insertStFile = 0;
		System.out.println("JY_DaoImpl stFileInsert start...");
		try {
			insertStFile = session.insert("jyStFileInsert", stFile);
		} catch (Exception e) {
			System.out.println("JY_DaoImpl stFileInsert Exception ->" + e.getMessage());
		}
		
		return insertStFile;
	}

	@Override
	public StFile stFileDetail(int stfile_key) {
		System.out.println("JY_DaoImpl stFileDetail start...");
		StFile detailStFile = new StFile();
		
		try {
			detailStFile = session.selectOne("jyStFileDetail", stfile_key);
		} catch (Exception e) {
			System.out.println("JY_DaoImpl stFileDetail Exception -> " + e.getMessage());
		}
		
		return detailStFile;
	}

	@Override
	public int stFileDelete(int stfile_key) {
		System.out.println("JY_DaoImpl stFileDelete start...");
		int deleteStFile = 0;
		
		try {
			deleteStFile = session.delete("jyStFileDelete", stfile_key);
		} catch (Exception e) {
			System.out.println("JY_DaoImpl stFileDelete Exception -> " + e.getMessage());
		}
		
		return deleteStFile;
	}

	@Override
	public int stFileUpdate(StFile stFile) {
		System.out.println("JY_DaoImpl stFileUpdate start...");
		int updateStFile = 0;
		
		try {
			updateStFile = session.update("jyStFileUpdate", stFile);
		} catch (Exception e) {
			System.out.println("JY_DaoImpl stFileUpdate Exception -> " + e.getMessage());
		}
		
		return updateStFile;
	}

	@Override
	public int studyGroupApp(Student student) {
		System.out.println("JY_DaoImpl studyGroupApp start...");
		int appStudyGroup = 0;
		
		try {
			appStudyGroup = session.insert("jyStudyGroupApp", student);
		
		} catch (Exception e) {
			System.out.println("JY_DaoImpl studyGroupApp Exception -> " + e.getMessage());
		}
		
		return appStudyGroup;
	}

	@Override
	public boolean searchMyApp(Student student) {
		System.out.println("JY_DaoImpl searchMyApp start...");
		boolean myAppSearch = false;
		
		try {
			Student myAppList = session.selectOne("jySearchMyApp", student);
			if (myAppList != null) {
	            myAppSearch = true;
	        }
		} catch (Exception e) {
			System.out.println("JY_DaoImpl searchMyApp Exception -> " + e.getMessage());
		}
		
		return myAppSearch;
	}

	@Override
	public List<Study1> studyJoinApproval(Study1 study) {
		System.out.println("JY_DaoImpl studyJoinApproval start...");
		List<Study1> joinApprovalStudy = null;
		
		try {
			System.out.println("JY_DaoImpl studyJoinApproval study->"+study);
			joinApprovalStudy = session.selectList("jyStudyJoinApproval", study);
			
			for(Study1 study2 : joinApprovalStudy) {
				System.out.println("JY_DaoImpl studyJoinApproval study2->"+study2);
			}
			
			
		} catch (Exception e) {
			System.out.println("JY_DaoImpl studyJoinApproval Exception -> " + e.getMessage());
		}
		
		return joinApprovalStudy;
	}

	@Override
	public List<Study1> studyJoinAppForm(Study1 study) {
		System.out.println("JY_DaoImpl studyJoinAppForm start...");
		List<Study1> sjaForm = null;
		
		try {
			sjaForm = session.selectList("jyStudyJoinAppForm", study);
		} catch (Exception e) {
			System.out.println("JY_DaoImpl studyJoinAppForm Exception -> " + e.getMessage());
		}
		
		return sjaForm;
	}

	@Override
	public int approveJoin(Student student) {
	    System.out.println("JY_DaoImpl approveJoin start...");
	    int joinApprove = 0;
	    
	    try {
	        // 첫 번째 쿼리 실행
	        joinApprove += session.update("updateJoinStudent", student);
	        // 두 번째 쿼리 실행
	        joinApprove += session.update("updateJoinStudy", student);
	    } catch (Exception e) {
	        System.out.println("JY_DaoImpl approveJoin Exception -> " + e.getMessage());
	    }
	    
	    return joinApprove;
	}

	@Override
	public Student searchMxPerson(Student student) {
		System.out.println("JY_DaoImpl searchMxPerson start...");
		Student mxPersonSearch = null;
		
		try {
			mxPersonSearch = session.selectOne("jySearchMxPerson", student);
		} catch (Exception e) {
			System.out.println("JY_DaoImpl searchMxPerson Exception -> " + e.getMessage());
		}
		
		return mxPersonSearch;
	}

}// class
