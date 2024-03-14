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
<h2> Apply Jobs </h2>
<br>
<form action="/applicant/jobs" method="get">
	Search : <input type="text" name="role" placeholder="By role-name"> &nbsp;&nbsp; 
	<input type="text" name="location" placeholder="By location"> &nbsp;&nbsp;
	<input type="text" name="skills" placeholder="By skills"> &nbsp;&nbsp;
	<button type="submit"> Search </button>
</form>
<table style="width:100%">
				<br><br><br>
				<tr class="header">
					<th style="width: 5%;">Job ID</th>
					<th style="width: 5%;">Role Name</th>
					<th style="width: 5%;">Description</th>
					<th style="width: 5%;">Location</th>
					<th style="width: 5%;">Work Experience</th>
					<th style="width: 5%;">Apply</th>
					
				</tr>
			<c:forEach var="j" items="${listOfJobs}">
			<c:if test = "${j.status == 'Available'}">
				<tr>
						<td style="width: 5%;">${j.jobID}</td>
						<td style="width: 5%;">${j.roleName}</td>
						<td style="width: 5%;">${j.descrp}</td>					
						<td style="width: 5%;">${j.location}</td>
						<td style="width: 5%;">${j.expReq}</td>
						<td>
							<form action="/applicant/jobs/${j.jobID}" method="post">
								<button id="${j.jobID}">Apply</button>
							</form>
							<%-- <input type="hidden" value="${j}" name = "jobObj"> --%>							
						</td>
				</tr>
			</c:if>
			</c:forEach>
			</table>
	<form action="/applicant/home" method="get">
			<div>
				<button>Home</button>
			</div>			
	</form>
		<strong>${successMessage}</strong>
 		<strong>${errorMessage}</strong>
</body>
</html>