package com.ford.connections;

import java.sql.*;

public class SampleConnection {
    public static void main(String[] args) {
        Connection con;
        String url = "jdbc:mysql://localhost:3306/ford";
        String user = "root";
        String password = "asdf";
        Statement stmt;

        try{
            //Step 1: Load the Driver
            //for MS SQL : com.microsoft.sqlserver.jdbc.SQLServerDriver
            //For MySQL:
            Class.forName("com.mysql.cj.jdbc.Driver");  // class.forname and con = step is different in the case of MS SQL;Rest all are the same
            con = DriverManager.getConnection(url,user,password);
            stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("select * from employee1");
            System.out.println("Employee Details are ....");
            while(rs.next())
            {
                System.out.println("Employee Id:" + rs.getString(1) + " Employee Name: " + rs.getString(2)  );
            }

        }
        catch(ClassNotFoundException ce){
            ce.printStackTrace();
        }
        catch(SQLException se){
            se.printStackTrace();
        }
    }

}
