package com.oracle.team2.service.MS_Service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.oracle.team2.dao.MS_Dao.MS_Dao_Interface;
import com.oracle.team2.model.Game2;
import com.oracle.team2.model.Member;
import com.oracle.team2.model.Payment;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MS_ServiceImpl implements MS_Service_Interface {

	private final MS_Dao_Interface msd;

	public Member msFindMemberById(String memberId) {
		return msd.msFindMemberById(memberId);
	}

	@Override
	public int insertgame(Game2 game, String memberId) {
		// 사용자 정보 조회
		Member member = msFindMemberById(memberId);

		// 조회된 사용자가 있을 경우
		if (member != null) {
			// 사용자 키를 게임 객체에 설정
			game.setMember_key(member.getMember_key());
		}

		System.out.println("MS_ServiceImpl insertgame Start...");

		int result = 0;
		// DAO를 통해 게임 등록 처리
		result = msd.insertGame(game, memberId);
		System.out.println("MS_ServiceImpl insertgame game —> " + game);

		return result;
	}

	@Override
	public List<Game2> gameList() {
	    List<Game2> gameList = null;
	    try {
	        System.out.println("MS_ServiceImpl gameList Start...");
	        gameList = msd.gameList();
	        System.out.println("MS_ServiceImpl gameList.size()  " + gameList.size());
	    } catch (Exception e) {
	        System.out.println("MS_ServiceImpl gameList Exception: " + e.getMessage());
	    }
	    return gameList;
	}


	@Override
	public byte[] getGameImage(String game_key) {
		System.out.println("MS_ServiceImpl getGameImage Start...");
		// 게임 키를 기반으로 DAO를 통해 이미지 데이터를 가져옵니다.
		byte[] imageData = msd.getGameImage(game_key);
		System.out
				.println("MS_ServiceImpl getGameImage imageData length: " + (imageData != null ? imageData.length : 0));
		return imageData;
	}

	@Override
	public List<Member> resgamePayList(Member member) {
		System.out.println("MS_ServiceImpl resgameList Start...");
		List<Member> resgamePayList = msd.resgamePayList(member);
		System.out.println("MS_ServiceImpl gameList.size()  " + resgamePayList.size());
		return resgamePayList;
	}


	@Override
	public List<Game2> getGamePrice(int gameKey) {
	    System.out.println("MS_ServiceImpl getGamePrice Start...");
	    List<Game2> game = null;
	    game = msd.getGamePrice(gameKey);
	    
	    return game;
	}

	@Override
	public int gamepayment(Payment payment) {
		System.out.println("MS_ServiceImpl gamepayment Start...");
		int insertResult = 0;
		insertResult = msd.gamepaymentInsert(payment);
		System.out.println("MS_ServiceImpl gamepayment payment —> " + payment);
		
		return insertResult;
	}

	 @Override
	    public List<Payment> getAllPurchaseHistory(int member_key) {
		 System.out.println("MS_ServiceImpl getAllPurchaseHistory Start...");
		 List<Payment> payments = null;
		 payments = msd.findAll(member_key);
	        return payments; // 모든 결제 내역을 가져옵니다.
	    }

	@Override
	public int deleteCard(int game_key) {
		int CardDelete = 0;
		System.out.println("MS_ServiceImpl deleteCard Start...");
		try {
			CardDelete = msd.deleteCard(game_key);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return CardDelete;
	}

	@Override
	public Payment getcheckPurchas(Payment payment) {
		System.out.println("MS_ServiceImpl getcheckPurchas Start...");
		payment = msd.checkPurchas(payment);
		return payment;
	}

	@Override
	public List<Payment> contentPayment(int gameKey) {
		System.out.println("MS_ServiceImpl contentPayment Start...");
		List<Payment> payment = msd.getcontentPayment(gameKey);
		return payment;
	}

	@Override
	public int UpdateCard(Game2 game) {
		int gmaeCardUpdate = 0;
		System.out.println("MS_ServiceImpl UpdateCard Start...");
		gmaeCardUpdate = msd.gmaeCardUpdate(game);

		return gmaeCardUpdate;
	}

	@Override
	public int MemebrFrUpdate(Member member) {
			int reMemerFUpdate = 0;
			System.out.println("MS_ServiceImpl MemebrFrUpdate Start...");
			reMemerFUpdate = msd.MemebrFrUpdate(member);

			return reMemerFUpdate;
		}

	@Override
	public List<Game2> badukContentList() {
		   List<Game2> badukContentList = null;
		    try {
		        System.out.println("MS_ServiceImpl badukContentList Start...");
		        badukContentList = msd.contentBadukList();
		        System.out.println("MS_ServiceImpl badukContentList.size()  " + badukContentList.size());
		    } catch (Exception e) {
		        System.out.println("MS_ServiceImpl badukContentList Exception: " + e.getMessage());
		    }
		    return badukContentList;
	}

	}

	
