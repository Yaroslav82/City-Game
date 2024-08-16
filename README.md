# Web Game "Міста" (Cities)

This project is a web-based game called "Міста" (Cities), where the player and the system take turns naming cities based on the last letter of the previous city's name. The game is implemented using Java with Spring Boot for the backend and Angular for the frontend.

## Features

- **Interactive Gameplay**: The game allows the player to compete against the system in naming cities.
- **Smart Letter Handling**: If a city's name ends with the letters 'ь', 'ъ', 'й', or 'и', the system will use the penultimate letter for the next city.
- **Session Management**: Multiple game sessions can run concurrently, and sessions are marked as inactive upon completion.
- **Error Handling**: The system checks if the player's city name starts with the correct letter and whether it has been used before. If either condition fails, the player receives an appropriate error message.
- **City List**: The list of cities is parsed from Wikipedia, ensuring a comprehensive and up-to-date collection.

## Database Setup

The application uses a MySQL database to store city names and game sessions. To set up the database, use the following Docker command:

```bash
docker run --name cities_mysql -e MYSQL_ROOT_PASSWORD=root -e MYSQL_DATABASE=cities_game_db -e MYSQL_USER=dev -e MYSQL_PASSWORD=abcd1234 -p 3306:3306 -d mysql:latest
