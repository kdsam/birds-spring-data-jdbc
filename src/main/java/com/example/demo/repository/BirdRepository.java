package com.example.demo.repository;

import com.example.demo.model.Bird;
import org.springframework.data.repository.CrudRepository;

public interface BirdRepository extends CrudRepository<Bird, Long> {
}
