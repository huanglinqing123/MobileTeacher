package com.example.yidongjiajiao;

public class sxiangxin {
	private String name;
	private String imageUrl;
	private String phone;
	private String sex;

	// private int id;
	String kechengtupian;

	public sxiangxin(String name, String imageUrl, String phone, String sex) {
		setName(name);
		setprice(imageUrl);
		setteachername(phone);
		setkechengtupian(sex);

	}

	private void setaddress2(String sex2) {
		// TODO Auto-generated method stub

	}

	// 姓名
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	// 手机号码
	public String getprice() {
		return imageUrl;
	}

	public void setprice(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	// 头像

	public String getteachername() {
		return phone;
	}

	public void setteachername(String phone) {
		this.phone = phone;
	}

	// 状态

	public String getkechengtupian() {
		return kechengtupian;
	}

	public void setkechengtupian(String kechengtupian) {
		this.kechengtupian = kechengtupian;
	}
}
