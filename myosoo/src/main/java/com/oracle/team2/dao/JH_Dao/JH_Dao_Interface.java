package com.oracle.team2.dao.JH_Dao;

import java.util.List;
import java.util.Map;

import com.oracle.team2.model.BComment;
import com.oracle.team2.model.Board;
import com.oracle.team2.model.Inquiry;
import com.oracle.team2.model.Member;

public interface JH_Dao_Interface {
	
	Member jhFindMemberById(String memberId);

	// 게시판 등록 
	void jhInsertBoard(Board board);

	// 게시판 상세 화면 
	Board jhFindByBoardKey(int board_key);
	
	// 댓글 리스트 
	List<BComment> jhFindCommentsByBoardKey(int board_key);
	
	// 게시판 삭제(댓글도 함께 삭제, 댓글 먼저 삭제)
	void jhDeleteBoardAndCommentsByBoardKey(int board_key);
	
	// 게시판 수정 (운영자) 
	void jhUpdateBoard(int board_key, String board_title, String board_content);
	
	// 댓글 commentKey max값 조회 
	int jhFindMaxCommentKey();

	// 댓글 작성 
	void jhInsertComment(BComment bcomment);
	
	// bcomment_key로 bcomment 찾기 
	BComment jhFindByBcommentKey(int bcomment_key);

	// 댓글 수정하기 
	void jhUpdateComment(int bcomment_key, String bcomment_content);
	
	// 댓글 삭제 
	void jhDeleteCommentsByBcommentKey(int bcomment_key);
	
	// 답글 상태 
	void jhUpdateBoardIsComment(int board_key);

	// 게시판 조회수 
	void jhBoardSee(int board_key);

	// 일대일 문의 본인 것만 확인하기 
	List<Board> jhSelectBoardByMemberKey(int memberKey);

	// 댓글 수 +1
	void jhIncreaseBoardComCount(int board_key);

	// 일대일 문의 삭제하기 
	void jhDeleteMyQnAAndCommentsByBoardKey(int board_key);
	
	// 댓글 수 -1
	void jhDecreaseComCount(int board_key);
	
	// 일대일 게시판 (운영자)
	List<Board> jhSelectBoardListByMakey(int boardMakey);

	// 일대일 게시판 답변 작성 (운영자)
	void jhInsertInquiry(int board_key, String inquiry_content);
	
	// 일대일 게시판 답변 확인
	List<Inquiry> jhFindInquiriesByBoardKey(int board_key);

	// 일대일 문의 답변 상태 확인 
	String jhFindInquiryContentByBoardKey(int board_key);
	
	// 일대일 게시판 답변 수정 
	Inquiry jhFindInquiryByBoardKey(int board_key);
	void jhUpdateInquiry(int board_key, String inquiry_content);
	
	// 일대일 게시판 답변 삭제 
	void jhDeleteInquiry(int board_key);
	void jhDecreaseInquiry(int board_key);

	// 검색기능
	List<Board> jhSearchBoardsByKeyword(String keyword);

	// 페이징 (Notice, Faq)
	int countBoardsNoticeFaq();
	List<Board> findBoardsNoticeFaq(int start, int end);

	// 페이징 (Qna)
	int countBoardsQna(Integer memberKey);
	List<Board> findBoardsQna(Integer memberKey, int start, int end);
	
	// 페이징 (Qna-Admin)
	int countBoardsQnaAdmin();
	List<Board> findBoardsQnaAdmin(int start, int end);

	

	

}
