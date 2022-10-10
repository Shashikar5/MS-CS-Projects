package com.ford.model;

import java.util.Date;

public class BusMaster {
    String busNo;
    String from;
    String to;
    String startingDate;
    String startingTime;
    String journeyDuration;
    String totalStops;
    String type;
    //BusDetails busDetails;

    public BusMaster(){
    }

    public BusMaster(String busNo, String from, String to, String startingDate, String startingTime, String journeyDuration, String totalStops, String type) {
        this.busNo = busNo;
        this.from = from;
        this.to = to;
        this.startingDate = startingDate;
        this.startingTime = startingTime;
        this.journeyDuration = journeyDuration;
        this.totalStops = totalStops;
        this.type = type;

    }

    public String getBusNo() {
        return busNo;
    }

    public void setBusNo(String busNo) {
        this.busNo = busNo;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getStartingDate() {
        return startingDate;
    }

    public void setStartingDate(String startingDate) {
        this.startingDate = startingDate;
    }

    public String getStartingTime() {
        return startingTime;
    }

    public void setStartingTime(String startingTime) {
        this.startingTime = startingTime;
    }

    public String getJourneyDuration() {
        return journeyDuration;
    }

    public void setJourneyDuration(String journeyDuration) {
        this.journeyDuration = journeyDuration;
    }

    public String getTotalStops() {
        return totalStops;
    }

    public void setTotalStops(String totalStops) {
        this.totalStops = totalStops;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }


    @Override
    public String toString() {
        return "BusMaster{" +
                "busNo='" + busNo + '\'' +
                ", from='" + from + '\'' +
                ", to='" + to + '\'' +
                ", startingDate='" + startingDate + '\'' +
                ", startingTime='" + startingTime + '\'' +
                ", journeyDuration='" + journeyDuration + '\'' +
                ", totalStops='" + totalStops + '\'' +
                ", type='" + type + '\'' +
                '}';
    }
}
