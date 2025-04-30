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

public class ClsConsultaUsuarios extends Conexion {

    public boolean Login(ClsUsuario obj) {
        PreparedStatement ps = null;
        Connection con = (Connection) getConexion();
        ResultSet res = null;
        String sql = "SELECT * FROM usuarios WHERE nombre_usuario=? and password_usuario=? and  estado_usuario=1";

        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, obj.getNombre());
            ps.setString(2, obj.getPassword());
            res = ps.executeQuery();
            //paso el resultado de la consulta al modelo
            if (res.next()) {
                obj.setId(res.getInt("id_usuario"));
                obj.setNombre(res.getString("nombre_usuario"));
                obj.setPassword(res.getString("password_usuario"));
                obj.setRol(res.getString("rol_usurio"));
                obj.setEstado(res.getInt("estado_usuario"));

                return true;
            }
            return false;
        } catch (SQLException ex) {
            Logger.getLogger(ClsConsultaUsuarios.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        } finally {
            try {
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(ClsConsultaUsuarios.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public boolean ExisteUsuario(ClsUsuario obj) {
        PreparedStatement ps = null;
        Connection con = (Connection) getConexion();
        ResultSet res = null;
        String sql = "SELECT * FROM usuarios WHERE nombre_usuario=? and password_usuario=?";

        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, obj.getNombre());
            ps.setString(2, obj.getPassword());
            res = ps.executeQuery();
            //paso el resultado de la consulta al modelo
            if (res.next()) {
                obj.setId(res.getInt("id_usuario"));
                obj.setNombre(res.getString("nombre_usuario"));
                obj.setPassword(res.getString("password_usuario"));
                obj.setRol(res.getString("rol_usurio"));
                obj.setEstado(res.getInt("estado_usuario"));
                return true;
            }
            return false;
        } catch (SQLException ex) {
            Logger.getLogger(ClsConsultaUsuarios.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        } finally {
            try {
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(ClsConsultaUsuarios.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    //Metodo para guardar
    public boolean Guardar(ClsUsuario usuario) {
        PreparedStatement ps = null;
        Connection con = (Connection) getConexion();
        String sql = "INSERT INTO usuarios (nombre_usuario,password_usuario,rol_usurio,estado_usuario) VALUES (?,?,?,?)";

        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, usuario.getNombre());
            ps.setString(2, usuario.getPassword());
            ps.setString(3, usuario.getRol());
            ps.setInt(4, usuario.getEstado());

            ps.execute();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(ClsConsultaUsuarios.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        } finally {
            try {
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(ClsConsultaUsuarios.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    //Metodo para editar
    public boolean Modificar(ClsUsuario usuario) {
        PreparedStatement ps = null;
        Connection con = (Connection) getConexion();
        String sql = "UPDATE usuarios SET nombre_usuario=?,password_usuario=?,rol_usurio=?,estado_usuario=? WHERE id_usuario=?";

        try {
            ps = con.prepareStatement(sql);

            ps.setString(1, usuario.getNombre());
            ps.setString(2, usuario.getPassword());
            ps.setString(3, usuario.getRol());
            ps.setInt(4, usuario.getEstado());
            ps.setInt(5, usuario.getId());
            ps.execute();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(ClsConsultaUsuarios.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        } finally {
            try {
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(ClsConsultaUsuarios.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    //Metodo Para Eliminar

    public boolean Eliminar(ClsUsuario usuario) {
        PreparedStatement ps = null;
        Connection con = (Connection) getConexion();
        String sql = "DELETE FROM usuarios WHERE id_usuario=?";

        try {
            ps = con.prepareStatement(sql);
            ps.setInt(1, usuario.getId());
            ps.execute();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(ClsConsultaUsuarios.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        } finally {
            try {
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(ClsConsultaUsuarios.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    //Metodo Para Mostrar Todos Los ClsUsuario
    public List MostrarClsUsuarios() throws Exception {
        ResultSet res;
        List obList = new ArrayList();
        PreparedStatement ps = null;
        Connection con = (Connection) getConexion();
        String sql = "select * from usuarios";
        try {
            ps = con.prepareStatement(sql);
            res = ps.executeQuery();
            while (res.next()) {
                ClsUsuario obj = new ClsUsuario();
                obj.setId(res.getInt("id_usuario"));
                obj.setNombre(res.getString("nombre_usuario"));
                obj.setPassword(res.getString("password_usuario"));
                obj.setRol(res.getString("rol_usurio"));
                obj.setEstado(res.getInt("estado_usuario"));

                obList.add(obj);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ClsConsultaUsuarios.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(ClsConsultaUsuarios.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return obList;
    }
    //Metodo Para Buscar ClsUsuarios por su nombre

    public boolean BuscarClsUsuario(ClsUsuario obj) {
        PreparedStatement ps = null;
        Connection con = (Connection) getConexion();
        ResultSet res = null;
        String sql = "SELECT * FROM usuarios WHERE nombre_usuario=?";

        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, obj.getNombre());
            res = ps.executeQuery();
            //paso el resultado de la consulta al modelo
            if (res.next()) {
                obj.setId(res.getInt("id_usuario"));
                obj.setNombre(res.getString("nombre_usuario"));
                obj.setPassword(res.getString("password_usuario"));
                obj.setRol(res.getString("rol_usurio"));
                obj.setEstado(res.getInt("estado_usuario"));

                return true;
            }
            return false;
        } catch (SQLException ex) {
            Logger.getLogger(ClsConsultaUsuarios.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        } finally {
            try {
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(ClsConsultaUsuarios.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public List ListarBussqueda(String nombre) throws Exception {
        ResultSet res;
        List listaemp = new ArrayList();
        PreparedStatement ps = null;
        Connection con = (Connection) getConexion();
        String sql = "SELECT * FROM usuarios WHERE nombre_usuario LIKE ?";
        try {

            ps = con.prepareStatement(sql);
            ps.setString(1, "%" + nombre + "%");
            res = ps.executeQuery();
            while (res.next()) {
                ClsUsuario obj = new ClsUsuario();
                obj.setId(res.getInt("id_usuario"));
                obj.setNombre(res.getString("nombre_usuario"));
                obj.setPassword(res.getString("password_usuario"));
                obj.setRol(res.getString("rol_usurio"));
                obj.setEstado(res.getInt("estado_usuario"));
                listaemp.add(obj);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ClsConsultaUsuarios.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(ClsConsultaUsuarios.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return listaemp;
    }

}
