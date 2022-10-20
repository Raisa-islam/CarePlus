package com.raisa.update1;

public class Task {
    String id, title, description;
    int hour, min;
    boolean everyday, sun, mon, tues, wed ,thurs, fri,sat;

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
        return String.valueOf(hour);
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public String getMin() {
        return String.valueOf(min);
    }

    public void setMin(int min) {
        this.min = min;
    }

    public boolean isEveryday() {
        return everyday;
    }

    public void setEveryday(boolean everyday) {
        this.everyday = everyday;
    }

    public boolean isSun() {
        return sun;
    }

    public void setSun(boolean sun) {
        this.sun = sun;
    }

    public boolean isMon() {
        return mon;
    }

    public void setMon(boolean mon) {
        this.mon = mon;
    }

    public boolean isTues() {
        return tues;
    }

    public void setTues(boolean tues) {
        this.tues = tues;
    }

    public boolean isWed() {
        return wed;
    }

    public void setWed(boolean wed) {
        this.wed = wed;
    }

    public boolean isThurs() {
        return thurs;
    }

    public void setThurs(boolean thurs) {
        this.thurs = thurs;
    }

    public boolean isFri() {
        return fri;
    }
    public boolean isSat() {
        return sat;
    }

    public void setFri(boolean fri) {
        this.fri = fri;
    }
    public void setSat(boolean sat) {
        this.sat = sat;
    }


    public Task( String title, String description, int hour, int min,
                boolean everyday, boolean sun, boolean mon, boolean tues,
                boolean wed, boolean thurs, boolean fri, boolean sat) {
        //this.id = id;
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
        this.sat = sat;
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