package com.javaex.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.javaex.vo.GuestbookVo;

public class GuestbookDao {

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

	// 방명록 전체 가져오기
	public List<GuestbookVo> getGbList() {

		List<GuestbookVo> gbList = new ArrayList<GuestbookVo>();

		this.getConnection();

		try {
			// 3. SQL문 준비 / 바인딩 / 실행
			String query = "";
			query += "select  no, ";
			query += "        name, ";
			query += "        password, ";
			query += "        content, ";
			query += "        reg_date ";
			query += "from guestbook ";
			query += "order by reg_date desc ";

			pstmt = conn.prepareStatement(query);
			rs = pstmt.executeQuery();

			// 4.결과처리
			while (rs.next()) {
				int no = rs.getInt("no");
				String name = rs.getString("name");
				String password = rs.getString("password");
				String content = rs.getString("content");
				String regDate = rs.getString("reg_date");

				GuestbookVo gbVo = new GuestbookVo(no, name, password, content, regDate);
				gbList.add(gbVo);
			}
		} catch (SQLException e) {
			System.out.println("error:" + e);
		}
		this.close();

		return gbList;
	}

	// 방명록 저장하기
	public int gbInsert(GuestbookVo gbVo) {

		int count = -1;

		this.getConnection();

		try {
			// 3. SQL문 준비 / 바인딩 / 실행
			String query = "";
			query += "insert into guestbook (no, name, password, content, reg_date) ";
			query += "values (seq_guestbook_no.nextval, ?, ?, ?, sysdate) ";

			pstmt = conn.prepareStatement(query);

			pstmt.setString(1, gbVo.getName());
			pstmt.setString(2, gbVo.getPassword());
			pstmt.setString(3, gbVo.getContent());

			count = pstmt.executeUpdate();

			// 4.결과처리
			System.out.println(count + "건 등록 완료.");

		} catch (SQLException e) {
			System.out.println("error:" + e);
		}
		this.close();

		return count;
	}

	// 방명록 삭제
	public int gbDelete(GuestbookVo gbVo) {
		
		int count = -1;
		
		this.getConnection();

		try {
			// 3. SQL문 준비 / 바인딩 / 실행
			String query = "";
			query += "delete from guestbook ";
			query += "where no = ? ";
			query += "and password = ? ";

			pstmt = conn.prepareStatement(query);

			pstmt.setInt(1, gbVo.getNo());
			pstmt.setString(2, gbVo.getPassword());

			count = pstmt.executeUpdate();

			// 4.결과처리
			System.out.println(count + "건 삭제 완료.");

		} catch (SQLException e) {
			System.out.println("error:" + e);
		}
		this.close();

		return count;
	}
}
