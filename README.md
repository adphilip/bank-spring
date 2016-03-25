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

Testing:
 - http://localhost:8080/accounts - GET
 - http://localhost:8080/accounts/1 - GET
 - http://localhost:8080/users - GET
 - http://localhost:8080/accounts/5 - PUT
 Content-Type: application/json
 {
         "id": 5,
         "funds": 5550
     }
 - http://localhost:8080/accounts/1 - PUT (I don't know the json)
  { - not good

    "Operation": "deposit",
    "funds": 1080.0,
  }