package com.oracle.team2.model;

import lombok.Data;

@Data // 인증코드
public class EcCode {
	private int eccode_key; // 인증코드 일련번호(PK)
	private String eccode_email; // 이메일주소
	private String eccode_code; // 인증코드
	private int eccode_confirm; // 인증여부 (미확인 0 / 확인 1)
	

}
