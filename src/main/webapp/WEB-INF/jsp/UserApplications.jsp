<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%> 
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Jobs Applied</title>
</head>
<style>
table, th, td {
  border:1px solid black;
}
</style>
<body>
<h3> Jobs Applied</h3>
<table style="width:100%">
				<br><br>
				<tr class="header">
					<th style="width: 5%;">Role Name</th>
					<th style="width: 5%;">Description</th>
					<th style="width: 5%;">Location</th>
					<th style="width: 5%;">Work Experience</th>
					<th style="width: 5%;">Status</th>
					<th style="width: 5%;">Operation</th>
				</tr>
			<c:forEach var="each" items="${applications}">
				<tr>
						<td style="width: 5%;">${each.jobDetails.roleName}</td>
						<td style="width: 5%;">${each.jobDetails.descrp}</td>					
						<td style="width: 5%;">${each.jobDetails.location}</td>
						<td style="width: 5%;">${each.jobDetails.expReq}</td>
						<td style="width: 5%;">${each.appStatus}</td>
						<c:choose>  
    						<c:when test="${each.appStatus != 'Reject'}">  
							<td>
								<form action="/applicant/jobs/delete/${each.applicationID}" method="get">
									<button class="delete" id="${each.applicationID}">Withdraw</button>
								</form>							
							</td>
    						</c:when>  
    						<c:otherwise>  
       							<td></td>
    						</c:otherwise>  
						</c:choose>  
				</tr>
			</c:forEach>
			</table>
	<form action="/applicant/home" method="get">
			<div>
				<button>Home</button>
			</div>			
	</form>
</body>
</html>