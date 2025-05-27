/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;

import Modelo.ClsClientes;
import Modelo.ClsConsultaClientes;
import Vista.frmCliente;
import Vista.frmPrincipal;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.security.Principal;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

public class CtrlClientes implements ActionListener, KeyListener {

    ClsClientes em;
    ClsConsultaClientes sqlemp;
    frmCliente frm;
    frmPrincipal menu = new frmPrincipal();

    public CtrlClientes(ClsClientes em, ClsConsultaClientes sqlemp, frmCliente frm, frmPrincipal menu) {
        this.em = em;
        this.sqlemp = sqlemp;
        this.frm = frm;
        this.menu = menu;
        this.frm.btnguardar.addActionListener(this);
        this.frm.btneditar.addActionListener(this);
        this.frm.btneliminar.addActionListener(this);
        this.frm.btncerrarformulario.addActionListener(this);
        this.frm.txtbuscar.addKeyListener(this);
        this.frm.txtcedula.addKeyListener(this);
        this.frm.txttelf.addKeyListener(this);
        this.frm.btnactualizar.addActionListener((ActionListener) this);

        this.frm.tbClientes.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {

                DefaultTableModel modelo = (DefaultTableModel) frm.tbClientes.getModel();
                int fila;
                fila = frm.tbClientes.getSelectedRow();
                frm.txtbuscar.setEnabled(false);
                frm.txtid.setText(modelo.getValueAt(fila, 0).toString());
                frm.txtcedula.setText(modelo.getValueAt(fila, 1).toString());
                frm.txtnombre.setText(modelo.getValueAt(fila, 2).toString());
                frm.txtapellido.setText(modelo.getValueAt(fila, 3).toString());
                frm.txtcorreo.setText(modelo.getValueAt(fila, 4).toString());
                frm.txttelf.setText(modelo.getValueAt(fila, 5).toString());

                frm.btnguardar.setEnabled(false);
                frm.btneditar.setEnabled(true);
                frm.btneliminar.setEnabled(true);
            }
        });
    }

    public void Iniciar() {
        frm.setTitle("Gestion de Clientes");
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
                em.setCedula(frm.txtcedula.getText());
                em.setNombre(frm.txtnombre.getText());
                em.setApellido(frm.txtapellido.getText());
                em.setCorreo(frm.txtcorreo.getText());
                em.setTelefono(frm.txttelf.getText());
                if (sqlemp.ExisteCliente(em)) {
                    JOptionPane.showMessageDialog(null, "El cliente con el nùmero de cédula " + em.getCedula() + " ya existe");
                } else {
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

        }
        if (e.getSource() == frm.btneditar) {
            if (Validar()) {

                em.setId(Integer.parseInt(frm.txtid.getText()));
                em.setCedula(frm.txtcedula.getText());
                em.setNombre(frm.txtnombre.getText());
                em.setApellido(frm.txtapellido.getText());
                em.setCorreo(frm.txtcorreo.getText());
                em.setTelefono(frm.txttelf.getText());

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
            if (frm.tbClientes.getSelectedRow() == -1) {
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

        if (e.getSource() == frm.txtcedula) {
            if (!Character.isDigit(c)
                    || frm.txtcedula.getText().length() >= 10) {
                e.consume();
            }
        } else if (e.getSource() == frm.txttelf) {
            if (!Character.isDigit(c)
                    || frm.txttelf.getText().length() >= 10) {
                e.consume();
            }
        } else if (e.getSource() == frm.txtbuscar) {
            if (!Character.isDigit(c)
                    || frm.txtbuscar.getText().length() >= 10) {
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
            em.setCedula(texto);

            if (texto.isEmpty()) {
                Mostrar();
                return;
            }

            String[] columnas = {"ID", "Cedula", "Nombre", "Apellido", "Correo", "Telefono"};
            DefaultTableModel tabla = new DefaultTableModel(null, columnas) {
                @Override
                public boolean isCellEditable(int row, int col) {
                    return false;
                }
            };

            try {
                List<ClsClientes> resultados = sqlemp.ListarBussqueda(texto);

                for (ClsClientes u : resultados) {
                    Object[] fila = {
                        u.getId(),
                        u.getCedula(),
                        u.getNombre(),
                        u.getApellido(),
                        u.getCorreo(),
                        u.getTelefono()
                    };
                    tabla.addRow(fila);
                }

                frm.tbClientes.setModel(tabla);

                DefaultTableCellRenderer Alinear = new DefaultTableCellRenderer();
                Alinear.setHorizontalAlignment(SwingConstants.RIGHT);
                if (frm.tbClientes.getColumnCount() >= 7) {
                    for (int i = 4; i < 7; i++) {
                        frm.tbClientes.getColumnModel().getColumn(i).setCellRenderer(Alinear);
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
        ClsClientes eqip;

        String[] columnas = {"ID", "Cedula", "Nombre", "Apellido", "Correo", "Telefono"};
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

            notas = sqlemp.MostrarClsClientes();
            if (!notas.isEmpty()) {
                for (int i = 0; i < notas.size(); i++) {

                    eqip = (ClsClientes) notas.get(i);
                    datos[0] = eqip.getId();
                    datos[1] = eqip.getCedula();
                    datos[2] = eqip.getNombre();
                    datos[3] = eqip.getApellido();
                    datos[4] = eqip.getCorreo();
                    datos[5] = eqip.getTelefono();

                    tabla.addRow(datos);
                }
                frm.tbClientes.setModel(tabla);

                DefaultTableCellRenderer Alinear = new DefaultTableCellRenderer();
                Alinear.setHorizontalAlignment(SwingConstants.RIGHT);
                if (frm.tbClientes.getColumnCount() >= 7) {
                    for (int i = 4; i < 7; i++) {
                        frm.tbClientes.getColumnModel().getColumn(i).setCellRenderer(Alinear);
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
        frm.txtapellido.setText(null);
        frm.txtcedula.setText(null);
        frm.txtnombre.setText(null);
        frm.txtcorreo.setText(null);
        frm.txttelf.setText(null);
        frm.txtbuscar.setText(null);

    }

    private boolean Validar() {

        if ("".equals(frm.txtnombre.getText())) {

            JOptionPane.showMessageDialog(null, "Debe ingresar datos");
            return false;

        }
        if ("".equals(frm.txtapellido.getText())) {

            JOptionPane.showMessageDialog(null, "Debe ingresar datos");
            return false;

        }
        if ("".equals(frm.txtcorreo.getText())) {

            JOptionPane.showMessageDialog(null, "Debe ingresar datos");
            return false;

        }
        if ("".equals(frm.txttelf.getText())) {

            JOptionPane.showMessageDialog(null, "Debe ingresar datos");
            return false;

        }

        return true;
    }
}
