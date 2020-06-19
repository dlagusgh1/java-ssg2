package com.sbs.example.demo.dao;

import java.util.Map;

import com.sbs.example.demo.db.DBConnection;
import com.sbs.example.demo.dto.Article;
import com.sbs.example.demo.dto.ArticleReply;
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
	
	// 특정 게시물의 댓글 가져오기
	public ArticleReply getComment(int id) {
		StringBuilder sb = new StringBuilder();

		sb.append(String.format("SELECT * "));
		sb.append(String.format("FROM `articleReply` "));
		sb.append(String.format("WHERE 1 "));
		sb.append(String.format("AND `id` = '%d' ", id));

		Map<String, Object> row = dbConnection.selectRow(sb.toString());
		
		if ( row.isEmpty() ) {
			return null;
		}
		
		return new ArticleReply(row);
	}
	
	// 게시물 삭제
	public void delete(int id) {
		StringBuilder sb = new StringBuilder();
		sb.append(String.format("DELETE FROM "));
		sb.append(String.format("articleReply WHERE id = '%d' ", id));
		
		dbConnection.delete(sb.toString());
		System.out.printf("댓글이 삭제되었습니다.\n");	
	}

}