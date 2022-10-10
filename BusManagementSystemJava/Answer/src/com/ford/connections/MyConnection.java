package com.ford.connections;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MyConnection {

    Connection con;
    String url;
    String user;
    String password;

    public MyConnection(){
        url = "jdbc:mysql://localhost:3306/javaAssessment";
        user = "root";
        password = "asdf";
    }

    public Connection getMyConnection()  {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection(url,user,password);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        catch(SQLException se){
            se.printStackTrace();
        }
        return con;
    }
}
