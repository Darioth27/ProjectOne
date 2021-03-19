/**
 * 
 */
window.onload = function() {
	//let btn = document.getElementById("display");
	//btn.addEventListener("click", viewRequests());
	//document.getElementById("account").addEventListener("onClick", doTest);
	//document.getElementById("account").addEventListener("click", viewAccount);
	welcome();
	viewAccount();
	document.getElementById("viewRequests").addEventListener("click", viewRequests);
	document.getElementById("editAccountBtn").addEventListener("click", editAccountView);
	document.getElementById("manageEmployees").addEventListener("click", viewEmployees);
	document.getElementById("manageRequests").addEventListener("click", viewAllRequests);
	document.getElementById("reqApproveBtn").addEventListener("click", approveRequest);
	document.getElementById("reqDenyBtn").addEventListener("click", denyRequest);
	document.getElementById("filterBtn").addEventListener("click", filterByUser);
}

function viewRequests() {
	// for every readystate, onreadystatechange will be executed
	var xhr = new XMLHttpRequest();
	xhr.onreadystatechange = function() {
		if ((xhr.readyState == 4) && (xhr.status==200)) {
			let userInfo = document.getElementById("requestsView");
			
			//document.getElementById("requestsTablePending").setAttribute("style", "display:table");
			let tb = document.getElementById("reqTablePending");
			tb.innerHTML = "<th>ID</th><th>Amount</th><th>Information</th><th>Status</th>";
			let tb2 = document.getElementById("reqTableApproved");
			tb2.innerHTML = "<th>ID</th><th>Amount</th><th>Information</th><th>Status</th>";
			
			let content = JSON.parse(xhr.response);
			
			var i = 0;
			for (i in content) {
				if (content[i].status == 'PENDING') {
					let row = document.createElement("tr");
					tb.append(row);
					let idcol = document.createElement("td");
					let amountcol = document.createElement("td");
					let infocol = document.createElement("td");
					let statuscol = document.createElement("td");
					idcol.textContent = content[i].id;
					amountcol.textContent = "$" + content[i].amount.toFixed(2);
					infocol.textContent = content[i].info;
					statuscol = content[i].status;
					row.append(idcol);
					row.append(amountcol);
					row.append(infocol);
					row.append(statuscol);
				}
				else if (content[i].status == 'APPROVED') {
					let row = document.createElement("tr");
					tb2.append(row);
					let idcol = document.createElement("td");
					let amountcol = document.createElement("td");
					let infocol = document.createElement("td");
					let statuscol = document.createElement("td");
					idcol.textContent = content[i].id;
					amountcol.textContent = "$" + content[i].amount.toFixed(2);
					infocol.textContent = content[i].info;
					statuscol = content[i].status;
					row.append(idcol);
					row.append(amountcol);
					row.append(infocol);
					row.append(statuscol);
				}
				
			}

		}
	};
	xhr.open("GET", "http://localhost:8080/ProjectOne/viewRequests");
	xhr.send();
}

function viewAccount() {
	var xhr = new XMLHttpRequest();
	xhr.onreadystatechange = function() {
		if ((xhr.readyState == 4) && (xhr.status==200)) {
			let infoDiv = document.getElementById("accountInfoDiv");

			let content = JSON.parse(xhr.response);
			let info0 = document.createElement("h4");
			info0.textContent = "Account Information";
			let info = document.createElement("h6");
			info.textContent = "Username: " + content.username;
			let info2 = document.createElement("h6");
			info2.textContent = "First Name: " + content.firstName;
			let info3 = document.createElement("h6");
			info3.textContent = "Last Name: " + content.lastName;
			let info4 = document.createElement("h6");
			info4.textContent = "User Type: " + content.userType;
			
			infoDiv.append(info0);
			infoDiv.append(info);
			infoDiv.append(info2);
			infoDiv.append(info3);
			infoDiv.append(info4);
		}
	};
	xhr.open("GET", "http://localhost:8080/ProjectOne/account");
	xhr.send();
}

function editAccountView() {
	var xhr = new XMLHttpRequest();
	xhr.onreadystatechange = function() {
		if ((xhr.readyState == 4) && (xhr.status==200)) {
			let content = JSON.parse(xhr.response);
			document.getElementById("usernameInput").value = content.username;
			document.getElementById("passwordInput").value = content.password;
			document.getElementById("firstNameInput").value = content.firstName;
			document.getElementById("lastNameInput").value = content.lastName;

		}
	};
	xhr.open("GET", "http://localhost:8080/ProjectOne/account");
	xhr.send();
}

function welcome() {
	var xhr = new XMLHttpRequest();
	xhr.onreadystatechange = function() {
		if ((xhr.readyState == 4) && (xhr.status==200)) {
			let content = JSON.parse(xhr.response);
			document.getElementById("welcomeText").textContent = "Welcome, " + content.firstName + " " + content.lastName;
		}
	};
	xhr.open("GET", "http://localhost:8080/ProjectOne/account");
	xhr.send();
}

function viewEmployees() {
	// for every readystate, onreadystatechange will be executed
	var xhr = new XMLHttpRequest();
	xhr.onreadystatechange = function() {
		if ((xhr.readyState == 4) && (xhr.status==200)) {
			
			let tb = document.getElementById("empTable");
			tb.innerHTML = "<th>ID</th><th>Username</th><th>Last Name</th><th>First Name</th><th>User Type</th>";
			
			let content = JSON.parse(xhr.response);
			
			var i = 0;
			for (i in content) {
				let row = document.createElement("tr");
				tb.append(row);
				let idcol = document.createElement("td");
				let usercol = document.createElement("td");
				let lnamecol = document.createElement("td");
				let fnamecol = document.createElement("td");
				let usertypecol = document.createElement("td");
				let viewcol = document.createElement("td");
				
				idcol.textContent = content[i].empID;
				usercol.textContent = content[i].username;
				lnamecol.textContent = content[i].lastName;
				fnamecol.textContent = content[i].firstName;
				usertypecol.textContent = content[i].userType;
				
				row.append(idcol);
				row.append(usercol);
				row.append(lnamecol);
				row.append(fnamecol);
				row.append(usertypecol);
			}

		}
	};
	xhr.open("GET", "http://localhost:8080/ProjectOne/viewEmployees");
	xhr.send();
}

function viewEmpTable() {
	var xhr = new XMLHttpRequest();
	xhr.onreadystatechange = function() {
		if ((xhr.readyState == 4) && (xhr.status==200)) {
			let userInfo = document.getElementById("manageEmp");
			
			//document.getElementById("requestsTablePending").setAttribute("style", "display:table");
			let tb = document.getElementById("empTable");
			tb.innerHTML = "<th>ID</th><th>Amount</th><th>Information</th><th>Status</th>";
			
			let content = JSON.parse(xhr.response);
			
			var i = 0;
			for (i in content) {
				let row = document.createElement("tr");
				tb.append(row);
				let idcol = document.createElement("td");
				let amountcol = document.createElement("td");
				let infocol = document.createElement("td");
				let statuscol = document.createElement("td");
				idcol.textContent = content[i].id;
				amountcol.textContent = "$" + content[i].amount.toFixed(2);
				infocol.textContent = content[i].info;
				statuscol = content[i].status;
				row.append(idcol);
				row.append(amountcol);
				row.append(infocol);
				row.append(statuscol);
			}

		}
	};
	xhr.open("GET", "http://localhost:8080/ProjectOne/viewRequests");
	xhr.send();
}

function viewAllRequests() {
	var xhr = new XMLHttpRequest();
	xhr.onreadystatechange = function() {
		if ((xhr.readyState == 4) && (xhr.status==200)) {
			let userInfo = document.getElementById("requestsView");
			document.getElementById("empNameField").textContent = "";
			//document.getElementById("requestsTablePending").setAttribute("style", "display:table");
			let tb = document.getElementById("allReqPending");
			tb.innerHTML = "<th>ID</th><th>Amount</th><th>Information</th><th>Status</th><th>R_ID</th>";
			let tb2 = document.getElementById("allReqApproved");
			tb2.innerHTML = "<th>ID</th><th>Amount</th><th>Information</th><th>Status</th><th>R_ID</th><th>Approver ID</th>";
			
			let content = JSON.parse(xhr.response);
			
			var i = 0;
			for (i in content) {
				if (content[i].status == 'PENDING') {
					let row = document.createElement("tr");
					tb.append(row);
					let idcol = document.createElement("td");
					let amountcol = document.createElement("td");
					let infocol = document.createElement("td");
					let statuscol = document.createElement("td");
					let rcol = document.createElement("td");
					idcol.textContent = content[i].id;
					amountcol.textContent = "$" + content[i].amount.toFixed(2);
					infocol.textContent = content[i].info;
					statuscol.textContent = content[i].status;
					rcol.textContent = content[i].empID;
					row.append(idcol);
					row.append(amountcol);
					row.append(infocol);
					row.append(statuscol);
					row.append(rcol);
				}
				else {
					let row = document.createElement("tr");
					tb2.append(row);
					let idcol = document.createElement("td");
					let amountcol = document.createElement("td");
					let infocol = document.createElement("td");
					let statuscol = document.createElement("td");
					let rcol = document.createElement("td");
					let approvercol = document.createElement("td");
					idcol.textContent = content[i].id;
					amountcol.textContent = "$" + content[i].amount.toFixed(2);
					infocol.textContent = content[i].info;
					statuscol.textContent = content[i].status;
					rcol.textContent = content[i].empID;
					approvercol.textContent = content[i].approverID;
					row.append(idcol);
					row.append(amountcol);
					row.append(infocol);
					row.append(statuscol);
					row.append(rcol);
					row.append(approvercol);
				}
				
			}

		}
	};
	xhr.open("GET", "http://localhost:8080/ProjectOne/viewAllRequests");
	xhr.send();
}


function approveRequest() {
	var xhr = new XMLHttpRequest();
	var requestID = document.getElementById("requestToApprove").value;
	var obj = { id: requestID }
	
	xhr.onreadystatechange = function() {
		if ((xhr.readyState == 4) && (xhr.status==200)) {
			//let content = JSON.parse(xhr.response);
			console.log("success");
			viewAllRequests();
		}
	};
	
	xhr.open("POST", "http://localhost:8080/ProjectOne/approveRequest");
	xhr.send(JSON.stringify(obj));
}

function denyRequest() {
	var xhr = new XMLHttpRequest();
	var requestID = document.getElementById("requestToApprove").value;
	var obj = { id: requestID }
	
	xhr.onreadystatechange = function() {
		if ((xhr.readyState == 4) && (xhr.status==200)) {
			//let content = JSON.parse(xhr.response);
			console.log("success");
			viewAllRequests();
		}
	};
	
	xhr.open("POST", "http://localhost:8080/ProjectOne/denyRequest");
	xhr.send(JSON.stringify(obj));
}

function filterByUser() {
	var xhr = new XMLHttpRequest();
	var requestID = document.getElementById("employeeToFilter").value;
	var obj = { empID: requestID }
	
	xhr.onreadystatechange = function() {
		if ((xhr.readyState == 4) && (xhr.status==200)) {
			
			let tb = document.getElementById("allReqPending");
			tb.innerHTML = "<th>ID</th><th>Amount</th><th>Information</th><th>Status</th><th>R_ID</th>";
			let tb2 = document.getElementById("allReqApproved");
			tb2.innerHTML = "<th>ID</th><th>Amount</th><th>Information</th><th>Status</th><th>R_ID</th><th>Approver ID</th>";
			
			let content = JSON.parse(xhr.response);
			
			var i = 0;
			for (i in content) {
				if (content[i].status == 'PENDING') {
					let row = document.createElement("tr");
					tb.append(row);
					let idcol = document.createElement("td");
					let amountcol = document.createElement("td");
					let infocol = document.createElement("td");
					let statuscol = document.createElement("td");
					let rcol = document.createElement("td");
					idcol.textContent = content[i].id;
					amountcol.textContent = "$" + content[i].amount.toFixed(2);
					infocol.textContent = content[i].info;
					statuscol.textContent = content[i].status;
					rcol.textContent = content[i].empID;
					row.append(idcol);
					row.append(amountcol);
					row.append(infocol);
					row.append(statuscol);
					row.append(rcol);
				}
				else {
					let row = document.createElement("tr");
					tb2.append(row);
					let idcol = document.createElement("td");
					let amountcol = document.createElement("td");
					let infocol = document.createElement("td");
					let statuscol = document.createElement("td");
					let rcol = document.createElement("td");
					let approvercol = document.createElement("td");
					idcol.textContent = content[i].id;
					amountcol.textContent = "$" + content[i].amount.toFixed(2);
					infocol.textContent = content[i].info;
					statuscol.textContent = content[i].status;
					rcol.textContent = content[i].empID;
					approvercol.textContent = content[i].approverID;
					row.append(idcol);
					row.append(amountcol);
					row.append(infocol);
					row.append(statuscol);
					row.append(rcol);
					row.append(approvercol);
				}
				
			}
			
			getName();
			
		}
	};
	xhr.open("POST", "http://localhost:8080/ProjectOne/filterRequests");
	xhr.send(JSON.stringify(obj));
}

function getName() {
	var xhr = new XMLHttpRequest();
	var requestID = document.getElementById("employeeToFilter").value;
	var obj = { empID: requestID }
	
	xhr.onreadystatechange = function() {
		if ((xhr.readyState == 4) && (xhr.status==200)) {
			
			let content = JSON.parse(xhr.response);

			document.getElementById("empNameField").textContent = "Requests made by " + content.firstName + " " + content.lastName;
		}
	};
	xhr.open("POST", "http://localhost:8080/ProjectOne/getEmployee");
	xhr.send(JSON.stringify(obj));
}










