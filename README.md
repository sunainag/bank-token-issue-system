# bank-token-issue-system

# Prerequisites:
```
Java 8

Maven

MySQL
```

**Schema design:**

![schema](https://user-images.githubusercontent.com/9528278/53480303-eef93a80-3aa0-11e9-98f2-6110d406621d.png)


**From Editor (IntelliJ or Eclipse) import as Existing Maven Project and run it as Spring Boot App.**


**Build the Project:**
```
mvn clean install
```

**Usage:**
```
If you run the spring boot application, the hibernate tables are created and data is populated from data.sql.
Run the application and go on http://localhost:8080/ for welcome page.
```

# End points:
```
context path=/abs/bank
```

**TokenController:**
```
GET /tokens Gives a counter wise list of active tokens
```
```
GET /tokens/{tokenNumber} Gives details for a particular token number passed
```
```
POST /tokens Generates/issues a new token, takes customer details and service(s) requested in TokenRequest as Request body

example: for POST request: http://localhost:8080/abs/bank/tokens

Request Body: 

          {
            "services":["C"],
            "customer":{
				"id":0,
                        "name":"aaass",
                        "mobile":"1343434",
                        "type":"REGULAR",
                        "address":{
				  "id":0,
                          "addressLine1":"addressLine12",
                          "addressLine2":"addressLine23",
                          "city":"city1",
                          "state":"state1",
                          "country":"country1",
                          "zipCode":"zipCode1",
                          "created":"14/02/2019"
                        }
            }
          }

```
**CounterController:**

```
GET /counters/{counterNumber}/token Get the next token from the counter queue for respective counterNumber.
```
```
GET /counters List all the counters for details
```
```
GET /counters/{counterNumber} Get the details of the counter with given 'counterNumber'
```
``` 
PATCH /counters/{counterNumber}/tokens/{tokenNumber} 

Comment on the respective token number for the current service at the given counter. It can be done by sending a map of updates with key as "comments" to make changes to the token object as follows:

Request Body: 
		{"comments":"Bring papers"}

Cancel an active token number at the given counter. It can be done by sending a map of updates with key as "action" and value as "CANCELLED":

Request Body: 
		{"action":"CANCELLED"}
		
Complete an active token number at the given counter. It can be done by sending a map of updates with key as "action" and value as "COMPLETED":

Request Body: 
		{"action":"COMPLETED"}
		
```

# Token Generation Strategy
```
The current default implementation first identifies counters based on the customer type and services offered by the counter, and then chooses the counter with the minimum queue size.
While issuing a token, the systems identifies all the services that need to be served by this token (either user chooses multiple services or service that by itself is a multi-counter service). Once a token is marked as complete for the current service, token is queued at the next counter for the next service.
```
**Token Sequence Generation**
```
A Simple date based sequential generator has been used. The sequence gets reset at 00 hours everyday.
```

**ToDo**
```
Authorization: Role based access for updating tokens
Token serve ratio based on premium or regular customers
Swagger implementation
Logger
```

