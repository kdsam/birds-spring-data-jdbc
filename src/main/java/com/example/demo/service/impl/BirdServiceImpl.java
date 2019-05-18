package com.example.demo.service.impl;

import com.example.demo.model.Bird;
import com.example.demo.repository.BirdRepository;
import com.example.demo.response.TimedResponse;
import com.example.demo.service.BirdService;
import com.querydsl.core.BooleanBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import static com.example.demo.model.QBird.bird;

@Service
public class BirdServiceImpl implements BirdService {
    private static final Logger LOG  = LoggerFactory.getLogger(BirdServiceImpl.class);

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

    @Override
    @Async
    public CompletableFuture<Page<Bird>> findAllBirdsWithPredicateAsync(Pageable pageable, String breed) throws InterruptedException {
        LOG.info("THREAD: {}", Thread.currentThread().getName());
        BooleanBuilder booleanBuilder = new BooleanBuilder();
        if (!StringUtils.isEmpty(breed)) {
            booleanBuilder.and(bird.breed.equalsIgnoreCase(breed));
        }
        Page<Bird> list = birdRepository.findAll(pageable, booleanBuilder.getValue());
        // Artificial delay of 1s for demonstration purposes
        Thread.sleep(2000L);
        return CompletableFuture.completedFuture(list);
    }

    @Override
    public TimedResponse<Page<Bird>> getFromCache(String reqid) {
        return birdRepository.findOneFromCache(reqid);
    }

    @Override
    public void putToCache(String reqid, TimedResponse<Page<Bird>> finalResp) {
        birdRepository.storeToCache(finalResp);
    }
}
