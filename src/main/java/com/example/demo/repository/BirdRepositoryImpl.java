package com.example.demo.repository;

import com.example.demo.model.Bird;
import com.example.demo.model.QBird;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.QBean;
import com.querydsl.sql.SQLQueryFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import static com.querydsl.core.types.Projections.bean;
import static com.example.demo.model.QBird.*;
import static com.querydsl.sql.SQLExpressions.all;


import java.util.List;

@Transactional(readOnly = true)
public class BirdRepositoryImpl implements BirdRepositoryCustom<Bird> {

    @Autowired
    private SQLQueryFactory queryFactory;

    final QBean<Bird> birdQBean = bean(Bird.class, bird.id, bird.breed, bird.name);

    @Autowired
    private BirdRepository birdRepository;

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

}
