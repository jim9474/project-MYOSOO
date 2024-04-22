package com.oracle.team2.service.JH_Service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.oracle.team2.model.BComment;
import com.oracle.team2.model.Board;
import com.oracle.team2.model.Inquiry;
import com.oracle.team2.model.Member;

public interface JH_Service_Interface {

	void jhInsertBoard(Board board, String memberId);

	Board getBoardDetail(int board_key);
	
	void deleteBoardAndComments(int board_key);
	
	List<BComment> jhFindCommentsByBoardKey(int board_key);

	void board_see(int board_key);

	Member jhFindMemberById(String memberId);

	void addComment(int board_key, String bcomment_content, String memberId);
	
	List<Board> jhGetMyQnAListByMemberKey(int memberKey);

	Board getMyQnADetail(int board_key);

	void jhInsertMyQnA(Board board, String memberId);

	void deleteMyQnAAndComments(int board_key);

	List<Board> getBoardListByMakey(int boardMakey);

	List<Inquiry> jhGetInquiriesByBoardKey(int board_key);

	void addInquiry(int board_key, String inquiry_content);

	void deleteComments(int bcomment_key);

	void decreaseComCount(int board_key);

	void updateComment(int bcomment_key, String bcomment_content);

	void updateBoard(int board_key, String board_title, String board_content);

	List<Board> searchBoardsByKeyword(String keyword);
	
	int getCountBoardsNoticeFaq();

	List<Board> findBoardsNoticeFaq(int start, int end);

	int getCountBoardsQna(Integer memberKey);

	List<Board> findBoardsQna(Integer memberKey, int start, int end);
	
	int getCountBoardsQnaAdmin();

	List<Board> findBoardsQnaAdmin(int start, int end);

	void updateInquiry(int board_key, String inquiry_content);

	void deleteInquiry(int board_key);

	void decreaseInquiry(int board_key);

}
