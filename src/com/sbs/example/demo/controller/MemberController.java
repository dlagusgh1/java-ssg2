package com.sbs.example.demo.controller;

import com.sbs.example.demo.dto.Member;
import com.sbs.example.demo.factory.Factory;
import com.sbs.example.demo.service.MemberService;

public class MemberController extends Controller {
	private MemberService memberService;

	public MemberController() {
		memberService = Factory.getMemberService();
	}

	public void doAction(Request reqeust) {
		if (reqeust.getActionName().equals("logout")) {
			actionLogout(reqeust);
		} else if (reqeust.getActionName().equals("login")) {
			actionLogin(reqeust);
		} else if (reqeust.getActionName().equals("whoami")) {
			actionWhoami(reqeust);
		} else if (reqeust.getActionName().equals("join")) {
			actionJoin(reqeust);
		}
	}
	
	// 로그 아웃
	private void actionLogout(Request reqeust) {
		System.out.println("====== 로그아웃 시작 ======");
		
		Member loginedMember = Factory.getSession().getLoginedMember();

		if (loginedMember != null) {
			Session session = Factory.getSession();
			System.out.println("로그아웃 되었습니다.");
			session.setLoginedMember(null);
		}
		
		System.out.println("====== 로그아웃 끝 ======");
	}
	
	// 로그인
	private void actionLogin(Request reqeust) {
		System.out.println("====== 로그인 시작 ======");
		
		System.out.printf("로그인 아이디 : ");
		String loginId = Factory.getScanner().nextLine().trim();

		System.out.printf("로그인 비번 : ");
		String loginPw = Factory.getScanner().nextLine().trim();

		Member member = memberService.getMemberByLoginIdAndLoginPw(loginId, loginPw);

		if (member == null) {
			System.out.println("일치하는 회원이 없습니다.");
		} else if(Factory.getSession().getLoginedMember() == null) {
			System.out.println(member.getName() + "님 환영합니다.");
			Factory.getSession().setLoginedMember(member);
		} else {
			System.out.println("현재 다른 회원이 로그인 중입니다.");
		}
		
		System.out.println("====== 로그인 끝 ======");
	}

	// 로그인 중인 대상이 누구인지
	private void actionWhoami(Request reqeust) {
		Member loginedMember = Factory.getSession().getLoginedMember();

		if (loginedMember == null) {
			System.out.println("비 회원");
		} else {
			System.out.println(loginedMember.getName());
		}

	}
	
	// 회원가입 
	private void actionJoin(Request reqeust) {
		System.out.println("====== 회원가입 시작 ======");
		
		System.out.printf("아이디 : ");
		String loginId = Factory.getScanner().nextLine();
		
		if (memberService.getMemberByLoginId(loginId) == null) {
			System.out.printf("비밀번호 : ");
			String loginPw = Factory.getScanner().nextLine();
			System.out.printf("이름 : ");
			String name = Factory.getScanner().nextLine();
			
			memberService.join(loginId, loginPw, name);
		} else if ((memberService.getMemberByLoginId(loginId) != null))  {
			System.out.println("중복되는 아이디가 존재합니다.");
		}
		
		System.out.println("====== 회원가입 끝 ======");
	}

}