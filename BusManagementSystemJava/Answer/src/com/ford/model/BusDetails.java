package com.ford.model;

public class BusDetails {
    String busNo;
    String totalSeats;
    String totalCommonSeats;
    String totalWomenSeats;
    String totalSpecialSeats;
    String availableCommonSeats;
    String availableWomenSeats;
    String availableSpecialSeats;

    public BusDetails(){
    }

    public BusDetails(String busNo, String totalSeats, String totalCommonSeats, String totalWomenSeats, String totalSpecialSeats, String availableCommonSeats, String availableWomenSeats, String availableSpecialSeats) {
        this.busNo = busNo;
        this.totalSeats = totalSeats;
        this.totalCommonSeats = totalCommonSeats;
        this.totalWomenSeats = totalWomenSeats;
        this.totalSpecialSeats = totalSpecialSeats;
        this.availableCommonSeats = availableCommonSeats;
        this.availableWomenSeats = availableWomenSeats;
        this.availableSpecialSeats = availableSpecialSeats;
    }

    public String getBusNo() {
        return busNo;
    }

    public void setBusNo(String busNo) {
        this.busNo = busNo;
    }

    public String getTotalSeats() {
        return totalSeats;
    }

    public void setTotalSeats(String totalSeats) {
        this.totalSeats = totalSeats;
    }

    public String getTotalCommonSeats() {
        return totalCommonSeats;
    }

    public void setTotalCommonSeats(String totalCommonSeats) {
        this.totalCommonSeats = totalCommonSeats;
    }

    public String getTotalWomenSeats() {
        return totalWomenSeats;
    }

    public void setTotalWomenSeats(String totalWomenSeats) {
        this.totalWomenSeats = totalWomenSeats;
    }

    public String getTotalSpecialSeats() {
        return totalSpecialSeats;
    }

    public void setTotalSpecialSeats(String totalSpecialSeats) {
        this.totalSpecialSeats = totalSpecialSeats;
    }

    public String getAvailableCommonSeats() {
        return availableCommonSeats;
    }

    public void setAvailableCommonSeats(String availableCommonSeats) {
        this.availableCommonSeats = availableCommonSeats;
    }

    public String getAvailableWomenSeats() {
        return availableWomenSeats;
    }

    public void setAvailableWomenSeats(String availableWomenSeats) {
        this.availableWomenSeats = availableWomenSeats;
    }

    public String getAvailableSpecialSeats() {
        return availableSpecialSeats;
    }

    public void setAvailableSpecialSeats(String availableSpecialSeats) {
        this.availableSpecialSeats = availableSpecialSeats;
    }

    @Override
    public String toString() {
        return "BusDetails{" +
                "busNo='" + busNo + '\'' +
                ", totalSeats='" + totalSeats + '\'' +
                ", totalCommonSeats='" + totalCommonSeats + '\'' +
                ", totalWomenSeats='" + totalWomenSeats + '\'' +
                ", totalSpecialSeats='" + totalSpecialSeats + '\'' +
                ", availableCommonSeats='" + availableCommonSeats + '\'' +
                ", availableWomenSeats='" + availableWomenSeats + '\'' +
                ", availableSpecialSeats='" + availableSpecialSeats + '\'' +
                '}';
    }
}
