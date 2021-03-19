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
import com.revature.model.Reimbursement;
import com.revature.util.ConnectionUtil;

public class ReimbursementDao implements BaseDao<Reimbursement>{

	private static ReimbursementDao remDao;
	final static Logger log = Logger.getLogger(ReimbursementDao.class);
	
	private ReimbursementDao() {};
	
	public static ReimbursementDao getInstance() {
		if (remDao == null) {
			remDao = new ReimbursementDao();
		}
		return remDao;
	}
	
	@Override
	public boolean insertProcedure(Reimbursement rem) {
		try(Connection conn = ConnectionUtil.getInstance().getConnection()){
			int statementIndex = 0;
			
			String storedProc = "CALL INSERT_REQUEST(?,?,?)";

			CallableStatement c = conn.prepareCall(storedProc);
			
			//Set attributes to insert
			c.setLong(++statementIndex, rem.getEmpID());
			c.setDouble(++statementIndex, rem.getAmount());
			c.setString(++statementIndex, rem.getInfo());
			
			if(c.executeUpdate() == 0) {
				return true;
			}
			
		} catch (SQLException e) {
			log.error("Exception occured in ReimbursementDao insertProcedure: " + e.getMessage());
			e.printStackTrace();
		}
		return false;
	}
	
	public boolean approve(Reimbursement rem, Employee emp) {
		try(Connection conn = ConnectionUtil.getInstance().getConnection()){
			
			String storedProc = "CALL APPROVE_REQUEST(?,?)";

			CallableStatement c = conn.prepareCall(storedProc);
			
			//Set attributes to insert
			c.setLong(1, rem.getId());
			c.setLong(2, emp.getEmpID());
			
			if(c.executeUpdate() == 0) {
				return true;
			}
			
		} catch (SQLException e) {
			log.error("Exception occured in ReimbursementDao approve: " + e.getMessage());
			e.printStackTrace();
		}
		return false;
	}
	
	public boolean deny(Reimbursement rem, Employee emp) {
		try(Connection conn = ConnectionUtil.getInstance().getConnection()){
			
			String storedProc = "CALL DENY_REQUEST(?,?)";

			CallableStatement c = conn.prepareCall(storedProc);
			
			//Set attributes to insert
			c.setLong(1, rem.getId());
			c.setLong(2, emp.getEmpID());
			
			if(c.executeUpdate() == 0) {
				return true;
			}
			
		} catch (SQLException e) {
			log.error("Exception occured in ReimbursementDao deny: " + e.getMessage());
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public List<Reimbursement> selectAll() {
		List<Reimbursement> allRems = new ArrayList<Reimbursement>();
		try (Connection conn = ConnectionUtil.getInstance().getConnection()) {

			String sql = "SELECT * FROM Reinbursements";
			PreparedStatement ps = conn.prepareStatement(sql);
			
			ResultSet rs = ps.executeQuery();
			
			while (rs.next()) {
				allRems.add(new Reimbursement(
						rs.getInt("R_ID"),
						rs.getInt("EMP_ID"),
						rs.getDouble("R_AMOUNT"),
						rs.getString("R_INFO"),
						rs.getString("R_STATUS"),
						rs.getInt("APPROVER_ID")));
			}
		} catch(SQLException s) {
			log.error("Exception occured in selectAll: " + s.getMessage());
		}
		return allRems;
	}
	
	public List<Reimbursement> selectAllOf(Employee emp) {
		List<Reimbursement> allRems = new ArrayList<Reimbursement>();
		try (Connection conn = ConnectionUtil.getInstance().getConnection()) {

			String sql = "SELECT * FROM Reinbursements WHERE EMP_ID=?";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setLong(1, emp.getEmpID());
			
			ResultSet rs = ps.executeQuery();
			
			while (rs.next()) {
				allRems.add(new Reimbursement(
						rs.getInt("R_ID"),
						rs.getInt("EMP_ID"),
						rs.getDouble("R_AMOUNT"),
						rs.getString("R_INFO"),
						rs.getString("R_STATUS"),
						rs.getInt("APPROVER_ID")));
			}
		} catch(SQLException s) {
			log.error("Exception occured in selectAllOf: " + s.getMessage());
		}
		return allRems;
	}

	public List<Reimbursement> selectPending() {
		List<Reimbursement> allRems = new ArrayList<Reimbursement>();
		try (Connection conn = ConnectionUtil.getInstance().getConnection()) {

			String sql = "SELECT * FROM Reinbursements WHERE R_STATUS='PENDING'";
			PreparedStatement ps = conn.prepareStatement(sql);
			
			ResultSet rs = ps.executeQuery();
			
			while (rs.next()) {
				allRems.add(new Reimbursement(
						rs.getInt("R_ID"),
						rs.getInt("EMP_ID"),
						rs.getDouble("R_AMOUNT"),
						rs.getString("R_INFO"),
						rs.getString("R_STATUS"),
						rs.getInt("APPROVER_ID")));
			}
		} catch(SQLException s) {
			log.error("Exception occured in selectPending: " + s.getMessage());
		}
		return allRems;
	}
	
	public List<Reimbursement> selectApproved() {
		List<Reimbursement> allRems = new ArrayList<Reimbursement>();
		try (Connection conn = ConnectionUtil.getInstance().getConnection()) {

			String sql = "SELECT * FROM Reinbursements WHERE R_STATUS='APPROVED'";
			PreparedStatement ps = conn.prepareStatement(sql);
			
			ResultSet rs = ps.executeQuery();
			
			while (rs.next()) {
				allRems.add(new Reimbursement(
						rs.getInt("R_ID"),
						rs.getInt("EMP_ID"),
						rs.getDouble("R_AMOUNT"),
						rs.getString("R_INFO"),
						rs.getString("R_STATUS"),
						rs.getInt("APPROVER_ID")));
			}
		} catch(SQLException s) {
			log.error("Exception occured in selectApproved: " + s.getMessage());
		}
		return allRems;
	}
}
