<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
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
			
		%>

		</head>
		<body>
			<div>
				<form action="UpdateController" method="post"
					enctype="multipart/form-data">
					<table cellpadding="8" class="table table-hover">
						<tr>
							<td align="right"><b>Upload profile picture: </b></td>

							<td><input type="file" name="ProfilePic"
								value="nothing"></td>
						</tr>
						<tr>
						<tr>
							<td align="right"><b>Full Name:* </b></td>
							<td><input type="text" class="form-control"
								name="name" value="<%=refUser.getName()%>">
								${validateNameMessage}</td>
						</tr>
					
						<tr>
							<td align="right"><b>Address:* </b></td>
							<td><input type="text" class="form-control"
								name="address" value="<%=refUser.getAddress()%>">
						</tr>
						<tr>
							<td align="right"><b>Mobile:* </b></td>
							<td><input type="number" class="form-control"
								name="mobile" value="<%=refUser.getMobile()%>">
								${validateMobileMessage}</td>
						</tr>
						<tr>
							<td align="right"><b>Qualification:*</b></td>

							<td><select class="form-control" name="qualification">
									<option value="university">University</option>
									<option value="juniorCollege">Junior College</option>
									<option value="polytechnic">Polytechnic</option>
									<option value="secondary">Secondary</option>
									<option value="primary">Primary</option>
							</select></td>
						</tr>
						<tr>
							<td align="right"><b>Attach Certificate:</b></td>

							<td><input type="file" name="attachments"></td>
						</tr>
						<tr>
							<td colspan="2" align="center"><br /> <!--  register button -->
								<input type="hidden" name="mobile"
								value="<%=refUser.getMobile()%>"> <input type="hidden"
								name="qualification"
								value="<%=refUser.getQualification()%>"> <input
								type="hidden" name="email"
								value="<%=refUser.getEmail()%>">
								<button type="submit" class="btn btn-dark btn-block">Update</button>
								<br />
								<p>${errorMessage}</p>* = required</td>
						</tr>
					</table>
				</form>
			</div>
		</body>
</html>