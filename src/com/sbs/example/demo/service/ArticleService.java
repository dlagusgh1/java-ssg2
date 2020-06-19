package com.sbs.example.demo.service;

import java.util.List;

import com.sbs.example.demo.dao.ArticleDao;
import com.sbs.example.demo.dto.Article;
import com.sbs.example.demo.dto.ArticleReply;
import com.sbs.example.demo.dto.Board;
import com.sbs.example.demo.factory.Factory;

public class ArticleService {
	private ArticleDao articleDao;

	public ArticleService() {
		articleDao = Factory.getArticleDao();
	}
	
	// article 게시물 리스트 가져오기(전체)
	public List<Article> getArticles() {
		return articleDao.getArticles();
	}

	// article 게시물 리스트(보드 코드에 따라 notice / free)
	public List<Article> getArticlesByBoardCode(String code) {
		return articleDao.getArticlesByBoardCode(code);
	}
		
	// 게시물 작성
	public int write(int boardId, int memberId, String title, String body) {
		Article article = new Article(boardId, memberId, title, body);
		return articleDao.save(article);
	}
	
	// 게시물 수정
	public void modify(int id) {
		// 게시물 작성자 or 관리자만 수정가능
		
		if (articleDao.getArticle(id).getMemberId() == Factory.getSession().getLoginedMember().getId() || Factory.getSession().getLoginedMember().getLoginId().equals("admin")  ) {
			articleDao.modify(id);	
		} else {
			System.out.println("게시물 작성자만 수정 가능합니다.");
		}
	}
	
	// 게시물 삭제
	public void delete(int id) {
		if (articleDao.getArticle(id).getMemberId() == Factory.getSession().getLoginedMember().getId() || Factory.getSession().getLoginedMember().getLoginId().equals("admin")  ) {
			articleDao.delete(id);	
		} else {
			System.out.println("게시물 작성자만 삭제 가능합니다.");
		}		
	}
	

	// 게시판 변경 관련 - 보드코드(notice(1) / free(2))에 따른 보드 가져오기
	public Board getBoardByCode(String boardCode) {
		return articleDao.getBoardByCode(boardCode);
	}

	// 보드 리스트 가져오기(전체)
	public List<Board> getBoards() {
		return articleDao.getBoards();
	}
	
	// 보드 가져오기 - 보드 번호에 따른 보드 가져오기
	public Board getBoard(int id) {
		return articleDao.getBoard(id);
	}

	// 초기에 보드 생성하기(notice / free)
	public int makeBoard(String name, String code) {
		Board oldBoard = articleDao.getBoardByCode(code);

		if (oldBoard != null) {
			return -1;
		}

		Board board = new Board(name, code);
		return articleDao.saveBoard(board);
	}
	
	// 초기에 보드 생성하기 관련 중복방지 - 존재여부 체크하여 없는 경우에만 생성
	public void makeBoardIfNotExists(String name, String code) {
		Board board = articleDao.getBoardByCode(code);
		
		if ( board == null ) {
			makeBoard(name, code);
		}
	}

	// 게시물 존재유무 확인
	public Article getArticle(int id) {
		return articleDao.getArticle(id);
	}
	
	// 게시물 가져오기 ( 댓글 작성을 위해 입력된 번호에 따른 해당 게시물 )
	public List<ArticleReply> getArticleRepliesByArticleId(int id) {
		return articleDao.getArticleRepliesByArticleId(id);
	}


}