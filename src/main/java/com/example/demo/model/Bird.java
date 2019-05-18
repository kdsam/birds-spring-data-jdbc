package com.example.demo.model;

import org.springframework.data.annotation.Id;

public class Bird {

    @Id
    private Long id;
    private String name;
    private String breed;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Bird(String name, String breed){
        this.name = name;
        this.breed = breed;
    }

    public String getBreed() {
        return breed;
    }

    public void setBreed(String breed) {
        this.breed = breed;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
