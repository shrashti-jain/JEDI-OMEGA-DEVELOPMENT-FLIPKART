package com.flipfit.bean;

public class GymCenter {
	private String centerId;
	private String centerName;
	private String location;
	private String city;

	public GymCenter(){}

	public GymCenter(String centerId,String name, String location, String city){
		this.centerId=centerId;
		this.name=name;
		this.location=location;
		this.city=city;
	}

	public void addSlot(Slot slot){}

	public List<Slot> getSlotByDate(String date){
		return null;
	}

	public String getCenterId() {
		return centerId;
	}
	public void setCenterId(String centerId) {
		this.centerId = centerId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
}
