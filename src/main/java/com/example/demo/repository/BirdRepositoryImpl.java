package com.example.demo.repository;

import com.example.demo.model.Bird;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;

public class BirdRepositoryImpl implements BirdRepositoryCustom<Bird> {

    @Autowired
    private BirdRepository birdRepository;

    @Override
    public Page<Bird> findAll(Pageable pageable) {
        long total = birdRepository.fetchCount();
        List<Bird> birds = birdRepository.findBirdsPaged(pageable.getPageSize(), pageable.getOffset());
        return new PageImpl<>(birds, pageable, total);
    }

}
