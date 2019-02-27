# bank-token-issue-system

# Prerequisites:
```
Java 8

Maven

MySQL
```

**Schema installation:**
```
Run the file schema.sql present in src/main/resources to set up the database. 

The tables created are: 
![alt text](https://raw.githubusercontent.com/sunainag/bank-token-issue-system/master/doc/Tables.png)

```

**From Editor (IntelliJ or Eclipse) import as Existing Maven Project and run it as Spring Boot App.**


**Build the Project:**
```
mvn clean install
```

**Usage:**
```
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
POST /tokens Generates a new token, takes customer and service(s) in TokenRequest as Request body

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
PUT /tokens/{tokenNumber}/cancel Cancels an active token
```
```
PUT /tokens/{tokenNumber}/complete Marks a service token a complete, in case the token is a multi-counter token, it gets queued at the next counter
```
```
PUT /tokens/{tokenNumber}/comment Records a comment against the current service of the token
```
```
GET /counters/{counterNumber} Get the details of the counter with given 'counterNumber'
```
```
GET /counters List all the counters for details
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
```
