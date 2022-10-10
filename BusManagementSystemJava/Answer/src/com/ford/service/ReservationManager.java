package com.ford.service;

import com.ford.ReadCsvFiles.ReadCsv;
import com.ford.connections.MyConnection;
import com.ford.model.BusDetails;
import com.ford.model.BusMaster;
import com.ford.model.PassengerInfo;

import java.io.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ReservationManager {

    List<BusMaster> busMasterList;
    List<BusDetails> busDetailsList;
    List<PassengerInfo> currentPassengerList;
    List<Reservation> currentReservationList;
    MyConnection myCon;
    Connection con;
    Statement stmt;
    PreparedStatement pstmt;
    ResultSet rs;
    ReadCsv readCsv;
    int seatNoCounter = 1;

    public ReservationManager(){
        busMasterList = new ArrayList<BusMaster>();
        busDetailsList = new ArrayList<BusDetails>();
        readCsv = new ReadCsv();
        busMasterList = readCsv.readBusMasterCsv();
        busDetailsList = readCsv.readBusDetailsCsv();
        currentPassengerList = new ArrayList<PassengerInfo>();
        currentReservationList = new ArrayList<Reservation>();

        currentPassengerList.add(new PassengerInfo(1,"Johnathon",'m',22,"Chennai",
                "Bangalore","01-Aug-21",false,false));
        currentPassengerList.add(new PassengerInfo(2,"Joseph",'m',21,"Chennai",
                "Bangalore","01-Aug-22",false,false));
        currentPassengerList.add(new PassengerInfo(3,"Jotaro",'m',20,"Chennai",
                "Madurai","02-Aug-22",true,false));
        currentPassengerList.add(new PassengerInfo(4,"Josuke",'m',19,"Madurai",
                "Bangalore","02-Aug-22",true,false));
        currentPassengerList.add(new PassengerInfo(5,"Giorno",'m',18,"Chennai",
                "Goa","03-Aug-22",false,false));
        currentPassengerList.add(new PassengerInfo(6,"Jolyne",'w',18,"Chennai",
                "Trichy","04-Aug-22",false,true));

        myCon = new MyConnection();
        con = myCon.getMyConnection();
    }

    public ReservationManager(List<BusMaster> busMasterList, List<PassengerInfo> currentPassengerList, List<Reservation> currentReservationList) {
        this.busMasterList = busMasterList;
        this.currentPassengerList = currentPassengerList;
        this.currentReservationList = currentReservationList;
    }

    public List<BusMaster> getBusMasterList() {
        return busMasterList;
    }

    public void setBusMasterList(List<BusMaster> busMasterList) {
        this.busMasterList = busMasterList;
    }

    public List<PassengerInfo> getCurrentPassengerList() {
        return currentPassengerList;
    }

    public void setCurrentPassengerList(List<PassengerInfo> currentPassengerList) {
        this.currentPassengerList = currentPassengerList;
    }

    public List<Reservation> getCurrentReservationList() {
        return currentReservationList;
    }

    public void setCurrentReservationList(List<Reservation> currentReservationList) {
        this.currentReservationList = currentReservationList;
    }

    @Override
    public String toString() {
        return "ReservationManager{" +
                "busMasterList=" + busMasterList +
                ", currentPassengerList=" + currentPassengerList +
                ", currentReservationList=" + currentReservationList +
                '}';
    }

    public boolean negativeBusSeats(BusDetails busDetails){
        if(convertStrToInt(busDetails.getTotalSeats())<0){
            return true;
        }else{
            return false;
        }
    }

    public boolean busNoValid(BusDetails busDetails){
        String busNo = busDetails.getBusNo();
        int i;int x=0,y=0;
        char s;
        for(i=0;i<busNo.length();i++){
            s = busNo.charAt(i);
           if(Character.isDigit(s)){
               x++;
           }
           if(!Character.isDigit(s)){
               y++;
           }
        }
        if(x==3&&y==1){
            return true;
        }else{
            return false;
        }
    }



    public void writeToErrorFile(){
        File textFile = new File("error.txt");
        boolean flag,flag1;
        try {
            PrintWriter out = new PrintWriter(textFile);
            Iterator<BusDetails> busDetailsIterator = busDetailsList.iterator();
            busDetailsIterator.next();
            while(busDetailsIterator.hasNext()){
                BusDetails bd = busDetailsIterator.next();
                flag = negativeBusSeats(bd);
                flag1 = busNoValid(bd);
                if(flag == true){
                    out.println(bd);
                }
                if(flag1 == false){
                    out.println(bd);
                }
            }
            out.close();
        }
        catch(FileNotFoundException fe){
            fe.printStackTrace();
        }
    }

    public void writeCsvDetailsToDB(){
        ReadCsv readCsv = new ReadCsv();
        busMasterList = readCsv.readBusMasterCsv();
        busDetailsList = readCsv.readBusDetailsCsv();
        try {
            int i=0;
            while(true) {
                PreparedStatement pstmt = con.prepareStatement("insert into busMaster values(?,?,?,?,?,?,?,?)");
                BusMaster bm1 = busMasterList.get(i);
                pstmt.setString(1, bm1.getBusNo());
                pstmt.setString(2, bm1.getFrom());
                pstmt.setString(3, bm1.getTo());
                pstmt.setString(4, bm1.getStartingDate());
                pstmt.setString(5, bm1.getStartingTime());
                pstmt.setString(6, bm1.getJourneyDuration());
                pstmt.setString(7, bm1.getTotalStops());
                pstmt.setString(8, bm1.getType());

                pstmt.execute();
                i++;
                if(i==10)break;
            }
        }
        catch(SQLException sqlException){
            sqlException.printStackTrace();
        }

        try {
            int i=0;
            while(true) {
                PreparedStatement pstmt = con.prepareStatement("insert into busDetails values(?,?,?,?,?,?,?,?)");
                BusDetails bm1 = busDetailsList.get(i);
                pstmt.setString(1, bm1.getBusNo());
                pstmt.setString(2, bm1.getTotalSeats());
                pstmt.setString(3, bm1.getTotalCommonSeats());
                pstmt.setString(4, bm1.getTotalWomenSeats());
                pstmt.setString(5, bm1.getTotalSpecialSeats());
                pstmt.setString(6, bm1.getAvailableCommonSeats());
                pstmt.setString(7, bm1.getAvailableWomenSeats());
                pstmt.setString(8, bm1.getAvailableSpecialSeats());

                pstmt.execute();
                i++;
                if(i==11)break;
            }
        }
        catch(SQLException sqlException){
            sqlException.printStackTrace();
        }
    }

    public void getAllBusMaster(){
        try {
            stmt = con.createStatement();
            rs = stmt.executeQuery("select * from BusMaster");
            while(rs.next()){
               BusMaster bm = new BusMaster();

                bm.setBusNo(rs.getString(0));
                bm.setFrom(rs.getString(1));
                bm.setTo(rs.getString(2));
                bm.setStartingDate(rs.getString(3));
                bm.setStartingTime(rs.getString(4));
                bm.setJourneyDuration(rs.getString(5));
                bm.setTotalStops(rs.getString(6));
                bm.setType(rs.getString(7));

                busMasterList.add(bm);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println(busMasterList);
    }

    public void getAllBusDetails(){
        try {
            stmt = con.createStatement();
            rs = stmt.executeQuery("select * from BusDetails");
            while(rs.next()){
                BusDetails bd = new BusDetails();

                bd.setBusNo(rs.getString(0));
                bd.setTotalSeats(rs.getString(1));
                bd.setTotalCommonSeats(rs.getString(2));
                bd.setTotalWomenSeats(rs.getString(3));
                bd.setTotalSpecialSeats(rs.getString(4));
                bd.setAvailableCommonSeats(rs.getString(5));
                bd.setAvailableWomenSeats(rs.getString(6));
                bd.setAvailableSpecialSeats(rs.getString(7));

                busDetailsList.add(bd);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println(busDetailsList);
    }

    public int convertStrToInt(String s){
        int x;
        x = Integer.parseInt(s);
        return x;
    }

    public String convertIntToStr(int x){
        String s;
        s = Integer.toString(x);
        return s;
    }

 public Reservation bookTicket(PassengerInfo passengerInfo){
        boolean flag = false;

     Iterator<PassengerInfo> currentPassengerListItr = currentPassengerList.iterator();
     String thisPassengerName = passengerInfo.getName();
     while(currentPassengerListItr.hasNext()){
         PassengerInfo currentPassenger = currentPassengerListItr.next();
         String currentPassengerName = currentPassenger.getName();
         if(thisPassengerName.equals(currentPassengerName)){
           flag = true;
         }
     }
     if(flag == false) {
         currentPassengerList.add(passengerInfo);//Adding the passenger info to the current passenger list

         boolean flag2 = false;
         Iterator<BusMaster> busMasterIterator = busMasterList.iterator();
         String passengerFrom = passengerInfo.getStartingPoint();
         String passengerTo = passengerInfo.getEndingPoint();
         String passengerDate = passengerInfo.getTravelDate();
         boolean passengerSpecialSeat = passengerInfo.isSpecialSeatNeeded();
         boolean passengerWomenSeat = passengerInfo.isWomanOnlySeatNeeded();
         BusMaster bmSuccess = new BusMaster();

         while (busMasterIterator.hasNext()) {
             BusMaster bm = busMasterIterator.next();
             String currentFrom = bm.getFrom();
             String currentTo = bm.getTo();
             String currentDate = bm.getStartingDate();

             if (passengerFrom.equals(currentFrom) && passengerTo.equals(currentTo)
                     && passengerDate.equals(currentDate)) {
                 flag2 = true;
                 bmSuccess = bm;
             }
         }

         boolean flag3 = false;
         if (flag2 == true) { // Checking in bus details list for seats if true and updating them
             String busNo = bmSuccess.getBusNo();
             BusDetails bdSuccess = new BusDetails();
             Iterator<BusDetails> busDetailsIterator = busDetailsList.iterator();
             while (busDetailsIterator.hasNext()) {
                 BusDetails bd = busDetailsIterator.next();

                 if (bd.getBusNo().equals(busNo)) {
                     String currentCommonSeats = bd.getAvailableCommonSeats();
                     String currentWomenSeats = bd.getAvailableWomenSeats();
                     String currentSpecialSeats = bd.getAvailableSpecialSeats();

                     if (passengerSpecialSeat == false && passengerWomenSeat == false) {
                         int x = convertStrToInt(currentCommonSeats);
                         if(x!=0){
                             x -= 1;
                         }else{
                             flag3 = true;break;
                         }
                         currentCommonSeats = convertIntToStr(x);
                         bd.setAvailableCommonSeats(currentCommonSeats);
                     } else if (passengerSpecialSeat == true && passengerWomenSeat == false) {
                         int x = convertStrToInt(currentSpecialSeats);
                         if(x!=0){
                             x -= 1;
                         }else{
                             flag3 = true;break;
                         }
                         currentSpecialSeats = convertIntToStr(x);
                         bd.setAvailableSpecialSeats(currentSpecialSeats);
                     } else if (passengerSpecialSeat == false && passengerWomenSeat == true) {
                         int x = convertStrToInt(currentWomenSeats);
                         if(x!=0){
                             x -= 1;
                         }else{
                             flag3 = true;break;
                         }
                         currentWomenSeats = convertIntToStr(x);
                         bd.setAvailableWomenSeats(currentWomenSeats);
                     }
                     bdSuccess = bd;
                 }
             }
             if(flag3 == false){
                 Reservation reservation = new Reservation(seatNoCounter,passengerInfo,bmSuccess,bdSuccess);
                 currentReservationList.add(reservation);
                 seatNoCounter += 1;
                 return reservation;
             }else{
                 System.out.println("Out of seats....Sorry");
                 return null;
             }
         } else {
             System.out.println("Starting , ending and starting date is not there in the list");
             return null;
         }
     } else {
         System.out.println("ur name already present");
         return null;
     }
 }

 public void writeToCsvFile(){
     Reservation reservation1,reservation2,reservation3,reservation4;
     PassengerInfo passengerInfo1,passengerInfo2,passengerInfo3,passengerInfo4;

     passengerInfo1 = new PassengerInfo(8,"Renuka P",'f',35,"Chennai",
             "Bangalore","01-Aug-22",false,true);

     passengerInfo2 = new PassengerInfo(9,"Sam",'m',20,"Madurai",
             "Bangalore","02-Aug-22",false,false);

     passengerInfo3 = new PassengerInfo(10,"Steve",'m',21,"Madurai",
             "Bangalore","02-Aug-22",true,false);

     passengerInfo4 = new PassengerInfo(11,"Nair",'m',25,"Madurai",
             "Bangalore","02-Aug-22",true,false);

     reservation1 = bookTicket(passengerInfo1);
     reservation2 = bookTicket(passengerInfo2);
     reservation3 = bookTicket(passengerInfo3);
     reservation4 = bookTicket(passengerInfo4);

     File csvFile = new File("reservation.csv");
     try {
         PrintWriter out = new PrintWriter(csvFile);
         for(Reservation reservation:currentReservationList){
             out.println(reservation);
         }
         out.close();
     }
     catch(FileNotFoundException fe){
         fe.printStackTrace();
     }
    }

    public boolean getAllReservationForBus(String busNo){
        boolean flag = true; // reservations not there
        Iterator<Reservation> reservationIterator = currentReservationList.iterator();
        Reservation reservation;
        while(reservationIterator.hasNext()){
            reservation = reservationIterator.next();
            if(reservation.getBusMaster().getBusNo().equals(busNo)){
                System.out.println(reservation);
                flag = false; // reservations are there
            }
        }
        return flag;
    }

    public void suggestAlternatePlan(PassengerInfo passengerInfo){
        List<BusMaster> passengerBusMasterList = new ArrayList<BusMaster>();
        String passengerStartingPoint = passengerInfo.getStartingPoint();
        String passengerEndingPoint = passengerInfo.getEndingPoint();

        Iterator<BusMaster> busMasterIterator = busMasterList.iterator();
        while(busMasterIterator.hasNext()){
            BusMaster bm = busMasterIterator.next();
            if(passengerStartingPoint.equals(bm.getFrom()) && passengerEndingPoint.equals(bm.getTo())){
                passengerBusMasterList.add(bm);
            }
        }

        Iterator<BusMaster> busMasterIterator1 = busMasterList.iterator();
        List<BusMaster> b1 = new ArrayList<BusMaster>();
        List<BusMaster> b2 = new ArrayList<BusMaster>();
        while(busMasterIterator1.hasNext()){
            BusMaster bm1 = busMasterIterator1.next();
            if(passengerStartingPoint.equals(bm1.getFrom()) && !passengerEndingPoint.equals(bm1.getTo())){
                b1.add(bm1);
            }
            if(!passengerStartingPoint.equals(bm1.getFrom()) && passengerEndingPoint.equals(bm1.getTo())){
                b2.add(bm1);
            }
        }

        for(BusMaster b1Item : b1){
            for(BusMaster b2Item : b2){
                if(b1Item.getTo().equals(b2Item.getFrom())){
                    passengerBusMasterList.add(b1Item);
                    passengerBusMasterList.add(b2Item);
                }
            }
        }

        System.out.println(passengerBusMasterList);
    }

    public boolean cancelTicket(Reservation reservation){
           boolean flag = false;
           Iterator<Reservation> reservationIterator = currentReservationList.iterator();
           while(reservationIterator.hasNext()){
              Reservation reservation1 = reservationIterator.next();
               if(reservation.getPassengerInfo().getName().equals(reservation1.getPassengerInfo().getName())){
                   flag = true;
                   if(reservation.getPassengerInfo().isSpecialSeatNeeded() == false &&
                           reservation.getPassengerInfo().isWomanOnlySeatNeeded() == false){
                       int x = convertStrToInt(reservation1.getBusDetails().getAvailableCommonSeats());
                       x+=1;
                       String s = convertIntToStr(x);
                       for(BusDetails bd:busDetailsList){
                           if(bd == reservation.getBusDetails()){
                               bd.setAvailableCommonSeats(s);
                           }
                       }

                   }  else if(reservation.getPassengerInfo().isSpecialSeatNeeded() == true &&
                           reservation.getPassengerInfo().isWomanOnlySeatNeeded() == false){
                       int x = convertStrToInt(reservation.getBusDetails().getAvailableSpecialSeats());
                       x+=1;
                       String s = convertIntToStr(x);
                       for(BusDetails bd:busDetailsList){
                           if(bd == reservation.getBusDetails()){
                               bd.setAvailableSpecialSeats(s);
                           }
                       }

                   }else if(reservation.getPassengerInfo().isSpecialSeatNeeded() == false &&
                           reservation.getPassengerInfo().isWomanOnlySeatNeeded() == true){
                       int x = convertStrToInt(reservation.getBusDetails().getAvailableWomenSeats());
                       x+=1;
                       String s = convertIntToStr(x);
                       for(BusDetails bd:busDetailsList){
                           if(bd == reservation.getBusDetails()){
                               bd.setAvailableWomenSeats(s);
                           }
                       }
                   }
                  // System.out.println(reservation);
               }
           }
           if(flag == true){
               currentPassengerList.remove(reservation.getPassengerInfo());
               currentReservationList.remove(reservation);
           }
           return  flag;
    }

    public static void main(String[] args) {
        ReservationManager reservationManager = new ReservationManager();
      //  reservationManager.writeToCsvFile();
        PassengerInfo passengerInfo1 = new PassengerInfo(8,"Renuka P",'f',35,"Chennai",
                "Madurai","02-Aug-22",false,true);
      //  reservationManager.suggestAlternatePlan(passengerInfo1);

        Reservation reservation = reservationManager.bookTicket(passengerInfo1);
        //System.out.println(reservation);

        //reservationManager.cancelTicket(reservation);

        BusMaster bm = new BusMaster("14RR","Madurai","Bangalore","02-Aug-22","14:00",
                "600 minutes","19","Normal");


        //reservationManager.writeToErrorFile();
    }
}
