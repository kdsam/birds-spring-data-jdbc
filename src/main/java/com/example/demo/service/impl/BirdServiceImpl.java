package com.example.demo.service.impl;

import com.example.demo.model.Bird;
import com.example.demo.repository.BirdRepository;
import com.example.demo.service.BirdService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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
}
