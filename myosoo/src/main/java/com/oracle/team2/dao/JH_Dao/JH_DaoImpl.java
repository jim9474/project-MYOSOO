package com.oracle.team2.dao.JH_Dao;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.oracle.team2.model.BComment;
import com.oracle.team2.model.Board;
import com.oracle.team2.model.Inquiry;
import com.oracle.team2.model.Member;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class JH_DaoImpl implements JH_Dao_Interface {

	private final SqlSession session;

	@Override
	public void jhInsertBoard(Board board) {
		session.insert("jhInsertBoard", board);
	}

	@Override
	public Member jhFindMemberById(String memberId) {
		 return session.selectOne("jhFindMemberById", memberId);
	}

	@Override
	public List<BComment> jhFindCommentsByBoardKey(int board_key) {
		return session.selectList("jhFindCommentsByBoardKey", board_key);
	}
	
	@Override
	public void jhDeleteBoardAndCommentsByBoardKey(int board_key) {
		// System.out.println("JH_DaoImpl jhDeleteBoardAndCommentsByBoardKey  " + board_key);
		session.delete("jhDeleteCommentsByBoardKey", board_key);
		session.delete("jhDeleteBoardByBoardKey", board_key);
	}
	
	@Override
	public void jhUpdateBoard(int board_key, String board_title, String board_content) {
		// System.out.println("JH_DaoImpl updateBoard board_key, board_title, board_content --> " + board_key + board_title + board_content);
		Map<String, Object> params = new HashMap<>();
	    params.put("board_key", board_key);
	    params.put("board_title", board_title);
	    params.put("board_content", board_content);
	    session.update("jhUpdateBoard", params);
	}
	
	@Override
	public void jhIncreaseBoardComCount(int board_key) {
		try {
	        session.update("jhIncreaseBoardComCount", board_key);
	    } catch (Exception e) {
	    }
	}
	
	@Override
	public Board jhFindByBoardKey(int board_key) {
		 return session.selectOne("jhFindByBoardKey", board_key);	
	}

	@Override
	public void jhInsertComment(BComment bcomment) {
		session.insert("jhInsertComment", bcomment);
	}
	
	@Override
	public void jhDeleteCommentsByBcommentKey(int bcomment_key) {
		// System.out.println("JH_DaoImpl jhDeleteCommentsByBcommentKey  " + bcomment_key);
		session.delete("jhDeleteCommentsByBcommentKey", bcomment_key);
	}

	@Override
	public void jhUpdateComment(int bcomment_key, String bcomment_content) {
		Map<String, Object> params = new HashMap<>();
	    params.put("bcomment_key", bcomment_key);
	    params.put("bcomment_content", bcomment_content);
	    session.update("jhUpdateComment", params);
	}

	@Override
	public void jhDecreaseComCount(int board_key) {
		// System.out.println("JH_DaoImpl jhDecreaseComCount  " + board_key);
		session.update("jhDecreaseComCount", board_key);
	}
	
	@Override
	public int jhFindMaxCommentKey() {
		return session.selectOne("jhFindMaxCommentKey");
	}

	@Override
	public void jhBoardSee(int board_key) {
		try {
	        session.update("jhBoardSee", board_key);
	        // System.out.println("조회수 증가 완료: " + board_key);
	    } catch (Exception e) {
	        // System.out.println("조회수 증가 실패: " + e.getMessage());
	    }
	}

	@Override
	public List<Board> jhSelectBoardByMemberKey(int memberKey) {
		return session.selectList("jhSelectBoardByMemberKey", memberKey);
	}

	
	@Override
	public void jhDeleteMyQnAAndCommentsByBoardKey(int board_key) {
		// System.out.println("JH_DaoImpl jhDeleteMyQnAAndCommentsByBoardKey  " + board_key);
		session.delete("jhDeleteInquiryByBoardKey", board_key);
		session.delete("jhDeleteBoardByBoardKey", board_key);
	}

	@Override
	public List<Board> jhSelectBoardListByMakey(int boardMakey) {
		return session.selectList("jhSelectBoardListByMakey", boardMakey);
	}

	@Override
	public void jhInsertInquiry(int board_key, String inquiry_content) {
		Map<String, Object> params = new HashMap<>();
		params.put("board_key", board_key);
		params.put("inquiry_content", inquiry_content);
		session.insert("jhInsertInquiry", params);
	}

	@Override
	public List<Inquiry> jhFindInquiriesByBoardKey(int board_key) {
		return session.selectList("jhFindInquiriesByBoardKey", board_key);
	}

	@Override
	public String jhFindInquiryContentByBoardKey(int board_key) {
		return session.selectOne("jhFindInquiryContentByBoardKey", board_key);
	}

	@Override
	public void jhUpdateBoardIsComment(int board_key) {
		session.update("jhUpdateBoardIsComment", board_key);
	}
	
	@Override
	public Inquiry jhFindInquiryByBoardKey(int board_key) {
		return session.selectOne("jhFindInquiryByBoardKey", board_key);
	}

	@Override
	public void jhUpdateInquiry(int board_key, String inquiry_content) {
		Map<String, Object> params = new HashMap<>();
		params.put("board_key", board_key);
		params.put("inquiry_content", inquiry_content);
		session.update("jhUpdateInquiry", params);		
	}

	@Override
	public BComment jhFindByBcommentKey(int bcomment_key) {
		return session.selectOne("jhFindByBcommentKey", bcomment_key);
	}

	@Override
	public List<Board> jhSearchBoardsByKeyword(String keyword) {
		return session.selectList("jhSearchBoardsByKeyword", keyword);
	}

	@Override
	public int countBoardsNoticeFaq() {
		return session.selectOne("jhCountBoardsNoticeFaq");
	}

	@Override
	public List<Board> findBoardsNoticeFaq(int start, int end) {
		Map<String, Integer> map = new HashMap<>();
        map.put("start", start);
        map.put("end", end);
        return session.selectList("jhFindBoardsNoticeFaq", map);
	}

	@Override
	public int countBoardsQna(Integer memberKey) {
		// System.out.println("JH_DaoImpl countBoardsQna  " + memberKey);
		return session.selectOne("jhCountBoardsQna", memberKey);
	}

	@Override
	public List<Board> findBoardsQna(Integer memberKey, int start, int end) {
		Map<String, Integer> map = new HashMap<>();
		map.put("memberKey", memberKey);
        map.put("start", start);
        map.put("end", end);
        return session.selectList("jhFindBoardsQna", map);
	}

	@Override
	public int countBoardsQnaAdmin() {
		return session.selectOne("jhCountBoardsQnaAdmin");
	}

	@Override
	public List<Board> findBoardsQnaAdmin(int start, int end) {
		// System.out.println("JH_DaoImpl findBoardsQnaAdmin  start : " +start + "end" + end);
		Map<String, Integer> map = new HashMap<>();
        map.put("start", start);
        map.put("end", end);
        return session.selectList("jhFindBoardsQnaAdmin", map);
	}

	@Override
	public void jhDeleteInquiry(int board_key) {
		session.delete("jhDeleteInquiry", board_key);
	}

	@Override
	public void jhDecreaseInquiry(int board_key) {
		session.update("jhDecreaseInquiry", board_key);
	}


}// class
