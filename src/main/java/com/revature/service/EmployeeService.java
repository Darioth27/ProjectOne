package com.revature.service;

import java.util.List;

import com.revature.data.EmployeeDao;
import com.revature.model.Employee;

public class EmployeeService {
	
	private static EmployeeService empServ;
	
	private EmployeeService() {}
	
	public static EmployeeService getInstance() {
		if (empServ == null) {
			empServ = new EmployeeService();
		}
		return empServ;
	}
	
	public boolean registerEmployee(Employee emp) {
		return EmployeeDao.getInstance().insertProcedure(emp);
	}

	public Employee getEmployee(String username) {
		return EmployeeDao.getInstance().select(username);
	}
	
	public boolean updateEmployee(Employee emp) {
		return EmployeeDao.getInstance().updateInformation(emp);
	}
	
	public List<Employee> getAllEmployees() {
		return EmployeeDao.getInstance().selectAll();
	}
}
