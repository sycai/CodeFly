# Interfaces  
Here are basic interfaces for front-end team and back-end team to follow:

## Register
The user is required to input ```username```, ```password```, and ```email address``` on the GUI. Once the user clicks the ```Register``` button, a post request will be sent to the server. Then the server will send a response to indicate if this registration is successful or not in the content body. The user will be navigated to different web pages based on the response.
```
Request(font-end)  
Request Method: POST  
URI: localhost:8080/register  
Request Body:  
Username: xxxxx  
Password: xxxxx  
Email: xxx@gmail.com  

Response(back-end)
Response Body:
Success/Fail
```

## Login
The user is supposed to input ```username``` and ```password``` to login. After the user clicks the ```Log In``` button, a post request will be sent to the server. Then the server will send a response to indicate if this login is successful or not in the content body. The user will be navigated to different web pages based on the response.
```
Request(font-end)  
Request Method: POST  
URI: localhost:8080/login  
Request Body:  
Username: xxxxx   
Password: xxxxx  

Response(back-end)  
Ressponse Body:  
Success/Fail  
```

## Question List
Users who successfully login will be navigated to the main control view of their own account. There is supposed to be a ```Get Question List``` button. Once clicked, a get request will be sent to the server to ask for all the questions’ number and their titles. The server will send a response which contains a list of ```key/value``` pair in the content body to indicate all the question numbers and corresponding titles.
```
Request(font-end  )
Request Method: GET  
URI: localhost:8080/questions  
Request Body:  

Response(back-end)  
Response Body:  
Question1: Two Sum  
Question2: Reverse Linked List   
…  
```

## Start
The user is supposed to be navigated to the view of question list after clicking the ```Get Question List``` button. Among all the questions, the user can select one and click the ```Start``` button to start programming. A post request will be sent to the server. Then the server will send a response which contains the question description in the content body to the user.
```
Request(font-end)  
Request Method: POST  
URI: localhost:8080/start  
Request Body:  
Username: xxx  
Question number: x  

Response(back-end)  
Response Body:  
Write an algorithm which computes the number of trailing zeros in n factorial.  
Example:
11! = 39916800, so the out should be 2.
```

## Submit Solution
When the user finishes programming, he could click the ```Submit``` button to send a post request to the server to store his solution on the server. Then the server will send a response to indicate if this solution is saved successfully or not.
```
Request(font-end)  
Request Method: POST  
URI: localhost:8080/submit  
Request Body:  
Username: xxx  
Question number: x  
Code:   
Import java.io  
Xxxx  
Xxxx  
Xxxx  

Response(back-end)  
Response Body:  
Success/Fail  
```

## Retrieve Solution
On the view of ```Question List```, there is supposed to be another button ```Retrieve Solution``` besides each question title. Once clicked, a post request will be sent to the server to retrieve the corresponding question’s previous solution the user has submitted. Then the server will send a response which contains the solution in its content body.
```
Request(font-end)  
Request Method: POST  
URI: localhost:8080/retrieve  
Request Body:  
Username: xxx  
Question number: x  

Response(back-end)  
Response Body:  
Import java.io  
Xxxx  
Xxxx  
Xxxx  
```

## Compile Solution (to be discussed and updated later)
## Run Solution (to be discussed and updated later)