# Converse
End-to-end encrypted group chat built with Java, Spring Boot and RabbitMQ

## What is this?
I wrote this primarily because I needed to do a project with Java for university and I had always wanted to try solving some cryptography challenges. This application provides the following features:
- Enables group chat using only RabbitMQ as the broker
- Provides a naive implementation of the Diffie–Hellman key exchange algorithm to establish a common secret between the chatting parties
- Provides a mechanism for joining users to securely receive the group's secret in an efficient and fault-tolerant manner (Using, again, RabbitMQ)
- Encrypts/decrypts the message as needed based on the established shared secret
- Optionall writes the encrypted messages to a provided database

## Requirements
- Java Runtime, obviously...
- RabbitMQ installed with default configuration on localhost
- (Optional) Account and API key from [restdb.io](https://restdb.io/) for the persistence layer
- You can configure these properties by taking a look at the [`application.properties`](https://github.com/ashkan-pm/converse/blob/master/src/main/resources/application.properties) file
