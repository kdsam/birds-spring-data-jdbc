package com.example.demo.repository;

import com.example.demo.model.Bird;
import com.example.demo.response.TimedResponse;
import com.querydsl.core.types.Predicate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface BirdRepositoryCustom<T> {

    Page<T> findAll(Pageable var1);
    List<Bird> findAll(Predicate... where);

    Page<Bird> findAll(Pageable pageable, Predicate... where);

    TimedResponse<Page<Bird>> findOneFromCache(String reqId);

    void storeToCache(TimedResponse<Page<Bird>> timedResponse);
}
