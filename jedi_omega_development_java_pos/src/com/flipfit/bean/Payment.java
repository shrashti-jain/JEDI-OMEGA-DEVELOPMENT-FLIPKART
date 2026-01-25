/**
 * 
 */
package com.flipfit.bean;

/**
 * 
 */
public class Payment {
	private String paymentId;
	private double amount;
	private String mode;
	private String status;

	public Payment(){}
	public Payment(String paymentId, double amount, String mode, String status){
		this.paymentId=paymentId;
		this.amount=amount;
		this.mode=mode;
		this.status=status;
	}

	public void processPayment(){}
	public void refundPayment(){}
	
	public String getPaymentId() {
		return paymentId;
	}
	public double getAmount() {
		return amount;
	}
	public String getMode() {
		return mode;
	}
	public String getStatus() {
		return status;
	}
}
