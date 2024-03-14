<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet" type="text/css" href="style/styles.css" />
</head>
<body>
 <div id = "signinForm" class="signin">
<form action="/home.htm" style="border:1px solid #ccc" method="post">
    <h1>Sign In</h1>
    <hr> 
     <label for="email"><b>Email</b></label>
    <input type="text" placeholder="Enter Email" name="email" required>

    <label for="password"><b>Password</b></label>
    <input type="password" placeholder="Enter Password" name="password" required>
    <br><br>
    <br><b>Role Type</b><br>
    <label for="Role"><b>Recruiter</b></label>
    <input type="radio" name="roleType" value = "Recruiter" required>
    
    <label for="Role"><b>Applicant</b></label>
    <input type="radio" name="roleType" value = "Applicant" required>
    
    <div>
      <button type="submit" class="buttonSubmitLogin">Log In</button>
    </div>
</form>
 </div>
 <div>
 <strong>${successMesssage}</strong>
 <strong>${errorMessage}</strong>
 </div>
 <div>
 <form action="/index.htm" method="get">
   <button class="buttonSubmitLogin">Sign Up</button>
 </form>
 </div>
</body>
</html>