package com.sbs.example.demo.dao;
import java.util.Map;

import com.sbs.example.demo.db.DBConnection;
import com.sbs.example.demo.dto.Member;
import com.sbs.example.demo.factory.Factory;

public class MemberDao {
	private DBConnection dbConnection;

	public MemberDao() {
		dbConnection = Factory.getDBConnection();
	}

	// 로그인 - 아이디 / 비밀번호 일치하는 게 있는지 확인
	public Member getMemberByLoginIdAndLoginPw(String loginId, String loginPw) {
		StringBuilder sb = new StringBuilder();

		sb.append(String.format("SELECT * "));
		sb.append(String.format("FROM `member` "));
		sb.append(String.format("WHERE 1 "));
		sb.append(String.format("AND `loginId` = '%s' ", loginId));
		sb.append(String.format("AND `loginPw` = '%s' ", loginPw));

		Map<String, Object> row = dbConnection.selectRow(sb.toString());
		
		if ( row.isEmpty() ) {
			return null;
		}
		
		return new Member(row);
	}

	// 회원가입 - 아이디 중복되는게 있는지 확인
	public Member getMemberByLoginId(String loginId) {
		StringBuilder sb = new StringBuilder();

		sb.append(String.format("SELECT * "));
		sb.append(String.format("FROM `member` "));
		sb.append(String.format("WHERE 1 "));
		sb.append(String.format("AND loginId = '%s' ", loginId));

		Map<String, Object> memberRow = dbConnection.selectRow(sb.toString());

		if (memberRow.isEmpty()) {
			return null;
		}

		return new Member(memberRow);
	}

	// 
	public Member getMember(int id) {
		StringBuilder sb = new StringBuilder();

		sb.append(String.format("SELECT * "));
		sb.append(String.format("FROM `member` "));
		sb.append(String.format("WHERE 1 "));
		sb.append(String.format("AND `id` = '%d' ", id));

		Map<String, Object> row = dbConnection.selectRow(sb.toString());
		
		if ( row.isEmpty() ) {
			return null;
		}
		
		return new Member(row);
	}

	// 회원가입
	public int save(Member member) {
		StringBuilder sb = new StringBuilder();

		sb.append(String.format("INSERT INTO member "));
		sb.append(String.format("SET regDate = '%s' ", member.getRegDate()));
		sb.append(String.format(", loginId = '%s' ", member.getLoginId()));
		sb.append(String.format(", loginPw = '%s' ", member.getLoginPw()));
		sb.append(String.format(", `name` = '%s' ", member.getName()));

		return dbConnection.insert(sb.toString());
	}
}