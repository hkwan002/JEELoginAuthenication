<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
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

.modal {
    display: none; /* Hidden by default */
    position: fixed; /* Stay in place */
    z-index: 1; /* Sit on top */
    padding-top: 280px; /* Location of the box */
    left: 0;
    top: 0;
    width: 100%; /* Full width */
    height: 100%; /* Full height */
    overflow: auto; /* Enable scroll if needed */
    background-color: rgb(0,0,0); /* Fallback color */
    background-color: rgba(0,0,0,0.4); /* Black w/ opacity */
}

/* Modal Content */
.modal-content {
    background-color: #fefefe;
    margin: auto;
    padding: 20px;
    border: 1px solid #888;
    width: 25%;
}

/* The Close Button */
.close {
    color: #aaaaaa;
    float: right;
    font-size: 28px;
    font-weight: bold;
}

.close:hover,
.close:focus {
    color: #000;
    text-decoration: none;
    cursor: pointer;
}

</style>
</head>
<body>

	<div class="header">
		<h1>
			<img src="abc.png">
		</h1>
	</div>
	<form method="post" action="UserController">
		<center>
			<table border="1" width="10%" cellpadding="6">
				<tbody>
					<tr>
						<td>Username</td>
						<td><input type="text" name="uname" />${UNcheck}</td>
					</tr>
					<tr>
						<td>Password</td>
						<td><input type="password" name="pwd" />${UPcheck}</td>

					</tr>
					<tr>
						<td><input type="submit" value="Login" /></td>
						<td><button id="myBtn" type="button">Forget Password</button></td>
					</tr>
						</tbody>
						</table>
						</form>
										<!-- The Modal -->
<div id="myModal" class="modal">

<!-- Modal content -->
  <div class="modal-content">
    <span class="close">&times;</span>
    <form method="get" action="PasswordController">
	<table>
		<tbody>
		<tr>
			<td>Enter Email Address:</td>
			<td><input type="email" name="email" size="25" required autofocus/></td>
		</tr>
		<tr>
			<td><input type="submit" value="submit" onclick="confirmation()" />
			
			</td>
		</tr>
	</tbody>
	</table>
	</form>
  </div>
</div>

	<div class="footer">
		<p>&#169copywrite Optimum Solutions</p>
	</div>
	<script>
// Get the modal
var modal = document.getElementById('myModal');

// Get the button that opens the modal
var btn = document.getElementById("myBtn");

// Get the <span> element that closes the modal
var span = document.getElementsByClassName("close")[0];

// When the user clicks the button, open the modal 
btn.onclick = function() {
    modal.style.display = "block";
}

// When the user clicks on <span> (x), close the modal
span.onclick = function() {
    modal.style.display = "none";
}

// When the user clicks anywhere outside of the modal, close it
window.onclick = function(event) {
    if (event.target == modal) {
        modal.style.display = "none";
    }
}

function confirmation() {
	alert("Successfully Submitted to Email Address");
}

</script>
</body>
</html>