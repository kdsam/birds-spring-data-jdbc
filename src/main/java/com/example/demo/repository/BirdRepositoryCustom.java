package com.example.demo.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BirdRepositoryCustom<T> {

    Page<T> findAll(Pageable var1);
}
