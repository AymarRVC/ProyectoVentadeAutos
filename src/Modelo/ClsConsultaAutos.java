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
public class ClsConsultaAutos extends Conexion {
    //Metodo para guardar
    public boolean Guardar(ClsAutos autos){
          PreparedStatement ps =null;
            Connection con= (Connection)getConexion();
            String sql="INSERT INTO autos(marca,modelo,anio,precio,estado) VALUES (?,?)";
            
        try {    
            ps=con.prepareStatement(sql);
             ps.setString(1, autos.getMarca());
             ps.setString(2, autos.getModelo());
             ps.setInt(3, autos.getAnio());
             ps.setDouble(4, autos.getPrecio());
             ps.setString(5, autos.getEstado());
             
            ps.execute();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(ClsConsultaAutos.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
        finally{
                try {
                    con.close();
                } catch (SQLException ex) {
                    Logger.getLogger(ClsConsultaAutos.class.getName()).log(Level.SEVERE, null, ex);
                }
        }
    }
    
   //Metodo para editar
      public boolean Modificar(ClsAutos autos){
           PreparedStatement ps =null;
           Connection con= (Connection)getConexion();
           String sql="UPDATE autos SET marca=?,modelo=?,anio=?,precio=?,estado=? WHERE id_auto=?";
            
        try {    
            ps=con.prepareStatement(sql);
            
             ps.setString(1, autos.getMarca());
             ps.setString(2, autos.getModelo());
             ps.setInt(3, autos.getAnio());
             ps.setDouble(4, autos.getPrecio());
             ps.setString(5, autos.getEstado());
             ps.setInt(6, autos.getId());
            ps.execute();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(ClsConsultaAutos.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
        finally{
                try {
                    con.close();
                } catch (SQLException ex) {
                    Logger.getLogger(ClsConsultaAutos.class.getName()).log(Level.SEVERE, null, ex);
                }
        }
    }
   //Metodo Para Eliminar
      public boolean Eliminar(ClsAutos autos){
            PreparedStatement ps =null;
            Connection con= (Connection)getConexion();
            String sql="DELETE FROM autos WHERE id_auto=?";
            
        try {    
            ps=con.prepareStatement(sql);
            ps.setInt(1, autos.getId());
            ps.execute();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(ClsConsultaAutos.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
        finally{
                try {
                    con.close();
                } catch (SQLException ex) {
                    Logger.getLogger(ClsConsultaAutos.class.getName()).log(Level.SEVERE, null, ex);
                }
        }
    }
   //Metodo Para Mostrar Todos Los ClsAutos
      public List MostrarClsAuto()throws Exception{
         ResultSet res;
         List obList = new ArrayList();
         PreparedStatement ps =null;
         Connection con= (Connection)getConexion();
         String sql="select * from autos";
         try {
             ps=con.prepareStatement(sql);
             res = ps.executeQuery();
             while (res.next()) {                 
                ClsAutos obj = new ClsAutos();
                obj.setId(res.getInt("id_auto"));
                obj.setMarca(res.getString("marca"));
                obj.setModelo(res.getString("modelo"));
                obj.setAnio(res.getInt("anio"));
                obj.setPrecio(res.getDouble("precio"));
                obj.setEstado(res.getString("estado"));
                
                obList.add(obj);
             }
         } catch (SQLException ex) {
            Logger.getLogger(ClsConsultaAutos.class.getName()).log(Level.SEVERE, null, ex);
        }
        finally{
                try {
                    con.close();
                } catch (SQLException ex) {
                    Logger.getLogger(ClsConsultaAutos.class.getName()).log(Level.SEVERE, null, ex);
                }
        }return obList;
     }
   //Metodo Para Buscar ClsAutos por su Marca
       public boolean BuscarClsEquipos(ClsAutos obj){
    PreparedStatement ps =null;
    Connection con= (Connection)getConexion();
    ResultSet res=null;
    String sql="SELECT * FROM autos WHERE marca=?";
            
        try {    
            ps=con.prepareStatement(sql);
             ps.setString(1, obj.getMarca());
            res=ps.executeQuery();
            //paso el resultado de la consulta al modelo
           if(res.next())
            {
                 obj.setId(res.getInt("id_auto"));
                obj.setMarca(res.getString("marca"));
                obj.setModelo(res.getString("modelo"));
                obj.setAnio(res.getInt("anio"));
                obj.setPrecio(res.getDouble("precio"));
                obj.setEstado(res.getString("estado"));
                return true;  
            }
            return false;
        } catch (SQLException ex) {
            Logger.getLogger(ClsConsultaAutos.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
        finally{
                try {
                    con.close();
                } catch (SQLException ex) {
                    Logger.getLogger(ClsConsultaAutos.class.getName()).log(Level.SEVERE, null, ex);
                }
        }
    }
}
