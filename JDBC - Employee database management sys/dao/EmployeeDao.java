package com.ford.dao;

import com.ford.connections.MyConnection;
import com.ford.model.Employee;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EmployeeDao {
    MyConnection myCon;
    Connection con;
    Statement stmt;
    PreparedStatement pstmt;
    ResultSet rs;

    public EmployeeDao(){
        myCon = new MyConnection();
        con = myCon.getMyConnection();
    }

    public String getMaxEmployeeId(){
        String maxEmpId="";
        try {
            pstmt = con.prepareStatement("select max(empId) from employee");
            rs = pstmt.executeQuery();
            rs.next();
            maxEmpId =  rs.getString(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return maxEmpId;
    }

    public List<String> getEmployeeIds(){
        List<String> empIds = new ArrayList<String>();
        try {
            stmt = con.createStatement();
            rs  = stmt.executeQuery("select empId from employee");
            while(rs.next()){
                empIds.add(rs.getString(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
       return empIds;
    }
    public static java.sql.Date utilToSqlDateConverter(java.util.Date utDate) {
        java.sql.Date sqlDate = null;
        if (utDate != null) {
            sqlDate = new java.sql.Date(utDate.getTime());
        }
        return sqlDate;
    }

    public List<Employee> getAllEmployees()
    {
        List<Employee> employees = new ArrayList<Employee>();
        try {
            stmt = con.createStatement();
            rs = stmt.executeQuery("select * from employee");
            while(rs.next()){
                Employee e = new Employee();

                e.setEmployeeId(rs.getString(1));
                e.setEmpName(rs.getString(2));
                e.setEmployeeAddress(rs.getString(3));
                e.setEmpPhone(rs.getString(4));
                e.setDateOfJoining(rs.getDate(5));
                e.setEmployeeSalary(rs.getFloat(6));

                employees.add(e);

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return employees;
    }

    public Employee getEmployeeById(String employeeId){
        Employee emp = new Employee();
        try {
            pstmt = con.prepareStatement("select * from employee where empId = ?");
            pstmt.setString(1,employeeId);
            rs = pstmt.executeQuery();
            rs.next();
            emp.setEmployeeId(rs.getString(1));
            emp.setEmpName(rs.getString(2));
            emp.setEmployeeAddress(rs.getString(3));
            emp.setEmpPhone(rs.getString(4));
            emp.setDateOfJoining(rs.getDate(5));
            emp.setEmployeeSalary(rs.getFloat(6));

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return emp;
    }
    public boolean insertEmployee(Employee employee)
    {
        boolean flag = false;
        try {
            PreparedStatement pstmt = con.prepareStatement("insert into employee values(?,?,?,?,?,?)");
            pstmt.setString(1,employee.getEmployeeId());
            pstmt.setString(2,employee.getEmpName());
            pstmt.setString(3,employee.getEmployeeAddress());
            pstmt.setString(4, employee.getEmpPhone());
            pstmt.setDate(5,utilToSqlDateConverter(employee.getDateOfJoining())); // Function for converting util to sql date
            pstmt.setFloat(6,employee.getEmployeeSalary());

            pstmt.execute();
            flag = true;
        } catch (SQLException e) {
            e.printStackTrace();
            flag = false;
        }
        return flag;
    }
    public boolean deleteEmployeeById(Employee employee,String employeeId){
        boolean flag = false;
        try {
            pstmt = con.prepareStatement("delete from employee where empId = ?");
            pstmt.setString(1,employeeId);
            pstmt.execute();
            flag = true;
        } catch (SQLException e) {
            e.printStackTrace();
            flag = false;
        }
        return flag;
    }
    public boolean updateEmployee(Employee employee,String employeeId){
        boolean flag = false;
        try {
            pstmt = con.prepareStatement("Update employee set empName = ?,empAddress = ?,empPhone = ?,DOB = ?,empSalary = ? where empId = ?");

            pstmt.setString(1,employee.getEmpName());
            pstmt.setString(2,employee.getEmployeeAddress());
            pstmt.setString(3,employee.getEmpPhone());
            pstmt.setDate(4,utilToSqlDateConverter(employee.getDateOfJoining()));
            pstmt.setFloat(5,employee.getEmployeeSalary());
            pstmt.setString(6,employee.getEmployeeId());

            pstmt.executeUpdate();
            flag = true;
        } catch (SQLException e) {
            e.printStackTrace();
            flag = false;
        }
        return flag;
    }

}
