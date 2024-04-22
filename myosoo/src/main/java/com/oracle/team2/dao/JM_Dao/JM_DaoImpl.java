package com.oracle.team2.dao.JM_Dao;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.oracle.team2.model.EcCode;
import com.oracle.team2.model.Member;

import lombok.RequiredArgsConstructor;
	
@Repository
@RequiredArgsConstructor
public class JM_DaoImpl implements JM_Dao_Interface {

    private final SqlSession session;

    @Override
    public Member login(String username) {
        System.out.println("로그인 다오 시작...");
        Member member = new Member();
        try {
            member.setMember_id(username);
            
            System.out.println("!!!!!!!!!!!!!!!"+member.getMember_id());
            member = session.selectOne("jmLogin", member);
        } catch (Exception e) {
            System.out.println("익셉션--? "+e.getMessage());
            e.printStackTrace();
        }
        
        System.out.println("정보는 갖고오는가..-->"+member);
        return member;
    }

    @Override
    public Member findID(String name, String email) {
    	System.out.println("아이디 찾기 다오 시작...");
        Member username = new Member();
        try {
            Member member = new Member();
            member.setMember_name(name);
            member.setMember_email(email);
            System.out.println("이름을 가져오는건가?"+member.getMember_name());
            System.out.println("이메일을 가져오는건가?"+member.getMember_email());
            username = session.selectOne("jmFindID", member);
        } catch (Exception e) {
            // 예외 처리
        	System.out.println("익셉션--? "+e.getMessage());
            e.printStackTrace();
        }
        return username;
    }

	@Override
	public String findpw(Member findpw) {
		String result = null;
		try {
			result = session.selectOne("jmFindPw", findpw);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	@Override
	public void ecSave(EcCode ecCode) {
        session.insert("jmEcSave", ecCode);
	}

	@Override
	public int confirmEmail(EcCode eccode) {
        int result = 0;
        try {
            eccode = session.selectOne("jmConfirmEmail", eccode);
            System.out.println(eccode);
            if (eccode != null) {
                result = session.update("jmUpdateEcCode", eccode);

            } else {
                // 인증 실패

            }
        } catch (Exception e) {
            e.printStackTrace();
            // 예외 발생 시 인증 실패로 처리

        }
        return result;
	}

	@Override
	public Member fiedPW(String name, String id, String email) {
		// TODO Auto-generated method stub
		return null;
	}

    @Override
    public int findPassword(Member member) {
        return session.selectOne("jmFindPassword", member);
    }

    @Override
    public int updatePassword(Member member) {
        return session.update("jmUpdatePassword", member);
    }
}
