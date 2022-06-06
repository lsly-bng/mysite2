package com.javaex.vo;

public class UserVo {

	// field
	private int no;
	private String id;
	private String name;
	private String password;
	private String gender;

	// constructor
	public UserVo() {
	}

	public UserVo(String id, String name, String password, String gender) {
		this.id = id;
		this.name = name;
		this.password = password;
		this.gender = gender;
	}

	public UserVo(int no, String id, String name, String password, String gender) {
		this.no = no;
		this.id = id;
		this.name = name;
		this.password = password;
		this.gender = gender;
	}

	public UserVo(int no, String name) {
		this.no = no;
		this.name = name;
	}

	public UserVo(int no, String name, String password, String gender) {
		this.no = no;
		this.name = name;
		this.password = password;
		this.gender = gender;
	}


	// method - g/s
	public int getNo() {
		return no;
	}

	public void setNo(int no) {
		this.no = no;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	// method - general
	@Override
	public String toString() {
		return "UserVo [no=" + no + ", id=" + id + ", name=" + name + ", password=" + password + ", gender=" + gender
				+ "]";
	}
}
