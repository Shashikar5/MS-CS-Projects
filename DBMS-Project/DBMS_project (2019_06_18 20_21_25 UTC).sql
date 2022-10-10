create table blood_inventory ( 
blood_code int, 
stored_date date, 
expiry_date date, 
blood_grp varchar(20), 
primary key(blood_code)); 
 
create table stock( 
stk_id int, 
quantity int, 
blood_code int, 
foreign key(blood_code) references blood_inventory(blood_code), 
primary key(stk_id)); 
 
create table hospital( 
h_name varchar(15), 
h_code int, 
address varchar(20), 
ph_no int, 
primary key(h_code)); 
 
create table manager( 
emp_id int, 
ph_no int, 
address varchar(25), 
name varchar(25), 
blood_code int, 
h_code int, 
foreign key(blood_code) references blood_inventory(blood_code), 
foreign key(h_code) references hospital(h_code), 
primary key(emp_id)); 
 
create table nurses( 
name varchar(20), 
ph_no int, 
address varchar(25), 
blood_code int, 
emp_id int, 
foreign key(blood_code) references blood_inventory(blood_code), 
foreign key(emp_id) references manager(emp_id), 
primary key(name,blood_code)); 
 
create table acceptor( 
age int, 
sex varchar(10), 
id_no int, 
h_code int, 
blood_code int, 
name varchar(20), 
ph_no int, 
foreign key(blood_code) references blood_inventory(blood_code), 
foreign key(h_code) references hospital(h_code), 
primary key(id_no)); 
 
create table receptionist( 
name varchar(20), 
ph_no int, 
address varchar(25), 
r_id int, 
h_code int, 
foreign key(h_code) references hospital(h_code), 
primary key(r_id)); 
 
create table bill_payment( 
blood_quantity int, 
vouchar_no int, 
id_no int, 
h_code int, 
price int, 
foreign key(h_code) references hospital(h_code), 
foreign key(id_no) references acceptor(id_no), 
primary key(vouchar_no)); 
 
create table website( 
web_id int, 
web_password int, 
event_info varchar(20), 
primary key(web_id)); 
 
create table charity( 
ph_no int, 
ch_id int, 
c_nam varchar(20), 
web_id int, 
foreign key(web_id) references website(web_id), 
primary key(ch_id)); 
 
create table NGO( 
name varchar(20), 
ph_no int, 
address varchar(25), 
web_id int, 
org_id int, 
foreign key(web_id) references website(web_id), 
primary key(org_id)); 
 
create table blood_camp( 
name varchar(20), 
ph_no int, 
address varchar(25), 
org_id int, 
bloodCamp_date date, 
BC_id int, 
foreign key(org_id) references NGO(org_id), 
primary key(BC_id)); 
 
create table donor( 
name varchar(20), 
ph_no int, 
address varchar(25), 
age int, 
sex varchar(20), 
d_id int, 
blood_code int, 
r_id int, 
BC_id int, 
foreign key(blood_code) references blood_inventory(blood_code), 
foreign key(r_id) references receptionist(r_id), 
foreign key(BC_id) references blood_camp(BC_id), 
primary key(d_id)); 
 
create table d_health_report( 
d_id int, 
weight int, 
d_HR_id int, 
blood_pressure int, 
pulse_rate int, 
primary key(d_HR_id), 
foreign key(d_id) references donor(d_id)); 
 
create table disease( 
d_id int, 
malaria varchar(15), 
AIDS varchar(15), 
cholrea varchar(15), 
foreign key(d_id) references donor(d_id), 
primary key(d_id)); 
 
create table volunteer( 
id int, 
name varchar(20), 
ph_no int, 
address varchar(25), 
blood_code int, 
BC_id int, 
foreign key(blood_code) references blood_inventory(blood_code), 
foreign key(BC_id) references blood_camp(BC_id), 
primary key(id)); 