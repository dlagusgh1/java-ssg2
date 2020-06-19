package com.sbs.example.demo.service;

import java.util.List;

import com.sbs.example.demo.dao.ArticleDao;
import com.sbs.example.demo.dao.CommentDao;
import com.sbs.example.demo.dto.Article;
import com.sbs.example.demo.dto.ArticleReply;
import com.sbs.example.demo.dto.Board;
import com.sbs.example.demo.factory.Factory;

public class CommentService {
	private CommentDao commentDao;

	public CommentService() {
		commentDao = Factory.getCommentDao();
	}
		
	// 게시물 작성
	public int write(int memberId, int articleId, String body) {
		ArticleReply articleReply = new ArticleReply(memberId, articleId, body);
		return commentDao.save(articleReply);
	}

}