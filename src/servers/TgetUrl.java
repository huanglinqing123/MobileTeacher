package servers;

public class TgetUrl {
	private String name;
	private String imageUrl;
	private String address;
	private String address2;
	private String price;
	private int statue;
	private String teachername;
	// private int id;
	String kechengtupian;

	public TgetUrl(String name, String address, String price,
			String teachername, String kechengtupian,String address2,int statue) {
		setName(name);
		setaddress(address);
		setprice(price);
		setteachername(teachername);
		setkechengtupian(kechengtupian);
		setaddress2(address2);
		setstatue(statue);

	}

	// 性别
	public String getaddress() {
		return address;
	}

	public void setaddress(String address) {
		this.address = address;
	}
	public String getaddress2() {
		return address2;
	}

	public void setaddress2(String address2) {
		this.address2 = address2;
	}
	public int getstatue() {
		return statue;
	}

	public void setstatue(int statue) {
		this.statue = statue;
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
		return price;
	}

	public void setprice(String price) {
		this.price = price;
	}

	// 头像

	public String getteachername() {
		return teachername;
	}

	public void setteachername(String teachername) {
		this.teachername = teachername;
	}

	// 状态

	public String getkechengtupian() {
		return kechengtupian;
	}

	public void setkechengtupian(String kechengtupian) {
		this.kechengtupian = kechengtupian;
	}
}
