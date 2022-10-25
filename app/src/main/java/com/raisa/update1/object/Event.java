package com.raisa.update1.object;

public class Event {
    String id, title, description, hour, min, dd, mm, yyyy, day;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getHour() {
        return hour;
    }

    public void setHour(String hour) {
        this.hour = hour;
    }

    public String getMin() {
        return min;
    }

    public void setMin(String min) {
        this.min = min;
    }

    public String getDd() {
        return dd;
    }

    public void setDd(String dd) {
        this.dd = dd;
    }

    public String getMm() {
        return mm;
    }

    public void setMm(String mm) {
        this.mm = mm;
    }

    public String getYyyy() {
        return yyyy;
    }

    public void setYyyy(String yyyy) {
        this.yyyy = yyyy;
    }

    public Event(String id, String title, String description, String hour, String min, String dd, String mm, String yyyy) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.hour = hour;
        this.min = min;
        this.dd = dd;
        this.mm = mm;
        this.yyyy = yyyy;

    }

    public Event(){

    }
}
