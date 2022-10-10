package com.ford.dao;

import com.ford.model.Employee;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class EmployeeDaoTest {
    EmployeeDao employeeDao;
    List<Employee> employeeList;
    @BeforeEach
    void setUp() throws Exception{
        employeeDao = new EmployeeDao();
        employeeList = new ArrayList<Employee>();
        /*
        E001	ShashikarA	FF50	75399900	1999-07-05	60000
        E002	Sasmitha	FF4	75423900	2003-02-05	55000
         E003	Usha	FF5	7548900	1972-07-08	70000
         E005	Jeyam	EF5	753252	1959-08-02	200000
		*/

       /* employeeList.add(new Employee("E001","ShashikarA","FF50","75399900",stringToDateConverter("1999-07-05"),60000));
        employeeList.add(new Employee("E002","Sasmitha","FF4","75423900",stringToDateConverter("2003-02-05"),55000));
        employeeList.add(new Employee("E003","Usha","FF5","7548900",stringToDateConverter("1972-07-08"),70000));
        employeeList.add(new Employee("E005","Jeyam","EF5","753252",stringToDateConverter("1959-08-02"),200000)); */
    }

    @AfterEach
    void tearDown() throws Exception{
    }

    public static java.util.Date stringToDateConverter(String stringDate) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dateFormat.setLenient(false);
        try {
            return dateFormat.parse(stringDate);
        } catch (ParseException pe) {
            return null;
        }
    }

//    @Test
//    public void shouldReturnAllEmployees(){
//        assertEquals(employeeList,employeeDao.getAllEmployees());
//    }


    @Test
    void insertEmployee() {
        String s = "1959-08-02";
        Employee e = new Employee();
        e.setEmployeeId("E005");
        e.setEmpName("Jeyam");
        e.setEmployeeAddress("EF5");
        e.setEmpPhone("753252");
        e.setDateOfJoining(stringToDateConverter(s));
        e.setEmployeeSalary(200000);
        assertTrue(employeeDao.insertEmployee(e));
    }

    @Test
    public void shouldGetEmpById(){
        String s = "1959-08-02";
        Employee e = new Employee("E005","Jeyam","EF5","753252.0",stringToDateConverter(s),200000);
        assertEquals(e,employeeDao.getEmployeeById("E005"));
    }

    @Test
    void deleteEmployeeById() {
        String empId = "E005";
        Employee employee = new Employee();
        assertTrue(employeeDao.deleteEmployeeById(employee,empId));
    }

    @Test
    void updateEmployee() {
        /* insert into employee values('E002','Sasmitha','FF4','75423897','2003-02-05',55000.0); */
        Employee e = new Employee();
        String empId = "E002";
        String s = "1999-08-02";

        e.setEmpName("Kapil");
        e.setEmployeeAddress("EE5");
        e.setEmpPhone("753256");
        e.setDateOfJoining(stringToDateConverter(s));
        e.setEmployeeSalary(100000);

        assertTrue(employeeDao.updateEmployee(e,empId));
    }
}