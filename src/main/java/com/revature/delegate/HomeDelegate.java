package com.revature.delegate;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.data.EmployeeDao;
import com.revature.data.ReimbursementDao;
import com.revature.model.Employee;
import com.revature.model.Reimbursement;
import com.revature.service.EmployeeService;
import com.revature.service.ReimbursementService;
import com.revature.service.SessionInformation;

public class HomeDelegate {
	
	final static Logger log = Logger.getLogger(HomeDelegate.class);
	
	public void goHome(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		// Get our session information
		HttpSession session = req.getSession();
		// Give a personalized response
		Employee login = (Employee) session.getAttribute("user");

		if (login == null) {
			resp.sendRedirect("login");
		} else {
			PrintWriter pw = resp.getWriter();
			pw.write("<!DOCTYPE html><html><head>" + "<meta charset=\"ISO-8859-1\"><title>HelloWorld</title>"
					+ "</head><body>");

			pw.write("<div><div>" + "<h4>Hello " + login.getUsername() + "</h4></div>"
					+ "<form action=\"logout\" method=\"post\">" + "<input type=\"submit\" value=\"Logout\"/>"
					+ "</form></div>");

			pw.write("</body></html>");
		}
	}

	public void getPage(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// Get our session information
		HttpSession session = req.getSession();
		// Give a personalized response
		Employee login = (Employee) session.getAttribute("user");

		if (login == null) {
			resp.sendRedirect("login");
		} else if (login.getUserType().equals("EMPLOYEE")){
			req.getRequestDispatcher("static/mainMenu.html").forward(req, resp);
			ObjectMapper mapper = new ObjectMapper();
			resp.setHeader("Content-Type", "application/json");
			mapper.writeValue(resp.getOutputStream(), login);
			log.info("Employee " + login.getEmpID() + " has logged in.");
		} else {
			req.getRequestDispatcher("static/mainMenuManager.html").forward(req, resp);
			ObjectMapper mapper = new ObjectMapper();
			resp.setHeader("Content-Type", "application/json");
			mapper.writeValue(resp.getOutputStream(), login);
			log.info("Manager " + login.getEmpID() + " has logged in.");
		}
	}

	public void showUserInfo(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// Get our session information
		HttpSession session = req.getSession();
		// Give a personalized response
		Employee login = (Employee) session.getAttribute("user");
		ObjectMapper mapper = new ObjectMapper();
		resp.setHeader("Content-Type", "application/json");
		mapper.writeValue(resp.getOutputStream(), login);
	}
	
	public void editUserInfo(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// Get our session information
		HttpSession session = req.getSession();
		// Give a personalized response
		Employee login = (Employee) session.getAttribute("user");
		String username = req.getParameter("userEdit");
		String password = req.getParameter("passEdit");
		String firstName = req.getParameter("fNameEdit");
		String lastName = req.getParameter("lNameEdit");
		login.setUsername(username);
		login.setPassword(password);
		login.setFirstName(firstName);
		login.setLastName(lastName);
		EmployeeService.getInstance().updateEmployee(login);
		resp.sendRedirect("home");
		//ObjectMapper mapper = new ObjectMapper();
		//resp.setHeader("Content-Type", "application/json");
		//mapper.writeValue(resp.getOutputStream(), login);
		log.info(login.getEmpID() + " has editted their info");
	}
	
	public void showAllUsers(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// Get our session information
		HttpSession session = req.getSession();
		// Give a personalized response
		Employee login = (Employee) session.getAttribute("user");
		ObjectMapper mapper = new ObjectMapper();
		resp.setHeader("Content-Type", "application/json");
		mapper.writeValue(resp.getOutputStream(), EmployeeService.getInstance().getAllEmployees());
		// System.out.println(ReimbursementDao.getInstance().selectAll().toString());
	}

	public void showTable(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// Get our session information
		HttpSession session = req.getSession();
		// Give a personalized response
		Employee login = (Employee) session.getAttribute("user");
		ObjectMapper mapper = new ObjectMapper();
		resp.setHeader("Content-Type", "application/json");
		mapper.writeValue(resp.getOutputStream(), ReimbursementDao.getInstance().selectAllOf(login));
		// System.out.println(ReimbursementDao.getInstance().selectAll().toString());
	}
	
	public void showAllTable(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// Get our session information
		HttpSession session = req.getSession();
		// Give a personalized response
		Employee login = (Employee) session.getAttribute("user");
		ObjectMapper mapper = new ObjectMapper();
		resp.setHeader("Content-Type", "application/json");
		mapper.writeValue(resp.getOutputStream(), ReimbursementDao.getInstance().selectAll());
		// System.out.println(ReimbursementDao.getInstance().selectAll().toString());
	}
	
	public void createReimbursement(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// Get our session information
		HttpSession session = req.getSession();
		// Give a personalized response
		Employee login = (Employee) session.getAttribute("user");
		double amount = Double.parseDouble(req.getParameter("reqAmountInput"));
		String info = req.getParameter("descriptionInput");
		Reimbursement rem = new Reimbursement(0, login.getEmpID(), amount, info, null, -1);
		ReimbursementService.getInstance().registerReimbursement(rem);
		resp.sendRedirect("home");
		// System.out.println(ReimbursementDao.getInstance().selectAll().toString());
		log.info("Reimbursement request made by " + login.getEmpID());
	}
	
	public void showSingleRemTable(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		Employee emp = new Employee(1, null, null, null, null, null);
		ObjectMapper mapper = new ObjectMapper();
		resp.setHeader("Content-Type", "application/json");
		mapper.writeValue(resp.getOutputStream(), ReimbursementDao.getInstance().selectAllOf(emp));
		// System.out.println(ReimbursementDao.getInstance().selectAll().toString());
	}

	public void approveReimbursement(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HttpSession session = req.getSession();
		// Give a personalized response
		Employee login = (Employee) session.getAttribute("user");
		System.out.println(login.toString());
		ObjectMapper mapper = new ObjectMapper();
		Reimbursement rem = mapper.readValue(req.getReader(), Reimbursement.class);
		
		System.out.println(rem.toString());
		System.out.println(login.toString());
		ReimbursementService.getInstance().approveReimbursement(rem, login);
		//resp.setHeader("Content-Type", "application/json");
		//mapper.writeValue(resp.getOutputStream(), ReimbursementDao.getInstance().);
		// System.out.println(ReimbursementDao.getInstance().selectAll().toString());
		log.info(login.getEmpID() + " has approved reimbursement " + rem.getId());
	}
	
	public void denyReimbursement(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HttpSession session = req.getSession();
		// Give a personalized response
		Employee login = (Employee) session.getAttribute("user");
		System.out.println(login.toString());
		ObjectMapper mapper = new ObjectMapper();
		Reimbursement rem = mapper.readValue(req.getReader(), Reimbursement.class);
		
		System.out.println(rem.toString());
		System.out.println(login.toString());
		ReimbursementService.getInstance().denyReimbursement(rem, login);
		//resp.setHeader("Content-Type", "application/json");
		//mapper.writeValue(resp.getOutputStream(), ReimbursementDao.getInstance().);
		// System.out.println(ReimbursementDao.getInstance().selectAll().toString());
		log.info(login.getEmpID() + " has denied reimbursement " + rem.getId());
	}
	
	public void filterReimbursement(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		ObjectMapper mapper = new ObjectMapper();
		Employee emp = mapper.readValue(req.getReader(), Employee.class);
		
		resp.setHeader("Content-Type", "application/json");
		mapper.writeValue(resp.getOutputStream(), ReimbursementDao.getInstance().selectAllOf(emp));
		
	}
	
	public void getEmployee(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		ObjectMapper mapper = new ObjectMapper();
		Employee emp = mapper.readValue(req.getReader(), Employee.class);
		
		resp.setHeader("Content-Type", "application/json");
		mapper.writeValue(resp.getOutputStream(), EmployeeDao.getInstance().selectByID(emp.getEmpID()));
		
	}
	
}
