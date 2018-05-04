<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
 <%@page import="java.io.InputStream"%>
    <%@page import="java.io.OutputStream"%>``
    <%@ page import="java.sql.*" %>
<html>
<head>
<script>
	history.pushState(null, null, '');
	window.addEventListener('popstate', function(event) {
	 history.pushState(null, null, '');
	});
</script>

<%@ page import="com.optimum.pojo.User"%>

<meta name="viewport" content="width=device-width, initial-scale=1">
<link href="css/bootstrap.css" rel="stylesheet" type="text/css">
<link href="css/style-screen.css" rel="stylesheet" type="text/css">
<link href="css/profile.css" rel="stylesheet" type="text/css">

<title>User Page</title>

</head>
<body>
	<center>
		<%
			User refUser = (User) session.getAttribute("currentUser"); 	
		String email = null;
		email=(String) session.getAttribute("refEmail");
		%>

		</head>
		<body>
			<div>
				<table cellpadding="8" border=1>
					<tr>
				
						<td><b>Profile Picture: </b></td>
						<td><% if(refUser.getProfilepic() == null){ %>
								<img class="profilepic" src="css/img/blank.png" alt="logo" />
							<%}else{ %>
								<img class="profilepic" src="ImageController?email=<%=email%>" alt="logo" />
							<%} %></td>
					</tr>
					<tr>
						<td><b>EmployeeID: </b></td>
						<td><%=refUser.getEmployeeID()%></td>
					</tr>
					<tr>
						<td><b>Name: </b></td>
						<td><%=refUser.getName()%></td>
					</tr>
					<tr>
						<td><b>Email: </b></td>
						<td><%=refUser.getEmail()%></td>
					</tr>
					<tr>
						<td><b>Address: </b></td>
						<td><%=refUser.getAddress()%></td>
					</tr>
					<tr>
						<td><b>Department: </b></td>
						<td><%=refUser.getDepartment()%></td>
					</tr>
					<tr>
						<td><b>Mobile: </b></td>
						<td><%=refUser.getMobile()%></td>
					</tr>
					<tr>
						<td><b>Qualification: </b></td>
						<td><%=refUser.getQualification()%></td>
					</tr>
					<tr>
						<td><b>Certificate: </b></td>
						<td><% if(refUser.getCertificate() == null){ %>
								<img class="certificate" src="css/img/blank.png" alt="logo" />
							<%}else{ %>
								<img class="certificate" src="CertificateController?email=<%=email%>" alt="logo" />
							<%} %></td>
				
				</table>

			</div>
		</body>
</html>