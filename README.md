System Overview

Build My Notes will listen to lectures, transcribe them, and provide summaries in various formats such as quizzes. The system will leverage AI tools for NLP processing and summarization.


Key Microservices

Speech Service: This will handle the conversion of audio (e.g., lecture recordings) to text using Google Cloud's Speech-to-Text API.

Summary Service: This will take the transcribed text from the Speech Service and generate a summary. It can use OpenAI API.

User Service: Manages user profiles, including storing and retrieving personal information, preferences, and usage data.

Notes Service: Stores user-generated notes, summaries, and other relevant content.

Quiz Service: Provides quizzes based on the notes or summaries for self-assessment.It use OpenAI API.

AI Interaction Service: Provides a 24/7 AI-powered subject expert teacher for Q&A and further explanations. It Use OpenAI API.

API Gateway: Acts as an entry point for all the client requests and routes them to the appropriate services.

Service Discovery (Eureka): Manages service registration and discovery for dynamic routing and scaling.


Technologies and Tools:

Backend: Spring Boot (Java)

Database: MySQL for relational data (user profiles, notes, summaries)

Authentication: JWT (JSON Web Tokens) for secure APIs

Service Discovery: Eureka

Speech-to-Text: Google Cloud Speech API

AI and NLP : OpenAI API.

API Gateway: Spring Cloud Gateway

Docker: To containerize the microservices

Kubernetes: For orchestration and scaling 
