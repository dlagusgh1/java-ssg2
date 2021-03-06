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
public class ArticleDao {
	private DBConnection dbConnection;

	public ArticleDao() {
		dbConnection = Factory.getDBConnection();
	}

	// article 게시물 리스트(전체)
	public List<Article> getArticles() {
		StringBuilder sb = new StringBuilder();

		sb.append(String.format("SELECT * "));
		sb.append(String.format("FROM `article` "));
		sb.append(String.format("WHERE 1 "));
		sb.append(String.format("ORDER BY id DESC "));

		List<Article> articles = new ArrayList<>();
		List<Map<String, Object>> rows = dbConnection.selectRows(sb.toString());

		for (Map<String, Object> row : rows) {
			articles.add(new Article(row));
		}

		return articles;
	}

	// article 게시물 리스트(보드 코드에 따라 notice / free)
	public List<Article> getArticlesByBoardCode(String code) {
		StringBuilder sb = new StringBuilder();

		sb.append(String.format("SELECT A.* "));
		sb.append(String.format("FROM `article` AS A "));
		sb.append(String.format("INNER JOIN `board` AS B "));
		sb.append(String.format("ON A.boardId = B.id "));
		sb.append(String.format("WHERE 1 "));
		sb.append(String.format("AND B.`code` = '%s' ", code));
		sb.append(String.format("ORDER BY A.id DESC "));

		List<Article> articles = new ArrayList<>();
		List<Map<String, Object>> rows = dbConnection.selectRows(sb.toString());

		for (Map<String, Object> row : rows) {
			articles.add(new Article(row));
		}

		return articles;
	}

	// 게시물 작성
	public int save(Article article) {
		StringBuilder sb = new StringBuilder();

		sb.append(String.format("INSERT INTO article "));
		sb.append(String.format("SET regDate = '%s' ", article.getRegDate()));
		sb.append(String.format(", `title` = '%s' ", article.getTitle()));
		sb.append(String.format(", `body` = '%s' ", article.getBody()));
		sb.append(String.format(", `memberId` = '%d' ", article.getMemberId()));
		sb.append(String.format(", `boardId` = '%d' ", article.getBoardId()));

		return dbConnection.insert(sb.toString());
	}

	// 게시물 수정
	public void modify(int id) {
		StringBuilder sb = new StringBuilder();

		System.out.printf("제목 : ");
		String title = Factory.getScanner().nextLine();
		System.out.printf("내용 : ");
		String body = Factory.getScanner().nextLine();

		sb.append(String.format("UPDATE article "));
		sb.append(String.format("SET title = '%s' ", title));
		sb.append(String.format(", `body` = '%s' ", body));
		sb.append(String.format("WHERE id = '%d' ", id));

		dbConnection.update(sb.toString());
		System.out.printf("%d번 글이 수정되었습니다.\n", id);

	}

	// 게시물 삭제
	public void delete(int id) {
		StringBuilder sb = new StringBuilder();
		sb.append(String.format("DELETE FROM "));
		sb.append(String.format("article WHERE id = '%d' ", id));

		dbConnection.delete(sb.toString());
		System.out.printf("%d번 글이 삭제되었습니다.\n", id);
	}

	// 게시판 변경 관련 - 보드코드(notice(1) / free(2))에 따른 보드 가져오기
	public Board getBoardByCode(String code) {
		StringBuilder sb = new StringBuilder();

		sb.append(String.format("SELECT * "));
		sb.append(String.format("FROM `board` "));
		sb.append(String.format("WHERE 1 "));
		sb.append(String.format("AND `code` = '%s' ", code));

		Map<String, Object> row = dbConnection.selectRow(sb.toString());

		if (row.isEmpty()) {
			return null;
		}

		return new Board(row);
	}

	// 보드 리스트 가져오기(전체)
	public List<Board> getBoards() {
		StringBuilder sb = new StringBuilder();

		sb.append(String.format("SELECT * "));
		sb.append(String.format("FROM `board` "));
		sb.append(String.format("WHERE 1 "));
		sb.append(String.format("ORDER BY id DESC "));

		List<Board> boards = new ArrayList<>();
		List<Map<String, Object>> rows = dbConnection.selectRows(sb.toString());

		for (Map<String, Object> row : rows) {
			boards.add(new Board(row));
		}

		return boards;
	}

	// 특정 게시물 가져오기.(게시물 존재유무 등 체크 위함)
	public Article getArticle(int id) {
		StringBuilder sb = new StringBuilder();

		sb.append(String.format("SELECT * "));
		sb.append(String.format("FROM `article` "));
		sb.append(String.format("WHERE 1 "));
		sb.append(String.format("AND `id` = '%d' ", id));

		Map<String, Object> row = dbConnection.selectRow(sb.toString());

		if (row.isEmpty()) {
			return null;
		}

		return new Article(row);
	}

	// 보드 가져오기 - 보드 번호에 따른 보드 가져오기
	public Board getBoard(int id) {
		StringBuilder sb = new StringBuilder();

		sb.append(String.format("SELECT * "));
		sb.append(String.format("FROM `board` "));
		sb.append(String.format("WHERE 1 "));
		sb.append(String.format("AND `id` = '%d' ", id));

		Map<String, Object> row = dbConnection.selectRow(sb.toString());

		if (row.isEmpty()) {
			return null;
		}

		return new Board(row);
	}

	// 보드 생성(notice / free)
	public int saveBoard(Board board) {
		StringBuilder sb = new StringBuilder();

		sb.append(String.format("INSERT INTO board "));
		sb.append(String.format("SET regDate = '%s' ", board.getRegDate()));
		sb.append(String.format(", `code` = '%s' ", board.getCode()));
		sb.append(String.format(", `name` = '%s' ", board.getName()));

		return dbConnection.insert(sb.toString());
	}

	// 게시물 가져오기 ( 댓글 작성을 위해 입력된 번호에 따른 해당 게시물 )
	public List<ArticleReply> getArticleRepliesByArticleId(int articleId) {
		StringBuilder sb = new StringBuilder();

		sb.append(String.format("SELECT * "));
		sb.append(String.format("FROM `articleReply` "));
		sb.append(String.format("WHERE articleId = '%d' ", articleId));
		sb.append(String.format("ORDER BY id DESC "));

		List<ArticleReply> articleReplies = new ArrayList<>();
		List<Map<String, Object>> rows = dbConnection.selectRows(sb.toString());

		for (Map<String, Object> row : rows) {
			articleReplies.add(new ArticleReply(row));
		}

		return articleReplies;
	}

}