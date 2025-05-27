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

public class ClsConsultaClientes extends Conexion {
    //Metodo para guardar

    public boolean Guardar(ClsClientes clientes) {
        PreparedStatement ps = null;
        Connection con = (Connection) getConexion();
        String sql = "INSERT INTO clientes (cedula,nombre,apellido,correo,teléfono) VALUES (?,?,?,?,?)";

        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, clientes.getCedula());
            ps.setString(2, clientes.getNombre());
            ps.setString(3, clientes.getApellido());
            ps.setString(4, clientes.getCorreo());
            ps.setString(5, clientes.getTelefono());

            ps.execute();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(ClsConsultaClientes.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        } finally {
            try {
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(ClsConsultaClientes.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    //Metodo para editar
    public boolean Modificar(ClsClientes clientes) {
        PreparedStatement ps = null;
        Connection con = (Connection) getConexion();
        String sql = "UPDATE clientes SET cedula=?,nombre=?,apellido=?,correo=?,teléfono=? WHERE id_cliente=?";

        try {
            ps = con.prepareStatement(sql);

            ps.setString(1, clientes.getCedula());
            ps.setString(2, clientes.getNombre());
            ps.setString(3, clientes.getApellido());
            ps.setString(4, clientes.getCorreo());
            ps.setString(5, clientes.getTelefono());
            ps.setInt(6, clientes.getId());
            ps.execute();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(ClsConsultaClientes.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        } finally {
            try {
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(ClsConsultaClientes.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    //Metodo Para Eliminar

    public boolean Eliminar(ClsClientes clientes) {
        PreparedStatement ps = null;
        Connection con = (Connection) getConexion();
        String sql = "DELETE FROM clientes WHERE id_cliente=?";

        try {
            ps = con.prepareStatement(sql);
            ps.setInt(1, clientes.getId());
            ps.execute();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(ClsConsultaClientes.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        } finally {
            try {
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(ClsConsultaClientes.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    //Metodo Para Mostrar Todos Los ClsAutos
    public List MostrarClsClientes() throws Exception {
        ResultSet res;
        List obList = new ArrayList();
        PreparedStatement ps = null;
        Connection con = (Connection) getConexion();
        String sql = "select * from clientes";
        try {
            ps = con.prepareStatement(sql);
            res = ps.executeQuery();
            while (res.next()) {
                ClsClientes obj = new ClsClientes();
                obj.setId(res.getInt("id_cliente"));
                obj.setCedula(res.getString("cedula"));
                obj.setNombre(res.getString("nombre"));
                obj.setApellido(res.getString("apellido"));
                obj.setCorreo(res.getString("correo"));
                obj.setTelefono(res.getString("teléfono"));

                obList.add(obj);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ClsConsultaClientes.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(ClsConsultaClientes.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return obList;
    }
    //Metodo Para Buscar Clsclientes por su nombre

    public boolean BuscarClsClientes(ClsClientes obj) {
        PreparedStatement ps = null;
        Connection con = (Connection) getConexion();
        ResultSet res = null;
        String sql = "SELECT * FROM clientes WHERE cedula=?";

        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, obj.getCedula());
            res = ps.executeQuery();
            //paso el resultado de la consulta al modelo
            if (res.next()) {
                obj.setId(res.getInt("id_cliente"));
                obj.setCedula(res.getString("cedula"));
                obj.setNombre(res.getString("nombre"));
                obj.setApellido(res.getString("apellido"));
                obj.setCorreo(res.getString("correo"));
                obj.setTelefono(res.getString("teléfono"));

                return true;
            }
            return false;
        } catch (SQLException ex) {
            Logger.getLogger(ClsConsultaClientes.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        } finally {
            try {
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(ClsConsultaClientes.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public List ListarBussqueda(String cedula) throws Exception {
        ResultSet res;
        List listaemp = new ArrayList();
        PreparedStatement ps = null;
        Connection con = (Connection) getConexion();
        String sql = "SELECT * FROM clientes WHERE cedula LIKE ?";
        try {

            ps = con.prepareStatement(sql);
            ps.setString(1, "%" + cedula + "%");
            res = ps.executeQuery();
            while (res.next()) {
                ClsClientes obj = new ClsClientes();
                obj.setId(res.getInt("id_cliente"));
                obj.setCedula(res.getString("cedula"));
                obj.setNombre(res.getString("nombre"));
                obj.setApellido(res.getString("apellido"));
                obj.setCorreo(res.getString("correo"));
                obj.setTelefono(res.getString("teléfono"));
                listaemp.add(obj);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ClsConsultaClientes.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(ClsConsultaClientes.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return listaemp;
    }

    public boolean ExisteCliente(ClsClientes obj) {
        PreparedStatement ps = null;
        Connection con = (Connection) getConexion();
        ResultSet res = null;
        String sql = "SELECT * FROM clientes WHERE cedula=?";

        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, obj.getCedula());
            res = ps.executeQuery();
            //paso el resultado de la consulta al modelo
            if (res.next()) {
                obj.setId(res.getInt("id_cliente"));
                obj.setCedula(res.getString("cedula"));
                obj.setNombre(res.getString("nombre"));
                obj.setApellido(res.getString("apellido"));
                obj.setCorreo(res.getString("correo"));
                obj.setTelefono(res.getString("teléfono"));
                return true;
            }
            return false;
        } catch (SQLException ex) {
            Logger.getLogger(ClsConsultaClientes.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        } finally {
            try {
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(ClsConsultaClientes.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
