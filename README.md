# Coupons Microservices

The Coupons Microservices service is a collection of microservices designed to handle coupon management and related functionality. This README provides instructions for installing and running the service locally.

## Architecture
![System Architecture Diagram](https://raw.githubusercontent.com/nitay12/coupon-project-ms/master/system-diagram.jpg)

## Installation Guide

### Prerequisites

Before getting started, ensure that you have the following prerequisites installed on your system:

- Java Development Kit (JDK) 11 or later
- Apache Maven
- RabbitMQ server (not necessary when using docker)
- MySQL server (not necessary when using docker)
- Docker (optional, for running with Docker containers)

### Clone the Repository

Start by cloning the repository to your local machine:

```shell 
git clone <repository-url>
```

```shell 
cd coupon-project-ms
```

### Build the Project

Next, build the project using Maven:
```shell
$ mvn clean install
```

### Start the Services

To run the Coupons Microservices service locally, you have two options: running the services individually or using Docker containers.

#### Running Services Individually

Start each microservice individually using the following commands:
```shell
$ cd coupon-management-service
$ DB_HOST=localhost DB_PORT=33066 DB_USERNAME=root DB_PASSWORD=password DB_NAME=coupon_management EUREKA_SERVER_URL=http://localhost:8761/eureka RABBITMQ_HOST=localhost mvn spring-boot:run
```
```shell
$ cd auth-service
$ DB_HOST=localhost DB_PORT=33067 DB_USERNAME=root DB_PASSWORD=password DB_NAME=auth RABBITMQ_HOST=localhost mvn spring-boot:run
```
```shell
$ cd expired-coupons-service
$ DB_HOST=localhost DB_PORT=33068 DB_USERNAME=root DB_PASSWORD=password DB_NAME=expired_coupons RABBITMQ_HOST=localhost mvn spring-boot:run
```
```shell
$ cd gateway-service
$ COUPONS_MANAGEMENT_URL=http://localhost:8080 AUTH_SERVICE_URL=http://localhost:8081 mvn spring-boot:run

```

#### Running with Docker Containers

If you prefer to run the services using Docker containers, follow these steps:

1. Build the Docker images:
```shell
docker-compose build
```

2. Start the services:
```shell
docker-compose up
```


### Accessing the APIs

Once the services are up and running, you can access the APIs using the gateway service:

- Gateway Service: http://localhost:8085

Refer to the individual microservice documentation for more information on the available APIs and their usage.
- Coupons management API: http://localhost:8080/swagger-ui/
- Auth service API: http://localhost:8081/swagger-ui/