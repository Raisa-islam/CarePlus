package com.raisa.update1;

public class Task {
    String id, title, description, hour, min, everyday, sun, mon, tues, wed ,thurs, fri,sat;
    public Task(){

    }

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

    public String getEveryday() {
        return everyday;
    }

    public void setEveryday(String everyday) {
        this.everyday = everyday;
    }

    public String getSun() {
        return sun;
    }

    public void setSun(String sun) {
        this.sun = sun;
    }

    public String getMon() {
        return mon;
    }

    public void setMon(String mon) {
        this.mon = mon;
    }

    public String getTues() {
        return tues;
    }

    public void setTues(String tues) {
        this.tues = tues;
    }

    public String getWed() {
        return wed;
    }

    public void setWed(String wed) {
        this.wed = wed;
    }

    public String getThurs() {
        return thurs;
    }

    public void setThurs(String thurs) {
        this.thurs = thurs;
    }

    public String getFri() {
        return fri;
    }

    public void setFri(String fri) {
        this.fri = fri;
    }

    public String getSat() {
        return sat;
    }

    public void setSat(String sat) {
        this.sat = sat;
    }

    public Task(String title, String description, String hour, String min,
                String everyday, String sun, String mon, String tues, String wed, String thurs, String fri, String sat) {

        this.title = title;
        this.description = description;
        this.hour = hour;
        this.min = min;
        this.everyday = everyday;
        this.sun = sun;
        this.mon = mon;
        this.tues = tues;
        this.wed = wed;
        this.thurs = thurs;
        this.fri = fri;
        this.sat = this.sat;
    }
}
// public String get_day(){
//        String m = "";
//        if(everyday)
//        {
//            m = "Everyday";
//            return m;
//        }
//        if (sun)
//        {
//            String n = "S ";
//            m += n;
//        }
//        if (mon)
//        {
//            String n = "M ";
//            m += n;
//        }
//        if (tues)
//        {
//            String n = "T ";
//            m += n;
//        }
//        if (wed)
//        {
//            String n = "W ";
//            m += n;
//        }
//        if (thurs)
//        {
//            String n = "T ";
//            m += n;
//        }
//        if (fri)
//        {
//            String n = "F ";
//            m += n;
//        }
//        if (sat)
//        {
//            String n = "S ";
//            m += n;
//        }
//
//        return m;
//    }