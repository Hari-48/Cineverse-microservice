# Cineverse-microservice



-- USER - SERVICE

1.In this service we create a new user and register with the application .
2.List the total users 
3.change the password  for existing user 
4.delete user 

-- list the user (GET)
API : http://localhost:8765/user/view-user

-- create a new user (POST)
API : http://localhost:8765/user/register
{
"userName": "tom_h",
"email": "tom.hanks@example.com",
"password": "Tom@0001",
"age": 40,
"firstName": "Tom",
"lastName": "Hanks",
"mobileNumber": "+91 9445566778",
"dateOfBirth": "1984-06-01",
"userType": "REGULAR"
}

-- change the password (PUT)
API : http://localhost:8765/user/change-password?oldPassword=Hari@2001&newPassword=Hari@2002&userName=hari





