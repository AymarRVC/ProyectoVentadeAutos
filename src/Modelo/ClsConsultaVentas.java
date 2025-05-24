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

public class ClsConsultaVentas extends Conexion {

    //Metodo para guardar
    public boolean Guardar(ClsVentas ventas) {
        PreparedStatement ps = null;
        Connection con = (Connection) getConexion();
        String sql = "INSERT INTO ventas (id_auto,id_cliente,fecha_venta,precio_final) VALUES (?,?,?,?)";

        try {
            ps = con.prepareStatement(sql);
            ps.setInt(1, ventas.getId_auto());
            ps.setInt(2, ventas.getId_cliente());
            ps.setDate(3, new java.sql.Date(ventas.getFecha().getTime()));
            ps.setDouble(4, ventas.getPrecios());

            ps.execute();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(ClsConsultaVentas.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        } finally {
            try {
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(ClsConsultaVentas.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    //Metodo para editar
    public boolean Modificar(ClsVentas ventas) {
        PreparedStatement ps = null;
        Connection con = (Connection) getConexion();
        String sql = "UPDATE ventas SET id_auto=?,id_cliente=?,fecha_venta=?,precio_final=?  WHERE id_venta=?";

        try {
            ps = con.prepareStatement(sql);

            ps.setInt(1, ventas.getId_auto());
            ps.setInt(2, ventas.getId_cliente());
            ps.setDate(3, new java.sql.Date(ventas.getFecha().getTime()));
            ps.setDouble(4, ventas.getPrecios());

            ps.setInt(5, ventas.getId());
            ps.execute();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(ClsConsultaVentas.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        } finally {
            try {
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(ClsConsultaVentas.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    //Metodo Para Eliminar

    public boolean Eliminar(ClsVentas ventas) {
        PreparedStatement ps = null;
        Connection con = (Connection) getConexion();
        String sql = "DELETE FROM ventas WHERE id_venta=?";

        try {
            ps = con.prepareStatement(sql);
            ps.setInt(1, ventas.getId());
            ps.execute();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(ClsConsultaVentas.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        } finally {
            try {
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(ClsConsultaVentas.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    //Metodo Para Mostrar Todos Las Clsventas
    public List MostrarClsVentas() throws Exception {
        ResultSet res;
        List obList = new ArrayList();
        PreparedStatement ps = null;
        Connection con = (Connection) getConexion();
        String sql = "select v.id_venta,v.id_auto,v.id_cliente,v.fecha_venta,v.precio_final,c.nombre+' '+ c.apellido as Nombrecompleto, "
                + "a.marca,a.estado from ventas v, clientes c, autos a WHERE c.id_cliente=v.id_cliente and a.id_auto=v.id_auto and a.estado=1";
        try {
            ps = con.prepareStatement(sql);
            res = ps.executeQuery();
            while (res.next()) {
                ClsVentas obj = new ClsVentas();
                obj.setId(res.getInt("id_venta"));
                obj.setId_auto(res.getInt("id_auto"));
                obj.setId_cliente(res.getInt("id_cliente"));
                obj.setFecha(res.getDate("fecha_venta"));
                obj.setPrecios(res.getDouble("precio_final"));
                obj.setCliente(res.getString("Nombrecompleto"));
                obj.setAuto(res.getString("marca"));

                obList.add(obj);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ClsConsultaVentas.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(ClsConsultaVentas.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return obList;
    }

    public boolean BuscarVentas(ClsVentas obj) {
        PreparedStatement ps = null;
        Connection con = (Connection) getConexion();
        ResultSet res = null;
        String sql = "select v.id_venta,v.id_auto,v.id_cliente,v.fecha_venta,v.precio_final,c.nombre+' '+ c.apellido as Nombrecompleto, "
                + "a.marca,a.estado from ventas v, clientes c, autos a WHERE a.marca=? and c.id_cliente=v.id_cliente and a.id_auto=v.id_auto and a.estado=1";

        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, obj.getAuto());
            res = ps.executeQuery();
            //paso el resultado de la consulta al modelo
            if (res.next()) {
                obj.setId(res.getInt("id_venta"));
                obj.setId_auto(res.getInt("id_auto"));
                obj.setId_cliente(res.getInt("id_cliente"));
                obj.setFecha(res.getDate("fecha_venta"));
                obj.setPrecios(res.getDouble("precio_final"));
                obj.setCliente(res.getString("Nombrecompleto"));
                obj.setAuto(res.getString("marca"));
                return true;
            }
            return false;
        } catch (SQLException ex) {
            Logger.getLogger(ClsConsultaVentas.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        } finally {
            try {
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(ClsConsultaVentas.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public List ListarBussqueda(String marca) throws Exception {
        ResultSet res;
        List listaemp = new ArrayList();
        PreparedStatement ps = null;
        Connection con = (Connection) getConexion();
        String sql = "select v.id_venta,v.id_auto,v.id_cliente,v.fecha_venta,v.precio_final,c.nombre+' '+ c.apellido as Nombrecompleto, "
                + "a.marca,a.estado from ventas v, clientes c, autos a WHERE c.id_cliente=v.id_cliente and a.id_auto=v.id_auto and a.estado=1 and a.marca LIKE ?";
        try {

            ps = con.prepareStatement(sql);
            ps.setString(1, "%" + marca + "%");
            res = ps.executeQuery();
            while (res.next()) {
                ClsVentas obj = new ClsVentas();
                obj.setId(res.getInt("id_venta"));
                obj.setId_auto(res.getInt("id_auto"));
                obj.setId_cliente(res.getInt("id_cliente"));
                obj.setFecha(res.getDate("fecha_venta"));
                obj.setPrecios(res.getDouble("precio_final"));
                obj.setCliente(res.getString("Nombrecompleto"));
                obj.setAuto(res.getString("marca"));
                listaemp.add(obj);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ClsConsultaVentas.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(ClsConsultaVentas.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return listaemp;
    }

    public ClsAutos BuscarAutos(ClsVentas am) {
        ClsAutos autos = new ClsAutos();
        PreparedStatement ps = null;
        Connection con = (Connection) getConexion();
        ResultSet res = null;
        String sql = "SELECT * FROM autos WHERE id_auto=? and estado=1";

        try {
            ps = con.prepareStatement(sql);
            ps.setInt(1, am.getId_auto());
            res = ps.executeQuery();
            //paso el resultado de la consulta equipos modelo
            if (res.next()) {
                autos.setId(res.getInt("id_auto"));
                autos.setMarca(res.getString("marca"));
                autos.setModelo(res.getString("modelo"));
                autos.setAnio(res.getInt("anio"));
                autos.setPrecio(res.getDouble("precio"));
                autos.setEstado(res.getInt("estado"));

                return autos;
            }
            return null;
        } catch (SQLException ex) {
            Logger.getLogger(ClsConsultaVentas.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        } finally {
            try {
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(ClsConsultaVentas.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }
    public ClsClientes BuscarClientes(ClsVentas am) {
        ClsClientes clientes = new ClsClientes();
        PreparedStatement ps = null;
        Connection con = (Connection) getConexion();
        ResultSet res = null;
        String sql = "SELECT * FROM clientes WHERE id_cliente=?";

        try {
            ps = con.prepareStatement(sql);
            ps.setInt(1, am.getId_cliente());
            res = ps.executeQuery();
            //paso el resultado de la consulta equipos modelo
            if (res.next()) {
               clientes.setId(res.getInt("id_cliente"));
                clientes.setCedula(res.getString("cedula"));
                clientes.setNombre(res.getString("nombre"));
                clientes.setApellido(res.getString("apellido"));
                clientes.setCorreo(res.getString("correo"));
                clientes.setTelefono(res.getString("teléfono"));

                return clientes;
            }
            return null;
        } catch (SQLException ex) {
            Logger.getLogger(ClsConsultaVentas.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        } finally {
            try {
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(ClsConsultaVentas.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

}
