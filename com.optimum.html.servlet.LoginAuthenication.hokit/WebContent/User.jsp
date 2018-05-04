<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Employee Home Page</title>
<style>
.header {
	padding: 1em;
	postion: fixed;
	left: 200;
	bottom: 200;
	text-align: center;
}

.footer {
	padding: 1em;
	position: fixed;
	left: 0;
	bottom: 0;
	width: 100%;
	background-color: #FE882C;
	color: black;
	text-align: center;
	font-weight: lighter;
}

.scrollit {
	overflow: scroll;
	height: 350px;
}

.left {
	text-align: left;
	float: left;
}

.sticky {
	position: fixed
}

body {
	border: 10px solid white;
}
</style>

</head>
<body>
	<div class="header">
		<h1>
			<img src="abc.png">
		</h1>
	</div>

	<form method="post" action="registerController">



		<table style="float: left" width=10%>
			<tbody>
			</tbody>
		</table>

		<div class="Menu">
			<table style="float: left" border=1 width=30%>
				<tbody>
					<tr>
						<td><font size="6">Welcome ${name}</font><br> <br>
								<br> Last Login Date and Time : ${lastAccessTime}<br> <br> <br>
							<div class="left">
								<button type="button" onclick="myFunction()">Logout</button>
							</div> <br> <br> <script>
								function myFunction() {
									var r =confirm("Wish to Confirm Logout?");
									if(r){
										 location.href = "Login.jsp";
									}
								}
							</script> 
								<br> <a href="EditUser.jsp" target="iframe">Edit Profile</a><br>
								<br> <a href="ViewUser.jsp" target="iframe">View Profile</a><br>
							<br> <br></td>
					</tr>
				</tbody>
			</table>
		</div>
		<div>
			<table style="float: left" width=10% cellpadding="4">
				<tbody>
				</tbody>
				</div>
			</table>
			<div>
				<iframe src="EditUser.jsp" width="50%" height="400px"
					name="iframe" id="iframe"> </iframe>
			</div>
	</form>


	<div class="footer">
		<p>&#169copywrite Optimum Solutions</p>
	</div>
</body>
</html>