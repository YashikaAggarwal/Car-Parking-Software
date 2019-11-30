package CustomerLog;

public class CustomerBean {
     String mob;
     String name;
     String address;
     String city;
     String pic;
     
     CustomerBean(){};
     public CustomerBean(String mob, String name, String address, String city, String pic) {
 		super();
 		this.mob = mob;
 		this.name = name;
 		this.address = address;
 		this.city = city;
 		this.pic = pic;
 	}
	public String getMob() {
		return mob;
	}
	public void setMob(String mob) {
		this.mob = mob;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getPic() {
		return pic;
	}
	public void setPic(String pic) {
		this.pic = pic;
	}
}
