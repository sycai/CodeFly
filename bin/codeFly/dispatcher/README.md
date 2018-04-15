# Interfaces  
Here are basic interfaces for front-end team and back-end team to follow:

## Index
This is the index page of the website. Once the user input the address of our website, a get request will be sent to the server. Then the server will send a response which contains the ```Index``` html file inside the response body.
```
Request(font-end)  
Request line: GET / HTTP/1.1 
Context:/   

Response(back-end)
Response Body:
<html>
xxxx
</html>
```

## getRegister
Once the user clicks the ```Register``` button from the index page, a get request will be sent to the server. Then the server will send a response which contains the ```Register``` html file inside the response body.
```
Request(font-end)  
Request line: GET /register HTTP/1.1 
Context:/getRegister   

Response(back-end)
Response Body:
<html>
xxxx
</html>
```

## getLogin
Once the user clicks the ```Login``` button from the index page, a get request will be sent to the server. Then the server will send a response which contains the ```Login``` html file inside the response body.
```
Request(font-end)  
Request line: GET /login HTTP/1.1 
Context:/getLogin   

Response(back-end)
Response Body:
<html>
xxxx
</html>
```

## Register
The user is required to input ```username```, ```password```, and ```email address``` on the GUI. Once the user clicks the ```Register``` button, a get request will be sent to the server. Then the server will send a response to indicate if this registration is successful or not in the content body. The user will be navigated to different web pages based on the response.
```
Request(font-end)  
Request line: POST /register HTTP/1.1  
Context:/register  
Request Body:    
username:xxxx
password:xxxx

Response(back-end)
Response Body:
Success/Fail
```

## Login
The user is supposed to input ```username``` and ```password``` to login. After the user clicks the ```Log In``` button, a get request will be sent to the server. Then the server will send a response to indicate if this login is successful or not in the content body. The user will be navigated to different web pages based on the response.
```
Request(font-end)  
Request line: POST /login HTTP/1.1  
Context:/login  
Request Body:
username:xxxx
password:xxxx   

Response(back-end)  
Ressponse Body:  
Success/Fail  
```

## Question List
Users who successfully login will be navigated to the main control view of their own account. There is supposed to be a ```Get Question List``` button. Once clicked, a get request will be sent to the server to ask for all the questions’ number and their titles. The server will send a response which contains a list of ```key/value``` pair in the content body to indicate all the question numbers and corresponding titles.
```
Request(font-end  )
Request line: GET /questions HTTP/1.1
Context:/questions  
Request Body:  

Response(back-end)  
Response Body:  
HTML that lists all the questions  
…  
```

## Get Specific Question
The user is supposed to be navigated to the view of question list after clicking the ```Get Question List``` button. Among all the questions, the user can select one and click the ```Start``` button to start programming. A get request will be sent to the server. Then the server will send a response which contains the question description in the content body to the user.
```
Request(font-end)  
Request line: GET /edit?username=xxx&qnum=x HTTP/1.1 
Context:/edit  
Request Body:    

Response(back-end)  
Response Body:  
HTML for question description and editor
```

## Submit Solution
When the user finishes programming, he could click the ```Submit``` button to send a post request to the server to store his solution on the server. Then the server will send a response to indicate if this solution is saved successfully or not.
```
Request(font-end)  
Request line: POST /edit?username=xxx&qnum=x HTTP/1.1
Context:/edit  
Request Body:     
Import java.io  
Xxxx  
Xxxx  
Xxxx  

Response(back-end)  
Response Body:  
Success/Fail  
```

## Retrieve Solution
On the view of ```Question List```, there is supposed to be another button ```Retrieve Solution``` besides each question title. Once clicked, a get request will be sent to the server to retrieve the corresponding question’s previous solution the user has submitted. Then the server will send a response which contains the solution in its content body.
```
Request(font-end)  
Request line: GET /retrieve?username=xxx&qnum=x HTTP/1.1  
Context:/retrive  
Request Body:   

Response(back-end)  
Response Body:  
Import java.io  
Xxxx  
Xxxx  
Xxxx  
```

## Compile Solution (to be discussed and updated later)
## Run Solution (to be discussed and updated later)