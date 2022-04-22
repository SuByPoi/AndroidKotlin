package com.midterm.nguyenthailoc.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Staff {
    @PrimaryKey(autoGenerate = true)
    private int id;
    @ColumnInfo
    private String title;
    @ColumnInfo
    private String timeStamp;
    @ColumnInfo
    private String desc;
    @ColumnInfo
    private String lat;
    @ColumnInfo
    private String lng;
    @ColumnInfo
    private String addr;
    @ColumnInfo
    private String zip;
    @ColumnInfo
    private String e;

    public Staff(String title, String timeStamp, String desc, String lat, String lng, String addr, String zip, String e) {
        this.title = title;
        this.timeStamp = timeStamp;
        this.desc = desc;
        this.lat = lat;
        this.lng = lng;
        this.addr = addr;
        this.zip = zip;
        this.e = e;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLng() {
        return lng;
    }

    public void setLng(String lng) {
        this.lng = lng;
    }

    public String getAddr() {
        return addr;
    }

    public void setAddr(String addr) {
        this.addr = addr;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public String getE() {
        return e;
    }

    public void setE(String e) {
        this.e = e;
    }
}
