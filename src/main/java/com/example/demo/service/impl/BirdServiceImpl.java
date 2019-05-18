package com.example.demo.service.impl;

import com.example.demo.model.Bird;
import com.example.demo.repository.BirdRepository;
import com.example.demo.service.BirdService;
import com.querydsl.core.BooleanBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static com.example.demo.model.QBird.bird;

@Service
public class BirdServiceImpl implements BirdService {

    @Autowired
    private BirdRepository birdRepository;

    @Override
    public List<Bird> findAllBirds() {
        List<Bird> list = new ArrayList<>();
        birdRepository.findAll().forEach(list::add);
        return list;
    }

    @Override
    public Page<Bird> findAllBirdsPaged(Pageable pageable) {
        return birdRepository.findAll(pageable);
    }

    @Override
    public List<Bird> findAllBirdsWithPredicate(String breed) {
        BooleanBuilder booleanBuilder = new BooleanBuilder();
        booleanBuilder.and(bird.breed.equalsIgnoreCase("parakeet"));
        List<Bird> list = new ArrayList<>();
        birdRepository.findAll(booleanBuilder.getValue()).forEach(list::add);
        return list;
    }
}
