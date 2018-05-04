<%@page import="java.sql.DriverManager"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.Statement"%>
<%@page import="java.sql.Connection"%>
<div align="center">
	<form method="post" action="">
		Search <input type="text" name="name" placeholder="" />
		</td>
		<td><input type="submit" value="Search" name="Submit" /></td>


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
		<html>

        <script>
        function Pager(tableName, itemsPerPage) {
            this.tableName = tableName;
            this.itemsPerPage = itemsPerPage;
            this.currentPage = 1;
            this.pages = 0;
            this.inited = false;
            this.showRecords = function(from, to) {
                var rows = document.getElementById(tableName).rows;
                for (var i = 1; i < rows.length; i++) {
                    if (i < from || i > to)
                        rows[i].style.display = 'none';
                    else
                        rows[i].style.display = '';
                }
            }
            this.showPage = function(pageNumber) {
                if (! this.inited) {
                    alert("not inited");
                    return;
                }
                var oldPageAnchor = document.getElementById('pg'+this.currentPage);
                oldPageAnchor.className = 'pg-normal';
                this.currentPage = pageNumber;
                var newPageAnchor = document.getElementById('pg'+this.currentPage);
                newPageAnchor.className = 'pg-selected';
                var from = (pageNumber - 1) * itemsPerPage + 1;
                var to = from + itemsPerPage - 1;
                this.showRecords(from, to);
                var pgNext = document.getElementById('pgNext');
                var pgPrev = document.getElementById('pgPrev');
                if (pageNumber == this.pages)
                    pgNext.style.visibility = 'hidden';
                else
                    pgNext.style.visibility = 'visible';
                if (pageNumber == 1)
                    pgPrev.style.visibility = 'hidden';
                else
                    pgPrev.style.visibility = 'visible';
            }
            this.prev = function() {
                if (this.currentPage > 1)
                    this.showPage(this.currentPage - 1);
            }
            this.next = function() {
                if (this.currentPage < this.pages) {
                    this.showPage(this.currentPage + 1);
                }
            }
            this.init = function() {
                var rows = document.getElementById(tableName).rows;
                var records = (rows.length - 1);
                this.pages = Math.ceil(records / itemsPerPage);
                this.inited = true;
            }
            this.showPageNav = function(pagerName, positionId) {
                if (! this.inited) {
                    alert("not inited");
                    return;
                }
                var element = document.getElementById(positionId);

                var pagerHtml = '<div align="center"><span id="pgPrev" onclick="' + pagerName + '.prev();" class="pg-normal"> &#171 Prev </span> | ';
                for (var page = 1; page <= this.pages; page++)
                    pagerHtml += '<span id="pg' + page + '" class="pg-normal" onclick="' + pagerName + '.showPage(' + page + ');">' + page + '</span> | ';
                pagerHtml += '<span id="pgNext" onclick="'+pagerName+'.next();" class="pg-normal"> Next &#187;</span></div>';
                element.innerHTML = pagerHtml;
            }
        }

        </script>
        <script type="text/javascript" src="js/pager.js"></script>
        <style type="text/css">
    .pg-normal {
        color: black;
        font-weight: normal;
        text-decoration: none;
        cursor: pointer;
        font-family:    'Lucida Grande',Verdana,Arial,Sans-Serif;
        font-size:10px
    }
    .pg-selected {
        color: black;
        font-weight: bold;
        text-decoration: underline;
        cursor: pointer;
        font-family:    'Lucida Grande',Verdana,Arial,Sans-Serif;
        font-size:10px
    }
    </style>
    </head>

		<table align="center" cellpadding="5" cellspacing="5" border="1" id="results">
			<tr>

			</tr>
			<tr>

				<td><b>Employee id</b></td>
				<td><b>Name</b></td>
				<td><b>NRIC</b></td>
				<td><b>Email</b></td>
				<td><b>Department</b></td>
				<td><b>Status</b></td>

			</tr>
			<%
				try {
					connection = DriverManager.getConnection(connectionUrl + dbName, userId, password);
					statement = connection.createStatement();
					String sql = "SELECT * FROM user where name like'%" + request.getParameter("name")
							+ "%' or department like'%" + request.getParameter("name") + "%' or nric like'%"
							+ request.getParameter("name") + "%' or employeeID like'%" + request.getParameter("name")
							+ "%'";
					resultSet = statement.executeQuery(sql);
					while (resultSet.next()) {
			%>
			<tr>

				<td><%=resultSet.getString("employeeID")%></td>
				<td><%=resultSet.getString("name")%></td>
				<td><%=resultSet.getString("nric")%></td>
				<td><%=resultSet.getString("email")%></td>
				<td><%=resultSet.getString("department")%></td>
				<td><%=resultSet.getBoolean("status")%></td>


			</tr>

			<%
				}

				} catch (Exception e) {
					e.printStackTrace();
				}
			%>
			
		</table>
		</form>
			</div>
			
		<div id="pageNavPosition"></div>


<script type="text/javascript">
    var pager = new Pager('results', 6);
    pager.init();
    pager.showPageNav('pager', 'pageNavPosition');
    pager.showPage(1);
    
</script>