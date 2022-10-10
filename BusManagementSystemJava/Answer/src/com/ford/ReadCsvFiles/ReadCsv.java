package com.ford.ReadCsvFiles;

import com.ford.model.BusDetails;
import com.ford.model.BusMaster;
import com.ford.model.PassengerInfo;
import com.ford.service.Reservation;

import java.io.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class ReadCsv {
    List<BusMaster> busMasters;
    List<BusDetails> busDetails;
    BusMaster bm;
    BusDetails bd;

    public ReadCsv(){
        busMasters  = new ArrayList<BusMaster>();
        busDetails = new ArrayList<BusDetails>();


    }

    public List<BusDetails> readBusDetailsCsv(){
        String line1 = "";
        String splitBy1 = ",";

        File file2 = new File("C:\\Java_ford_Training\\FordFiles\\Bus-Details.csv");
        try{
            BufferedReader br = new BufferedReader(new FileReader(file2));
            while((line1 = br.readLine()) != null){
                bd = new BusDetails();

                String[] e1 = line1.split(splitBy1);

                bd.setBusNo(e1[0]);
                bd.setTotalSeats(e1[1]);
                bd.setTotalCommonSeats(e1[2]);
                bd.setTotalWomenSeats(e1[3]);
                bd.setTotalSpecialSeats(e1[4]);
                bd.setAvailableCommonSeats(e1[5]);
                bd.setAvailableWomenSeats(e1[6]);
                bd.setAvailableSpecialSeats(e1[7]);

                busDetails.add(bd);
            }
        }
        catch(IOException io){
            io.printStackTrace();
        }

        return busDetails;
    }


    public List<BusMaster> readBusMasterCsv(){
        String line = "";
        String splitBy = ",";

        File file1 = new File("C:\\Java_ford_Training\\FordFiles\\Bus-Master.csv");
        try{
            BufferedReader br = new BufferedReader(new FileReader(file1));
            while((line = br.readLine()) != null){
                bm = new BusMaster();

                String[] e = line.split(splitBy);

                bm.setBusNo(e[0]);
                bm.setFrom(e[1]);
                bm.setTo(e[2]);
                bm.setStartingDate(e[3]);
                bm.setStartingTime(e[4]);
                bm.setJourneyDuration(e[5]);
                bm.setTotalStops(e[6]);
                bm.setType(e[7]);

               // System.out.println(bm);

                busMasters.add(bm);

            }
        }
        catch(IOException io){
            io.printStackTrace();
        }

       return busMasters;
    }




    public static void main(String[] args) {
        ReadCsv readCsv = new ReadCsv();
        List<BusMaster> bm = readCsv.readBusMasterCsv();
        List<BusDetails> bd = readCsv.readBusDetailsCsv();
        System.out.println(bd);
        System.out.println(bm);

        PassengerInfo passengerInfo = new PassengerInfo(001,"Shashikar",'m',22,"Chennai",
                "Trichy","02-Aug-2019",false,false );

        //System.out.println(passengerInfo);



        BusMaster busMaster1 = bm.get(2);
        BusDetails busDetails1 = bd.get(2);
        //System.out.println(busMaster1);

        Reservation reservation = new Reservation(12,passengerInfo,busMaster1,busDetails1);
        //System.out.println(reservation);




    }


}
