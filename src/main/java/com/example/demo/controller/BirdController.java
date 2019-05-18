package com.example.demo.controller;

import com.example.demo.model.Bird;
import com.example.demo.service.BirdService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/birds")
public class BirdController {

    @Autowired
    private BirdService birdService;

    @GetMapping
    public List<Bird> listBirds(){
        return birdService.findAllBirds();
    }

    @GetMapping("/paged")
    public Page<Bird> listBirdsPaged(Pageable pageable){
        return birdService.findAllBirdsPaged(pageable);
    }

    @GetMapping("/breed")
    public List<Bird> listBirdsWithBreed(String breed){
        return birdService.findAllBirdsWithPredicate(breed);
    }
}
