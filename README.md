# Getting Started

## Requirements
- Java 8 already installed

## Steps
1. Clone repository:
    ```git
       git clone git clone https://mamadoubarry@bitbucket.org/mamadou-dev/uaa-server.git
    ```
2. Use gradle wrapper to build project
    ```
       ./gradlew clean build - Unix
       gradle clean build - Windows
    ```
3. To run application
    ```
        ./gradlew bootRun
    ```

## Endpoints
1. Login 
    ```bash
    curl -X POST \
      http://localhost:8080/uaa/api/login \
      -H 'Content-Type: application/json' \
      -d '{
        "username":"user",
        "password":"user"
        }'
    ```
    You will need to login admin to manage users. 
    ```bash
    curl -v -X POST \
      http://localhost:8080/uaa/api/login \
      -H 'Content-Type: application/json' \
      -d '{
        "username":"admin",
        "password":"admin"
        }'
    ```
    The server will return a JWT Token on the response header.  
    You will need this for the following endpoints.
2. Create User
    ```bash
    curl -X POST \
      http://localhost:8080/uaa/api/v1/users \
      -H "Authorization: Bearer $JWT_TOKEN_FROM_STEP1" \
      -H 'Content-Type: application/json' \
      -d '{
        "firstName": "first",
        "lastName": "last",
        "username": "user",
        "password": "user",
        "email": "user@email.com",
        "authorities": [
                {
                    "role": "ROLE_USER"
                }
            ],
        "enabled": true
    }'
    ```
3. Get User by ID
    ```Bash
    curl -X GET \
      http://localhost:8080/uaa/api/v1/users/2 \
      -H Authorization: Bearer $JWT_TOKEN_FROM_STEP1" \
    ```
4. Update User by ID
```Bash
curl -X PUT \
  http://localhost:8080/uaa/api/v1/users/2 \
  -H 'Accept: application/json' \
  -H Authorization: Bearer $JWT_TOKEN_FROM_STEP1"  \
  -H 'Content-Type: application/json' \
  -d '{
	"firstName": "first",
	"lastName": "last1",
	"username": "user12",
	"password": "user1",
	"email": "user1@email.com",
	"authorities": [
			{
				"role": "ROLE_USER"
			}
		],
	"enabled": true
}'
```
