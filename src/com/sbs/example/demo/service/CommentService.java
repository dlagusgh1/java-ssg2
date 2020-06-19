package com.sbs.example.demo.service;

import com.sbs.example.demo.dao.CommentDao;
import com.sbs.example.demo.dto.ArticleReply;
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

	// 게시물 삭제
	public void delete(int id) {	
		if (commentDao.getComment(id).getMemberId() == Factory.getSession().getLoginedMember().getId() || Factory.getSession().getLoginedMember().getLoginId().equals("admin")  ) {
			commentDao.delete(id);	
		} else {
			System.out.println("게시물 작성자만 삭제 가능합니다.");
			return;
		}
	}

}