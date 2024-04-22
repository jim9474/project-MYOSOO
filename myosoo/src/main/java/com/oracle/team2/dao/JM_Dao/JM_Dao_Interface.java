package com.oracle.team2.dao.JM_Dao;

import com.oracle.team2.model.EcCode;
import com.oracle.team2.model.Member;

public interface JM_Dao_Interface {

	Member login(String username);

	String findpw(Member findpw);

	void ecSave(EcCode ecCode);

	int confirmEmail(EcCode eccode);

	Member findID(String name, String email);

	Member fiedPW(String name, String id, String email);

	int findPassword(Member member);

	int updatePassword(Member member);
}
