package com.oracle.team2.dao.MS_Dao;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.oracle.team2.model.Game2;
import com.oracle.team2.model.Member;
import com.oracle.team2.model.Payment;

public interface MS_Dao_Interface {

	int insertGame(Game2 game, String memberId);

	// List<Game> gameList();

	byte[] getGameImage(String game_key);

	Member msFindMemberById(String memberId);

	List<Game2> gameList();

	List<Member> resgamePayList(Member member);


	int gamepaymentInsert(Payment payment);


	List<Game2> getGamePrice(int gameKey);

	List<Payment> findAll(int member_key);

	int deleteCard(int game_key) throws Exception;

	Payment checkPurchas(Payment payment);

	List<Payment> getcontentPayment(int gameKey);

	int gmaeCardUpdate(Game2 game);

	int MemebrFrUpdate(Member member);

	List<Game2> contentBadukList();
	

}
