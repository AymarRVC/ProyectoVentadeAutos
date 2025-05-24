/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;

import Modelo.ClsAutos;
import Modelo.ClsClientes;
import Modelo.ClsConsultaAutos;
import Modelo.ClsConsultaClientes;
import Modelo.ClsConsultaVentas;
import Modelo.ClsVentas;
import Vista.frmPrincipal;
import Vista.frmVentas;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

public class CtrlVentas implements ActionListener, KeyListener {

    ClsVentas em;
    ClsConsultaVentas sqlemp;
    frmVentas frm;
    frmPrincipal menu = new frmPrincipal();

    public CtrlVentas(ClsVentas em, ClsConsultaVentas sqlemp, frmVentas frm, frmPrincipal menu) {
        this.em = em;
        this.sqlemp = sqlemp;
        this.frm = frm;
        this.menu = menu;
        this.frm.btnguardar.addActionListener(this);
        this.frm.btneditar.addActionListener(this);
        this.frm.btneliminar.addActionListener(this);
        this.frm.btncerrarformulario.addActionListener(this);
        this.frm.cmbautos.addActionListener((ActionListener) this);
        this.frm.cmbclientes.addActionListener((ActionListener) this);
        this.frm.txtbuscar.addKeyListener(this);
        this.frm.txtprecio.addKeyListener(this);
        this.frm.btnactualizar.addActionListener((ActionListener) this);

        this.frm.JVentas.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {

                DefaultTableModel modelo = (DefaultTableModel) frm.JVentas.getModel();
                int fila;
                fila = frm.JVentas.getSelectedRow();
                frm.txtbuscar.setEnabled(false);
                frm.txtid.setText(modelo.getValueAt(fila, 0).toString());
                frm.cmbautos.setSelectedItem(modelo.getValueAt(fila, 1).toString());
                frm.cmbclientes.setSelectedItem(modelo.getValueAt(fila, 2).toString());
                String fecha = String.valueOf(modelo.getValueAt(fila, 3));
                frm.txtprecio.setText(modelo.getValueAt(fila, 4).toString());

                SimpleDateFormat s = new SimpleDateFormat("yyyy-MM-dd");
                java.util.Date fechasa;
                try {
                    fechasa = (java.util.Date) s.parse(fecha);

                    frm.fecha.setDate(fechasa);

                } catch (ParseException ex) {
                    Logger.getLogger(CtrlVentas.class.getName()).log(Level.SEVERE, null, ex);
                }

                frm.btnguardar.setEnabled(false);
                frm.btneditar.setEnabled(true);
                frm.btneliminar.setEnabled(true);
            }
        });
    }

    public void ListarAutos() throws Exception {
        ClsConsultaAutos conse = new ClsConsultaAutos();
        List objList = conse.MostrarClsAuto();
        Iterator iterador = objList.iterator();
        while (iterador.hasNext()) {
            ClsAutos eml = (ClsAutos) iterador.next();
            frm.cmbautos.addItem(eml);
        }
    }

    public void ListarClientes() throws Exception {
        ClsConsultaClientes conse = new ClsConsultaClientes();
        List objList = conse.MostrarClsClientes();
        Iterator iterador = objList.iterator();
        while (iterador.hasNext()) {
            ClsClientes eml = (ClsClientes) iterador.next();
            frm.cmbclientes.addItem(eml);
        }
    }

    public void Iniciar() throws Exception {
        frm.setTitle("Gestion de Ventas");
        frm.txtid.setVisible(false);
        frm.btneditar.setEnabled(false);
        frm.btneliminar.setEnabled(false);
        Mostrar();
        ListarAutos();
        ListarClientes();
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
                ClsAutos iol = (ClsAutos) frm.cmbautos.getSelectedItem();
                em.setId_auto(iol.getId());
                ClsClientes iol2 = (ClsClientes) frm.cmbclientes.getSelectedItem();
                em.setId_cliente(iol2.getId());
                String fecha;
                java.util.Date date = new java.util.Date();
                SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");
                fecha = f.format(frm.fecha.getDate());

                try {

                    em.setFecha(new SimpleDateFormat("yyyy-MM-dd").parse(fecha));
                } catch (ParseException ex) {
                    Logger.getLogger(CtrlVentas.class.getName()).log(Level.SEVERE, null, ex);
                }
                em.setPrecios(Double.parseDouble(frm.txtprecio.getText()));

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
                ClsAutos iol = (ClsAutos) frm.cmbautos.getSelectedItem();
                em.setId_auto(iol.getId());
                ClsClientes iol2 = (ClsClientes) frm.cmbclientes.getSelectedItem();
                em.setId_cliente(iol2.getId());
                String fecha;
                java.util.Date date = new java.util.Date();
                SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");
                fecha = f.format(frm.fecha.getDate());

                try {

                    em.setFecha(new SimpleDateFormat("yyyy-MM-dd").parse(fecha));
                } catch (ParseException ex) {
                    Logger.getLogger(CtrlVentas.class.getName()).log(Level.SEVERE, null, ex);
                }
                em.setPrecios(Double.parseDouble(frm.txtprecio.getText()));

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
            if (frm.JVentas.getSelectedRow() == -1) {
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
        if (e.getSource() == frm.cmbautos) {
            ClsAutos autoSeleccionado = (ClsAutos) frm.cmbautos.getSelectedItem();
            if (autoSeleccionado != null) {
                frm.txtprecio.setText(String.valueOf(autoSeleccionado.getPrecio()));
            }
        }

    }

    @Override
    public void keyTyped(KeyEvent e) {
        char c = e.getKeyChar();

        if (e.getSource() == frm.txtprecio) {
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
            em.setAuto(texto);

            if (texto.isEmpty()) {
                Mostrar();
                return;
            }

            String[] columnas = {"ID", "Auto", "Cliente", "Fecha", "Precio"};
            DefaultTableModel tabla = new DefaultTableModel(null, columnas) {
                @Override
                public boolean isCellEditable(int row, int col) {
                    return false;
                }
            };

            try {
                List<ClsVentas> resultados = sqlemp.ListarBussqueda(texto);
                ClsAutos clsAutos;
                ClsClientes clsClientes;
                for (ClsVentas u : resultados) {
                    clsAutos = sqlemp.BuscarAutos(u);
                    clsClientes = sqlemp.BuscarClientes(u);
                    Object[] fila = {
                        u.getId(),
                        clsAutos.getMarca(),
                        clsClientes.toString(),
                        u.getFecha(),
                        u.getPrecios()
                    };
                    tabla.addRow(fila);
                }

                frm.JVentas.setModel(tabla);

                DefaultTableCellRenderer Alinear = new DefaultTableCellRenderer();
                Alinear.setHorizontalAlignment(SwingConstants.RIGHT);
                if (frm.JVentas.getColumnCount() >= 7) {
                    for (int i = 4; i < 7; i++) {
                        frm.JVentas.getColumnModel().getColumn(i).setCellRenderer(Alinear);
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
        ClsVentas eqip;
        ClsAutos clsAutos;
        ClsClientes clsClientes;

        String[] columnas = {"ID", "Auto", "Cliente", "Fecha", "Precio"};
        Object[] datos = new Object[5];
        DefaultTableModel tabla = new DefaultTableModel(null, columnas) {
            @Override
            public boolean isCellEditable(int i, int j) {
                if (i == 6) {
                    return true;
                } else {
                    return false;
                }
            }
        };
        ClsAutos obj1 = (ClsAutos) frm.cmbautos.getSelectedItem();
        ClsClientes obj2 = (ClsClientes) frm.cmbclientes.getSelectedItem();

        try {

            notas = sqlemp.MostrarClsVentas();
            if (!notas.isEmpty()) {
                for (int i = 0; i < notas.size(); i++) {

                    eqip = (ClsVentas) notas.get(i);
                    datos[0] = eqip.getId();
                    clsAutos = sqlemp.BuscarAutos(eqip);
                    datos[1] = clsAutos.getMarca();
                    clsClientes = sqlemp.BuscarClientes(eqip);
                    datos[2] = clsClientes.toString();
                    datos[3] = eqip.getFecha();
                    datos[4] = eqip.getPrecios();

                    tabla.addRow(datos);
                }
                frm.JVentas.setModel(tabla);

                DefaultTableCellRenderer Alinear = new DefaultTableCellRenderer();
                Alinear.setHorizontalAlignment(SwingConstants.RIGHT);
                if (frm.JVentas.getColumnCount() >= 7) {
                    for (int i = 4; i < 7; i++) {
                        frm.JVentas.getColumnModel().getColumn(i).setCellRenderer(Alinear);
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
        frm.txtprecio.setText(null);
        frm.txtbuscar.setText(null);

    }

    private boolean Validar() {

        if ("".equals(frm.fecha.getDate())) {

            JOptionPane.showMessageDialog(null, "Debe ingresar datos");
            return false;

        }

        return true;
    }
}
