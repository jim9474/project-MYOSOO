package com.oracle.team2.service.JM_Service;


import com.oracle.team2.service.DY_Service.DY_ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import com.oracle.team2.dao.JM_Dao.JM_Dao_Interface;
import com.oracle.team2.model.EcCode;
import com.oracle.team2.model.Member;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class JM_ServiceImpl implements JM_Service_Interface {

	private final JM_Dao_Interface jmd;
	private final JavaMailSender mailSender;
	private static int authNumber;

	private final BCryptPasswordEncoder bCryptPasswordEncoder;
	

	@Override
	public Member login(String username) {
		System.out.println("로그인 서비스 시작...");
		Member member = jmd.login(username);
		System.out.println("여기까진 오나????");	
		return member;
	}
	
	@Override	
	public Member findID(String name, String email) {
		System.out.println("아이디 찾기 서비스 시작...");
		Member member = jmd.findID(name, email);
		System.out.println("아이디를 찾았나?" + member.getMember_id());
		return member;
	}

	public int sendMail(String member_email) {
		System.out.println("인증번호 전송 서비스 시작...");
		System.out.println(member_email);
		String setfrom = "dladyd1119@gmail.com"; // 보내는사람 이메일
		String title = "MYOSOO 인증 이메일입니다"; // 제목

		try {
		// Mime : 전자우편 인터넷 표준 format
		MimeMessage message = mailSender.createMimeMessage();
		MimeMessageHelper messageHelper = new MimeMessageHelper(message, true, "UTF-8");
		messageHelper.setFrom(setfrom); // 보내는사람 생략하면 정상작동하지 않음
		messageHelper.setTo(member_email); // 받는사람 이메일
		messageHelper.setSubject(title); // 메일제목 생략가능
		authNumber = (int) (Math.random() * 900000) + 100000; // 6자리 난수

		messageHelper.setText("MYOSOO 인증번호입니다.\n" + authNumber); // 메일 내용

		System.out.println("인증번호 : " + authNumber);
		mailSender.send(message);

		EcCode ecCode = new EcCode();
		ecCode.setEccode_email(member_email);
		ecCode.setEccode_code(String.valueOf(authNumber));

		jmd.ecSave(ecCode);

		System.out.println("ecCode " + ecCode);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return authNumber;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public String findPassword(Member member) {
		if(jmd.findPassword(member) > 0){
			var authNumber = this.sendMail(member.getMember_email());
			log.info("##### authNumber :: {}", authNumber);
			return "success";
		}
		return "fail";
	}

	@Override
	public String findPasswordConfirm(EcCode ecCode) {
		return (jmd.confirmEmail(ecCode) > 0) ? "success" : "fail";
	}

	@Override
	public String updatePassword(Member member, String password) {
		member.setMember_pw(bCryptPasswordEncoder.encode(password));
		log.info("## bcryptedPw = {}", member.getMember_pw());
		System.out.println("## bcryptedPw = {}"+member.getMember_pw());
		int result = jmd.updatePassword(member);
		if(result > 0) {
			return "success";
		} else return "fail";
		// return (jmd.updatePassword(member) > 0) ? "success" : "fail";
	}



}
