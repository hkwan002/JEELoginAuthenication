<%@page import="java.sql.DriverManager"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.Statement"%>
<%@page import="java.sql.Connection"%>

<%
String id = request.getParameter("userId");
String driverName = "com.mysql.jdbc.Driver";
String connectionUrl = "jdbc:mysql://192.168.200.200/";
String dbName = "jee_hokit";
String userId = "root";
String password = "root";

try {
Class.forName(driverName);
} catch (ClassNotFoundException e) {
e.printStackTrace();
}

Connection connection = null;
Statement statement = null;
ResultSet resultSet = null;
%>

<div align="center">
	<form method="post" action="StatusController">
<table align="center" cellpadding="5" cellspacing="5" border="1">
<tr>

</tr>
<tr>
<td><b>Employee id</b></td>
<td><b>Name</b></td>
<td><b>Email</b></td>
<td><b>Department</b></td>
<td><b>Status</b></td>

</tr>
<%
try{ 
connection = DriverManager.getConnection(connectionUrl+dbName, userId, password);
statement=connection.createStatement();
String sql ="SELECT * FROM user where department!='admin'";

resultSet = statement.executeQuery(sql);
while(resultSet.next()){
%>
<tr>

<td><%=resultSet.getString("employeeID") %></td>
<td><%=resultSet.getString("name") %></td>
<td><%=resultSet.getString("email") %></td>
<td><%=resultSet.getString("department") %></td>
<td><%=resultSet.getString("status")%></td>

</tr>
<% 
}
%>
<tr><td>Employee ID</td><td><input type="text" name="employeeID"/></td><td><input type="submit" value="Lock/Unlock"/></td></tr>

<%
} catch (Exception e) {
e.printStackTrace();
}
%>
</table>
</form>
		</div>