/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import javaBeans.Cliente;
import javaBeans.Empleado;
import javaBeans.Equipo;
import javaBeans.OrdenTrabajo;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import model.ClienteModel;
import model.EmpleadoModel;
import model.EquipoModel;
import model.OrdenTrabajoModel;
import view.FrmClientesView;
import view.FrmEmpleadosView;
import view.FrmEquiposView;
import view.FrmNuevoClienteView;
import view.FrmNuevoEmpleadoView;
import view.FrmNuevoEquipoView;
import view.FrmOrdenTrabajoView;
import view.FrmNuevoOrdenTrabajoView;

/**
 *
 * @author Arthur
 */
public class OrdenTrabajoController implements WindowListener, ActionListener, KeyListener, MouseListener, FocusListener {

    //Atributos
    private OrdenTrabajo ordenTrabajo;
    private OrdenTrabajoModel ordenTrabajoModel;
    private FrmOrdenTrabajoView frmOrdenA;
    private FrmNuevoOrdenTrabajoView frmOrdenB;

    private DefaultTableModel modelo;
    private int opcion;

    //JavaBeans
    Equipo equipo = new Equipo();
    Cliente cliente = new Cliente();
    Empleado empleado = new Empleado();

    //Models
    EquipoModel equipoModel = new EquipoModel();
    ClienteModel clienteModel = new ClienteModel();
    EmpleadoModel empleadoModel = new EmpleadoModel();

    //Frames A
    FrmEquiposView frmEquiposA = new FrmEquiposView(null, true);
    FrmClientesView frmClientesA = new FrmClientesView(null, true);
    FrmEmpleadosView frmEmpleadosA = new FrmEmpleadosView(null, true);

    //Frames B
    FrmNuevoEquipoView frmEquiposB = new FrmNuevoEquipoView(null, true);
    FrmNuevoClienteView frmClientesB = new FrmNuevoClienteView(null, true);
    FrmNuevoEmpleadoView frmEmpleadosB = new FrmNuevoEmpleadoView(null, true);

    //Constructor
    public OrdenTrabajoController(OrdenTrabajo ordenTrabajo, OrdenTrabajoModel ordenTrabajoModel, FrmOrdenTrabajoView frmOrdenA, FrmNuevoOrdenTrabajoView frmOrdenB) {
        this.ordenTrabajo = ordenTrabajo;
        this.ordenTrabajoModel = ordenTrabajoModel;
        this.frmOrdenA = frmOrdenA;
        this.frmOrdenB = frmOrdenB;

        //Componentes del FrameA
        this.frmOrdenA.setLocationRelativeTo(null);
        this.frmOrdenA.addWindowListener(this);
        this.frmOrdenA.lblInfo.addMouseListener(this);
        this.frmOrdenA.lblTexto.setVisible(false);
        this.frmOrdenA.tblTabla.requestFocus();
        this.frmOrdenA.tblTabla.getTableHeader().setResizingAllowed(false);
        this.frmOrdenA.tblTabla.getTableHeader().setReorderingAllowed(false);
        this.frmOrdenA.btnNuevo.addActionListener(this);
        this.frmOrdenA.btnEditar.addActionListener(this);
        this.frmOrdenA.btnEliminar.addActionListener(this);
        this.frmOrdenA.txtBuscar.addKeyListener(this);
        this.frmOrdenA.txtBuscar.addFocusListener(this);

        //Componentes del FrameB
        this.frmOrdenB.setLocationRelativeTo(null);
        this.frmOrdenB.btnGuardar.addActionListener(this);
        this.frmOrdenB.btnLimpiar.addActionListener(this);
        this.frmOrdenB.btnCancelar.addActionListener(this);
        this.frmOrdenB.btnBuscarCliente.addActionListener(this);
        this.frmOrdenB.btnBuscarEmpleado.addActionListener(this);
        this.frmOrdenB.btnBuscarEquipo.addActionListener(this);
    }

    //Metodos
    public void limpiarFrmB() {
        frmOrdenB.txtCliente.setText(null);
        frmOrdenB.txtNombreCliente.setText(null);
        frmOrdenB.txtEmpleado.setText(null);
        frmOrdenB.txtNombreEmpleado.setText(null);
        frmOrdenB.txtEquipo.setText(null);
        frmOrdenB.txtMarca.setText(null);
        frmOrdenB.txtTotal.setText(null);
        frmOrdenB.btnBuscarCliente.requestFocus();
    }

    @Override
    public void focusGained(FocusEvent e) {
        if (frmOrdenA.txtBuscar.getText().equals("Filtrar por OC o ID Empleado")) {
            frmOrdenA.txtBuscar.setText(null);
            frmOrdenA.txtBuscar.setForeground(Color.BLACK);
        }
    }

    @Override
    public void focusLost(FocusEvent e) {
        if (frmOrdenA.txtBuscar.getText().equals("")) {
            frmOrdenA.txtBuscar.setText("Filtrar por OC o ID Empleado");
            frmOrdenA.txtBuscar.setForeground(Color.LIGHT_GRAY);
        }
    }

    @Override
    public void windowActivated(WindowEvent e) {
        String columnas[] = {"ID Orden", "ID Cliente", "ID Empleado", "ID Equipo", "Total"};
        modelo = new DefaultTableModel(null, columnas) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        frmOrdenA.txtBuscar.setText("Filtrar por OC o ID Empleado");
        frmOrdenA.txtBuscar.setForeground(Color.LIGHT_GRAY);
        frmOrdenA.tblTabla.requestFocus();
        ResultSet rs = ordenTrabajoModel.mostrarOrdenTrabajo();

        try {

            if (rs.isFirst()) {
                do {
                    ordenTrabajo = new OrdenTrabajo(rs.getInt(1), rs.getInt(2), rs.getString(3), rs.getInt(4), rs.getInt(5));
                    Object newRow[] = {ordenTrabajo.getIdOrdenTrabajo(), ordenTrabajo.getIdCliente(), ordenTrabajo.getIdEmpleado(),
                        ordenTrabajo.getIdEquipo(), ordenTrabajo.getTotal()};
                    modelo.addRow(newRow);
                } while (rs.next());
            }
            frmOrdenA.tblTabla.setModel(modelo);
            frmOrdenA.lblCantidadRegistros.setText("Cantidad de Registros: " + modelo.getRowCount());

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(frmOrdenA, ex.getMessage());
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        String columnas[] = {"ID Orden", "ID Cliente", "ID Empleado", "ID Equipo", "Total"};
        modelo = new DefaultTableModel(null, columnas) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        ResultSet rs = ordenTrabajoModel.filtrarOrdenTrabajo(frmOrdenA.txtBuscar.getText().trim());

        try {

            if (rs.isFirst()) {
                do {
                    ordenTrabajo = new OrdenTrabajo(rs.getInt(1), rs.getInt(2), rs.getString(3), rs.getInt(4), rs.getInt(5));
                    Object newRow[] = {ordenTrabajo.getIdOrdenTrabajo(), ordenTrabajo.getIdCliente(), ordenTrabajo.getIdEmpleado(),
                        ordenTrabajo.getIdEquipo(), ordenTrabajo.getTotal()};
                    modelo.addRow(newRow);
                } while (rs.next());
            }
            frmOrdenA.tblTabla.setModel(modelo);
            frmOrdenA.lblCantidadRegistros.setText("Cantidad de Registros: " + modelo.getRowCount());

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(frmOrdenA, ex.getMessage());
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (frmOrdenA.isActive()) { //Botones del frmOrdenA
            switch (e.getActionCommand()) {

                case "Nuevo":
                    opcion = 1;
                    frmOrdenB.setTitle("Registro de OrdenTrabajo");
                    limpiarFrmB();
                    frmOrdenB.setVisible(true);
                    break;

                case "Editar":
                    if (frmOrdenA.tblTabla.getSelectedRowCount() == 1) {

                        opcion = 2;
                        frmOrdenB.setTitle("Actualización de OrdenTrabajo");
                        int fila = frmOrdenA.tblTabla.getSelectedRow();
                        ordenTrabajo.setIdOrdenTrabajo(Integer.parseInt(frmOrdenA.tblTabla.getValueAt(fila, 0).toString()));

                        frmOrdenB.txtCliente.setText(frmOrdenA.tblTabla.getValueAt(fila, 0).toString());
                        frmOrdenB.txtEmpleado.setText(frmOrdenA.tblTabla.getValueAt(fila, 1).toString());
                        frmOrdenB.txtEquipo.setText(frmOrdenA.tblTabla.getValueAt(fila, 2).toString());
                        frmOrdenB.txtTotal.setText(frmOrdenA.tblTabla.getValueAt(fila, 3).toString());

                        frmOrdenB.setVisible(true);

                    } else {
                        JOptionPane.showMessageDialog(frmOrdenA, "Debe seleccionar un registro");
                    }
                    break;

                case "Eliminar":
                    if (frmOrdenA.tblTabla.getSelectedRowCount() == 1) {

                        int fila = frmOrdenA.tblTabla.getSelectedRow();
                        ordenTrabajo.setIdOrdenTrabajo(Integer.parseInt(frmOrdenA.tblTabla.getValueAt(fila, 0).toString()));
                        int resp = JOptionPane.showConfirmDialog(frmOrdenA, "¿Desea eliminar el ordenTrabajo " + ordenTrabajo.getIdOrdenTrabajo() + "?", "Seleccionar una Opción", 0);

                        if (resp == 0) {
                            if (ordenTrabajoModel.eliminarOrdenTrabajo(ordenTrabajo.getIdOrdenTrabajo())) {
                                JOptionPane.showMessageDialog(frmOrdenA, "OrdenTrabajo eliminado");
                            } else {
                                JOptionPane.showMessageDialog(frmOrdenA, "Error al eliminar");
                            }
                        }

                    } else {
                        JOptionPane.showMessageDialog(frmOrdenA, "Debe seleccionar un registro");
                    }
                    break;

            }
        }

        if (frmOrdenB.isActive()) { //Botones del frmOrdenB
            switch (e.getActionCommand()) {

                case "Guardar":
                    try {
                    ordenTrabajo.setIdCliente(Integer.parseInt(frmOrdenB.txtCliente.getText().trim()));
                    ordenTrabajo.setIdEmpleado(frmOrdenB.txtEmpleado.getText().trim());
                    ordenTrabajo.setIdEquipo(Integer.parseInt(frmOrdenB.txtEquipo.getText().trim()));
                    ordenTrabajo.setTotal(Integer.parseInt(frmOrdenB.txtTotal.getText().trim()));
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(frmOrdenB, "Error de tipo de datos");
                }

                if (opcion == 1) {
                    if (ordenTrabajoModel.insertarOrdenTrabajo(ordenTrabajo)) {
                        JOptionPane.showMessageDialog(frmOrdenB, "OrdenTrabajo Registrado");
                        frmOrdenB.dispose();
                    } else {
                        JOptionPane.showMessageDialog(frmOrdenB, "Error al guardar");
                    }
                } else {
                    if (ordenTrabajoModel.modificarOrdenTrabajo(ordenTrabajo)) {
                        JOptionPane.showMessageDialog(frmOrdenB, "OrdenTrabajo Actualizado");
                        frmOrdenB.dispose();
                    } else {
                        JOptionPane.showMessageDialog(frmOrdenB, "Error al editar");
                    }
                }
                break;

                case "Limpiar":
                    limpiarFrmB();
                    break;

                case "Cancelar":
                    frmOrdenB.dispose();
                    break;

            }

            if (e.getSource() == frmOrdenB.btnBuscarCliente) {
                ClienteController clienteController = new ClienteController(cliente, clienteModel, frmClientesA, frmClientesB);

                frmClientesA.lblInfo.setVisible(true);
                frmClientesA.btnNuevo.setVisible(false);
                frmClientesA.btnEditar.setVisible(false);
                frmClientesA.btnEliminar.setVisible(false);
                frmClientesA.tblTabla.addMouseListener(this);

                frmClientesA.setLocationRelativeTo(frmOrdenB.btnBuscarEquipo);
                frmClientesA.setVisible(true);
            }
            if (e.getSource() == frmOrdenB.btnBuscarEmpleado) {

            }
            if (e.getSource() == frmOrdenB.btnBuscarEquipo) {

            }
        }

    }

    @Override
    public void windowOpened(WindowEvent e) {
    }

    @Override
    public void windowClosing(WindowEvent e) {
    }

    @Override
    public void windowClosed(WindowEvent e) {
    }

    @Override
    public void windowIconified(WindowEvent e) {
    }

    @Override
    public void windowDeiconified(WindowEvent e) {
    }

    @Override
    public void windowDeactivated(WindowEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getClickCount() == 2) {

            if (e.getSource() == frmClientesA.tblTabla) {

                int fila = frmClientesA.tblTabla.getSelectedRow();
                frmOrdenB.txtCliente.setText(frmClientesA.tblTabla.getValueAt(fila, 0).toString());
                frmOrdenB.txtNombreCliente.setText(frmClientesA.tblTabla.getValueAt(fila, 1).toString());
                frmClientesA.dispose();

            }

        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        frmOrdenA.lblTexto.setVisible(true);
    }

    @Override
    public void mouseExited(MouseEvent e) {
        frmOrdenA.lblTexto.setVisible(false);
    }

}
