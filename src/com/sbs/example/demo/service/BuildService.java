package com.sbs.example.demo.service;

import java.util.List;

import com.sbs.example.demo.dto.Article;
import com.sbs.example.demo.dto.Board;
import com.sbs.example.demo.factory.Factory;
import com.sbs.example.demo.util.Util;

// Service
public class BuildService {
	private ArticleService articleService;
	private int count;
	private int notice_count;
	private int free_count;
	private static boolean worked;

	public BuildService() {
		articleService = Factory.getArticleService();
	}

	static {
		worked = false;
	}

	// build site 자동 수행
	public void startWork() {
		worked = true;
		new Thread(() -> {
			while (worked) {
				try {
					System.out.println("사이트 자동 생성 진행");
					buildSite();
					creatMain();
					creatStatistics();
					Thread.sleep(10000); // 10초간 일시정지
				} catch (InterruptedException e) {

				}
			}
		}).start();
	}

	// build site 자동 수행 종료
	public void stopWork() {
		System.out.println("사이트 자동 생성 종료");
		worked = false;
	}

	// 사이트 통계 화면 생성(각 항목 해당 값만 추가하기)
	public void creatStatistics() {

		count = Factory.getDBConnection().selectRowIntValue("SELECT COUNT(id) FROM article");
		notice_count = Factory.getDBConnection().selectRowIntValue(
				"SELECT boardId, COUNT(boardId) AS cnt FROM article WHERE boardId > 1 GROUP BY boardId;");
		free_count = Factory.getDBConnection().selectRowIntValue(
				"SELECT boardId, COUNT(boardId) AS cnt FROM article WHERE boardId < 2 GROUP BY boardId;");
		Util.makeDir("site");
		Util.makeDir("site/article");
		String head = Util.getFileContents("site_template/part/head.html");
		String foot = Util.getFileContents("site_template/part/foot.html");

		String fileName = "Statistics.html";

		String html = "";

		String template = Util.getFileContents("site_template/article/Statistics.html");

		List<Article> articles = articleService.getArticles();

		html += "<h2 class=\"t1-h\">사이트 통계</h2>";
		html += "<table border=1>";
		html += "<thead>";
		html += "<tr>";
		html += "<td class=\"td1\">전체 게시물 수</td>";
		html += "<td colspan=3>" + count + "개</td>";
		html += "</tr>";
		html += "<tr>";
		html += "<td class=\"td1\">공지사항 게시물 수</td>";
		html += "<td colspan=3>" + notice_count + "개</td>";
		html += "</tr>";
		html += "<tr>";
		html += "<td class=\"td1\">자유게시판 게시물 수</td>";
		html += "<td colspan=3>" + free_count + "개</td>";
		html += "</tr>";

		html = template.replace("${TR}", html);

		html = head + html + foot;

		Util.writeFileContents("site/article/" + fileName, html);
	}

	// 사이트 로그인 화면 생성(생성완료)
	public void creatLogin() {
		Util.makeDir("site");
		Util.makeDir("site/article");
		String head = Util.getFileContents("site_template/part/head.html");
		String foot = Util.getFileContents("site_template/part/foot.html");

		String fileName = "login.html";

		String html = "";

		String template = Util.getFileContents("site_template/article/login.html");

		html = template.replace("${TR}", html);

		html = head + html + foot;

		Util.writeFileContents("site/article/" + fileName, html);
	}

	/*
	 * // head (게시판 목록 자동생성 관련) - 진행 중 public void creatHead() {
	 * Util.makeDir("site"); Util.makeDir("site/article");
	 * 
	 * String head = Util.getFileContents("site_template/part/head.html"); String
	 * foot = Util.getFileContents("site_template/part/foot.html");
	 * 
	 * // head 게시판 목록 자동생성 구현 과련 List<Board> boards = articleService.getBoards();
	 * 
	 * for (Board board : boards) {
	 * 
	 * String fileName = board.getName() + "-boardList-1.html";
	 * 
	 * String html = "";
	 * 
	 * String template = Util.getFileContents("site_template/part/head.html");
	 * 
	 * html += "<li><a href=" + board.getName() + "-list-1.html>" + board.getName()
	 * + "</a></li>";
	 * 
	 * html = template.replace("${TR}", html);
	 * 
	 * html = head + html + foot;
	 * 
	 * Util.writeFileContents("site/article/" + fileName, html); } }
	 */

	// 사이트 홈 화면 생성(생성완료)
	public void creatMain() {
		Util.makeDir("site");
		Util.makeDir("site/article");
		String head = Util.getFileContents("site_template/part/head.html");
		String foot = Util.getFileContents("site_template/part/foot.html");

		String fileName = "main.html";

		String html = "";

		String template = Util.getFileContents("site_template/article/main.html");

		html += "<style> .main-box {text-align: center; padding: 100px;}";
		html += ".main-box > img{width: 800px; border-radius: 50px;}</style>";

		html += "<nav class=\"main-box\">";
		html += "<img src=\"main\\images\\main1.jpg\" alt=\"\"></nav>";

		html = head + html + foot;

		Util.writeFileContents("site/article/" + fileName, html);

	}

	// 리스트 / 상세보기 사이트 생성
	public void buildSite() {
		Util.makeDir("site");
		Util.makeDir("site/article");

		String head = Util.getFileContents("site_template/part/head.html");
		String foot = Util.getFileContents("site_template/part/foot.html");

		// 각 게시판 별 게시물리스트 페이지 생성
		List<Board> boards = articleService.getBoards();

		for (Board board : boards) {
			// 존재하는 보드 들의 이름으로 다 만들어진 ( 4개있을 경우 4개 생성 됨 )
			String fileName = board.getCode() + "-list-1.html";

			String html = "";

			// article이 모두 들어있음.
			List<Article> articles = articleService.getArticlesByBoardCode(board.getCode());

			String template = Util.getFileContents("site_template/article/list.html");

			if (board.getName().equals("notice")) {
				html += "<h2> 공지사항 게시판</h2>";
			} else if (board.getName().equals("free")) {
				html += "<h2> 자유 게시판</h2>";
			} else {
				html += "<h2> " + board.getName() + "게시판</h2>";
			}

			for (Article article : articles) {

				if (board.getCode().equals("1")) {
					notice_count++;
				} else if (board.getCode().equals("2")) {
					free_count++;
				}

				html += "<tr>";
				html += "<td>" + article.getId() + "</td>";
				html += "<td><a href=\"" + article.getId() + ".html\">" + article.getTitle() + "</a></td>";
				html += "<td>" + article.getRegDate() + "</td>";
				html += "</tr>";

			}

			html = template.replace("${TR}", html);

			html = head + html + foot;

			Util.writeFileContents("site/article/" + fileName, html);
		}

		// 게시물 별 파일 생성
		List<Article> articles = articleService.getArticles();

		for (Article article : articles) {
			
			String template = Util.getFileContents("site_template/article/detail.html");
			
			String html = "";
			html += "<h2 class=\"t1-h\">상세보기</h2>";
			html += "<table border=1>";
			html += "<thead>";
			html += "<tr>";
			html += "<td class=\"td1\" colspan=4>게시물 상세보기</td>";
			html += "</tr>";
			html += "<tr>";
			html += "<td class=\"td1\">게시물 번호</td>";
			html += "<td colspan=3>" + article.getId() + "</td>";
			html += "</tr>";
			html += "<tr>";
			html += "<td class=\"td1\">제목</td>";
			html += "<td colspan=3>" + article.getTitle() + "</td>";
			html += "</tr>";
			html += "</tr>";
			html += "<td class=\"td1\">내용</td>";
			html += "<td colspan=3>" + article.getBody() + "</td>";
			html += "</tr>";
			html += "<tr>";
			html += "<td class=\"td1\">작성일자</td>";
			html += "<td colspan=3>" + article.getRegDate() + "</td>";
			html += "</tr>";
			html += "<tr>";
			html += "<td colspan=3><a href=\"" + (article.getId() - 1) + ".html\\\">이전글</a></td>";
			html += "<td colspan=3><a href=\"" + (article.getId() + 1) + ".html\\\">다음글</a></td>";
			html += "</tr>";

			html = template.replace("${TR}", html);
			
			html = head + html + foot;

			Util.writeFileContents("site/article/" + article.getId() + ".html", html);
		}
	}

}