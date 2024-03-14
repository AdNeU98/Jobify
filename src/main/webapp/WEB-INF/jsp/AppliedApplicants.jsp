<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%> 
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Applied Applicants</title>
</head>
<style>
table, th, td {
  border:1px solid black;
}
</style>
<body>
<table style="width:100%">
				<br><br>
				<tr>
					<th>Applicantion ID</th>
					<th>First Name</th>
					<th>Last Name</th>
					<th>Work Experience</th>
					<th>Resume Link</th>
					<th>Status</th>
					<th>Accept Operation</th>
					<th>Reject Operation</th>
					
				</tr>
				<c:forEach var="j" items="${lsOfApplications}">
				<tr>
						<td>${j.applicationID}</td>
						<td>${j.appDetails.user.fname}</td>
						<td>${j.appDetails.user.lname}</td>
						<td>${j.appDetails.wrkExp}</td>						
						<td>${j.appDetails.resumeLink}</td>
						<td>${j.appStatus}</td>
						
						<c:choose>  
    						<c:when test="${j.appStatus == 'In Review'}">  
       						<td>
								<form action="/recruiter/jobs/${jobId}/applicants" method="post">
									<button>Accept</button>
									<input type="hidden" name="status" value="Accept">
									<input type="hidden" name="applicationID" value="${j.applicationID}">
								</form>							
							</td>
							<td>
								<form action="/recruiter/jobs/${jobId}/applicants" method="post">
									<button>Reject</button>
									<input type="hidden" name="status" value="Reject">
									<input type="hidden" name="applicationID" value="${j.applicationID}">
								</form>	
							</td> 
    						</c:when>  
    						<c:otherwise>  
       							<td></td>  
       							<td></td> 
    						</c:otherwise>  
						</c:choose>
				</tr>
			</c:forEach>
</table>
<form action="/recruiter/home" action="get">
	<button>Home</button>
</form>
<strong>${successMessage}</strong>
<strong>${errorMessage}</strong>
</body>
</html>