package com.javaex.dao;

import java.util.List;

import com.javaex.vo.BoardVo;


public class DaoTest {

	public static void main(String[] args) {

		BoardDao brdDao = new BoardDao();
		List<BoardVo> brdList = brdDao.getBoardList();
		
		System.out.println(brdList);
		
	}

}
