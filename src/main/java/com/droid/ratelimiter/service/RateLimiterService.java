package com.droid.ratelimiter.service;

import java.time.Instant;
import java.util.concurrent.TimeUnit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

@Service
public class RateLimiterService {
  private static final int MAX_REQUESTS = 5;
  private static final int WINDOW = 60;

  @Autowired private ValueOperations<String, String> valueOperations;

  public boolean isRequestAllowed(String userId) {
    String key = "rate limit:" + userId;
    long currentTime = Instant.now().getEpochSecond();

    String windowStartOfClient = valueOperations.get(key + ":windows start");
    String requestCountOfClient = valueOperations.get(key + ":request count");

    long windowStart = (windowStartOfClient == null) ? 0 : Long.parseLong(windowStartOfClient);
    int requestCount = (requestCountOfClient == null) ? 0 : Integer.parseInt(requestCountOfClient);

    if (currentTime - windowStart < WINDOW) {
      if (requestCount >= MAX_REQUESTS) {
        return false;
      } else {
        valueOperations.increment(key + ":request count", 1);
        return true;
      }
    } else {
      valueOperations.set(
          key + ":windows start", String.valueOf(currentTime), WINDOW, TimeUnit.SECONDS);
      valueOperations.set(key + ":request count", "1", WINDOW, TimeUnit.SECONDS);
      return true;
    }
  }
}
