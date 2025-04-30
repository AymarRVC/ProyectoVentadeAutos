/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;

import Modelo.ClsConsultaUsuarios;
import Modelo.ClsUsuario;
import Vista.frmUsuario;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

public class CtrlUsuario implements ActionListener, KeyListener {

    ClsUsuario em;
    ClsConsultaUsuarios sqlemp;
    frmUsuario frm;

    public CtrlUsuario(ClsUsuario em, ClsConsultaUsuarios sqlemp, frmUsuario frm) {
        this.em = em;
        this.sqlemp = sqlemp;
        this.frm = frm;
        this.frm.btnguardar.addActionListener(this);
        this.frm.btneditar.addActionListener(this);
        this.frm.btneliminar.addActionListener(this);
        this.frm.txtbuscar.addKeyListener(this);
        this.frm.btnactualizar.addActionListener((ActionListener) this);

        this.frm.JClientes.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {

                DefaultTableModel modelo = (DefaultTableModel) frm.JClientes.getModel();
                int fila;
                fila = frm.JClientes.getSelectedRow();
                frm.txtbuscar.setEnabled(false);
                frm.txtid.setText(modelo.getValueAt(fila, 0).toString());
                frm.txtusuario.setText(modelo.getValueAt(fila, 1).toString());

                String rol = modelo.getValueAt(fila, 2).toString();
                frm.cmbrol.setSelectedItem(rol);
                String estint = modelo.getValueAt(fila, 3).toString();
                String estsString;
                if ("1".equals(estint)) {
                    estsString = "Habilitado";
                    frm.cmbestado.setSelectedItem(estsString);
                } else {
                    estsString = "Deshabilitado";
                    frm.cmbestado.setSelectedItem(estsString);
                }

                frm.btnguardar.setEnabled(false);
                frm.btneditar.setEnabled(true);
                frm.btneliminar.setEnabled(true);
            }
        });
    }

    public void Iniciar() {
        frm.setTitle("Gestion de Usuario");
        frm.txtid.setVisible(false);
        frm.btneditar.setEnabled(false);
        frm.btneliminar.setEnabled(false);
        Mostrar();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == frm.btnactualizar) {

            Mostrar();
            Limpiar();
            frm.btneditar.setEnabled(false);
            frm.btneliminar.setEnabled(false);
            frm.txtbuscar.setEnabled(true);
            frm.btnguardar.setEnabled(true);
            frm.txtbuscar.setText(null);
        }
        if (e.getSource() == frm.btnguardar) {

            if (Validar()) {
                frm.txtbuscar.setEnabled(true);
                em.setNombre(frm.txtusuario.getText());
                em.setPassword(frm.txtclave.getText());
                em.setRol((String) frm.cmbrol.getSelectedItem());
                String estadosString = (String) frm.cmbestado.getSelectedItem();
                int estadoint;
                if ("Habilitado".equals(estadosString)) {
                    estadoint = 1;
                    em.setEstado(estadoint);
                } else {
                    estadoint = 0;
                    em.setEstado(estadoint);
                }

                if (sqlemp.Guardar(em)) {

                    JOptionPane.showMessageDialog(null, "Guardado");
                    Mostrar();
                    Limpiar();
                } else {
                    JOptionPane.showMessageDialog(null, "No se guardó la informacion");
                    Limpiar();
                }
            }

        }
        if (e.getSource() == frm.btneditar) {
            if (Validar()) {

                em.setId(Integer.parseInt(frm.txtid.getText()));
                em.setNombre(frm.txtusuario.getText());
                em.setPassword(frm.txtclave.getText());
                em.setRol((String) frm.cmbrol.getSelectedItem());
                String estadosString = (String) frm.cmbestado.getSelectedItem();
                int estadoint;
                if ("Habilitado".equals(estadosString)) {
                    estadoint = 1;
                    em.setEstado(estadoint);
                } else {
                    estadoint = 0;
                    em.setEstado(estadoint);
                }

                if (sqlemp.Modificar(em)) {
                    JOptionPane.showMessageDialog(null, "Se actualizo la informacion");
                    Limpiar();
                    Mostrar();
                    frm.btnguardar.setEnabled(true);
                    frm.txtbuscar.setEnabled(true);
                    frm.btneditar.setEnabled(false);
                    frm.btneliminar.setEnabled(false);
                } else {
                    JOptionPane.showMessageDialog(null, "No se actualizo la informacion");
                    Limpiar();
                    frm.btnguardar.setEnabled(true);
                    frm.txtbuscar.setEnabled(true);
                    frm.btneditar.setEnabled(false);
                    frm.btneliminar.setEnabled(false);
                }

            }
        }
        if (e.getSource() == frm.btneliminar) {
            if (frm.JClientes.getSelectedRow() == -1) {
                JOptionPane.showMessageDialog(frm, "Debe seleccionar la fila que desea eliminar");
            } else {
                em.setId(Integer.parseInt(frm.txtid.getText()));
                if (sqlemp.Eliminar(em)) {
                    JOptionPane.showMessageDialog(frm, "Eliminado");
                    Mostrar();
                    Limpiar();
                    frm.btnguardar.setEnabled(true);
                    frm.txtbuscar.setEnabled(true);
                    frm.btneditar.setEnabled(false);
                    frm.btneliminar.setEnabled(false);
                }
            }
        }

    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getSource() == frm.txtbuscar) {
            String texto = frm.txtbuscar.getText().trim();
            em.setNombre(texto);

            if (texto.isEmpty()) {
                Mostrar();
                return;
            }

            String[] columnas = {"ID", "USU", "ROL", "ESTADO"};
            DefaultTableModel tabla = new DefaultTableModel(null, columnas) {
                @Override
                public boolean isCellEditable(int row, int col) {
                    return false;
                }
            };

            try {
                List<ClsUsuario> resultados = sqlemp.ListarBussqueda(texto);

                for (ClsUsuario u : resultados) {
                    Object[] fila = {
                        u.getId(),
                        u.getNombre(),
                        u.getRol(),
                        u.getEstado()
                    };
                    tabla.addRow(fila);
                }

                frm.JClientes.setModel(tabla);

                DefaultTableCellRenderer Alinear = new DefaultTableCellRenderer();
                Alinear.setHorizontalAlignment(SwingConstants.RIGHT);
                if (frm.JClientes.getColumnCount() >= 7) {
                    for (int i = 4; i < 7; i++) {
                        frm.JClientes.getColumnModel().getColumn(i).setCellRenderer(Alinear);
                    }
                }

            } catch (Exception ex) {
                Logger.getLogger(CtrlUsuario.class.getName())
                        .log(Level.SEVERE, null, ex);
            }
        }
    }

    public void Mostrar() {
        List notas;
        ClsUsuario eqip;

        String[] columnas = {"ID", "USU", "ROL", "ESTADO"};
        Object[] datos = new Object[4];
        DefaultTableModel tabla = new DefaultTableModel(null, columnas) {
            @Override
            public boolean isCellEditable(int i, int j) {
                if (i == 5) {
                    return true;
                } else {
                    return false;
                }
            }
        };

        try {

            notas = sqlemp.MostrarClsUsuarios();
            if (!notas.isEmpty()) {
                for (int i = 0; i < notas.size(); i++) {

                    eqip = (ClsUsuario) notas.get(i);
                    datos[0] = eqip.getId();
                    datos[1] = eqip.getNombre();
                    datos[2] = eqip.getRol();
                    datos[3] = eqip.getEstado();

                    tabla.addRow(datos);
                }
                frm.JClientes.setModel(tabla);

                DefaultTableCellRenderer Alinear = new DefaultTableCellRenderer();
                Alinear.setHorizontalAlignment(SwingConstants.RIGHT);
                if (frm.JClientes.getColumnCount() >= 7) {
                    for (int i = 4; i < 7; i++) {
                        frm.JClientes.getColumnModel().getColumn(i).setCellRenderer(Alinear);
                    }
                }
            } else {
                JOptionPane.showMessageDialog(null, "No encontro información");
                Limpiar();
                frm.txtbuscar.setText(null);
            }

        } catch (Exception ex) {
            Logger.getLogger(CtrlUsuario.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void Limpiar() {

        frm.txtid.setText(null);
        frm.txtusuario.setText(null);
        frm.txtclave.setText(null);
        frm.txtbuscar.setText(null);

    }

    private boolean Validar() {

        if ("".equals(frm.txtusuario.getText())) {

            JOptionPane.showMessageDialog(null, "Debe ingresar datos");
            return false;

        }
        if ("".equals(frm.txtclave.getText())) {

            JOptionPane.showMessageDialog(null, "Debe ingresar datos");
            return false;

        }

        return true;
    }
}
