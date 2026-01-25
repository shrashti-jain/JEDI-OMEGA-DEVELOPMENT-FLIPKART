package com.flipfit.bean;

public class Trainer {
	
	private String trainerId;
	private String name;
	private String specialization;
	private String contact;

	public Trainer(){}

	public Trainer(String trainerId, String name,String specialization, String contact){
		this.trainerId=trainerId;
		this.name=name;
		this.specialization=specialization;
		this.contact=contact;
	}
	
	public String getTrainerId() {
		return trainerId;
	}
	public void setTrainerId(String trainerId) {
		this.trainerId = trainerId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSpecialization() {
		return Specialization;
	}
	public void setSpecialization(String specialization) {
		Specialization = specialization;
	}
	public String getContact() {
		return contact;
	}
	public void setContact(String contact) {
		this.contact = contact;
	}
}
