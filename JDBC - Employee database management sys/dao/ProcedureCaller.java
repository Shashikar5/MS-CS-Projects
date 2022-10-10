package com.ford.dao;

import com.ford.connections.MyConnection;
import com.ford.model.Employee;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProcedureCaller {

    MyConnection myCon;
    Connection con;
    Statement stmt;
    CallableStatement cstmt;
    ResultSet rs;

    public ProcedureCaller(){
        myCon = new MyConnection();
        con = myCon.getMyConnection();
    }

    public Employee fetchEmployeeDetailsByIdThruProcOutParam(String empId){
        Employee e = new Employee();
        try {
            cstmt = con.prepareCall("call fetchEmployeeDetailsProc1(?,?,?,?)");
            cstmt.setString(1,empId);

            cstmt.registerOutParameter(2,Types.VARCHAR);
            cstmt.registerOutParameter(3, Types.VARCHAR);
            cstmt.registerOutParameter(4,Types.FLOAT);

            cstmt.executeUpdate();

            String employeeName = cstmt.getString(2);
            String empAddress = cstmt.getString(3);
            float empSal = cstmt.getFloat(4);

            e = new Employee(empId,employeeName,empAddress, empSal);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
          return e;
    }
    public List<Employee> fetchEmployeeProc(){
        List<Employee> employeeList = new ArrayList<Employee>();
        try {
            cstmt = con.prepareCall("call fetchEmployeeDetailsByIdProc1()");  // Callable statements are used for SQL Procedure calls
            rs = cstmt.executeQuery();
            while(rs.next()) {
                Employee e = new Employee();
                e.setEmployeeId(rs.getString(1));
                e.setEmpName(rs.getString(2));
                e.setEmployeeAddress(rs.getString(3));
                e.setEmpPhone(rs.getString(4));
                e.setDateOfJoining(rs.getDate(5));
                e.setEmployeeSalary(rs.getFloat(6));

                employeeList.add(e);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return employeeList;
    }

    public Employee fetchEmployeeByIdProc(String empId){
        Employee emp1 = new Employee();
        try {
            cstmt  = con.prepareCall("call fetchEmployeeDetailsByIdProc(?)");
            cstmt.setString(1,empId);
            rs = cstmt.executeQuery();
            rs.next();

            emp1.setEmployeeId(rs.getString(1));
            emp1.setEmpName(rs.getString(2));
            emp1.setEmployeeAddress(rs.getString(3));
            emp1.setEmpPhone(rs.getString(4));
            emp1.setDateOfJoining(rs.getDate(5));
            emp1.setEmployeeSalary(rs.getFloat(6));

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return emp1;
    }
}
