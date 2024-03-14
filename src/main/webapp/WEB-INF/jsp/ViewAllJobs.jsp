<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%> 
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>View All Jobs</title>
</head>
<style>
table, th, td {
  border:1px solid black;
}
</style>
<body>
<h3> All Jobs Posted</h3>
<table style="width:100%">
				<br><br>
				<tr class="header">
					<th style="width: 5%;">Job ID</th>
					<th style="width: 5%;">Role Name</th>
					<th style="width: 5%;">Description</th>
					<th style="width: 5%;">Location</th>
					<th style="width: 5%;">Work Experience</th>
					<th style="width: 5%;">Status</th>
				</tr>
			<c:forEach var="j" items="${listOfJobs}">
				<tr>
						<td style="width: 5%;">${j.jobID}</td>
						<td style="width: 5%;">${j.roleName}</td>
						<td style="width: 5%;">${j.descrp}</td>					
						<td style="width: 5%;">${j.location}</td>
						<td style="width: 5%;">${j.expReq}</td>
						<td style="width: 5%;">${j.status}</td>
				</tr>
			</c:forEach>
			</table>
	<form action="/recruiter/home" method="get">
			<div>
				<button>Home</button>
			</div>			
	</form>
</body>
</html>