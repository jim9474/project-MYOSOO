package com.oracle.team2.service.MS_Service;

import java.util.List;

import com.oracle.team2.model.Game2;
import com.oracle.team2.model.Member;
import com.oracle.team2.model.Payment;

public interface MS_Service_Interface {

	int insertgame(Game2 game, String memberId);

	byte[] getGameImage(String game_key);

	List<Game2> gameList();

	List<Member> resgamePayList(Member member);

	int gamepayment(Payment payment);

	List<Game2> getGamePrice(int gameKey);

	List<Payment> getAllPurchaseHistory(int member_key);

	int deleteCard(int game_key);

	Payment getcheckPurchas(Payment payment);

	List<Payment> contentPayment(int gameKey);

	int UpdateCard(Game2 game);

	int MemebrFrUpdate(Member member);

	List<Game2> badukContentList();

}
