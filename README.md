# Quiz Application

## Overview
This project is a web-based quiz application built using Java, Spring Boot, Thymeleaf, and MongoDB. Users can browse available quizzes, take them, and receive results based on their answers.

## Features
- View all available quizzes
- Take quizzes and submit answers
- View quiz results

## Technologies
- Java
- Spring Boot, Lombok
- Thymeleaf, HTML, CSS
- MongoDB
- Maven
- JUnit, Mockito

## Prerequisites
- JDK 17 or higher
- appropriate network settings - your network firewall cannot block port 27017

## Getting Started

### Clone the Repository
Current project version does not support building project and creating JAR file by yourself. To run project, please follow these steps:
```bash
git clone https://github.com/fijalkoa/QuizApp
java -jar QuizApp/target/Quizzes-0.0.1-SNAPSHOT.jar
```

## Endpoints
- http://localhost:8080/api/v1/home
- http://localhost:8080/api/v1/quizzes

## Preview
QuizApp homepage
![image](https://github.com/fijalkoa/QuizApp/blob/main/previews/quiz-home-page.png?raw=true)

Show all quizzes
![image](https://github.com/fijalkoa/QuizApp/blob/main/previews/quiz-allQuizes-page.png?raw=true)

Quiz description
![image](https://github.com/fijalkoa/QuizApp/blob/main/previews/quiz-quizDescription-page.png?raw=true)

Quiz form
![image](https://github.com/fijalkoa/QuizApp/blob/main/previews/quiz-quizform-page.png?raw=true)
