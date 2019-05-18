package com.example.demo.controller;

import com.example.demo.model.Bird;
import com.example.demo.response.TimedResponse;
import com.example.demo.service.BirdService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/birds")
public class BirdController {

    private static final Logger LOG  = LoggerFactory.getLogger(BirdController.class);
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

    @GetMapping("/breed/async")
    public TimedResponse<Page<Bird>> listBirdsWithBreedAsync(String breed, String reqid, Pageable pageable) throws InterruptedException {
        LOG.info("THREAD: {}", Thread.currentThread().getName());
        TimedResponse<Page<Bird>> resp = null;
        if (StringUtils.isEmpty(reqid)){
            String genId = UUID.randomUUID().toString().replaceAll("-", "");
            resp = new TimedResponse<>();
            resp.setStartThread(Thread.currentThread().getName());
            resp.setRequestId(genId);
            resp.setStatus("EXECUTING...");
            TimedResponse<Page<Bird>> finalResp = resp;
            birdService.findAllBirdsWithPredicateAsync(pageable, breed).thenApply(r -> {
                LOG.info("DONE!!!!");
                finalResp.setStatus("DONE");
                finalResp.setData(r);
                finalResp.setEndThread(Thread.currentThread().getName());
                birdService.putToCache(reqid, finalResp);
                return  r;
            })
                    .exceptionally(ex -> {LOG.info(ex.getMessage()); return null ;});
        } else {
            return birdService.getFromCache(reqid);
        }

        return resp;
    }
}
