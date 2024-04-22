package com.oracle.team2.service.JH_Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.oracle.team2.dao.JH_Dao.JH_Dao_Interface;
import com.oracle.team2.model.BComment;
import com.oracle.team2.model.Board;
import com.oracle.team2.model.Inquiry;
import com.oracle.team2.model.Member;

import lombok.RequiredArgsConstructor;


@Service
@RequiredArgsConstructor
public class JH_ServiceImpl implements JH_Service_Interface {
	
	private final JH_Dao_Interface dao;
	 
	public Member jhFindMemberById(String memberId) {
		 return dao.jhFindMemberById(memberId);
	}

	//게시글 등록하기 -> insert
	@Override
	public void jhInsertBoard(Board board, String memberId) {
		// member 조회
		 Member member = jhFindMemberById(memberId); 
		    if (member != null) {
		    	// 조회한 사용자 이름을 Board 객체에 설정
		        board.setBoard_writer(member.getMember_name());
		        // 혹은 사용자 키(member_key)를 Board 객체에 설정
		        board.setMember_key(member.getMember_key());
		    }
		// System.out.println("JH_ServiceImpl jhInsertBoard board --> " + board);
		dao.jhInsertBoard(board);
	}

	// 게시판 상세 내용 확인 
	@Override
	public Board getBoardDetail(int board_key) {
		// System.out.println("JH_ServiceImpl getBoardDetail board --> " + board_key);
		return dao.jhFindByBoardKey(board_key);
	}
	
	// 게시판 삭제 
	@Override
	@Transactional
	public void deleteBoardAndComments(int board_key) {
		// System.out.println("JH_ServiceImpl deleteBoardAndCommentsByBoardKey board --> " + board_key);
	    dao.jhDeleteBoardAndCommentsByBoardKey(board_key);
	}
	
	// 게시판 수정 
	@Override
	public void updateBoard(int board_key, String board_title, String board_content) {
		// System.out.println("JH_ServiceImpl updateBoard board_key, board_title, board_content --> " + board_key + board_title + board_content);
		dao.jhUpdateBoard(board_key, board_title, board_content);
	}
	
	// 댓글 확인 
	@Override
	public List<BComment> jhFindCommentsByBoardKey(int board_key) {
		// System.out.println("JH_ServiceImpl jhFindCommentsByBoardKey board --> " + board_key);
		return dao.jhFindCommentsByBoardKey(board_key);
	}

	// 조회수 
	@Override
	public void board_see(int board_key) {
		dao.jhBoardSee(board_key);
	}

	// 댓글 달기 
	@Override
	public void addComment(int board_key, String bcomment_content, String memberId) {
		// System.out.println("JH_ServiceImpl addComment" + bcomment_content);
		Member member = this.jhFindMemberById(memberId); // 회원 정보 조회
		 if (member != null) {
	            BComment bcomment = new BComment();
	            
	            int newCommentKey = generateNewCommentKey(); // 댓글 키 생성 
	            bcomment.setBcomment_key(newCommentKey);
	            bcomment.setBoard_key(board_key);
	            bcomment.setMember_key(member.getMember_key());
	            bcomment.setBcomment_content(bcomment_content);
	            bcomment.setBcomment_writer(member.getMember_name());
	            // 댓글 입력 
	            dao.jhInsertComment(bcomment);
	            // 댓글 수 +1 
	            dao.jhIncreaseBoardComCount(board_key);
		 	}
		}
	
		// 댓글 key 수동으로 체크 후 줘야 함 (데이터베이스 auto increament 때문)
		private int generateNewCommentKey() {
			 return dao.jhFindMaxCommentKey() + 1;
		}
		
	// 댓글 수정 (본인이 작성한 것에 한함)
	@Override
	public void updateComment(int bcomment_key, String bcomment_content) {
		 if (bcomment_content == null || bcomment_content.trim().isEmpty()) {
	            throw new IllegalArgumentException("Content cannot be empty");
	        }
	        BComment bComment = dao.jhFindByBcommentKey(bcomment_key);
	        // System.out.println("JH_ServiceImpl updateComment bcomment_key :" + bcomment_key);
	        if (bComment != null) {
	            bComment.setBcomment_content(bcomment_content);
	            dao.jhUpdateComment(bcomment_key, bcomment_content);
	    }
	}
		
	// 댓글 삭제 (본인이 작성한 것에 한함) 
	@Override
	public void deleteComments(int bcomment_key) {
		// System.out.println("JH_ServiceImpl deleteComments board --> " + bcomment_key);
		dao.jhDeleteCommentsByBcommentKey(bcomment_key);
	}

	// 댓글수 -1 
	@Override
	public void decreaseComCount(int board_key) {
		// System.out.println("JH_ServiceImpl decreaseComCount board --> " + board_key);
		dao.jhDecreaseComCount(board_key);
	}

	// 일대일 문의 게시판
	@Override
	public List<Board> jhGetMyQnAListByMemberKey(int memberKey) {
		return dao.jhSelectBoardByMemberKey(memberKey);
	}

	// 일대일 문의 게시판 상세보기 
	@Override
	public Board getMyQnADetail(int board_key) {
		// System.out.println("JH_ServiceImpl getMyQnADetail board_key :" + board_key);
        return dao.jhFindByBoardKey(board_key);
	}

	// 일대일 문의 게시판 작성하기 
	@Override
	public void jhInsertMyQnA(Board board, String memberId) {
		Member member = jhFindMemberById(memberId); // 또는 다른 방식으로 사용자 정보를 조회
	    if (member != null) {
	    	// 조회한 사용자 이름을 Board 객체에 설정
	        board.setBoard_writer(member.getMember_name());
	        // 혹은 사용자 키(member_key)를 Board 객체에 설정
	        board.setMember_key(member.getMember_key());
	    }
	    // System.out.println("JH_ServiceImpl jhInsertMyQnA board --> " + board);
	    dao.jhInsertBoard(board);
	}

	// 일대일 문의 게시판 삭제하기 (회원)
	@Override
	@Transactional
	public void deleteMyQnAAndComments(int board_key) {
		// System.out.println("JH_ServiceImpl deleteMyQnAAndComments board --> " + board_key);
		dao.jhDeleteMyQnAAndCommentsByBoardKey(board_key);
	}
	
	// 일대일 문의 게시판 목록 (운영자)
	@Override
	public List<Board> getBoardListByMakey(int boardMakey) {
		return dao.jhSelectBoardListByMakey(boardMakey);
	}

	// 일대일 문의 게시판 답변 작성 (운영자) 
	@Override
	public void addInquiry(int board_key, String inquiry_content) {
		dao.jhInsertInquiry(board_key, inquiry_content);
		dao.jhUpdateBoardIsComment(board_key);
	}
	
	// 일대일 문의 답글 수정 
	@Override
	public void updateInquiry(int board_key, String inquiry_content) {
		Inquiry inquiry = dao.jhFindInquiryByBoardKey(board_key);
		inquiry.setInquiry_content(inquiry_content);
		dao.jhUpdateInquiry(board_key, inquiry_content);
	}
	
	// 일대일 문의 답글 삭제 (운영자)
	@Override
	public void deleteInquiry(int board_key) {
		dao.jhDeleteInquiry(board_key);
	}
	
	// 일대일 문의 답글 삭제에 따른 isComment 변경 
	@Override
	public void decreaseInquiry(int board_key) {
		dao.jhDecreaseInquiry(board_key);
	}

	// 일대일 문의 답변에 대한 board_key 받기 
	@Override
	public List<Inquiry> jhGetInquiriesByBoardKey(int board_key) {
		  return dao.jhFindInquiriesByBoardKey(board_key);
	}

	// 검색 기능 
	@Override
	public List<Board> searchBoardsByKeyword(String keyword) {
		return dao.jhSearchBoardsByKeyword(keyword);
	}
	
	// 페이징 기능 (Notice, Faq)
	@Override
	public int getCountBoardsNoticeFaq() {
		// System.out.println("JH_ServiceImpl boardList getCountBoardsNoticeFaq Start");
		return dao.countBoardsNoticeFaq();
	}

	@Override
	public List<Board> findBoardsNoticeFaq(int start, int end) {
		// System.out.println("JH_ServiceImpl boardList paging findBoards start : " + start + ", end : " + end);
		return dao.findBoardsNoticeFaq(start, end);
	}
	
	// 페이징 기능 (Qna)
	@Override
	public int getCountBoardsQna(Integer memberKey) {
		return dao.countBoardsQna(memberKey);
	}

	@Override
	public List<Board> findBoardsQna(Integer memberKey, int start, int end) {
		// System.out.println("JH_ServiceImpl boardList paging findBoards memberKey" + memberKey + " start : " + start + ", end : " + end);
        return dao.findBoardsQna(memberKey, start, end);
	}
	
	// 페이징 기능 (Qna Admin)
	@Override
	public int getCountBoardsQnaAdmin() {
		return dao.countBoardsQnaAdmin();
	}

	@Override
	public List<Board> findBoardsQnaAdmin(int start, int end) {
		// System.out.println("JH_ServiceImpl boardList paging findBoards start :" + start + ", end : " + end);
        return dao.findBoardsQnaAdmin(start, end);
	}

}