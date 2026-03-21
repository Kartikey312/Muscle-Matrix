📌 Fitness Microservices Project (Spring Boot + AI + Gateway)
🚀 Project Overview

This project is a full-stack microservices-based fitness tracking system built using Spring Boot, React, and modern cloud-native tools. It demonstrates how to design and implement a scalable backend architecture with multiple services communicating efficiently.

The system allows users to track activities, manage profiles, and receive AI-based recommendations using asynchronous communication.

👉 Inspired by this tutorial:
Watch Full Project Video

🏗️ Architecture

The application follows a microservices architecture with the following components:

User Service → Handles user registration and profile
Activity Service → Manages fitness activities
AI Service → Generates recommendations using AI
API Gateway → Single entry point for all services
Eureka Server → Service discovery
Config Server → Centralized configuration

👉 Communication:

Sync → REST APIs
Async → Kafka
⚙️ Tech Stack
🔹 Backend
Java + Spring Boot
Spring Cloud (Gateway, Eureka, Config)
Spring Security + Keycloak (OAuth2)
Kafka (event-driven communication)
JPA + MySQL
MongoDB
🔹 Frontend
React (Vite)
Redux Toolkit
Material UI
Axios
🔹 DevOps / Tools
Docker
AWS (optional deployment)
GitHub
🔄 Workflow
User logs in via Keycloak (OAuth2)
Gateway intercepts request and syncs user
User creates activity
Activity Service sends event to Kafka
AI Service consumes event
AI generates recommendation
Response returned to user

👉 The project demonstrates event-driven microservices using Kafka

📦 Features

✔ User Authentication (OAuth2 + Keycloak)
✔ Activity Tracking
✔ AI-based Recommendations
✔ Microservices Communication
✔ API Gateway Routing
✔ Service Discovery (Eureka)
✔ Centralized Config
✔ Event-driven Architecture (Kafka)

📂 Project Structure
fitness-project/
│
├── api-gateway/
├── user-service/
├── activity-service/
├── ai-service/
├── config-server/
├── eureka-server/
└── frontend/
🛠️ Setup Instructions
1️⃣ Clone Repository
git clone https://github.com/Kartikey312/Muscle-Matrix.git
cd fitness-project
2️⃣ Start Backend Services

Run in order:

# Eureka Server
cd eureka-server
mvn spring-boot:run

# Config Server
cd config-server
mvn spring-boot:run

# Other Services
cd user-service
cd activity-service
cd ai-service
cd api-gateway
3️⃣ Start Frontend
cd frontend
npm install
npm run dev
4️⃣ Access Application
Frontend → http://localhost:5173
Gateway → http://localhost:8080
🔐 Authentication
Uses Keycloak (OAuth2)
JWT token is validated at API Gateway
User sync handled automatically
🧠 AI Integration
AI Service consumes Kafka events
Generates fitness recommendations
Uses external AI APIs (OpenAI / Gemini)
📈 Learning Outcomes

This project helps you understand:

Microservices architecture
API Gateway pattern
Event-driven systems (Kafka)
Authentication (OAuth2 + Keycloak)
Real-world backend design
🚀 Future Improvements
Add Notifications Service
Implement Rate Limiting
Add Monitoring (Prometheus + Grafana)
Deploy on Kubernetes
👨‍💻 Author

Kartikey Mishra

⭐ Final Note

This is a production-level backend project, demonstrating real-world concepts like:

Microservices
Distributed systems
Scalable backend design
