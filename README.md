# HealthApp

HealthApp is an innovative platform aimed at revolutionizing the way individuals manage their health and nutrition. The app combines a suite of tools and features to help users make informed decisions about their dietary habits, fitness goals, and overall wellness.

## Features

- **Fitness Calculator:** Enables users to calculate their Body Mass Index (BMI), body fat percentage, daily calorie needs, and ideal weight.
- **Recipe Manager:** Allows users and nutritionists to share and discover healthy recipes, fostering a community of well-being.

## Architecture

Utilizing a Super App architecture, HealthApp integrates various mini-applications into a single platform, providing a comprehensive and seamless user experience. This approach allows for a diverse range of services and functionalities to be accessed through one unified interface, enhancing user engagement and satisfaction.

## Technologies

This project leverages modern technologies to ensure a responsive, reliable, and user-centric experience:
- **Spring Boot** for backend development, providing quick, stand-alone, production-grade application deployment.
- **MongoDB** for a flexible, scalable database solution, managing diverse data sets efficiently.
- **Docker** to containerize the application, ensuring consistency across different environments.
- **Swagger** for designing, building, and documenting RESTful APIs, enhancing developer experience and frontend-backend integration.
- **JUnit** for robust backend testing, ensuring the application's reliability and functionality.
- **Logger**: Integrated logging mechanism for monitoring application performance, tracking down issues, and ensuring smooth operation.

## Getting Started

To get a local copy up and running, follow these simple steps:

1. **Clone the Repository**

   Use the following command to clone the repo:
  ```git clone git@github.com:Asnvir/HealthApp.git```

2. **Open the Project in an IDE**

Open the project in your preferred IDE, such as IntelliJ IDEA, to access the codebase and project files.

3. **Run Docker Compose**

To set up the necessary environment and services, use Docker Compose:
- Navigate to the project's root directory in the terminal.
- Run the command:
  ```
  docker compose up
  ```
This will start all the services defined in the `compose.yaml` file located at the root of your project.

4. **Run the Application**

With the services up and running, start the application from your IDE to begin using it locally.

**P.S.** The link to access Swagger UI is [http://localhost:8085/webjars/swagger-ui/index.html](http://localhost:8085/webjars/swagger-ui/index.html), but this may vary based on the project configuration. Check the project settings for the most accurate URL.

