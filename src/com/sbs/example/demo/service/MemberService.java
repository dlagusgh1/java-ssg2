package com.sbs.example.demo.service;

import com.sbs.example.demo.dao.MemberDao;
import com.sbs.example.demo.dto.Member;
import com.sbs.example.demo.factory.Factory;

public class MemberService {
	private MemberDao memberDao;

	public MemberService() {
		memberDao = Factory.getMemberDao();
	}

	// 로그인 - 아이디 / 비밀번호 일치하는 게 있는지 확인
	public Member getMemberByLoginIdAndLoginPw(String loginId, String loginPw) {
		return memberDao.getMemberByLoginIdAndLoginPw(loginId, loginPw);
	}

	// 회원가입
	public int join(String loginId, String loginPw, String name) {
		Member oldMember = memberDao.getMemberByLoginId(loginId);

		if (oldMember != null) {
			return -1;
		}

		Member member = new Member(loginId, loginPw, name);
		return memberDao.save(member);
	}

	// 회원가입 - 아이디 중복방지 기능
	public Object getMemberByLoginId(String loginId) {
		return memberDao.getMemberByLoginId(loginId);
	}

	// 
	public Member getMember(int id) {
		return memberDao.getMember(id);
	}

	// 관리자 생성(초기 관리자 없을 경우 생성 / 중복 생성 방지)
	public void makeAdminUserIfNotExists() {
		Member member = memberDao.getMemberByLoginId("admin");
		
		if (member == null) {
			join("admin", "admin", "관리자");
		}
	}
	
}