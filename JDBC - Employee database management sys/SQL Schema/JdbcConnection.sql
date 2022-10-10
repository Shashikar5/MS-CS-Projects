create database fordNew;
use fordNew;
create table employee(
empId varchar(10) primary key,
empName varchar(40),
empAddress varchar(40),
empPhone float,
DOB date,
empSalary float
);
insert into employee 
values('E001','Shashikar','FF3','7548897','1999-07-05',65000.0);
insert into employee 
values('E002','Sasmitha','FF4','75423897','2003-02-05',55000.0);
insert into employee 
values('E003','Usha','FF5','7548898','1972-07-08',70000.0);
select * from employee;
select max(empId) from employee;

create table supplier(
supplierId varchar(20) primary key,
supplierName varchar(50),
supplierAddress varchar(100),
supplyAmount int
);

insert into supplier values('S001','Ram','Tamil Nadu',20000);
insert into supplier values('S002','Sam','Telangana',30000);
insert into supplier values('S003','Sara','Mumbai',40000);

select * from supplier;

/*--------------------Procedure - Set of SQL statements--------------------------------------*/
delimiter $$
use fordNew $$
create procedure CallProc100()
begin
declare num1, num2 , total int;
set num1 = 100;
set num2 = 200;
set total = num1 + num2;
select total as 'Total is';
end $$

call CallProc100();

/*delimiter $$
use fordNew $$
create procedure CallProc101(in num1 int,in num2 int)
begin
declare total int;
declare resultStr varchar(80);
set total = num1 + num2;
if total >= 50 and total < 60 then set resultStr = 'Passed';
else if total >=60 and total < 70 then set resultStr = 'Very good First Class';
else
set resultStr = 'sorry try again';
end if;
select total as 'Your Total is';
select resultStr as 'Your result is';
end $$

call CallProc101(30,45);

delimiter $$
use fordNew $$
create procedure CallProc103(in num1 int,in num2 int,out total int,out resultStr varchar(50))
begin
declare total int;
declare resultStr varchar(80);
set total = num1 + num2;
if total >= 50 and total < 60 then set resultStr = 'Passed';
else if total >=60 and total < 70 then set resultStr = 'Very good First Class';
else
set resultStr = 'sorry try again';
end if;
end $$ 

call CallProc103(35,36,@tot,@res);
select @tot as 'Final Total';
select @res as 'Final Result';  */

delimiter $$
use fordnew $$
create procedure fetchEmployeeDetailsByIdProc1()
begin
select * from employee;
end $$

call fetchEmployeeDetailsByIdProc1();

delimiter $$
use fordnew $$
create procedure fetchEmployeeDetailsByIdProc(in employeeId varchar(20))
begin
select * from employee where empId = employeeId;
end $$

call fetchEmployeeDetailsByIdProc('E001');

delimiter $$
use fordNew $$
create procedure fetchEmployeeDetailsProc1(in employeeId varchar(20),out employeeName varchar(50),out employeeAddress varchar(50),out employeeSalary float)
begin
set employeeName = (select empName from employee where empId = employeeId);
set employeeAddress = (select empAddress from employee where empId = employeeId);
set employeeSalary = (select empSalary from employee where empId = employeeId);
select employeeName as 'Employee Name';
select employeeAddress as 'Employee Address';
select employeeSalary as 'Employee Salary';
end $$

call fetchEmployeeDetailsProc1('E001',@empName,@empAddr,@empSal);



/*-----------------------------------------------------------*/

