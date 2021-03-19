package com.revature.model;

public class Reimbursement {
	private int id;
	private int empID;
	private double amount;
	private String info;
	private String status;
	private int approverID;
	public Reimbursement(int id, int empID, double amount, String info, String status, int approverID) {
		super();
		this.id = id;
		this.empID = empID;
		this.amount = amount;
		this.info = info;
		this.status = status;
		this.approverID = approverID;
	}
	
	public Reimbursement(int id) {
		this.id = id;
	}
	
	public Reimbursement() {
		super();
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getEmpID() {
		return empID;
	}
	public void setEmpID(int empID) {
		this.empID = empID;
	}
	public double getAmount() {
		return amount;
	}
	public void setAmount(double amount) {
		this.amount = amount;
	}
	public String getInfo() {
		return info;
	}
	public void setInfo(String info) {
		this.info = info;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public int getApproverID() {
		return approverID;
	}
	public void setApproverID(int approverID) {
		this.approverID = approverID;
	}
	@Override
	public String toString() {
		return "Reimbursement [id=" + id + ", empID=" + empID + ", amount=" + amount + ", info=" + info + ", status="
				+ status + ", approverID=" + approverID + "]";
	}
	
	
}
