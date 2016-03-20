package com.meeple.cloud.hivernage.view.object;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MyCalendar implements Comparable<MyCalendar>{

	private String calendar_id;
	private String title, description;
	private Date startDate, endDate;
	
	public static SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy - HH:mm");
	
	public MyCalendar() {}

	public String getCalendar_id() {
		return calendar_id;
	}

	public void setCalendar_id(String calendar_id) {
		this.calendar_id = calendar_id;
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

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	@Override
	public int compareTo(MyCalendar another) {
		return getStartDate().compareTo(another.getStartDate());
	}

	public String getStrStartDate() {
		return MyCalendar.formatter.format(startDate);
	}

	public String getStrEndDate() {
		return MyCalendar.formatter.format(endDate);
	}
	
	public boolean isSameDay() {
		return startDate.equals(endDate);
	}
	
}
