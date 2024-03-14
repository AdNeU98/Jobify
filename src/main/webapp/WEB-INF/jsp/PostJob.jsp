<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
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
		<h2>Create Job Posting</h2>
		<form:form action="/recruiter/jobs/save" method="post" modelAttribute="job">
			<div>
			<form:label path="roleName">Role Name:</form:label>
			<form:input path="roleName"/>
			<form:errors path="roleName" cssClass="error" />
			</div>
			
			<div>
			<form:label path="descrp">Skills:</form:label>
			<form:input path="descrp" />
			<form:errors path="descrp" cssClass="error" />
			</div>	
					
			<div>
			<form:label path="expReq">Work Experience (minimum in years)</form:label>
			<form:input path="expReq" />
			<form:errors path="expReq" cssClass="error" />
			</div>

			<div>
			<form:label path="location">Location:</form:label>
			<form:input path="location"/>
			<form:errors path="location" cssClass="error" />
			</div>
			
			<form:hidden path="status" value="Available"/>
									
			<div>
				<button class="buttonLogin">Post Job</button>
			</div>
		</form:form>
		
		<form action="/recruiter/home" method="get">
			<div>
				<button>Home</button>
			</div>			
		</form>
		
		 <strong>${successMesssage}</strong>
 		<strong>${errorMessage}</strong>
	</div>
</body>
</html>