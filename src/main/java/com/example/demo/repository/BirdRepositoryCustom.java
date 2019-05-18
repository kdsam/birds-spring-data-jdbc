package com.example.demo.repository;

import com.example.demo.model.Bird;
import com.querydsl.core.types.Predicate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface BirdRepositoryCustom<T> {

    Page<T> findAll(Pageable var1);
    List<Bird> findAll(Predicate... where);

}
