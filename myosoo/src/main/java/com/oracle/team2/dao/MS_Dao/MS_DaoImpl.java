package com.oracle.team2.dao.MS_Dao;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import com.oracle.team2.model.Game2;
import com.oracle.team2.model.Member;
import com.oracle.team2.model.Payment;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class MS_DaoImpl implements MS_Dao_Interface {

	private final SqlSession session;

	@Override
	public Member msFindMemberById(String memberId) {
		return session.selectOne("msFindMemberById", memberId);
	}

	@Override
	public int insertGame(Game2 game, String memberId) {
		try {
			System.out.println("MS_DaoImpl insertGame Start...");
			return session.insert("msInsertGame", game);
		} catch (DataAccessException e) {
			System.out.println("MS_DaoImpl insertGame Exception: " + e.getMessage());
			throw e;
		}
	}

	@Override
	public List<Game2> gameList() {
	    List<Game2> gameList = null;
	    try {
	        System.out.println("MS_DaoImpl gameList Start...");
	        gameList = session.selectList("msGameDetail");
	    } catch (Exception e) {
	        System.out.println("MS_DaoImpl gameList Exception: " + e.getMessage());
	    }
	    return gameList;
	}


	@Override
	public byte[] getGameImage(String game_key) {
		try {
			System.out.println("MS_DaoImpl getGameImage Start...");
			// 게임 키를 기반으로 이미지 데이터를 데이터베이스에서 조회
			byte[] imageData = session.selectOne("msGetGameImage", game_key);
			System.out
					.println("MS_DaoImpl getGameImage imageData length: " + (imageData != null ? imageData.length : 0));
			return imageData;
		} catch (DataAccessException e) {
			System.out.println("MS_DaoImpl getGameImage Exception: " + e.getMessage());
			throw e;
		}
	}

	@Override
	public List<Member> resgamePayList(Member member) {
		List<Member> resGamemePayList = new ArrayList<>();
		try {
			System.out.println("MS_DaoImpl gameList Start...");
			resGamemePayList = session.selectList("msGamePay");
		} catch (Exception e) {
			System.out.println("MS_DaoImpl resGamemePayList Exception: " + e.getMessage());
			// 예외가 발생했을 때 빈 리스트를 반환
		}
		return resGamemePayList;
	}



	@Override
	public List<Game2> getGamePrice(int gameKey) {
	    List<Game2> games = null;
	    try {
	        System.out.println("MS_DaoImpl getGamePrice: Start...");
	        games = session.selectList("msGamePrice", gameKey);
	    } catch (Exception e) {
	        System.out.println("MS_DaoImpl - getGamePrice: Exception: " + e.getMessage());
	        throw e;
	    }
	    return games;
	}

		@Override
		public int gamepaymentInsert(Payment payment) {
			
			int result = 0;
			for(int i=0; i<payment.getPaymentPrices().length; i++) {
				int pr = payment.getPaymentPrices()[i]; 
				payment.setPayment_price(pr);
				try {
					System.out.println("MS_DaoImpl gamepaymentInsert Start...");
					System.out.println("MS_DaoImpl gamepaymentInsert insertResult: " + payment);
					
					result = session.insert("msPayInsert", payment);
					
					
				} catch (Exception e) {
					System.out.println("MS_DaoImpl gamepaymentInsert Exception: " + e.getMessage());
					throw e;
				}
			}
//			 int insertResult = 0;
			return result;
			//return insertResult;
		}

		@Override
		public List<Payment> findAll(int member_key) {
			List<Payment>  payments = null;
			try {
		        System.out.println("MS_DaoImpl findAll: Start...");
		        payments = session.selectList("mspamentAll",member_key);
		        System.out.println("MS_DaoImpl payments.size()——>"+payments.size());
		        System.out.println("MS_DaoImpl payments.size()——>"+payments);
		    } catch (Exception e) {
		        System.out.println("MS_DaoImpl - findAll: Exception: " + e.getMessage());
		    }
			return payments;
		}

		// MS_DaoImpl.java

		@Override
		public int deleteCard(int game_key) throws Exception {
		    System.out.println("MS_DaoImpl deleteCard Start...");
		    int cardDelete = 0;
		    try {
		        cardDelete = session.delete("msCardDelete", game_key);
		    } catch (Exception e) {
		        // 예외를 상위 레이어로 전달
		        throw new Exception("게임 카드 삭제 중 오류가 발생했습니다", e);
		    }
		    return cardDelete;
		}

		@Override
		public Payment checkPurchas(Payment payment) {
			System.out.println("MS_DaoImpl checkPurchas Start...");
			
			int pr = payment.getGameKey(); 
			payment.setGame_key(pr);
			try {
				// mapper ID, Parameter
				payment = session.selectOne("msGetcheckPurchas", payment);
				System.out.println("MS_DaoImpl checkPurchas getGame_key->" + payment.getGame_key());

			} catch (Exception e) {
				System.out.println("MS_DaoImpl checkPurchas Exception->" + e.getMessage());
			}
			return payment;
		}

		@Override
		public List<Payment> getcontentPayment(int gameKey) {
			System.out.println("MS_DaoImpl getcontentPayment Start...");
			List<Payment> paymentLi = null;
			try {
				paymentLi = session.selectList("mscontentPayment", gameKey);
			} catch (Exception e) {
				System.out.println(e.getMessage());
				throw new RuntimeException("가게 정보를 가져오는 데 실패했습니다.", e);
			}
			return paymentLi;
		}

		@Override
		public int gmaeCardUpdate(Game2 game) {
			System.out.println("MS_DaoImpl gmaeCardUpdate start..");
			int CardUpdate = 0;
			try {
				CardUpdate = session.update("maGameUpdate", game);
			} catch (Exception e) {
				System.out.println("MS_DaoImpl gmaeCardUpdate Exception -> " + e.getMessage());
			}

			return CardUpdate;
		}

		@Override
		public int MemebrFrUpdate(Member member) {
			System.out.println("MS_DaoImpl MemebrFrUpdate start..");
			int MemebrFrUpdate = 0;
			try {
				MemebrFrUpdate = session.update("msPayupdate", member);
			} catch (Exception e) {
				System.out.println("MS_DaoImpl MemebrFrUpdate Exception -> " + e.getMessage());
			}

			return MemebrFrUpdate;
		}

		@Override
		public List<Game2> contentBadukList() {
			 List<Game2> contentBadukList = null;
			    try {
			        System.out.println("MS_DaoImpl contentBadukList Start...");
			        contentBadukList = session.selectList("mscontentBaduk");
			    } catch (Exception e) {
			        System.out.println("MS_DaoImpl contentBadukList Exception: " + e.getMessage());
			    }
			    return contentBadukList;
		}

}// class
