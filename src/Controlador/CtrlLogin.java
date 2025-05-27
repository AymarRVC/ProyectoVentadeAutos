/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;

import Modelo.ClsConsultaUsuarios;
import Modelo.ClsUsuario;
import Vista.frmLogin;
import Vista.frmPrincipal;
import java.awt.BorderLayout;
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
        this.frm.passTxt.setEchoChar('*');
        this.frm.setLocationRelativeTo(null);
        this.frm.getRootPane().setDefaultButton(this.frm.btningresar);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == frm.btningresar) {

            if (Validar()) {
                user.setNombre(frm.userTxt.getText());
                user.setPassword(frm.passTxt.getText());

                if (usuario.ExisteUsuario(user)) {
                    if (usuario.Login(user)) {

                        JOptionPane.showMessageDialog(null, "Bienvenido " + user.getNombre());
                        frmPrincipal menu = new frmPrincipal();
                        menu.setExtendedState(JFrame.MAXIMIZED_BOTH);
                        menu.setDefaultCloseOperation(menu.EXIT_ON_CLOSE);
                        menu.setVisible(true);
                        menu.lbluser.setText("Tipo de Usuario Logueado: " + user.getRol());
                        FondoPanel fondo2 = new FondoPanel();
                        menu.Contenedor.setLayout(new BorderLayout());
                        menu.Contenedor.add(fondo2, BorderLayout.CENTER);
                        menu.Contenedor.setComponentZOrder(fondo2, menu.Contenedor.getComponentCount() - 1);

                        menu.Contenedor.addComponentListener(new java.awt.event.ComponentAdapter() {
                            @Override
                            public void componentResized(java.awt.event.ComponentEvent evt) {
                                fondo2.repaint();
                            }
                        });
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
        if (e.getSource() == frm.jchmostrar) {
            if (frm.jchmostrar.isSelected()) {
                frm.passTxt.setEchoChar((char) 0); // Muestra la clave
            } else {
                frm.passTxt.setEchoChar('*'); // Oculta la clave
            }
        }

    }

    private boolean Validar() {

        if ("".equals(frm.userTxt.getText())) {

            JOptionPane.showMessageDialog(null, "Debe ingresar datos");
            return false;

        }
        if ("".equals(frm.passTxt.getText())) {

            JOptionPane.showMessageDialog(null, "Debe ingresar datos");
            return false;

        }

        return true;
    }
}
