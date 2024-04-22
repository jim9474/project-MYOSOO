package com.oracle.team2.model;

import lombok.Data;

@Data // 공통코드
public class Common {
	private int makey; // 대분류(PK) 
	
	private int mikey; // 소분류(PK)
	
	private String keyvalue; // 키값
	
// 회원 : 대분류 100 / 소분류 10=교육자, 20=학습자, 30=일반인, 40=운영자
// 자료 : 대분류 200 / 소분류 10=튜토리얼, 20=교육영상
// 서비스 : 대분류 300 / 소분류 10=full(유료), 20=short(무료)
// 게시판 - 공지사항 : 대분류 400 / 소분류 10=공통, 20=상품안내, 30=이용안내, 40=기타
//       - FAQ : 대분류 500 / 소분류 10=상품결제, 20=숙제관리, 30=학습그룹, 40=기타
//       - 문의사항 : 대분류 600 / 소분류 10=회원정보, 20=상품구매, 30=사이트이용 40=기타
	
                  
}
