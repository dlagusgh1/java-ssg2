package com.sbs.example.demo.controller;

import com.sbs.example.demo.dto.Board;
import com.sbs.example.demo.dto.Member;

// Session
// 현재 사용자가 이용중인 정보
// 이 안의 정보는 사용자가 프로그램을 사용할 때 동안은 계속 유지된다.
public class Session {
	private Member loginedMember;
	private Board currentBoard;

	public Member getLoginedMember() {
		return loginedMember;
	}

	// 현재 로그인 중인 맴버 정보 저장
	public void setLoginedMember(Member loginedMember) {
		this.loginedMember = loginedMember;
	}

	// 현재 게시판 정보 가져오기
	public Board getCurrentBoard() {
		return currentBoard;
	}

	// 현재 게시판 정보 저장
	public void setCurrentBoard(Board currentBoard) {
		this.currentBoard = currentBoard;
	}

	// 로그인 중인지 여부 확인
	public boolean isLogined() {
		return loginedMember != null;
	}
}