package com.meeple.cloud.hivernage.view.object;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MyCalendar implements Comparable<MyCalendar> {
    public static SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy - HH:mm");
    private String calendar_id;
    private String camping;
    private String description;
    private Date endDate;
    private Date startDate;
    private String title;

    public String getCalendar_id() {
        return this.calendar_id;
    }

    public void setCalendar_id(String calendar_id) {
        this.calendar_id = calendar_id;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCamping() {
        return this.camping;
    }

    public void setCamping(String camping) {
        this.camping = camping;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getStartDate() {
        return this.startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return this.endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public int compareTo(MyCalendar another) {
        return getStartDate().compareTo(another.getStartDate());
    }

    public String getStrStartDate() {
        return formatter.format(this.startDate);
    }

    public String getStrEndDate() {
        return formatter.format(this.endDate);
    }


    public boolean isSameDay() {
        return this.startDate.equals(this.endDate);
    }
}
