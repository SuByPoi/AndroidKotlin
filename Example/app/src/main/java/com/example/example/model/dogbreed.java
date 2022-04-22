package com.example.example.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class dogbreed {
    @PrimaryKey(autoGenerate = true)
    private int id;
    @ColumnInfo
    private String life_span;
    @ColumnInfo
    private String name;
    @ColumnInfo
    private String origin;
    @ColumnInfo
    private String temperament;
    @ColumnInfo
    private String url;

    public dogbreed(String life_span, String name, String origin, String temperament, String url) {
        this.life_span = life_span;
        this.name = name;
        this.origin = origin;
        this.temperament = temperament;
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

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
