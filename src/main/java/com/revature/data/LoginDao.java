package com.revature.data;

import com.revature.model.Employee;

public interface LoginDao {
	Employee login(String username, String password);
}
