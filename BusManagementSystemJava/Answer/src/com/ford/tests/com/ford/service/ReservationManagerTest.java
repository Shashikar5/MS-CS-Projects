package com.ford.service;

import com.ford.model.BusDetails;
import com.ford.model.BusMaster;
import com.ford.model.PassengerInfo;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ReservationManagerTest {
    ReservationManager reservationManager;
    PassengerInfo passengerInfo,passengerInfo1,passengerInfo2,passengerInfo3,passengerInfo4,passengerInfo5;
    @BeforeEach
    void setUp(){
        reservationManager = new ReservationManager();
        passengerInfo = new PassengerInfo(7,"Shashikar",'m',22,"Chennai",
                "Trichy","04-Aug-22",false,false);

        passengerInfo1 = new PassengerInfo(8,"Sophia",'w',21,"Madurai",
                "Bangalore","02-Aug-22",false,true);

        passengerInfo2 = new PassengerInfo(9,"Sam",'m',20,"Madurai",
                "Bangalore","02-Aug-22",true,false);

        passengerInfo3 = new PassengerInfo(10,"Steve",'m',21,"Madurai",
                "Bangalore","02-Aug-22",true,false);

        passengerInfo4 = new PassengerInfo(11,"Nair",'m',25,"Madurai",
                "Bangalore","02-Aug-22",true,false);

        passengerInfo5 = new PassengerInfo(12,"Tyson",'m',45,"Doha",
                "Wakra","09-Aug-22",true,false);

    }

    @AfterEach
    void tearDown(){

    }

    @Test
    void bookCommonTicket() {
        Reservation reservation;
        BusMaster busMaster = new BusMaster("201A","Chennai","Trichy","04-Aug-22","20:45",
                "400 minutes","5","Normal");
        BusDetails busDetails = new BusDetails("201A","40",
                "35","5","0","35","5","0");
        reservation = reservationManager.bookTicket(passengerInfo);
        Reservation reservation1 = new Reservation(1,passengerInfo,busMaster,busDetails);
        System.out.println(reservation1);
        System.out.println(reservation);
        assertTrue(reservation1.getPassengerInfo().equals(reservation.getPassengerInfo()));
    }

    @Test
    void bookWomenTicket(){
        Reservation reservation;
        BusMaster bm = new BusMaster("146R","Madurai","Bangalore","02-Aug-22","14:00",
                "600 minutes","19","Normal");
        BusDetails bd = new BusDetails("146R","32","26","4","2",
                "26","3","2");
        Reservation reservation1 = new Reservation(1,passengerInfo1,bm,bd);
        reservation = reservationManager.bookTicket(passengerInfo1);
        System.out.println(reservation);
        System.out.println(reservation1);
        assertTrue(reservation1.getPassengerInfo().equals(reservation.getPassengerInfo()));
    }

    @Test
    public void bookSpecialSeats(){
        Reservation reservation;
        BusMaster bm = new BusMaster("146R","Madurai","Bangalore","02-Aug-22","14:00",
                "600 minutes","19","Normal");
        BusDetails bd = new BusDetails("146R","32","26","4","2",
                "26","4","1");
        Reservation reservation1 = new Reservation(1,passengerInfo2,bm,bd);
        reservation = reservationManager.bookTicket(passengerInfo2);
        System.out.println(reservation1);
        System.out.println(reservation);
        assertTrue(reservation1.getPassengerInfo().equals(reservation.getPassengerInfo()));
    }

    @Test
    public void bookingDeniedDueToNoSeats(){
        Reservation reservation1,reservation2,reservation3;

        reservation1 = reservationManager.bookTicket(passengerInfo2);
        reservation2 = reservationManager.bookTicket(passengerInfo3);
        reservation3 = reservationManager.bookTicket(passengerInfo4);
        System.out.println(reservation1);
        System.out.println(reservation2);
        System.out.println(reservation3);
        assertEquals(null,reservation3);
    }

    @Test
    public void bookedDeniedDueToNoBuses(){
        Reservation reservation;
        reservation = reservationManager.bookTicket(passengerInfo5);
        System.out.println(reservation);
        assertEquals(null,reservation);
    }

    @Test
    public void getReservationBasedOnBusNo(){
        Reservation reservation1,reservation2,reservation3;
        String busNo = "146R";
        reservation1 = reservationManager.bookTicket(passengerInfo2);
        reservation2 = reservationManager.bookTicket(passengerInfo3);
        reservation3 = reservationManager.bookTicket(passengerInfo);

        assertFalse(reservationManager.getAllReservationForBus(busNo));//Refer reservation manager --> false means reservation exists
    }

    @Test
    public void getNoReservationBasedOnBusNo(){
        Reservation reservation1,reservation2,reservation3;
        String busNo = "180A";
        reservation1 = reservationManager.bookTicket(passengerInfo2);
        reservation2 = reservationManager.bookTicket(passengerInfo3);
        reservation3 = reservationManager.bookTicket(passengerInfo);

        assertTrue(reservationManager.getAllReservationForBus(busNo));//Refer reservation manager --> true means no reservations exists
    }

    @Test
    public void cancelReservationSuccess(){
        Reservation reservation1,reservation2,reservation3;

        reservation1 = reservationManager.bookTicket(passengerInfo);
        reservation2 = reservationManager.bookTicket(passengerInfo2);
        reservation3 = reservationManager.bookTicket(passengerInfo3);

        assertTrue(reservationManager.cancelTicket(reservation1));
    }

    @Test
    public void cancelReservationFailure(){
        Reservation reservation1,reservation2,reservation3;

        reservation1 = reservationManager.bookTicket(passengerInfo);
        reservation2 = reservationManager.bookTicket(passengerInfo2);

        BusMaster bm = new BusMaster("146R","Madurai","Bangalore","02-Aug-22","14:00",
                "600 minutes","19","Normal");
        BusDetails bd = new BusDetails("146R","32","26","4","2",
                "26","4","1");

        reservation3 = new Reservation(100,passengerInfo5,bm,bd);

        assertFalse(reservationManager.cancelTicket(reservation3));
    }






}