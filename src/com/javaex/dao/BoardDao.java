package com.javaex.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.javaex.vo.BoardVo;

public class BoardDao {

	// 0. import java.sql.*;
	private Connection conn = null;
	private PreparedStatement pstmt = null;
	private ResultSet rs = null;

	private String driver = "oracle.jdbc.driver.OracleDriver";
	private String url = "jdbc:oracle:thin:@localhost:1521:xe";
	private String id = "webdb";
	private String pw = "webdb";

	// DB 연결 메소드
	private void getConnection() {
		try {
			// 1. JDBC 드라이버 (Oracle) 로딩
			Class.forName(driver);

			// 2. Connection 얻어오기
			conn = DriverManager.getConnection(url, id, pw);
			// System.out.println("접속성공");

		} catch (ClassNotFoundException e) {
			System.out.println("error: 드라이버 로딩 실패 - " + e);
		} catch (SQLException e) {
			System.out.println("error:" + e);
		}
	}

	// 자원정리 메소드
	private void close() {
		// 5. 자원정리
		try {
			if (rs != null) {
				rs.close();
			}
			if (pstmt != null) {
				pstmt.close();
			}
			if (conn != null) {
				conn.close();
			}
		} catch (SQLException e) {
			System.out.println("error:" + e);
		}
	}

	// 게시판 리스트
	public List<BoardVo> getBoardList() {
		return getBoardList("");
	}

	// 게시판 전체 / 검색 시 정보 가져오기
	public List<BoardVo> getBoardList(String keyword) {

		List<BoardVo> brdList = new ArrayList<BoardVo>();

		this.getConnection();

		try {
			// 3. SQL문 준비 / 바인딩 / 실행
			String query = "";
			query += "select  b.no, ";
			query += "        b.title, ";
			query += "        b.content, ";
			query += "        u.name, ";
			query += "        b.hit, ";
			query += "        to_char(b.reg_date, 'yyyy-mm-dd hh24:mi') reg_date, ";
			query += "        b.user_no ";
			query += "from board b, users u ";
			query += "where b.user_no = u.no ";

			// 바인딩
			if (keyword == "" || keyword == null) {
				query += "order by reg_date desc  ";
				pstmt = conn.prepareStatement(query);
			} else {
				query += "and title like ? ";
				query += "order by reg_date desc  ";
				
				pstmt = conn.prepareStatement(query); 
				
				pstmt.setString(1, '%' + keyword + '%'); 
			}

			rs = pstmt.executeQuery();

			// 4.결과처리
			while (rs.next()) {
				int no = rs.getInt("no");
				String title = rs.getString("title");
				String content = rs.getString("content");
				String userName = rs.getString("name");
				int hit = rs.getInt("hit");
				String regDate = rs.getString("reg_date");
				int userNo = rs.getInt("user_no");

				BoardVo bVo = new BoardVo(no, title, content, hit, regDate, userNo, userName);
				brdList.add(bVo);
			}
		} catch (SQLException e) {
			System.out.println("error:" + e);
		}
		this.close();

		return brdList;
	}

	// 게시판 글 1개 불러오기
	public BoardVo brdRead(int no) {

		BoardVo bVo = null;

		this.getConnection();

		try {
			// SQL문 준비
			String query = "";
			query += "select  b.no, ";
			query += "        b.title, ";
			query += "        b.content, ";
			query += "        u.name, ";
			query += "        b.hit, ";
			query += "        to_char(b.reg_date, 'yyyy-mm-dd hh24:mi') reg_date, ";
			query += "        b.user_no ";
			query += "from board b, users u ";
			query += "where b.user_no = u.no ";
			query += "and b.no = ? ";

			// 바인딩
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, no);

			// 실행
			rs = pstmt.executeQuery();

			// 4.결과처리
			while (rs.next()) {
				int bNo = rs.getInt("no");
				String title = rs.getString("title");
				String content = rs.getString("content");
				String userName = rs.getString("name");
				int hit = rs.getInt("hit");
				String regDate = rs.getString("reg_date");
				int userNo = rs.getInt("user_no");

				bVo = new BoardVo(bNo, title, content, hit, regDate, userNo, userName);
			}
		} catch (SQLException e) {
			System.out.println("error:" + e);
		}
		this.close();

		return bVo;
	}

	// 게시판 글쓰기 메소드
	public int brdWrite(BoardVo bVo) {

		int count = -1;

		this.getConnection();

		try {
			// 3. SQL문 준비 / 바인딩 / 실행
			String query = "";
			query += "insert into board ";
			query += "values (seq_board_no.nextval, ?, ?, 0, sysdate, ?) ";

			pstmt = conn.prepareStatement(query);

			pstmt.setString(1, bVo.getTitle());
			pstmt.setString(2, bVo.getContent());
			pstmt.setInt(3, bVo.getUserNo());

			count = pstmt.executeUpdate();

			// 4.결과처리
			System.out.println(count + "건 등록 완료.");

		} catch (SQLException e) {
			System.out.println("error:" + e);
		}
		this.close();

		return count;
	}

	// 게시글 삭제 메소드
	public int brdDelete(int no) {

		int count = -1;

		this.getConnection();

		try {
			// 3. SQL문 준비 / 바인딩 / 실행
			String query = "";
			query += "delete from board ";
			query += "where no = ? ";

			pstmt = conn.prepareStatement(query);

			pstmt.setInt(1, no);

			count = pstmt.executeUpdate();

			// 4.결과처리
			System.out.println(count + "건 삭제 완료.");

		} catch (SQLException e) {
			System.out.println("error:" + e);
		}
		this.close();

		return count;
	}

	// 게시글 수정 메소드
	public int brdModify(BoardVo bVo) {

		int count = -1;

		this.getConnection();

		try {
			// SQL문 준비
			String query = "";
			query += "update board ";
			query += "set title = ?, ";
			query += "    content = ? ";
			query += "where no = ? ";

			// 바인딩
			pstmt = conn.prepareStatement(query);

			pstmt.setString(1, bVo.getTitle());
			pstmt.setString(2, bVo.getContent());
			pstmt.setInt(3, bVo.getNo());

			// 실행
			count = pstmt.executeUpdate();

			// 4.결과처리
			System.out.println(count + "건이 수정 완료.");

		} catch (SQLException e) {
			System.out.println("error:" + e);
		}
		this.close();

		return count;
	}

	// 조회수 업데이트 메소드
	public int updateHit(int no) {

		int count = -1;

		this.getConnection();

		try {
			// SQL문 준비
			String query = "";
			query += "update board ";
			query += "set hit = hit+1 ";
			query += "where no = ? ";

			// 바인딩
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, no);

			// 실행
			count = pstmt.executeUpdate();

			// 4.결과처리
			System.out.println(count + "건이 반영 완료.");

		} catch (SQLException e) {
			System.out.println("error:" + e);
		}
		this.close();

		return count;
	}
}
