# Java Multithreaded ThreadPool Server 💻⚡

A scalable, efficient multithreaded server built in Java that listens on port `8010` and handles concurrent client requests using a custom ThreadPool — inspired by the asynchronous nature of JavaScript's Event Loop.

## 🔧 Tech Stack

- Java (JDK 8+)
- Socket Programming
- ThreadPool Executor
- Shutdown Hook for graceful termination

## 🚀 Features

- Handles multiple client requests concurrently
- Uses fixed thread pool to manage threads efficiently
- Clean shutdown using Runtime shutdown hooks
- Can be extended to build REST-like API services

## 📂 Project Structure
Server.java           // Main multithreaded server logic
ClientHandler.java    // Logic to process each client request (extendable)

## 📌 How it Works

- The server listens on port `8010`
- On accepting a client connection, it delegates the processing to a thread from the pool
- A shutdown hook ensures the server exits gracefully when terminated

## ✨ Why This Project?

JavaScript handles concurrency with a single-threaded **Event Loop** using callbacks and Promises.

In contrast, Java provides robust multithreading capabilities. This project mimics JavaScript's async nature by efficiently managing multiple threads via a ThreadPool, enabling non-blocking server-side logic in Java.

## 🧪 Usage

1. Run the server:
   ```bash
   java Server

telnet localhost 8010

## ⚡ Performance Testing

Load tested the server using Apache JMeter:
- Simulated 600000 concurrent users using TCP Sampler
- Achieved [X] requests/sec with minimal latency
- Observed ThreadPool resilience under burst loads

Test Plan available in `TCP Sampler.jmx` file.
