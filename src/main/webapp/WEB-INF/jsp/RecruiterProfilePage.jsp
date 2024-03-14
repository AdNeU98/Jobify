<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Profile Page</title>
</head>
<body>

<h2>Profile Page</h2> <br>

First Name: ${user.fname}<br>
Last Name: ${user.lname}<br>
Email: ${user.email}<br>
Team Name: ${userObj.teamName}<br>
Expertise: ${userObj.expertise}<br>

<br><br>
<div>
<h2> Update the following :</h2>
<form action="/recruiter/updateProfile" method="post">
			<div>
			<label>Team Name:</label>
			<input name="teamName" value = "${userObj.teamName}" type="text" size="20"/>
			</div>
			
			<div>
			<label >Expertise:</label>
			<input name="expertise" value = "${userObj.expertise}" type="text" size="20"/>
			</div>	
			
			<br>		
			<div>
				<button>Update Profile</button>
			</div>
		</form>
		<br><br>
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