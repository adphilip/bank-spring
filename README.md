# bank-spring
Spring training 25.03.2016 - 1&1.

JSON:

Accounts:
[
  {
    "id": 1,
    "funds": 100.0
  },
  {
    "id": 2,
    "funds": 10.0
  },
  {
    "id": 3,
    "funds": 50.0
  }
]

Users:
[
  {
    "id": 1,
    "name": "Ion",
    "accounts": [

    ]
  },
  {
    "id": 2,
    "name": "Gheorghe",
    "accounts": [

    ]
  }
]

Testing - from RestClient:
 - http://localhost:8080/accounts - GET
 - http://localhost:8080/accounts/1 - GET
 - http://localhost:8080/users - GET
 - http://localhost:8080/accounts/5 - PUT
 Content-Type: application/json
 {
         "id": 5,
         "funds": 5550
     }
 - http://localhost:8080/accounts/1 - PUT
{
  "operation": "DEPOSIT",
  "amount": 1080
}

- http://localhost:8080/accounts/ - PUT

 {
    "source": 1,
    "destination": 4,
    "amount": 1034
  }