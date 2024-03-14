<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%> 
<c:set var="status" scope="session" value="${j.status}"/> 
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
<h3> All Jobs Created</h3>
<table style="width:100%">
				<br><br>
				<tr>
					<th>Job ID</th>
					<th>Role Name</th>
					<th>Skills</th>
					<th>Location</th>
					<th>Work Experience</th>
					<th>Status</th>
					<th>Operate Job</th>
					<th>Update Job</th>
					<th>Applicants</th>
					
				</tr>
				<c:forEach var="j" items="${listOfJobs}">
				<tr>
						<td>${j.jobID}</td>
						<td>${j.roleName}</td>
						<td>${j.descrp}</td>					
						<td>${j.location}</td>
						<td>${j.expReq}</td>
						<td>${j.status}</td>

						<c:choose>  
    						<c:when test="${j.status == 'Available'}">  
       						<td>
								<form action="/recruiter/jobs/status/${j.jobID}" method="get">
									<button>Expire</button>
								</form>							
							</td>
    						</c:when>
    						<c:when test="${j.status == 'Expired'}">
        						<td>
									<form action="/recruiter/jobs/status/${j.jobID}" method="get">
										<button>Available</button>
									</form>							
								</td>   							
    						</c:when>  
    						<c:otherwise>   
       							<td></td> 
    						</c:otherwise> 
						</c:choose>
						<td>
							<form action="/recruiter/jobs/update/${j.jobID}" method="get">
								<button>Update</button>
							</form>	
						</td>  
						<td>
							<form action="/recruiter/jobs/${j.jobID}/applicants" method="get">
									<button>Applicants</button>
							</form>	
						</td>  
				</tr>
			</c:forEach>
</table>
<br><br><br>

<form action="/recruiter/pdfView" method="get">
	<button> PDF View </button>
</form>
<strong>${successMessage}</strong>
<strong>${errorMessage}</strong>
<br>
<form action="/recruiter/home" action="get">
	<button>Home</button>
</form>
</body>
</html>