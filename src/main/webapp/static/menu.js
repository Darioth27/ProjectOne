/*console.log(4);
setTimeout (() => console.log(5),500);
//syntax below means same as the above
setTimeout (function () {
	console.log(6);
}, 500);*/

//simple ajax call/request
/*var xhr = new XMLHttpRequest();
xhr.onreadystatechange = function() {
	console.log(//"response text: " + xhr.responseText
			//"http status: " + xhr.status +
			//"http status text: " + xhr.statusText +
			"readyState: " + xhr.readyState
			//"onreadystatechange: " + xhr.onreadystatechange
	);	
}

xhr.open("GET", "https://api.myjson.com/bins/eq9je");
xhr.send();*/

/* http status codes
 * 100 - informational
 * 200 - success
 * 300 - redirection
 * 400 - client error
 * 500 - server error
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

