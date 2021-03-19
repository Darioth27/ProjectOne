package com.revature.service;

import java.util.List;

import com.revature.data.ReimbursementDao;
import com.revature.model.Employee;
import com.revature.model.Reimbursement;

public class ReimbursementService {
private static ReimbursementService empServ;
	
	private ReimbursementService() {}
	
	public static ReimbursementService getInstance() {
		if (empServ == null) {
			empServ = new ReimbursementService();
		}
		return empServ;
	}
	
	public boolean registerReimbursement(Reimbursement rem) {
		return ReimbursementDao.getInstance().insertProcedure(rem);
	}
	
	public boolean approveReimbursement(Reimbursement rem, Employee emp) {
		return ReimbursementDao.getInstance().approve(rem, emp);
	}
	
	public boolean denyReimbursement(Reimbursement rem, Employee emp) {
		return ReimbursementDao.getInstance().deny(rem, emp);
	}

	public List<Reimbursement> getReimbursements() {
		return ReimbursementDao.getInstance().selectAll();
	}
	
	public List<Reimbursement> getReimbursementsOf(Employee emp) {
		return ReimbursementDao.getInstance().selectAll();
	}
	
	public List<Reimbursement> getPending() {
		return ReimbursementDao.getInstance().selectPending();
	}
	
	public List<Reimbursement> getApproved() {
		return ReimbursementDao.getInstance().selectApproved();
	}
}
