Redis Sliding Window Rate Limiter

This project implements a sliding window rate limiter using Spring Boot and Redis. The rate limiter ensures that clients (e.g., users or IP addresses) can only make a certain number of requests within a specific time window. Requests exceeding the limit are rejected with a 429 Too Many Requests response.

Features

	•	Sliding Window Algorithm: Provides a more dynamic rate limiting approach by counting requests over a moving window of time.
	•	Redis Backed: Utilizes Redis for fast in-memory storage of request counts and window timestamps, ensuring low-latency, high-performance rate limiting.
	•	Atomic Operations: Leverages Redis atomic operations like INCR to safely increment request counts, ensuring accuracy in concurrent environments.
 
