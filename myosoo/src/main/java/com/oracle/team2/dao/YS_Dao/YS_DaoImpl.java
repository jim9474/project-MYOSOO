package com.oracle.team2.dao.YS_Dao;


import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.oracle.team2.model.Game1;
import com.oracle.team2.model.Student;
import com.oracle.team2.model.Study2;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class YS_DaoImpl implements YS_Dao_Interface {

	private final SqlSession session;

	@Override
	public List<Game1> getSelectBox(int member_key) {
		System.out.println("YS_DaoImpl getSelectBox start");
		List<Game1>result = null;
		
		try {
			result = session.selectList("ysGetSelectBoxList",member_key);
		} catch (Exception e) {
			System.out.println("YS_DaoImpl getSelectBox e.getLocalizedMessage() start" + e.getLocalizedMessage());
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public List<Game1> getSelectBoxDetail(int member_key) {
		List<Game1>result = null;
		try {
			result = session.selectList("ysGetSelectListDetail",member_key);
			System.out.println("YS_DaoImpl getSelectBoxDetail result start" + result);
		} catch (Exception e) {
			System.out.println("YS_DaoImpl getSelectBoxDetail e.getMessage() start" + e.getMessage());
			e.printStackTrace();
		}
		return result;
	}


	@Override
	public Game1 getContentsName(Game1 game) {
		System.out.println("YS_DaoImpl getContentsName start");
		Game1 resultGame =null;
		try {
			resultGame = session.selectOne("ysGetContentNames",game);
			System.out.println("게임명 : " + game);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resultGame;
	}

	@Override
	public void studyGroupInsert(Study2 study) {
		System.out.println("YS_DaoImpl studyGroupInsert start");
		int studyresult = 0;
		System.out.println("YS_DaoImpl studyGroupInsert study.getGame_key() start : " + study.getGame_key());
		try {
			studyresult = session.insert("ysStudyInsert", study);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("YS_DaoImpl studyGroupInsert e.getMessage() start" + e.getMessage());
		}
		
	}



	@Override
	public List<Study2> getStudyGroupSelect(int member_key) {
		System.out.println("YS_DaoImpl getStudyGroupSelect start");
		
		List<Study2>resultList = null;
		try {
			resultList = session.selectList("ysGetStudyGroupSelect",member_key);
			System.out.println(resultList);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resultList;
	}

	@Override
	public Study2 studyGroupSelectUpdateForm(Study2 study) {
	    System.out.println("YS_DaoImpl studyGroupSelectUpdate start");
	    
	    Study2 result = null;
	    
	    try {
	        result = session.selectOne("ysSelectStudyOne", study);
	        System.out.println(result);
	        
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    
	    return result; 
	}

	@Override
	public Study2 getUpateName(Study2 study) {
		System.out.println("YS_DaoImpl getUpateName start");
		
		Study2 result = null;
		try {
			result = session.selectOne("ysgetUpateName",study);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public int studyGroupUpdateAll(Study2 study) {
		System.out.println("YS_DaoImpl studyGroupUpdateAll start");
		
		int result = 0;
		try {
			result = session.update("ysStudyGroupSelectUpdateAll",study);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public int studyGroupDelete(Study2 study) {
		System.out.println("YS_DaoImpl studyGroupDelete start");
		int result = 0;
		try {
			result = session.delete("studyGroupDelete",study);
		} catch (Exception e) {
			e.getMessage();
		}
		
		return result;
	}

	@Override
	public List<Study2> studyDetailselectBoxList(Study2 study) {
		System.out.println("YS_DaoImpl studyDetailselectBoxList start");
		System.out.println("YS_DaoImpl studyDetailselectBoxList : "+study);
		List<Study2>result = null;
		
		try {
			result = session.selectList("studyDetailselectBoxList",study);
			System.out.println("YS_DaoImpl studyDetailselectBoxList"+result);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public Study2 getstudyGroupOne(Study2 study) {
		System.out.println("YS_DaoImpl getstudyGroupOne start");
		
		Study2 result = null;
		
		try {
			result = session.selectOne("ysGetStudyGroupOne", study);
			System.out.println("YS_DaoImpl getstudyGroupOne result start" + result);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public int getTotaCount(Student student) {
		System.out.println("YS_DaoImpl getTotaCount start");
		int result = 0;
		
		try {
			result = session.selectOne("ysGetTotalCount",student);
			System.out.println("YS_DaoImpl getTotaCount 인원 체크 : " + result);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public List<Student> getStudyGoupMemberList(Student student) {
		List<Student>resultList = null;
		try {
			resultList = session.selectList("ysGetStudyGoupMemberList",student);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resultList;
	}

	

	

}// class
