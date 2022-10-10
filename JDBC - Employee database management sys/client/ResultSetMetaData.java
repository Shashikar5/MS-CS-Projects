package com.ford.client;

import java.sql.*;

public class ResultSetMetaData {

    public static void main(String[] args) {

        Connection con;
        String url;
        String user;
        String password;
        ResultSet rs;
        Statement stmt;


        url = "jdbc:mysql://localhost:3306/fordNew";
        user = "root";
        password = "asdf";

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection(url,user,password);
            stmt = con.createStatement();
            rs = stmt.executeQuery("select * from employee");
            java.sql.ResultSetMetaData rsmd = rs.getMetaData();
            DatabaseMetaData dbmd = con.getMetaData();

            System.out.println("The Result Set Meta Data Details....");
            String tableName = rsmd.getTableName(1);
            int noOfColumns = rsmd.getColumnCount();
            System.out.println("Table associated with rs is " + tableName);
            for(int i=1;i<noOfColumns;i++){
                System.out.println("Column Name : " + rsmd.getColumnName(i) + " Column Type : " + rsmd.getColumnType(i) + " Column Label : " + rsmd.getColumnLabel(i) +
                        " Column Type Name:  " + rsmd.getColumnTypeName(i));
            }

            System.out.println("The Database meta data is ");
            System.out.println("--------------------------------------------------------------");
            System.out.println("DataBase major version : " + dbmd.getDatabaseMajorVersion());  // Self Explanatory
            System.out.println("DataBase minor version : " + dbmd.getDatabaseMinorVersion());
            System.out.println("DataBase Driver(Connector) : " + dbmd.getDriverName());
            System.out.println("Driver version : " + dbmd.getDriverVersion());
            System.out.println("Database product name : " + dbmd.getDatabaseProductName());
            System.out.println("Database connection details : " + dbmd.getConnection());

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        catch(SQLException se){
            se.printStackTrace();
        }


    }





}
