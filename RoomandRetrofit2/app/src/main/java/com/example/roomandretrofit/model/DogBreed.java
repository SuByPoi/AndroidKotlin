package com.example.roomandretrofit.model;

public class DogBreed {
    private int id;
    private String life_span;
    private String name;
    private String origin;
    private String temperament;
    private Weight weight;
    private String url;

    public DogBreed(int id, String life_span, String name, String origin, String temperament, Weight weight, String url) {
        this.id = id;
        this.life_span = life_span;
        this.name = name;
        this.origin = origin;
        this.temperament = temperament;
        this.weight = weight;
        this.url = url;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLife_span() {
        return life_span;
    }

    public void setLife_span(String life_span) {
        this.life_span = life_span;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public String getTemperament() {
        return temperament;
    }

    public void setTemperament(String temperament) {
        this.temperament = temperament;
    }

    public Weight getWeight() {
        return weight;
    }

    public void setWeight(Weight weight) {
        this.weight = weight;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
