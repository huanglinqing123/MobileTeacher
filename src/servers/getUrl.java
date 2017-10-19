package servers;

public class getUrl {
	private String name;
	private String imageUrl;
	private String sex;
	private String phone;
	private String touxiang;
	private int id;
	public getUrl(String name,String sex,String phone,String touxiang,int id){
		   setName(name);
		   setsex(sex);
		   setphone(phone);
		   settouxiang(touxiang);
		   setid(id);
		    
	   }
	//性别
	public String getsex() {
		return sex;
	}
	public void setsex(String sex) {
		this.sex = sex;
	}
	//姓名
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
//手机号码
	public String  getphone() {
		return phone;
	}
	public void  setphone(String phone) {
		this.phone = phone;
	}
	//头像
	
	public String  gettouxiang() {
		return touxiang;
	}
	public void  settouxiang(String touxiang) {
		this.touxiang = touxiang;
	}
	//状态
	
		public int  getid() {
			return id;
		}
		public void  setid(int id) {
			this.id = id;
		}
}
