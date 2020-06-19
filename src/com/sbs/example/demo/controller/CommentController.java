package com.sbs.example.demo.controller;

import java.util.List;

import com.sbs.example.demo.dto.Article;
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
			actionCommentWrite(reqeust);
		} else if (reqeust.getActionName().equals("delete")) {
			actionCommentDelete(reqeust);
		}
	}

	// 댓글 삭제
	private void actionCommentDelete(Request reqeust) {
		System.out.println("====== 댓글 삭제 시작 ======");

		// 로그인 여부 체크
		if (Factory.getSession().getLoginedMember() != null) {
			int id;

			// 번호 입력이 제대로 되었는지 체크
			try {
				id = Integer.parseInt(reqeust.getArg1());
			} catch (NumberFormatException e) {
				System.out.println("게시물 번호를 숫자로 입력해주세요.");
				return;
			}

			Article article = articleService.getArticle(id);

			// 댓글 삭제 할 게시물 존재여부 체크
			if (article == null) {
				System.out.println("해당 게시물은 존재하지 않습니다.");
				return;
			}
			
			commentService.delete(id);
			
		} else {
			System.out.println("로그인 한 회원만 게시물 삭제가 가능합니다.");
			return;
		}

		System.out.println("====== 댓글 삭제 끝 ======");
	}

	// 댓글 작성
	private void actionCommentWrite(Request reqeust) {
		System.out.println("====== 댓글 작성 시작 ======");

		// 로그인 여부 체크
		if (Factory.getSession().getLoginedMember() != null) {
			int id;

			// 번호 입력이 제대로 되었는지 체크
			try {
				id = Integer.parseInt(reqeust.getArg1());
			} catch (NumberFormatException e) {
				System.out.println("게시물 번호를 숫자로 입력해주세요.");
				return;
			}

			Article article = articleService.getArticle(id);

			// 댓글 작성 할 게시물 존재여부 체크
			if (article == null) {
				System.out.println("해당 게시물은 존재하지 않습니다.");
				return;
			}

			System.out.printf("댓글 내용 : ");
			String body = Factory.getScanner().nextLine();

			// 현재 로그인한 회원의 id 가져오기
			int memberId = Factory.getSession().getLoginedMember().getId();

			int articleId = article.getId();
			commentService.write(memberId, articleId, body);

			System.out.printf("%d번 글에 댓글이 작성 되었습니다.\n", id);
		} else {
			System.out.println("로그인 한 회원만 댓글 작성이 가능합니다.");
			return;
		}

		System.out.println("====== 댓글 작성 끝 ======");
	}

}