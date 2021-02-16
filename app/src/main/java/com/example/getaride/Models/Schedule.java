package com.example.getaride.Models;

public class Schedule {
    String fullName, location, date, sTime, eTime;

    public Schedule() {
    }

    public Schedule(String fullName, String location, String date, String sTime, String eTime) {
        this.fullName = fullName;
        this.location = location;
        this.date = date;
        this.sTime = sTime;
        this.eTime = eTime;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getsTime() {
        return sTime;
    }

    public void setsTime(String sTime) {
        this.sTime = sTime;
    }

    public String geteTime() {
        return eTime;
    }

    public void seteTime(String eTime) {
        this.eTime = eTime;
    }
}
