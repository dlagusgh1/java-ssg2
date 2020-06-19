package com.sbs.example.demo.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.sbs.example.demo.db.DBConnection;
import com.sbs.example.demo.dto.Article;
import com.sbs.example.demo.dto.ArticleReply;
import com.sbs.example.demo.dto.Board;
import com.sbs.example.demo.factory.Factory;

// Dao
public class CommentDao {
	private DBConnection dbConnection;

	public CommentDao() {
		dbConnection = Factory.getDBConnection();
	}
	// 게시물 작성
	public int save(ArticleReply articleReply) {
		StringBuilder sb = new StringBuilder();

		sb.append(String.format("INSERT INTO articleReply "));
		sb.append(String.format("SET regDate = '%s' ", articleReply.getRegDate()));
		sb.append(String.format(", `body` = '%s' ", articleReply.getBody()));
		sb.append(String.format(", `memberId` = '%d' ", articleReply.getMemberId()));
		sb.append(String.format(", `articleId` = '%d' ", articleReply.getArticleId()));

		return dbConnection.insert(sb.toString());
	}

}