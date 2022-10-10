package com.ford.service;

import com.ford.model.BusDetails;
import com.ford.model.BusMaster;
import com.ford.model.PassengerInfo;

public class Reservation {
    int seatNumber;
    PassengerInfo passengerInfo;
    BusMaster busMaster;
    BusDetails busDetails;


    public Reservation(int seatNumber, PassengerInfo passengerInfo, BusMaster busMaster, BusDetails busDetails) {
        this.seatNumber = seatNumber;
        this.passengerInfo = passengerInfo;
        this.busMaster = busMaster;
        this.busDetails = busDetails;
    }

    public int getSeatNumber() {
        return seatNumber;
    }

    public void setSeatNumber(int seatNumber) {
        this.seatNumber = seatNumber;
    }

    public PassengerInfo getPassengerInfo() {
        return passengerInfo;
    }

    public void setPassengerInfo(PassengerInfo passengerInfo) {
        this.passengerInfo = passengerInfo;
    }

    public BusMaster getBusMaster() {
        return busMaster;
    }

    public void setBusMaster(BusMaster busMaster) {
        this.busMaster = busMaster;
    }

    public BusDetails getBusDetails() {
        return busDetails;
    }

    public void setBusDetails(BusDetails busDetails) {
        this.busDetails = busDetails;
    }

    @Override
    public String toString() {
        return "Reservation{" +
                "seatNumber=" + seatNumber +
                ", passengerInfo=" + passengerInfo +
                ", busMaster=" + busMaster +
                ", busDetails=" + busDetails +
                '}';
    }
}
