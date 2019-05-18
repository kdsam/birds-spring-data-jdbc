package com.example.demo.repository;

import com.example.demo.model.Bird;
import com.example.demo.response.TimedResponse;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.QBean;
import com.querydsl.sql.SQLQueryFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;

import static com.querydsl.core.types.Projections.bean;
import static com.example.demo.model.QBird.*;
import static com.querydsl.sql.SQLExpressions.all;


import java.util.List;
import java.util.concurrent.TimeUnit;

@Transactional(readOnly = true)
public class BirdRepositoryImpl implements BirdRepositoryCustom<Bird> {
    private static final Logger LOG  = LoggerFactory.getLogger(BirdRepositoryImpl.class);

    @Autowired
    private RedisTemplate redisTemplate;
    private HashOperations hashOperations;
    private String KEY = "";
    private Long expiresIn = 120L;

    @Autowired
    private SQLQueryFactory queryFactory;

    final QBean<Bird> birdQBean = bean(Bird.class, bird.id, bird.breed, bird.name);

    @Autowired
    private BirdRepository birdRepository;

    @PostConstruct
    private void init() {
        KEY = "/" + "dev" + "/birds/";
        this.hashOperations = redisTemplate.opsForHash();
    }


    @Override
    public Page<Bird> findAll(Pageable pageable) {
        long total = birdRepository.fetchCount();
        List<Bird> birds = birdRepository.findBirdsPaged(pageable.getPageSize(), pageable.getOffset());
        return new PageImpl<>(birds, pageable, total);
    }

    @Override
    public List<Bird> findAll(Predicate... where) {
        return queryFactory.select(birdQBean)
                .from(bird)
                .where(where)
                .fetch();
    }

    @Override
    public Page<Bird> findAll(Pageable pageable, Predicate... where) {
        long total = queryFactory.select(birdQBean)
                .from(bird)
                .where(where)
                .fetchCount();

        List<Bird> birds = queryFactory.select(birdQBean)
                .from(bird)
                .where(where)
                .limit(pageable.getPageSize())
                .offset(pageable.getOffset())
                .fetch();
        return new PageImpl<>(birds, pageable, total);
    }


    @Override
    public TimedResponse<Page<Bird>> findOneFromCache(String reqId) {
        return (TimedResponse<Page<Bird>>) hashOperations.get(KEY + reqId, reqId);
    }

    @Override
    public void storeToCache(TimedResponse<Page<Bird>> timedResponse) {
        hashOperations.put(KEY + timedResponse.getRequestId(), timedResponse.getRequestId(), timedResponse);
        Boolean isTokenExpireSet = redisTemplate.expire(KEY + timedResponse.getRequestId(), expiresIn,
                TimeUnit.SECONDS);
        LOG.info("Activity Timed Result {} expires in Redis set to {} : {}", KEY + timedResponse.getRequestId(),
                expiresIn, isTokenExpireSet);
    }


}
