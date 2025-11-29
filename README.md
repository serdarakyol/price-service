# Price Query Service (E-Commerce Tariff System)
## Overview
This repository contains a Spring Boot 4.0.0 application designed to determine the final selling price (`PRICE`) for a specific product at a precise date and time. The service correctly handles overlapping tariffs by applying the highest `PRIORITY` rule.

The solution is built on Java 17 and strictly adheres to the Hexagonal Architecture and SOLID principles, ensuring high maintainability, testability, and clean separation of concerns.

## Architecture and Design
Hexagonal Architecture
The project is structured into three distinct modules (Domain, Application, Infrastructure) that separate the business core from external technologies.
* **Domain**: Contains the immutable business entity (`Price`) and the Ports (`PriceRepositoryPort`).
* **Application (Core)**: Contains the Use Case (`GetPriceUseCase`), which implements the main logic (date validation and Priority sorting). This layer is independent of Spring Boot or JPA.
* **Infrastructure (Adapters)**: Contains the Controller (REST API) and the Persistence layer (H2, JPA entities, and Adapters).

This design ensures:
* **SOLID Principles**: High cohesion and loose coupling. Dependency Inversion is achieved as the Use Case depends on the Domain Port (PriceRepositoryPort), not the concrete JPA implementation.
* **Clarity of Code**: Clean separation makes the business logic immediately apparent.


## Data Efficiency and Performance
The application is configured for optimal performance on data extraction.
* **H2 Initialisation**: The H2 in-memory database is initialised at startup using `schema.sql` and `data.sql`, ensuring the 4 required records are immediately available.
* **Indexing**: The PRICES table schema includes three specialized indexes to accelerate the complex lookup query:
  * `idx_prices_product_brand`: Speeds up filtering by required key fields.
  * `idx_prices_start_end`: Aids in rapid date range checks.
  * `idx_prices_product_brand_dates`: Provides maximum Efficiency for the core query filtering by product, brand, and date range.
* **Unique Result**: The business logic guarantees a single final price is returned, selecting the highest PRIORITY tariff among all applicable candidates.

## API REST Endpoint
The service exposes a clean, well-documented GET endpoint that follows REST best practices. The full contract is detailed in the OpenAPI (OAS) specification located in docs/oapi.yaml.

## Testing and Validation
The project includes robust Pruebas de integración using Spring Boot's MockMvc framework. These tests directly validate the unique result logic for the 5 scenarios requested in the prompt, confirming the correct application of the date and priority rules. To run them locally, `mvn clean test` or `./mvnw test` commands can be used on root directory of the project.

## Getting Started
**Prerequisites**
* Java 17
* Git (for Control de versiones and CI)

### Running the Application
For the best experience, especially if managing multiple Java versions, sdkman is recommended.

#### 1. Linux/macOS (Recommended via Dev Script)
Use the project's development script for building and running (Should be run on root directory of the project):
```shell
./dev code-run
```

#### 2. Cross-Platform / Docker
If not using Linux/macOS, or for consistent environment configuration, build and run the Docker image:
##### Build the Docker Image:
```shell
docker build -t price-service .
```
##### Run the Container:
```shell
docker run -p 8080:8080 price-service
```
