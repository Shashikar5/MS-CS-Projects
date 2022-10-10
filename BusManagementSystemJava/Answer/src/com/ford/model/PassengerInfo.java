package com.ford.model;

import java.util.Date;

public class PassengerInfo {
    long passengerId;
    String name;
    char sex;
    int age;
    String startingPoint;
    String endingPoint;
    String travelDate;
    boolean isSpecialSeatNeeded;
    boolean isWomanOnlySeatNeeded;



    public PassengerInfo(long passengerId, String name, char sex, int age, String startingPoint, String endingPoint, String travelDate, boolean isSpecialSeatNeeded, boolean isWomanOnlySeatNeeded) {
        this.passengerId = passengerId;
        this.name = name;
        this.sex = sex;
        this.age = age;
        this.startingPoint = startingPoint;
        this.endingPoint = endingPoint;
        this.travelDate = travelDate;
        this.isSpecialSeatNeeded = isSpecialSeatNeeded;
        this.isWomanOnlySeatNeeded = isWomanOnlySeatNeeded;
    }

    public long getPassengerId() {
        return passengerId;
    }

    public void setPassengerId(long passengerId) {
        this.passengerId = passengerId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public char getSex() {
        return sex;
    }

    public void setSex(char sex) {
        this.sex = sex;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getStartingPoint() {
        return startingPoint;
    }

    public void setStartingPoint(String startingPoint) {
        this.startingPoint = startingPoint;
    }

    public String getEndingPoint() {
        return endingPoint;
    }

    public void setEndingPoint(String endingPoint) {
        this.endingPoint = endingPoint;
    }

    public String getTravelDate() {
        return travelDate;
    }

    public void setTravelDate(String travelDate) {
        this.travelDate = travelDate;
    }

    public boolean isSpecialSeatNeeded() {
        return isSpecialSeatNeeded;
    }

    public void setSpecialSeatNeeded(boolean specialSeatNeeded) {
        isSpecialSeatNeeded = specialSeatNeeded;
    }

    public boolean isWomanOnlySeatNeeded() {
        return isWomanOnlySeatNeeded;
    }

    public void setWomanOnlySeatNeeded(boolean womanOnlySeatNeeded) {
        isWomanOnlySeatNeeded = womanOnlySeatNeeded;
    }

    @Override
    public String toString() {
        return "PassengerInfo{" +
                "passengerId=" + passengerId +
                ", name='" + name + '\'' +
                ", sex=" + sex +
                ", age=" + age +
                ", startingPoint='" + startingPoint + '\'' +
                ", endingPoint='" + endingPoint + '\'' +
                ", travelDate=" + travelDate +
                ", isSpecialSeatNeeded=" + isSpecialSeatNeeded +
                ", isWomanOnlySeatNeeded=" + isWomanOnlySeatNeeded +
                '}';
    }
}
