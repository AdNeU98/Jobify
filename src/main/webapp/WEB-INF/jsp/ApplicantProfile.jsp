<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Applicant Profile</title>
</head>
<body>
<h2>Profile Page</h2> <br>

First Name: ${appObj.user.fname}<br>
Last Name: ${appObj.user.lname}<br>
Email: ${appObj.user.email}<br>
Work Experience: ${appObj.wrkExp}<br>
Resume Link: ${appObj.resumeLink}<br>

<br><br>
<div>
<h2> Update the following :</h2>
<form action="/applicant/updateProfile" method="post">
			<div>
			<label>Work Experience:</label>
			<input name="workEx" value = "${appObj.wrkExp}" type="text" size="20"/>
			</div>
			
			<div>
			<label >Resume Link:</label>
			<input name="resume" value = "${appObj.resumeLink}" type="text" size="20"/>
			</div>	
			
			<br>		
			<div>
				<button>Update Profile</button>
			</div>
		</form>
		<br><br>
		<form action="/applicant/home" method="get">
			<div>
				<button>Home</button>
			</div>			
		</form>
		
		 <strong>${successMessage}</strong>
 		<strong>${errorMessage}</strong>
</div>

</body>
</html>