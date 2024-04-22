package com.oracle.team2.service.JM_Service;

import com.oracle.team2.model.EcCode;
import com.oracle.team2.model.Member;

public interface JM_Service_Interface {


	Member login(String username);

	Member findID(String name, String email);

	String findPassword(Member member);

	String findPasswordConfirm(EcCode ecCode);

	String updatePassword(Member member, String password);
}
