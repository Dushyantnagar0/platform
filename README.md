# Platform Service Repository

This repository hosts a collection of user-based APIs designed to accommodate different user roles: public, customer, and admin. The APIs are structured with rate limiting using the Redis sliding window rate limit algorithm to ensure optimized performance.

## Features

### API Structure

- **Public APIs:** Accessible to all users.
- **Customer APIs:** Accessible to logged-in users.
- **Admin APIs:** Restricted to specific authorized users.

## Public APIs

These APIs are accessible to everyone:

- **Login API**: Allows users to receive an OTP (One-Time Password) for authentication.
- **Validate OTP API**: Validates the OTP entered by the user to authenticate, providing a token for access.

## User/Customer APIs

Accessible to authenticated users:

- **Update Personal Info**: Enables users to update their personal information.

## Admin APIs

Restricted to admin access only:

- **Admin-only Operations**: Provides functionalities solely for admin use.


### Rate Limiting

All APIs have been integrated with a Redis-based sliding window rate limit algorithm to manage and control request rates effectively.

### Error Handling and Responses

The repository includes robust error handling mechanisms within the servlet filter and method-level implementations. This ensures consistent and clear responses to various error scenarios, contributing to a better user experience.

### Database and Integration Support

- **MongoDB:** Built-in support for MongoDB, leveraging its functionalities within the APIs.
- **Kafka:** Integration for asynchronous messaging and event-driven architecture.
- **Redis:** Utilizing Redis support for efficient caching and data storage.
- **Loading-cache:** Utilizing guava loading cache for caching.

### Multi-threading and Asynchronous Flows

The repository is designed to support multi-threading and asynchronous flows, ensuring efficient execution and handling of concurrent tasks.

## Usage

### Installation

1. Clone this repository to your local machine.
2. Install MongoDB, Redis, Kafka, and zookeeper on your local machine.

# Configuration Details

Below are the essential configuration parameters required for this service to run. Modify these values according to your specific setup.

| Parameter       | Description                                           | Example                |
|-----------------|-------------------------------------------------------|------------------------|
| `dbName`        | Name of the database                                  | `Platform`             |
| `kafka.port`    | Kafka server port number                               | `9092`                 |
| `mongo_port`    | MongoDB server port number                             | `mongodb://localhost:27017` |
| `redis.port`    | Redis server port number                               | `6379`                 |
| `server.port`   | Port for this service to run                           | `8080`                 |
| `secretKey`     | Secret key used for encryption/signing (any string)    | `MySecretKey123!`      |


## Contribution

Contributions are welcome! If you wish to contribute to the development or have any suggestions, please follow the guidelines outlined in [CONTRIBUTING.md].

## License

This project is licensed under the [License Name] - see the [LICENSE.md] file for details.
