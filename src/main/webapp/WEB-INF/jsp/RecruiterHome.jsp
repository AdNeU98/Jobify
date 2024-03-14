<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<h1>Recruiter Home</h1>

<form action = "/recruiter/profile" method = "get">
	<button> Manage Profile </button>
</form>
<br>

<form action = "/recruiter/jobs/create" method = "get">
	<button> Post a Job</button>
</form>
<br>

<form action = "/recruiter/jobs" method = "get">
	<button> View All Jobs</button>
</form>
<br>

<form action = "/recruiter/jobs/created" method = "get">
	<button> Operate on Created Jobs</button>
</form>
<br>

<form action = "/recruiter/logout" method = "get">
	<button> Logout </button>
</form>
<br>

 <strong>${successMessage}</strong>
 <strong>${errorMessage}</strong>
</body>
</html>