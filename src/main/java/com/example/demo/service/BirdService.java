package com.example.demo.service;

import com.example.demo.model.Bird;
import com.example.demo.response.TimedResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface BirdService {
    List<Bird> findAllBirds();
    Page<Bird> findAllBirdsPaged(Pageable pageable);
    List<Bird> findAllBirdsWithPredicate(String breed);
    CompletableFuture<Page<Bird>> findAllBirdsWithPredicateAsync(Pageable pageable, String breed) throws InterruptedException;
    TimedResponse<Page<Bird>> getFromCache(String reqid);
    void putToCache(String reqid, TimedResponse<Page<Bird>> finalResp);
}
