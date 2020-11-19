/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.sql.*;
/**
 *
 * @author Leonardo
 */
public class Database {
    public static Connection conector() throws ClassNotFoundException{
        java.sql.Connection conexao = null;
        String driver = "com.mysql.jdbc.Driver";
        String url = "jdbc:mysql://localhost:3306/condessa";
        try{
            Class.forName(driver);
            conexao = DriverManager.getConnection(url,"root","");
            return conexao;
        }catch(SQLException e){
            return null;
        }
        
    }
    
    /*public Connection getConnection(){
         String url = "jdbc:mysql://localhost:3306/condessa";
        try{
            return (Connection) DriverManager.getConnection(url,"root","");
        }catch(SQLException e){
            throw new RuntimeException(e);
        }
    }
    */
}
