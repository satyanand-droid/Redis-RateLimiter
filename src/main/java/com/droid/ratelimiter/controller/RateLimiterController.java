package com.droid.ratelimiter.controller;

import com.droid.ratelimiter.service.RateLimiterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@RestController
@RequestMapping("/IsRequestAllowed")
public class RateLimiterController {

    @Autowired
    RateLimiterService rateLimiterService;

    @GetMapping("/{userId}")
    public ResponseEntity<String> isRequestAllowed(@PathVariable String userId){
        if(rateLimiterService.isRequestAllowed(userId)){
            return ResponseEntity.ok("request allowed");
        }
        else{
            return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS).body("too many requests");
        }
    }
}
