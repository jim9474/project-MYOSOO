package com.oracle.team2.controller;

import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.oracle.team2.model.BComment;
import com.oracle.team2.model.Inquiry;
import com.oracle.team2.model.Board;
import com.oracle.team2.service.JH_Service.JH_Paging;
import com.oracle.team2.service.JH_Service.JH_Service_Interface;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class JH_Controller {
	private final JH_Service_Interface jhs;
	private final CommonController cc;

	// 게시판 글 목록 + 페이징 (Q&A, Faq) 
	@GetMapping("/boardList")
	public String boardList(HttpSession session, @RequestParam(value = "currentPage", required = false, defaultValue = "1") String currentPage, Model model) {
	    Integer memberKey = (Integer) session.getAttribute("member_key");
	    if (memberKey == null) {
	        return "redirect:/loginViews"; // 로그인 페이지로 리다이렉트
	    }

	    int totalPage = jhs.getCountBoardsNoticeFaq();
	    System.out.println("게시판 totalPage--->"+totalPage);
	    JH_Paging paging = new JH_Paging(totalPage, currentPage);

	    List<Board> boards = jhs.findBoardsNoticeFaq(paging.getStart(), paging.getEnd());
	    model.addAttribute("boards", boards);
	    model.addAttribute("page", paging);

	    // System.out.println("JH_Controller boardList paging totalPage : " + totalPage + ", currentPage : " + currentPage);
	    
	    return "/JH_views/boardList"; // JH_views 폴더 내의 boardList 뷰 반환
	}
	
	// 게시판 작성 화면 (운영자) 
	@GetMapping("/boardWrite")
	public String showBoardWritePage() {
	    return "/JH_views/boardWrite"; 
	}
	
	// 게시판 등록하기
	@PostMapping("/submitBoard")
    public String jhSubmitBoard(Board board, HttpSession session) {
		// 세션에서 로그인된 사용자 정보를 읽어옴
	    String memberId = (String) session.getAttribute("member_id");
		// System.out.println("JH_Controller board_insert Start... " + board);
		
		jhs.jhInsertBoard(board, memberId);
    	return "redirect:/boardList";
    }	

	// 게시판 상세 뷰 
	@GetMapping("/boardDetail")	
	public String getBoardDetail(@RequestParam("board_key") int board_key, Model model) {
		
		// 조회수 증가
		jhs.board_see(board_key);
		System.out.println("혹시 이거 두번호출되나?");
        Board board = jhs.getBoardDetail(board_key);
        // 댓글 
        List<BComment> bcomment = jhs.jhFindCommentsByBoardKey(board_key);
        
        model.addAttribute("board", board);
        model.addAttribute("bcomment", bcomment);
        
        // System.out.println("JH_Controller getBoardDetail Start... " + board_key);
        // System.out.println("JH_Controller jhFindCommentsByBoardKey Start... " + board_key);
        // System.out.println("JH_Controller board_see Start... " + bcomment);
	    
	    return "/JH_views/boardDetail";
	}

	// 게시판 삭제 (운영자만)
	@DeleteMapping("/deleteBoardAndComments/{board_key}")
	public ResponseEntity<?> deleteBoardAndComments(@PathVariable("board_key") int board_key) {
	    try {
	        // System.out.println("Deleting board and comments with boardKey: " + board_key);
	        jhs.deleteBoardAndComments(board_key);
	        return ResponseEntity.ok().build();
	    } catch (Exception e) {
	        e.printStackTrace(); 
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
	                             .body("Error deleting board: " + e.getMessage()); 
	    }
	}
	
	// 게시판 수정 (운영자만)   
	@PostMapping("/updateBoard/{board_key}")
	public ResponseEntity<?> updateBoard(@PathVariable("board_key") int board_key, @RequestParam("board_title") String board_title, @RequestParam("board_content") String board_content) {
		
		// System.out.println("JH_Controller updateBoard Start... board_key: " + board_key + ", board_title: " + board_title + ", board_content: " + board_content);
        try {
            // System.out.println("Updating comment with key: " + board_key);
            jhs.updateBoard(board_key, board_title, board_content);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error updating comment: " + e.getMessage());
        }
	}

	// 댓글 달기 
	@PostMapping("/bcomment/add")
	public String addComment(@RequestParam("board_key") int board_key, @RequestParam("bcomment_content") String bcomment_content, HttpSession session) {
		
	    String memberId = (String) session.getAttribute("member_id");
	    // System.out.println("JH_Controller addComment Start... " + board_key);
        // System.out.println("JH_Controller addComment Start... " + bcomment_content);
	    jhs.addComment(board_key, bcomment_content, memberId);
	    
	    return "redirect:/boardDetail?board_key=" + board_key;
	}
	
	// 댓글 수정 (본인이 작성한 것에 한함)
    @PostMapping("/updateComments/{bcomment_key}")
    public ResponseEntity<?> updateComments(@PathVariable("bcomment_key") int bcomment_key, @RequestParam("bcomment_content") String bcomment_content) {
    	System.out.println("JH_Controller updateComments Start... bcomment_key" + bcomment_key);
    	System.out.println("JH_Controller updateComments Start... bcomment_content" + bcomment_content);
        try {
            // System.out.println("Updating comment with key: " + bcomment_key);
            jhs.updateComment(bcomment_key, bcomment_content);
            return ResponseEntity.ok().body("bcomment_content updated successfully");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error updating comment: " + e.getMessage());
        }
    }
	
	// 댓글 삭제 (본인이 작성한 것에 한함)
	@DeleteMapping("/deleteComments/{bcomment_key}")
	public ResponseEntity<?> deleteComments(@PathVariable("bcomment_key") int bcomment_key, @RequestParam("board_key") int board_key) {
	    try {
	        // System.out.println("Deleting comments with boardKey: " + bcomment_key);
	        jhs.deleteComments(bcomment_key);
	        // 댓글 삭제 후 게시글의 댓글 수 감소
	        jhs.decreaseComCount(board_key);
	        return ResponseEntity.ok().build();
	        
	    } catch (Exception e) {
	        e.printStackTrace(); 
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
	                             .body("Error deleting board: " + e.getMessage()); 
	    }
	}

	// 일대일 문의 게시판 목록(회원) + 페이징
	@GetMapping("/myQnAList")
	public String myQnAList(HttpSession session, @RequestParam(value = "currentPage", required = false, defaultValue = "1") String currentPage, Model model) {
	    Integer memberKey = (Integer) session.getAttribute("member_key");
	    // System.out.println("JH_Controller myQnAList Start... " + memberKey);

	    if (memberKey == null) {
	        return "redirect:/loginViews";  // 로그인 페이지로 리다이렉트
	    }

	    int totalRecords = jhs.getCountBoardsQna(memberKey);  // 특정 사용자의 문의글 개수를 가져오는 메소드가 필요
	    JH_Paging paging = new JH_Paging(totalRecords, currentPage);

	    List<Board> myQnAList = jhs.findBoardsQna(memberKey, paging.getStart(), paging.getEnd());
	    model.addAttribute("myQnAList", myQnAList);
	    model.addAttribute("page", paging);

	    return "JH_views/myQnAList";  // JH_views 폴더 내의 myQnAList 뷰 반환
	}

	// 일대일 문의 게시판 세부내용 확인하기(회원)
	@RequestMapping(value = "/myQnADetail")
	public String myQnADetail(@RequestParam("board_key") int board_key, Model model) {
	    Board board = jhs.getMyQnADetail(board_key);
	    
	    // 답변  
	    List<Inquiry> inquiry = jhs.jhGetInquiriesByBoardKey(board_key);
	    model.addAttribute("board", board);
        model.addAttribute("inquiry", inquiry);
	    // System.out.println("JH_Controller myQnADetail Start... board_key : " + board_key);
	    
	    return "/JH_views/myQnADetail"; 
	}
	
	// 일대일 문의 게시판 작성하는 화면 이동 (회원)
		@RequestMapping(value = "/myQnAWrite")
		public String myQnAWrite() {
			return "/JH_views/myQnAWrite";
		}
		
	// 일대일 문의 게시판 작성하기 (회원)
	@PostMapping("/submitMyQnA")
    public String jhSubmitMyQnA(Board board, HttpSession session) {

	    String memberId = (String) session.getAttribute("member_id");
		// System.out.println("JH_Controller board_insert Start... " + board);
		jhs.jhInsertBoard(board, memberId);
    	return "redirect:/myQnAList";
    }
	
	// 일대일 문의 게시판 삭제하기 (회원)
	@DeleteMapping("/deleteMyQnA/{board_key}")
    public ResponseEntity<?> deleteMyQnAAndComments(@PathVariable("board_key") int board_key) {
        try {
		    // System.out.println("JH_Controller deleteMyQnA Start... boardKey: " + board_key);
		    jhs.deleteMyQnAAndComments(board_key);
		    return ResponseEntity.ok().build();
	    } catch (Exception e) {
	        e.printStackTrace(); 
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
	                             .body("Error deleting board: " + e.getMessage()); 
	    }
	}
	
	// 일대일 문의 게시판 목록(운영자)
	@GetMapping("/qnaList")
	public String qnaList(HttpSession session, @RequestParam(value = "currentPage", required = false, defaultValue = "1") String currentPage, Model model) {
		
		Integer memberKey = (Integer) session.getAttribute("member_key");
		
		// 로그인 상태가 아니면 로그인 필요상태를 나타내줌 -> 프론트엔드에서 처리하기 위해서 ! 
		// 추후 alert 추가 예정  
	    // 만약 memberKey가 null이면, 로그인 페이지로 리다이렉트
		if (memberKey == null) {
		    return "redirect:/loginViews";
		}
		
		int totalPage = jhs.getCountBoardsQnaAdmin();
	    JH_Paging paging = new JH_Paging(totalPage, currentPage);
				
	    List<Board> boards = jhs.findBoardsQnaAdmin(paging.getStart(), paging.getEnd());
	    model.addAttribute("boards", boards);
	    model.addAttribute("page", paging);
		
		return "/JH_views/qnaList";
	}

	// 일대일 문의 게시판 답변 작성 화면(운영자)
	@PostMapping("/inquiry/add")
    public String addInquiry(@RequestParam("board_key") int board_key,
    						@RequestParam("inquiry_content") String inquiry_content) {
		jhs.addInquiry(board_key, inquiry_content);
		return "redirect:/qnaAnswer?board_key=" + board_key;
    }
	
	// 일대일 문의 게시판 세부내용 확인하기 (운영자) 
	@RequestMapping(value = "/qnaAnswer")
	public String qnaAnswer(@RequestParam("board_key") int board_key, Model model) {
	    Board board = jhs.getBoardDetail(board_key);
	    
	    // 답글 inquiry
	    List<Inquiry> inquiry = jhs.jhGetInquiriesByBoardKey(board_key);
        
        model.addAttribute("board", board);
        model.addAttribute("inquiry", inquiry);
	    // System.out.println("JH_Controller qnaAnswer Start... board_key : " + board_key);
	    // System.out.println("JH_Controller qnaAnswer model : " + model);
	    
	    return "/JH_views/qnaAnswer"; 
	}
	
	// 일대일 문의 게시판 답글 수정 (운영자)
	@PostMapping("/updateInquiry/{board_key}") 
	  public ResponseEntity<?> updateInquiry(@RequestParam("board_key") int board_key, @RequestParam("inquiry_content") String inquiry_content) {
	    	System.out.println("JH_Controller updateInquiry Start... board_key" + board_key);
	    	System.out.println("JH_Controller updateInquiry Start... inquiry_content" + inquiry_content);
	        try {
	            // System.out.println("Updating comment with key: " + board_key);
	            jhs.updateInquiry(board_key, inquiry_content);
	            return ResponseEntity.ok().body("inquiry_content updated successfully");
	        } catch (Exception e) {
	            e.printStackTrace();
	            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error updating comment: " + e.getMessage());
	        }
	    }

	// 일대일 문의 게시판 답글 삭제 (운영자)
	@DeleteMapping("/deleteInquiry/{board_key}")
	public ResponseEntity<?> deleteInquiry(@RequestParam("board_key") int board_key) {
	    try {
	        // System.out.println("Deleting comments with boardKey: " + board_key);
	        jhs.deleteInquiry(board_key);
	        // 답글 삭제 후 답글 상태 변경 
	        jhs.decreaseInquiry(board_key);
	        return ResponseEntity.ok().build();
	        
	    } catch (Exception e) {
	        e.printStackTrace(); 
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
	                             .body("Error deleting Inquiry: " + e.getMessage()); 
	    }
	}
	
	// 검색 기능
	@GetMapping("/searchBoards")
	public ResponseEntity<List<Board>> searchBoards(@RequestParam("keyword") String keyword) {
	    List<Board> boards = jhs.searchBoardsByKeyword(keyword);
	    // System.out.println("JH_Controller searchBoards Start... keyword : " + keyword);
	    return ResponseEntity.ok(boards);
	}

}