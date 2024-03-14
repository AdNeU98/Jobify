<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>

<h2>Hi ${appObj.user.fname}! Welcome</h2>

<form action = "/applicant/profile" method = "get">
	<button> Manage Profile </button>
</form>
<br>
<form  action = "/applicant/jobs" method="get">
	<button> Apply Jobs </button>
</form>
<br>
<form action="/applicant/jobs/applied" method="get">
	<button> View Applied Jobs </button>
</form>
<br>
<form action="/index.htm" method="post">
	<button> Delete Account </button>
</form>
<br>
<form action = "/applicant/logout" method = "get">
	<button> Logout </button>
</form>
<br>
<strong>${successMessage}</strong>
 <strong>${errorMessage}</strong>
</body>
</html>