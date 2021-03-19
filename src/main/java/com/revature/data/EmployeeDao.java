package com.revature.data;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.revature.model.Employee;
import com.revature.util.ConnectionUtil;

public class EmployeeDao implements BaseDao<Employee>{
	
	private static EmployeeDao empDao;
	final static Logger log = Logger.getLogger(EmployeeDao.class);
	//private static ConnectionUtil cu = ConnectionUtil.getInstance();
	
	private EmployeeDao() {}

	public static EmployeeDao getInstance() {
		if (empDao == null) {
			empDao = new EmployeeDao();
		}
		return empDao;
	}
	
	public boolean insertProcedure(Employee emp) {
		try(Connection conn = ConnectionUtil.getInstance().getConnection()){
			int statementIndex = 0;
			
			String storedProc = "CALL INSERT_EMP(?,?,?,?)";

			CallableStatement c = conn.prepareCall(storedProc);
			
			//Set attributes to insert
			c.setString(++statementIndex, emp.getUsername());
			c.setString(++statementIndex, emp.getPassword());
			c.setString(++statementIndex, emp.getFirstName());
			c.setString(++statementIndex, emp.getLastName());
			
			if(c.executeUpdate() == 0) {
				return true;
			}
			
		} catch (SQLException e) {
			log.error("Exception occured in EmployeeDao insertProcedure: " + e.getMessage());
			e.printStackTrace();
		}
		return false;

	}
	
	public boolean updateInformation(Employee emp) {
		try(Connection conn = ConnectionUtil.getInstance().getConnection()){
			int statementIndex = 0;
			
			String storedProc = "CALL UPDATE_EMP(?,?,?,?,?)";

			CallableStatement c = conn.prepareCall(storedProc);
			
			//Set attributes to insert
			c.setLong(++statementIndex, emp.getEmpID());
			c.setString(++statementIndex, emp.getUsername());
			c.setString(++statementIndex, emp.getPassword());
			c.setString(++statementIndex, emp.getFirstName());
			c.setString(++statementIndex, emp.getLastName());
			
			if(c.executeUpdate() == 0) {
				return true;
			}
			
		} catch (SQLException e) {
			log.error("Exception occured in EmployeeDao updateInformation: " + e.getMessage());
			e.printStackTrace();
		}
		return false;
	}

	public Employee select(String username) {
		try (Connection conn = ConnectionUtil.getInstance().getConnection()) {
			//String storedProc = "CALL VALIDATE_USER(?,?)";

			//CallableStatement c = conn.prepareCall(storedProc);
			String sql = "SELECT * FROM Employee WHERE EMP_USERNAME=?";
			PreparedStatement ps = conn.prepareStatement(sql);
			
			//Set attributes to insert
			ps.setString(1, username);
			
			ResultSet rs = ps.executeQuery();
			
			if (rs.next()) {
				return new Employee(
						rs.getInt("EMP_ID"),
						rs.getString("EMP_USERNAME"),
						rs.getString("EMP_PASSWORD"),
						rs.getString("FIRST_NAME"),
						rs.getString("LAST_NAME"),
						rs.getString("USER_TYPE"));
			}
		} catch(SQLException s) {
			log.error("Exception occured in select: " + s.getMessage());
			System.out.println(s.getMessage());
		}
		return null;
	}
	
	public Employee selectByID(int id) {
		try (Connection conn = ConnectionUtil.getInstance().getConnection()) {
			//String storedProc = "CALL VALIDATE_USER(?,?)";

			//CallableStatement c = conn.prepareCall(storedProc);
			String sql = "SELECT * FROM Employee WHERE EMP_ID=?";
			PreparedStatement ps = conn.prepareStatement(sql);
			
			//Set attributes to insert
			ps.setLong(1, id);
			
			ResultSet rs = ps.executeQuery();
			
			if (rs.next()) {
				return new Employee(
						rs.getInt("EMP_ID"),
						rs.getString("EMP_USERNAME"),
						rs.getString("EMP_PASSWORD"),
						rs.getString("FIRST_NAME"),
						rs.getString("LAST_NAME"),
						rs.getString("USER_TYPE"));
			}
		} catch(SQLException s) {
			log.error("Exception occured in select: " + s.getMessage());
			System.out.println(s.getMessage());
		}
		return null;
	}

	public List<Employee> selectAll() {
		List<Employee> allEmployees = new ArrayList<Employee>();
		try (Connection conn = ConnectionUtil.getInstance().getConnection()) {

			String sql = "SELECT * FROM Employee";
			PreparedStatement ps = conn.prepareStatement(sql);
			
			ResultSet rs = ps.executeQuery();
			
			while (rs.next()) {
				allEmployees.add(new Employee(
						rs.getInt("EMP_ID"),
						rs.getString("EMP_USERNAME"),
						rs.getString("EMP_PASSWORD"),
						rs.getString("FIRST_NAME"),
						rs.getString("LAST_NAME"),
						rs.getString("USER_TYPE")));
			}
		} catch(SQLException s) {
			log.error("Exception occured in selectAll: " + s.getMessage());
		}
		return allEmployees;
	}
}
