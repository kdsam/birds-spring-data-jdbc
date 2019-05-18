package com.example.demo.repository;

import com.example.demo.model.Bird;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BirdRepository extends CrudRepository<Bird, Long>, BirdRepositoryCustom<Bird> {

    @Query("SELECT count(1) from bird")
    Long fetchCount();

    @Query("select * from bird limit :pageSize offset :offset")
    List<Bird> findBirdsPaged(@Param("pageSize") Integer pageSize, @Param("offset") Long offset);
}
