/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;

import Modelo.ClsConsultaUsuarios;
import Modelo.ClsUsuario;
import Vista.frmLogin;
import Vista.frmPrincipal;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Iterator;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class CtrlLogin implements ActionListener {

    ClsUsuario user;
    ClsConsultaUsuarios usuario;
    frmLogin frm;

    public CtrlLogin(ClsUsuario user, ClsConsultaUsuarios usuario, frmLogin frm) throws Exception {
        this.user = user;
        this.usuario = usuario;
        this.frm = frm;
        this.frm.btningresar.addActionListener(this);
        this.frm.jchmostrar.addActionListener(this);

    }

    public void Iniciar() {
        this.frm.txtpassword.setEchoChar('*');
        this.frm.setLocationRelativeTo(null);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == frm.btningresar) {

            if (Validar()) {
                user.setNombre(frm.txtusuario.getText());
                user.setPassword(frm.txtpassword.getText());

                if (usuario.ExisteUsuario(user)) {
                    if (usuario.Login(user)) {

                        JOptionPane.showMessageDialog(null, "Bienvenido " + user.getNombre());
                        frmPrincipal menu = new frmPrincipal();
                        menu.setExtendedState(JFrame.MAXIMIZED_BOTH);
                        menu.setDefaultCloseOperation(menu.EXIT_ON_CLOSE);
                        menu.setVisible(true);
                        menu.lbluser.setText("Tipo de Usuario Logueado: " + user.getRol());
                        
                        if ("Vendedor".equals(user.getRol())) {
                            menu.jMenu1.setVisible(false);
                        }
                        frm.dispose();

                    } else {
                        JOptionPane.showMessageDialog(null, "Error de Logueo");
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "ClsUsuario/Clave Incorrectos");
                }

            }

        }
        if(e.getSource() == frm.jchmostrar){
            if (frm.jchmostrar.isSelected()) {
                frm.txtpassword.setEchoChar((char) 0); // Muestra la clave
            } else {
                frm.txtpassword.setEchoChar('*'); // Oculta la clave
            }
        }

    }

    private boolean Validar() {

        if ("".equals(frm.txtusuario.getText())) {

            JOptionPane.showMessageDialog(null, "Debe ingresar datos");
            return false;

        }
        if ("".equals(frm.txtpassword.getText())) {

            JOptionPane.showMessageDialog(null, "Debe ingresar datos");
            return false;

        }

        return true;
    }
}
