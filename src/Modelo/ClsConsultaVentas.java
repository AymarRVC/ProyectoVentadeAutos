/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ecede
 */
public class ClsConsultaVentas extends Conexion {
    //Metodo para guardar
    public boolean Guardar(ClsVentas ventas){
          PreparedStatement ps =null;
            Connection con= (Connection)getConexion();
            String sql="INSERT INTO ventas (fecha_venta,precio_final) VALUES (?,?)";
            
        try {    
            ps=con.prepareStatement(sql);
             ps.setString(1, ventas.getFecha());
             ps.setDouble(2, ventas.getPrecios());
            
             
             
            ps.execute();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(ClsConsultaVentas.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
        finally{
                try {
                    con.close();
                } catch (SQLException ex) {
                    Logger.getLogger(ClsConsultaVentas.class.getName()).log(Level.SEVERE, null, ex);
                }
        }
    }
    //Metodo para editar
      public boolean Modificar(ClsVentas ventas){
           PreparedStatement ps =null;
           Connection con= (Connection)getConexion();
           String sql="UPDATE ventas SET fecha_venta=?,precio_final=?  WHERE id_venta=?";
            
        try {    
            ps=con.prepareStatement(sql);
            
             ps.setString(1, ventas.getFecha());
             ps.setDouble(2,ventas.getPrecios());
            
             ps.setInt(3, ventas.getId());
            ps.execute();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(ClsConsultaVentas.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
        finally{
                try {
                    con.close();
                } catch (SQLException ex) {
                    Logger.getLogger(ClsConsultaVentas.class.getName()).log(Level.SEVERE, null, ex);
                }
        }
    }
   //Metodo Para Eliminar
      public boolean Eliminar(ClsVentas ventas){
            PreparedStatement ps =null;
            Connection con= (Connection)getConexion();
            String sql="DELETE FROM ventas WHERE id_venta=?";
            
        try {    
            ps=con.prepareStatement(sql);
            ps.setInt(1, ventas.getId());
            ps.execute();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(ClsConsultaVentas.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
        finally{
                try {
                    con.close();
                } catch (SQLException ex) {
                    Logger.getLogger(ClsConsultaVentas.class.getName()).log(Level.SEVERE, null, ex);
                }
        }
    }
    //Metodo Para Mostrar Todos Las Clsventas
      public List MostrarClsVentas()throws Exception{
         ResultSet res;
         List obList = new ArrayList();
         PreparedStatement ps =null;
         Connection con= (Connection)getConexion();
         String sql="select * from ventas";
         try {
             ps=con.prepareStatement(sql);
             res = ps.executeQuery();
             while (res.next()) {                 
                ClsVentas obj = new ClsVentas ();
                obj.setId(res.getInt("id_venta"));
                obj.setFecha(res.getString("fecha_venta"));
                obj.setPrecios(res.getDouble("precio_final"));
                
                
                
                
                obList.add(obj);
             }
         } catch (SQLException ex) {
            Logger.getLogger(ClsConsultaVentas.class.getName()).log(Level.SEVERE, null, ex);
        }
        finally{
                try {
                    con.close();
                } catch (SQLException ex) {
                    Logger.getLogger(ClsConsultaVentas.class.getName()).log(Level.SEVERE, null, ex);
                }
        }return obList;
     }
   //Metodo Para Buscar ClsUsuarios por su nombre
       public boolean BuscarClsUsuario(ClsVentas obj){
    PreparedStatement ps =null;
    Connection con= (Connection)getConexion();
    ResultSet res=null;
    String sql="SELECT * FROM ventas WHERE ClsVentas =?";
            
        try {    
            ps=con.prepareStatement(sql);
             ps.setInt(1, obj.getId());
            res=ps.executeQuery();
            //paso el resultado de la consulta al modelo
           if(res.next())
            {
                 obj.setId(res.getInt("id_usuario"));
                obj.setNombre(res.getString("nombre_usuario"));
                obj.setPassword(res.getString("password_usuario"));
                obj.setRol(res.getString("rol_usurio"));
                obj.setEstado(res.getString("estado_usuario"));
                
                return true;  
            }
            return false;
        } catch (SQLException ex) {
            Logger.getLogger(ClsConsultaVentas.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
        finally{
                try {
                    con.close();
                } catch (SQLException ex) {
                    Logger.getLogger(ClsConsultaVentas.class.getName()).log(Level.SEVERE, null, ex);
                }
        }
    }
}
