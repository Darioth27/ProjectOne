package com.revature.service;

import com.revature.model.Employee;

public class SessionInformation {
	
	private static SessionInformation si;
	private static Employee currentEmployee;
	
	private SessionInformation() {}
	
	public static SessionInformation getInstance() {
		if (si == null) {
			si = new SessionInformation();
		}
		return si;
	}
	
	public Employee currentEmployee() {
		return currentEmployee;
	}
	
	public void setCurrentEmployee(Employee e) {
		currentEmployee = e;
	}
	
	public void endSession() {
		currentEmployee = null;
	}

}
