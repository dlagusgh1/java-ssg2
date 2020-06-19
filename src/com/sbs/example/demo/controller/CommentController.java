package com.sbs.example.demo.controller;

import java.util.List;

import com.sbs.example.demo.dto.Article;
import com.sbs.example.demo.dto.Board;
import com.sbs.example.demo.factory.Factory;
import com.sbs.example.demo.service.ArticleService;
import com.sbs.example.demo.service.CommentService;

public class CommentController extends Controller {
	private CommentService commentService;
	private ArticleService articleService;

	public CommentController() {
		commentService = Factory.getCommentService();
		articleService = Factory.getArticleService();
	}

	public void doAction(Request reqeust) {
		if (reqeust.getActionName().equals("write")) {
			actionWrite(reqeust);
		} 
	}
	
	// 댓글 작성
	private void actionWrite(Request reqeust) {
		System.out.println("====== 댓글 작성 시작 ======");
		
		
		if(Factory.getSession().getLoginedMember() != null) {
			
			System.out.printf("댓글 작성 할 게시물 번호 : ");
			int id = Factory.getScanner().nextInt();
			Factory.getScanner().nextLine();
			
			List<Article> articles = articleService.getArticles();
			for (Article article : articles) {
				if (article.getId() == id) {
					System.out.printf("댓글 내용 : ");
					String body = Factory.getScanner().nextLine();

					// 현재 로그인한 회원의 id 가져오기
					int memberId = Factory.getSession().getLoginedMember().getId();
					
					int articleId = article.getId();
					commentService.write(memberId, articleId, body);
				}
			}
			System.out.printf("%d번 글에 댓글이 작성 되었습니다.\n", id);
		} else {
			System.out.println("로그인 한 회원만 게시물 작성이 가능합니다.");
		}
		
		System.out.println("====== 댓글 작성 끝 ======");
	}
	
	/*
	 * Board currentBoard = Factory.getSession().getCurrentBoard();
		
		System.out.printf("상세보기 할 게시물 번호 : ");
		int id = Factory.getScanner().nextInt();
		Factory.getScanner().nextLine();
		
		List<Article> articles = articleService.getArticlesByBoardCode(currentBoard.getCode());
		for (Article article : articles) {
			if (article.getId() == id) {
				System.out.println("게시물 번호 : " + article.getId() );
				System.out.println("게시물 제목 : " + article.getTitle());
				System.out.println("게시물 내용 : " + article.getBody());
				System.out.println("게시물 작성자 : " + Factory.getMemberDao().getMember(article.getMemberId()).getLoginId() );
				System.out.println("게시물 등록일자 : " + article.getRegDate() );
			}
		}
	 * */
		
}