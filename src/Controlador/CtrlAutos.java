/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;

import Modelo.ClsAutos;
import Modelo.ClsConsultaAutos;
import Vista.frmAuto;
import Vista.frmPrincipal;
import java.awt.BorderLayout;
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

public class CtrlAutos implements ActionListener, KeyListener {

    ClsAutos em;
    ClsConsultaAutos sqlemp;
    frmAuto frm;
    frmPrincipal menu = new frmPrincipal();

    public CtrlAutos(ClsAutos em, ClsConsultaAutos sqlemp, frmAuto frm, frmPrincipal menu) {
        this.em = em;
        this.sqlemp = sqlemp;
        this.frm = frm;
        this.menu = menu;
        this.frm.btnguardar.addActionListener(this);
        this.frm.btneditar.addActionListener(this);
        this.frm.btneliminar.addActionListener(this);
        this.frm.btncerrarformulario.addActionListener(this);
        this.frm.txtbuscar.addKeyListener(this);
        this.frm.txtanio.addKeyListener(this);
        this.frm.txtprecio.addKeyListener(this);
        this.frm.btnactualizar.addActionListener((ActionListener) this);

        this.frm.JAutos.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {

                DefaultTableModel modelo = (DefaultTableModel) frm.JAutos.getModel();
                int fila;
                fila = frm.JAutos.getSelectedRow();
                frm.txtbuscar.setEnabled(false);
                frm.txtid.setText(modelo.getValueAt(fila, 0).toString());
                frm.txtmarca.setText(modelo.getValueAt(fila, 1).toString());
                frm.txtmodelo.setText(modelo.getValueAt(fila, 2).toString());
                frm.txtanio.setText(modelo.getValueAt(fila, 3).toString());
                frm.txtprecio.setText(modelo.getValueAt(fila, 4).toString());
                String estint = modelo.getValueAt(fila, 5).toString();
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
        frm.setTitle("Gestion de Autos");
        frm.txtid.setVisible(false);
        frm.btneditar.setEnabled(false);
        frm.btneliminar.setEnabled(false);
        Mostrar();

        //frm.setVisible(true);
    }

    public void cerrar() {

        FondoPanel fondo2 = new FondoPanel();
        menu.Contenedor.removeAll();
        menu.Contenedor.revalidate();
        menu.Contenedor.repaint();
        menu.Contenedor.setLayout(new BorderLayout());
        menu.Contenedor.add(fondo2, BorderLayout.CENTER);
        menu.Contenedor.setComponentZOrder(fondo2, menu.Contenedor.getComponentCount() - 1);

        menu.Contenedor.addComponentListener(new java.awt.event.ComponentAdapter() {
            @Override
            public void componentResized(java.awt.event.ComponentEvent evt) {
                fondo2.repaint();
            }
        });
        frm.setVisible(false);

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
        if (e.getSource() == frm.btncerrarformulario) {
            cerrar();
        }
        if (e.getSource() == frm.btnguardar) {

            if (Validar()) {
                frm.txtbuscar.setEnabled(true);
                em.setMarca(frm.txtmarca.getText());
                em.setModelo(frm.txtmodelo.getText());
                int anio = Integer.parseInt(frm.txtanio.getText());
                if (anio < 1900 || anio > 2155) {
                    JOptionPane.showMessageDialog(null, "Ingrese un año válido entre 1900 y 2155.");
                    return;
                }
                em.setAnio(anio);
                em.setPrecio(Double.parseDouble(frm.txtprecio.getText()));
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
                em.setMarca(frm.txtmarca.getText());
                em.setModelo(frm.txtmodelo.getText());
                int anio = Integer.parseInt(frm.txtanio.getText());
                if (anio < 1900 || anio > 2155) {
                    JOptionPane.showMessageDialog(null, "Ingrese un año válido entre 1900 y 2155.");
                    return;
                }
                em.setAnio(anio);
                em.setPrecio(Double.parseDouble(frm.txtprecio.getText()));
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
            if (frm.JAutos.getSelectedRow() == -1) {
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
        char c = e.getKeyChar();

        if (e.getSource() == frm.txtanio) {
            if (!Character.isDigit(c)) {
                e.consume();
            }
        } else if (e.getSource() == frm.txtprecio) {
            if (!Character.isDigit(c) && c != '.') {
                e.consume();
            }

            if (c == '.' && frm.txtprecio.getText().contains(".")) {
                e.consume(); 
            }
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getSource() == frm.txtbuscar) {
            String texto = frm.txtbuscar.getText().trim();
            em.setMarca(texto);

            if (texto.isEmpty()) {
                Mostrar();
                return;
            }

            String[] columnas = {"ID", "Marca", "Modelo", "Año", "Precio", "Estado"};
            DefaultTableModel tabla = new DefaultTableModel(null, columnas) {
                @Override
                public boolean isCellEditable(int row, int col) {
                    return false;
                }
            };

            try {
                List<ClsAutos> resultados = sqlemp.ListarBussqueda(texto);

                for (ClsAutos u : resultados) {
                    Object[] fila = {
                        u.getId(),
                        u.getMarca(),
                        u.getModelo(),
                        u.getAnio(),
                        u.getPrecio(),
                        u.getEstado()
                    };
                    tabla.addRow(fila);
                }

                frm.JAutos.setModel(tabla);

                DefaultTableCellRenderer Alinear = new DefaultTableCellRenderer();
                Alinear.setHorizontalAlignment(SwingConstants.RIGHT);
                if (frm.JAutos.getColumnCount() >= 7) {
                    for (int i = 4; i < 7; i++) {
                        frm.JAutos.getColumnModel().getColumn(i).setCellRenderer(Alinear);
                    }
                }

            } catch (Exception ex) {
                Logger.getLogger(CtrlClientes.class.getName())
                        .log(Level.SEVERE, null, ex);
            }
        }
    }

    public void Mostrar() {
        List notas;
        ClsAutos eqip;

        String[] columnas = {"ID", "Marca", "Modelo", "Año", "Precio", "Estado"};
        Object[] datos = new Object[6];
        DefaultTableModel tabla = new DefaultTableModel(null, columnas) {
            @Override
            public boolean isCellEditable(int i, int j) {
                if (i == 7) {
                    return true;
                } else {
                    return false;
                }
            }
        };

        try {

            notas = sqlemp.MostrarClsAuto();
            if (!notas.isEmpty()) {
                for (int i = 0; i < notas.size(); i++) {

                    eqip = (ClsAutos) notas.get(i);
                    datos[0] = eqip.getId();
                    datos[1] = eqip.getMarca();
                    datos[2] = eqip.getModelo();
                    datos[3] = eqip.getAnio();
                    datos[4] = eqip.getPrecio();
                    datos[5] = eqip.getEstado();

                    tabla.addRow(datos);
                }
                frm.JAutos.setModel(tabla);

                DefaultTableCellRenderer Alinear = new DefaultTableCellRenderer();
                Alinear.setHorizontalAlignment(SwingConstants.RIGHT);
                if (frm.JAutos.getColumnCount() >= 7) {
                    for (int i = 4; i < 7; i++) {
                        frm.JAutos.getColumnModel().getColumn(i).setCellRenderer(Alinear);
                    }
                }
            } else {
                JOptionPane.showMessageDialog(null, "No encontro información");
                Limpiar();
                frm.txtbuscar.setText(null);
            }

        } catch (Exception ex) {
            Logger.getLogger(CtrlClientes.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void Limpiar() {

        frm.txtid.setText(null);
        frm.txtmarca.setText(null);
        frm.txtanio.setText(null);
        frm.txtmodelo.setText(null);
        frm.txtprecio.setText(null);
        frm.txtbuscar.setText(null);

    }

    private boolean Validar() {

        if ("".equals(frm.txtmarca.getText())) {

            JOptionPane.showMessageDialog(null, "Debe ingresar datos");
            return false;

        }
        if ("".equals(frm.txtmodelo.getText())) {

            JOptionPane.showMessageDialog(null, "Debe ingresar datos");
            return false;

        }
        if ("".equals(frm.txtanio.getText())) {

            JOptionPane.showMessageDialog(null, "Debe ingresar datos");
            return false;

        }
        if ("".equals(frm.txtprecio.getText())) {

            JOptionPane.showMessageDialog(null, "Debe ingresar datos");
            return false;

        }

        return true;
    }
}
