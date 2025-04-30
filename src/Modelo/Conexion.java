/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * 
 */
public class Conexion {
     final String bdd="venta_autos"; 
   final String user="root";
    String pwd="";
    String url="jdbc:mysql://localhost:3306/" + bdd;
    Connection conexion;
    
    public Connection getConexion(){
    
       try {
           Class.forName("com.mysql.cj.jdbc.Driver");
           conexion=(Connection)DriverManager.getConnection(url,user,pwd);
       } catch (ClassNotFoundException | SQLException ex) {
           Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
       }
       
        return conexion;
    }
}
