# tinyURL
TinyURL Service
Overview
A simple Java-based URL shortening service that converts long URLs into shorter, easy-to-share links using a unique 6-character code.

Features
Shorten URLs: Generate unique short links for any original URL.
Retrieve Original URL: Decode a short link back to its original URL.
Persistent Storage: Uses MySQL database for storing URLs.
Technologies Used
Java for core application
MySQL for database management
JDBC for database connectivity
Setup Instructions
Clone the repository:
bash
Copy code
git clone https://github.com/your_username/tinyurl-service.git
Database Setup:
Create a MySQL database named tinyurl.
Run the following SQL command:
sql
Copy code
CREATE TABLE urls(
  id INT AUTO_INCREMENT PRIMARY KEY, 
  original_url TEXT NOT NULL, 
  short_url VARCHAR(10) UNIQUE NOT NULL, 
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
Compile and Run:
bash
Copy code
javac Main.java
java Main
Usage
Shorten a URL: Input the original URL to receive a shortened link.
Retrieve URL: Input the short code to get the original URL.
