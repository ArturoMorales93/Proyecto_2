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
import javaBeans.DetalleOrden;
import javaBeans.Empleado;
import javaBeans.Equipo;
import javaBeans.OrdenTrabajo;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import model.ClienteModel;
import model.DetalleOrdenModel;
import model.EmpleadoModel;
import model.EquipoModel;
import model.OrdenTrabajoModel;
import view.FrmClientesView;
import view.FrmDetalleOrdenView;
import view.FrmEmpleadosView;
import view.FrmEquiposView;
import view.FrmNuevoClienteView;
import view.FrmNuevoDetalleOrdenView;
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
    private FrmOrdenTrabajoView frmOrdenTrabajoA;
    private FrmNuevoOrdenTrabajoView frmOrdenTrabajoB;

    private DefaultTableModel modelo;
    private int opcion;

    //JavaBeans
    Equipo equipo = new Equipo();
    Cliente cliente = new Cliente();
    Empleado empleado = new Empleado();
    DetalleOrden detalleOrden = new DetalleOrden();

    //Models
    EquipoModel equipoModel = new EquipoModel();
    ClienteModel clienteModel = new ClienteModel();
    EmpleadoModel empleadoModel = new EmpleadoModel();
    DetalleOrdenModel detalleOrdenModel = new DetalleOrdenModel();

    //Frames A
    FrmEquiposView frmEquiposA = new FrmEquiposView(null, true);
    FrmClientesView frmClientesA = new FrmClientesView(null, true);
    FrmEmpleadosView frmEmpleadosA = new FrmEmpleadosView(null, true);
    FrmDetalleOrdenView frmDetalleOrdenA = new FrmDetalleOrdenView(null, true);

    //Frames B
    FrmNuevoEquipoView frmEquiposB = new FrmNuevoEquipoView(null, true);
    FrmNuevoClienteView frmClientesB = new FrmNuevoClienteView(null, true);
    FrmNuevoEmpleadoView frmEmpleadosB = new FrmNuevoEmpleadoView(null, true);
    FrmNuevoDetalleOrdenView frmDetalleOrdenB = new FrmNuevoDetalleOrdenView(null, true);

    //Controllers
    ClienteController clienteController = new ClienteController(cliente, clienteModel, frmClientesA, frmClientesB);

    //Constructor
    public OrdenTrabajoController(OrdenTrabajo ordenTrabajo, OrdenTrabajoModel ordenTrabajoModel, FrmOrdenTrabajoView frmOrdenTrabajoA, FrmNuevoOrdenTrabajoView frmOrdenTrabajoB) {
        this.ordenTrabajo = ordenTrabajo;
        this.ordenTrabajoModel = ordenTrabajoModel;
        this.frmOrdenTrabajoA = frmOrdenTrabajoA;
        this.frmOrdenTrabajoB = frmOrdenTrabajoB;

        //Componentes del FrameA
        this.frmOrdenTrabajoA.setLocationRelativeTo(null);
        this.frmOrdenTrabajoA.addWindowListener(this);
        this.frmOrdenTrabajoA.lblInfo.addMouseListener(this);
        this.frmOrdenTrabajoA.lblTexto.setVisible(false);
        this.frmOrdenTrabajoA.tblTabla.requestFocus();
        this.frmOrdenTrabajoA.tblTabla.getTableHeader().setResizingAllowed(false);
        this.frmOrdenTrabajoA.tblTabla.getTableHeader().setReorderingAllowed(false);
        this.frmOrdenTrabajoA.btnNuevo.addActionListener(this);
        this.frmOrdenTrabajoA.btnEditar.addActionListener(this);
        this.frmOrdenTrabajoA.btnEliminar.addActionListener(this);
        this.frmOrdenTrabajoA.txtBuscar.addKeyListener(this);
        this.frmOrdenTrabajoA.txtBuscar.addFocusListener(this);

        //Componentes del FrameB
        this.frmOrdenTrabajoB.setLocationRelativeTo(null);
        this.frmOrdenTrabajoB.btnGuardar.addActionListener(this);
        this.frmOrdenTrabajoB.btnLimpiar.addActionListener(this);
        this.frmOrdenTrabajoB.btnCancelar.addActionListener(this);
        this.frmOrdenTrabajoB.btnBuscarCliente.addActionListener(this);
        this.frmOrdenTrabajoB.btnBuscarEmpleado.addActionListener(this);
        this.frmOrdenTrabajoB.btnBuscarEquipo.addActionListener(this);
    }

    //Metodos
    public void limpiarFrmB() {
        frmOrdenTrabajoB.txtCliente.setText(null);
        frmOrdenTrabajoB.txtNombreCliente.setText(null);
        frmOrdenTrabajoB.txtEmpleado.setText(null);
        frmOrdenTrabajoB.txtNombreEmpleado.setText(null);
        frmOrdenTrabajoB.txtEquipo.setText(null);
        frmOrdenTrabajoB.txtMarca.setText(null);
        frmOrdenTrabajoB.txtTotal.setText(null);
        frmOrdenTrabajoB.btnBuscarCliente.requestFocus();
    }

    @Override
    public void focusGained(FocusEvent e) {
        if (frmOrdenTrabajoA.txtBuscar.getText().equals("Filtrar por OC o ID Empleado")) {
            frmOrdenTrabajoA.txtBuscar.setText(null);
            frmOrdenTrabajoA.txtBuscar.setForeground(Color.BLACK);
        }
    }

    @Override
    public void focusLost(FocusEvent e) {
        if (frmOrdenTrabajoA.txtBuscar.getText().equals("")) {
            frmOrdenTrabajoA.txtBuscar.setText("Filtrar por OC o ID Empleado");
            frmOrdenTrabajoA.txtBuscar.setForeground(Color.LIGHT_GRAY);
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

        frmOrdenTrabajoA.txtBuscar.setText("Filtrar por OC o ID Empleado");
        frmOrdenTrabajoA.txtBuscar.setForeground(Color.LIGHT_GRAY);
        frmOrdenTrabajoA.tblTabla.requestFocus();
        ResultSet rs = ordenTrabajoModel.mostrarOrdenTrabajo();

        try {

            if (rs.isFirst()) {
                do {
                    ordenTrabajo = new OrdenTrabajo(rs.getInt(1), rs.getInt(2), rs.getInt(3), rs.getInt(4), rs.getInt(5));
                    Object newRow[] = {ordenTrabajo.getIdOrdenTrabajo(), ordenTrabajo.getIdCliente(), ordenTrabajo.getIdEmpleado(),
                        ordenTrabajo.getIdEquipo(), ordenTrabajo.getTotal()};
                    modelo.addRow(newRow);
                } while (rs.next());
            }
            frmOrdenTrabajoA.tblTabla.setModel(modelo);
            frmOrdenTrabajoA.lblCantidadRegistros.setText("Cantidad de Registros: " + modelo.getRowCount());

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(frmOrdenTrabajoA, ex.getMessage());
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

        ResultSet rs = ordenTrabajoModel.filtrarOrdenTrabajo(frmOrdenTrabajoA.txtBuscar.getText().trim());

        try {

            if (rs.isFirst()) {
                do {
                    ordenTrabajo = new OrdenTrabajo(rs.getInt(1), rs.getInt(2), rs.getInt(3), rs.getInt(4), rs.getInt(5));
                    Object newRow[] = {ordenTrabajo.getIdOrdenTrabajo(), ordenTrabajo.getIdCliente(), ordenTrabajo.getIdEmpleado(),
                        ordenTrabajo.getIdEquipo(), ordenTrabajo.getTotal()};
                    modelo.addRow(newRow);
                } while (rs.next());
            }
            frmOrdenTrabajoA.tblTabla.setModel(modelo);
            frmOrdenTrabajoA.lblCantidadRegistros.setText("Cantidad de Registros: " + modelo.getRowCount());

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(frmOrdenTrabajoA, ex.getMessage());
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (frmOrdenTrabajoA.isActive()) { //Botones del frmOrdenTrabajoA
            switch (e.getActionCommand()) {

                case "Nuevo":
                    opcion = 1;
                    frmOrdenTrabajoB.setTitle("Registro de OrdenTrabajo");
                    limpiarFrmB();
                    frmOrdenTrabajoB.setVisible(true);
                    break;

                case "Editar":
                    if (frmOrdenTrabajoA.tblTabla.getSelectedRowCount() == 1) {

                        opcion = 2;
                        frmOrdenTrabajoB.setTitle("Actualización de OrdenTrabajo");
                        int fila = frmOrdenTrabajoA.tblTabla.getSelectedRow();
                        ordenTrabajo.setIdOrdenTrabajo(Integer.parseInt(frmOrdenTrabajoA.tblTabla.getValueAt(fila, 0).toString()));

                        frmOrdenTrabajoB.txtCliente.setText(frmOrdenTrabajoA.tblTabla.getValueAt(fila, 1).toString());
                        frmOrdenTrabajoB.txtEmpleado.setText(frmOrdenTrabajoA.tblTabla.getValueAt(fila, 2).toString());
                        frmOrdenTrabajoB.txtEquipo.setText(frmOrdenTrabajoA.tblTabla.getValueAt(fila, 3).toString());
                        frmOrdenTrabajoB.txtTotal.setText(frmOrdenTrabajoA.tblTabla.getValueAt(fila, 4).toString());

                        frmOrdenTrabajoB.setVisible(true);

                    } else {
                        JOptionPane.showMessageDialog(frmOrdenTrabajoA, "Debe seleccionar un registro");
                    }
                    break;

                case "Eliminar":
                    if (frmOrdenTrabajoA.tblTabla.getSelectedRowCount() == 1) {

                        int fila = frmOrdenTrabajoA.tblTabla.getSelectedRow();
                        ordenTrabajo.setIdOrdenTrabajo(Integer.parseInt(frmOrdenTrabajoA.tblTabla.getValueAt(fila, 0).toString()));
                        int resp = JOptionPane.showConfirmDialog(frmOrdenTrabajoA, "¿Desea eliminar el ordenTrabajo " + ordenTrabajo.getIdOrdenTrabajo() + "?", "Seleccionar una Opción", 0);

                        if (resp == 0) {
                            if (ordenTrabajoModel.eliminarOrdenTrabajo(ordenTrabajo.getIdOrdenTrabajo())) {
                                JOptionPane.showMessageDialog(frmOrdenTrabajoA, "OrdenTrabajo eliminado");
                            } else {
                                JOptionPane.showMessageDialog(frmOrdenTrabajoA, "Error al eliminar");
                            }
                        }

                    } else {
                        JOptionPane.showMessageDialog(frmOrdenTrabajoA, "Debe seleccionar un registro");
                    }
                    break;

            }
        }

        if (frmOrdenTrabajoB.isActive()) { //Botones del frmOrdenTrabajoB
            switch (e.getActionCommand()) {

                case "Guardar":
                    try {
                    ordenTrabajo.setIdCliente(Integer.parseInt(frmOrdenTrabajoB.txtCliente.getText().trim()));
                    ordenTrabajo.setIdEmpleado(Integer.parseInt(frmOrdenTrabajoB.txtEmpleado.getText().trim()));
                    ordenTrabajo.setIdEquipo(Integer.parseInt(frmOrdenTrabajoB.txtEquipo.getText().trim()));
                    ordenTrabajo.setTotal(Integer.parseInt(frmOrdenTrabajoB.txtTotal.getText().trim()));
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(frmOrdenTrabajoB, "Error de tipo de datos");
                }

                if (opcion == 1) {
                    if (ordenTrabajoModel.insertarOrdenTrabajo(ordenTrabajo)) {
                        JOptionPane.showMessageDialog(frmOrdenTrabajoB, "OrdenTrabajo Registrado");
                        frmOrdenTrabajoB.dispose();
                    } else {
                        JOptionPane.showMessageDialog(frmOrdenTrabajoB, "Error al guardar");
                    }
                } else {
                    if (ordenTrabajoModel.modificarOrdenTrabajo(ordenTrabajo)) {
                        JOptionPane.showMessageDialog(frmOrdenTrabajoB, "OrdenTrabajo Actualizado");
                        frmOrdenTrabajoB.dispose();
                    } else {
                        JOptionPane.showMessageDialog(frmOrdenTrabajoB, "Error al editar");
                    }
                }
                break;

                case "Limpiar":
                    limpiarFrmB();
                    break;

                case "Cancelar":
                    frmOrdenTrabajoB.dispose();
                    break;

            }

            if (e.getSource() == frmOrdenTrabajoB.btnBuscarCliente) {
                frmClientesA.lblInfo.setVisible(true);
                frmClientesA.btnNuevo.setVisible(false);
                frmClientesA.btnEditar.setVisible(false);
                frmClientesA.btnEliminar.setVisible(false);
                frmClientesA.tblTabla.addMouseListener(this);

                frmClientesA.setLocationRelativeTo(frmOrdenTrabajoB.btnBuscarEquipo);
                frmClientesA.setVisible(true);
            }
            if (e.getSource() == frmOrdenTrabajoB.btnBuscarEmpleado) {

            }
            if (e.getSource() == frmOrdenTrabajoB.btnBuscarEquipo) {

            }
        }

    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getClickCount() == 2) {

            if (e.getSource() == frmOrdenTrabajoA.tblTabla) {
                frmDetalleOrdenA.setVisible(true);
            }
            
            if (e.getSource() == frmClientesA.tblTabla) {

                int fila = frmClientesA.tblTabla.getSelectedRow();
                frmOrdenTrabajoB.txtCliente.setText(frmClientesA.tblTabla.getValueAt(fila, 0).toString());
                frmOrdenTrabajoB.txtNombreCliente.setText(frmClientesA.tblTabla.getValueAt(fila, 1).toString());
                frmClientesA.dispose();

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
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        frmOrdenTrabajoA.lblTexto.setVisible(true);
    }

    @Override
    public void mouseExited(MouseEvent e) {
        frmOrdenTrabajoA.lblTexto.setVisible(false);
    }

}
