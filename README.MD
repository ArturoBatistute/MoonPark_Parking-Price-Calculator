# Programming task - Java developers

MoonPark is a Norwegian parking company. MoonPark has commissioned you to
develop a web service that calculates the price of a parking in their parking facility.
The price of parking is given by the parking zones. 

Today there are three zones: **M1**, **M2** and **M3**. It is expected that the number of price zones will increase in the near
future.
**(Kroner/NOK/kr is the local currency in Norway)**
### Task 1:
Calculate the price of a parking in the M1 price zone.
The cost of parking in M1 is calculated as follows:
- 60 NOK per hour, calculated per minute.
  Defining input and output values is part of the task. The same applies to tests.

### Task 2:
  Calculate the price for a parking in the M1 or M2 price zone.
  The cost of parking in M2 is calculated as follows:
- NOK 100 per started hour on weekdays.
- On the weekend, the price is doubled, ie 200 NOK per started hour.

### Task 3:
  Expose the code from task 2 as a RESTful web service.
- Clients should be able to make a GET to the path / zone with necessary input
  parameters as part of the query string.
- The response should be a JSON structure.

### Task 4:
  Extend the fare motor with support for the M3 zone:
  The price of parking in M3 is calculated as follows:
- Monday to Saturday between 08:00 and 16:00 the first hour is free, then it costs 2
  NOK per minute.- Monday to Saturday except these times, it costs 3 NOK per minute. Sundays drivers park free of charge.

### Task 5:
  MoonPark makes your price calculator available to the public. This proves very
  popular, and several third party services start to use it right away. Unfortunately, it
  appears that some clients, for various reasons, make extremely large amounts of
  calls, which sometimes causes the service to be overloaded.
  Suggest a strategy to solve this problem. Explain the answer

**Answer:** The solution for the **Task 5** should be an implementation of a load balancer service. 
The load balancer will be the **"entrypoint"** to access our service and the responsible to redirect 
traffic to one application instance without causing overload of resources.

In other words, we will have multiples instances of our services based on the incoming traffic following the desired load 
balancer algorithm (ex. Round Robin, Least Connections..).

Some examples of load balancer: Google CLoud - Cloud Load Balancing, AWS - Elastic Load Balancing, NGINX.

![Alt text](src/main/resources/images/LoadBalancer.jpg?raw=true "Load Balancer")

## API Documentation

http://localhost:8080/swagger-ui/index.html#/


