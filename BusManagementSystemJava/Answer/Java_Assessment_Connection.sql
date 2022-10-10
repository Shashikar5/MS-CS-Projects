create database javaAssessment;
use javaAssessment;

create table busMaster(
busNo varchar(20) ,
from1 varchar(20),
to1 varchar(20),
startingDate date,
startingTime varchar(20),
journeyDuration varchar(20),
totalStops varchar(20),
type1 varchar(20)
);

create table busDetails(
busNo1 varchar(20),
totalSeats varchar(20),
totalCommonSeats varchar(20),
totalWomenSeats varchar(20),
totalSpecialSeats varchar(20),
availableCommonSeats varchar(20),
availableWomenSeats varchar(20),
availableSpecialSeats varchar(20)
);


