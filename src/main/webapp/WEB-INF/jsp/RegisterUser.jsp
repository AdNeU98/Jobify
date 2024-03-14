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
		<h2>User Registration</h2>
		<form:form action="register-success.htm" method="post" modelAttribute="user">
			<div>
			<form:label path="fname">First Name:</form:label>
			<form:input path="fname" />
			<form:errors path="fname" cssClass="error" />
			</div>
			
			<div>
			<form:label path="lname">Last Name:</form:label>
			<form:input path="lname" />
			<form:errors path="lname" cssClass="error" />
			</div>	
					
			<div>
			<form:label path="email">E-mail:</form:label>
			<form:input path="email" />
			<form:errors path="email" cssClass="error" />
			</div>

			<div>
			<form:label path="password">Password:</form:label>
			<form:password path="password"/>
			<form:errors path="password" cssClass="error" />
			</div>
			
			<div>
			<form:label path="userType">Role Type:</form:label>
			<form:radiobutton path="userType" value="Applicant"/>Applicant
			<form:radiobutton path="userType" value="Recruiter"/>Recruiter
			<form:errors path="userType" cssClass="error" />
			</div>						
			<div>
				<button class="buttonLogin">Register</button>
			</div>
		</form:form>
		<form action="/login.htm" method="get">
			<div>
				<button class="buttonSignUp">Sign In</button>
			</div>			
		</form>
		
		 <strong>${successMesssage}</strong>
 		<strong>${errorMessage}</strong>
	</div>
</body>
</html>