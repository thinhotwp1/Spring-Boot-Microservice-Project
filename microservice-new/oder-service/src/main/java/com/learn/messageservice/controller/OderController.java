package com.learn.messageservice.controller;


import com.learn.messageservice.dto.OderRequest;
import com.learn.messageservice.service.OderService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.CompletableFuture;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/oder")
public class OderController {

    private final OderService oderService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @CircuitBreaker(name = "inventory", fallbackMethod = "fallbackMethod")
    public CompletableFuture<String> placeOder(@RequestBody OderRequest oderRequest){
        return CompletableFuture.supplyAsync(()->oderService.placeOder(oderRequest));
    }
    public CompletableFuture<String> fallbackMethod(OderRequest oderRequest, RuntimeException runtimeException){
        return CompletableFuture.supplyAsync(()->"Inventory is busy, please oder after sometime !");
    }
}
