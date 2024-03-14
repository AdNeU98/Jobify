<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>    
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>User Registration Form</title>
<link rel="stylesheet" type="text/css" href="style/styles.css" />
<style type="text/css">
	.error {
		color: red;
		font-style: italic;
	}
	form {
		display: table;
	}
	form div {
		display: table-row;
	}
	label, input, span, select {
		display: table-cell;
		margin: 5px;
		text-align: left;		
	}
	input[type=text], input[type=password], select, textarea {
		width: 200px;
		margin: 5px;
	}

	form div div {
		display: table-cell;
	}	
</style>
</head>
<body>
	<div align="center">
		<h2>Update Job Posting</h2>
		<form action="/recruiter/jobs/update" method="post">
			<div>
			<label path="roleName">Role Name:</label>
			<input name="roleName" value = "${jobObj.getRoleName()}" type="text" size="10"/>
			<%-- <form:errors path="roleName" cssClass="error" /> --%>
			</div>
			
			<div>
			<label path="descrp">Description:</label>
			<input name="descrp" value = "${jobObj.getDescrp()}" type="text" size="100"/>
			<%-- <form:errors path="descrp" cssClass="error" /> --%>
			</div>	
					
			<div>
			<label path="expReq">Work Experience (minimum in years)</label>
			<input name="expReq" value="${jobObj.getExpReq()}" type="text" size="100"/>
			<%-- <form:errors path="expReq" cssClass="error" /> --%>
			</div>

			<div>
			<label path="location">Location:</label>
			<input name="location" value="${jobObj.getLocation()}" type="text" size="100"/>
			<%-- <form:errors path="location" cssClass="error" /> --%>
			</div>
			
			<input type = "hidden" name = "jobId" value = "${jobObj.getJobID()}">						
			<div>
				<button class="buttonLogin">Update Job</button>
			</div>
		</form>
		
		<form action="/recruiter/home" method="get">
			<div>
				<button>Home</button>
			</div>			
		</form>
		
		 <strong>${successMessage}</strong>
 		<strong>${errorMessage}</strong>
	</div>
</body>
</html>