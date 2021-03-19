package com.revature.data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.revature.model.Employee;
import com.revature.util.ConnectionUtil;

public class LoginOracle implements LoginDao {
	private static ConnectionUtil cu = ConnectionUtil.getInstance();
	
	@Override
	public Employee login(String username, String password) {
		Employee login = null;
		Connection conn = null;
		conn = cu.getConnection();
		String sql = "Select * from EMPLOYEE "
				+ "where EMP_USERNAME = ? and EMP_PASSWORD =?";
		
		
		try {
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setString(1,username);
			pstmt.setString(2,password);
			
			ResultSet rs = pstmt.executeQuery();
			if(rs.next()) {
				login = new Employee(rs.getInt("EMP_ID"),
						rs.getString("EMP_USERNAME"),
						rs.getString("EMP_PASSWORD"),
						rs.getString("FIRST_NAME"),
						rs.getString("LAST_NAME"),
						rs.getString("USER_TYPE"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return login;
	}
}
