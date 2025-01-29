# Currency Exchange and Discount Calculation

### Stack
* Java 21
* Spring boot spring security feign client
* OpenExchangeRate integration

### Integrated with open exchange rate
* It exposes an endpoint to calculate exchange rate based on base and target currency
* Apply discount using simple rule engine

### Build and Run Application
* Obtain appid toke from open exchange and set env var EXCHANGE_API_API_ID
* mvn clean install
* mvn spring-boot:run

### Get Access Token
* Obtain toke by calling below endpoint
  curl --location 'http://localhost:8080/auth-token' \
  --header 'Content-Type: application/json' \
  --header 'Accept: application/json' \
  --data '{
  "userName":"user1",
  "password":"password"
  }'

### Discount rules

![screenshot](docs/rule-engine.jpg)

## Endpoint to calculate exchange rate

curl --location 'http://localhost:8080/v1/api/exchange-rates/calculate' \
--header 'Authorization: Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ1c2VyMSIsImlhdCI6MTczNzgxNjc0NiwiZXhwIjoxNzM3OTE2NzQ2fQ.VqSV4oQHHrs7u7OT0L2qLbggU2C3W4rIIE5h5wRxq7hvH1ONP5vEpelELzR9rL89SMljfv0nthfzfXz82Kl59Q' \
--header 'Content-Type: application/json' \
--data '{
"totalAmount": 400,
"base": "USD",
"target": "AED",
"userType": "EMPLOYEE",
"tenure": 1,
"items": [
{
"name": "item1",
"value": "12",
"category": "ELECTRONICS"
},
{
"name": "item2",
"value": "13",
"category": "GROCERY"
}

    ]
}'

