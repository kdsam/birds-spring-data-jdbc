package com.example.demo.service;

import com.example.demo.model.Bird;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.querydsl.core.types.Predicate;

import java.util.List;

public interface BirdService {
    List<Bird> findAllBirds();
    Page<Bird> findAllBirdsPaged(Pageable pageable);
    List<Bird> findAllBirdsWithPredicate(String breed);
}
