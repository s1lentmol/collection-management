# Collection Management

An interactive client-server application for managing a collection of objects over UDP, with persistent storage in a PostgreSQL database and secure user authentication.

## Features

- **Interactive Collection Management**: Perform CRUD operations on collection items.
- **UDP Communication**: Efficient message exchange between client and server using the User Datagram Protocol.
- **Persistent Storage**: All collection data and user credentials are stored in PostgreSQL.
- **User Authentication**: Secure registration and login process with hashed passwords.
- **Multi-User Support**: Isolated collections per user account.

## Architecture

The application follows a client-server model:

- **Client**: A Java application responsible for capturing user commands and displaying results.
- **Server**: A Java-based UDP server that processes client requests, executes database operations, and returns responses.
- **Database**: PostgreSQL holds persistent data for collection items and user credentials.

