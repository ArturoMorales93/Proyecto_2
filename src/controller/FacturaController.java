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
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import javaBeans.DetalleFactura;
import javaBeans.Factura;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import model.DetalleFacturaModel;
import model.FacturaModel;
import view.FrmClientesView;
import view.FrmDetalleFacturaView;
import view.FrmEmpleadosView;
import view.FrmOrdenTrabajoView;
import view.FrmFacturacionView;
import view.FrmNuevoDetalleFacturaView;
import view.FrmNuevoFacturaView;

/**
 *
 * @author Arthur
 */
public class FacturaController implements WindowListener, ActionListener, KeyListener, MouseListener, FocusListener {

    //Atributos
    private Factura factura;
    private FacturaModel facturaModel;
    private FrmFacturacionView frmFacturaA;
    private FrmNuevoFacturaView frmFacturaB;

    private FrmClientesView frmClientesA;
    private FrmEmpleadosView frmEmpleadosA;
    private FrmOrdenTrabajoView frmOrdenTrabajoA;

    private DefaultTableModel modelo;
    private int opcion;

    DetalleFactura detalleFactura = new DetalleFactura();
    DetalleFacturaModel detalleFacturaModel = new DetalleFacturaModel();
    FrmDetalleFacturaView frmDetalleFacturaA = new FrmDetalleFacturaView(null, true);
    FrmNuevoDetalleFacturaView frmDetalleFacturaB = new FrmNuevoDetalleFacturaView(null, true);

    //Constructor
    public FacturaController(Factura factura, FacturaModel facturaModel, FrmFacturacionView frmFacturaA, FrmNuevoFacturaView frmFacturaB,
            FrmClientesView frmClientesA, FrmEmpleadosView frmEmpleadosA, FrmOrdenTrabajoView frmOrdenTrabajoA) {
        this.factura = factura;
        this.facturaModel = facturaModel;
        this.frmFacturaA = frmFacturaA;
        this.frmFacturaB = frmFacturaB;

        this.frmClientesA = frmClientesA;
        this.frmEmpleadosA = frmEmpleadosA;
        this.frmOrdenTrabajoA = frmOrdenTrabajoA;

        //Componentes del FrameA
        this.frmFacturaA.addWindowListener(this);
        this.frmFacturaA.lblInfo.addMouseListener(this);
        this.frmFacturaA.lblTexto.setVisible(false);
        this.frmFacturaA.tblTabla.requestFocus();
        this.frmFacturaA.tblTabla.getTableHeader().setResizingAllowed(false);
        this.frmFacturaA.tblTabla.getTableHeader().setReorderingAllowed(false);
        this.frmFacturaA.tblTabla.addMouseListener(this);
        this.frmFacturaA.btnNuevo.addActionListener(this);
       // this.frmFacturaA.btnEditar.addActionListener(this);
        this.frmFacturaA.btnEliminar.addActionListener(this);
        this.frmFacturaA.txtBuscar.addKeyListener(this);
        this.frmFacturaA.txtBuscar.addFocusListener(this);

        //Componentes del FrameB
        this.frmFacturaB.setLocationRelativeTo(null);
        this.frmFacturaB.btnGuardar.addActionListener(this);
        this.frmFacturaB.btnLimpiar.addActionListener(this);
        this.frmFacturaB.btnCancelar.addActionListener(this);
        this.frmFacturaB.btnBuscarCliente.addActionListener(this);
        this.frmFacturaB.btnBuscarEmpleado.addActionListener(this);
        this.frmFacturaB.btnBuscarOrden.addActionListener(this);
        this.frmFacturaB.txtFecha.addFocusListener(this);
    }

    //Metodos
    public void limpiarFrmB() {
        if (opcion == 1) {
            frmFacturaB.txtIdFactura.setText(null);
        }
        frmFacturaB.txtCliente.setText(null);
        frmFacturaB.txtNombreCliente.setText(null);
        frmFacturaB.txtEmpleado.setText(null);
        frmFacturaB.txtNombreEmpleado.setText(null);
        frmFacturaB.txtOrdenTrabajo.setText(null);
        frmFacturaB.txtFecha.setText(null);
        frmFacturaB.txtImpuesto.setText(null);
        frmFacturaB.btnBuscarOrden.requestFocus();
    }

    @Override
    public void focusGained(FocusEvent e) {
        if (frmFacturaA.isActive()) {
            if (frmFacturaA.txtBuscar.getText().equals("Filtrar por ID de Factura")) {
                frmFacturaA.txtBuscar.setText(null);
                frmFacturaA.txtBuscar.setForeground(Color.BLACK);
            }
        }

        if (frmFacturaB.isActive()) {
            if (frmFacturaB.txtFecha.getText().equals("aaaa/mm/dd")) {
                frmFacturaB.txtFecha.setText("");
                frmFacturaB.txtFecha.setForeground(Color.BLACK);
            }
        }
    }

    @Override
    public void focusLost(FocusEvent e) {
        if (frmFacturaA.isActive()) {
            if (frmFacturaA.txtBuscar.getText().equals("")) {
                frmFacturaA.txtBuscar.setText("Filtrar por ID de Factura");
                frmFacturaA.txtBuscar.setForeground(Color.LIGHT_GRAY);
            }
        }

        if (frmFacturaB.isActive()) {
            if (frmFacturaB.txtFecha.getText().equals("    /  /  ")) {
                frmFacturaB.txtFecha.setText("aaaa/mm/dd");
                frmFacturaB.txtFecha.setForeground(Color.LIGHT_GRAY);
            }
        }
    }

    @Override
    public void windowActivated(WindowEvent e) {
        String columnas[] = {"ID Factura", "Fecha", "ID Orden", "ID Empleado", "ID Cliente", "Impuesto", "Subtotal", "Total"};
        modelo = new DefaultTableModel(null, columnas) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        frmFacturaA.txtBuscar.setText("Filtrar por ID de Factura");
        frmFacturaA.txtBuscar.setForeground(Color.LIGHT_GRAY);
        frmFacturaA.lblTexto.setVisible(false);
        frmFacturaA.tblTabla.requestFocus();
        ResultSet rs = facturaModel.mostrarFactura();
        float total;

        try {

            if (rs.isFirst()) {
                do {
                    total = 0;
                    total = rs.getInt(7)+(rs.getInt(7)*((float) rs.getInt(6)/100));
                    factura = new Factura(rs.getInt(1), rs.getString(2), rs.getInt(3), rs.getString(4), rs.getInt(5), rs.getInt(6),
                            rs.getInt(7), rs.getInt(8));
                    Object newRow[] = {factura.getIdFactura(), factura.getFecha(), factura.getIdOrden(), factura.getIdEmpleado(),
                        factura.getIdCliente(), factura.getImpuesto(), factura.getSubTotal(), total};
                    modelo.addRow(newRow);
                } while (rs.next());
            }
            frmFacturaA.tblTabla.setModel(modelo);
            frmFacturaA.lblCantidadRegistros.setText("Cantidad de Registros: " + modelo.getRowCount());

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(frmFacturaA, ex.getMessage());
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        String columnas[] = {"ID Factura", "Fecha", "ID Orden", "ID Empleado", "ID Cliente", "Impuesto", "Subtotal", "Total"};
        modelo = new DefaultTableModel(null, columnas) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        ResultSet rs = facturaModel.filtrarFactura(frmFacturaA.txtBuscar.getText().trim());

        try {

            if (rs.isFirst()) {
                do {
                    factura = new Factura(rs.getInt(1), rs.getString(2), rs.getInt(3), rs.getString(4), rs.getInt(5), rs.getInt(6),
                            rs.getInt(7), rs.getInt(8));
                    Object newRow[] = {factura.getIdFactura(), factura.getFecha(), factura.getIdOrden(), factura.getIdEmpleado(),
                        factura.getIdCliente(), factura.getImpuesto(), factura.getSubTotal(), factura.getTotal()};
                    modelo.addRow(newRow);
                } while (rs.next());
            }
            frmFacturaA.tblTabla.setModel(modelo);
            frmFacturaA.lblCantidadRegistros.setText("Cantidad de Registros: " + modelo.getRowCount());

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(frmFacturaA, ex.getMessage());
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (frmFacturaA.isActive()) { //Botones del frmFacturaA
            switch (e.getActionCommand()) {

                case "Nuevo":
                    opcion = 1;
                    frmFacturaB.setTitle("Registro de Factura");
                    frmFacturaB.txtIdFactura.setEnabled(true);
                    frmFacturaB.txtSubTotal.setText("0");
                    frmFacturaB.txtTotal.setText("0");
                    limpiarFrmB();
                    frmFacturaB.setVisible(true);
                    break;

//                case "Editar":
//                    if (frmFacturaA.tblTabla.getSelectedRowCount() == 1) {
//
//                        opcion = 2;
//                        frmFacturaB.setTitle("Actualización de Factura");
//                        frmFacturaB.txtIdFactura.setEnabled(false);
//                        int fila = frmFacturaA.tblTabla.getSelectedRow();
//                        factura.setIdFactura(Integer.parseInt(frmFacturaA.tblTabla.getValueAt(fila, 0).toString()));
//                        frmFacturaB.txtIdFactura.setText(String.valueOf(frmFacturaA.tblTabla.getValueAt(fila, 0)));
//                        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyy-MM-dd");
//                        String FechaString = frmFacturaA.tblTabla.getValueAt(fila, 1).toString();
//                        //string to date
//                        LocalDate localDate = LocalDate.parse(FechaString, dateTimeFormatter);
//                        frmFacturaB.txtFecha.setDate(localDate);
//
//                        frmFacturaB.txtOrdenTrabajo.setText(frmFacturaA.tblTabla.getValueAt(fila, 2).toString());
//                        frmFacturaB.txtEmpleado.setText(frmFacturaA.tblTabla.getValueAt(fila, 3).toString());
//                        frmFacturaB.txtCliente.setText(frmFacturaA.tblTabla.getValueAt(fila, 4).toString());
//                        frmFacturaB.txtImpuesto.setText(frmFacturaA.tblTabla.getValueAt(fila, 5).toString());
//                        frmFacturaB.txtSubTotal.setText(frmFacturaA.tblTabla.getValueAt(fila, 6).toString());
//                        frmFacturaB.txtTotal.setText(frmFacturaA.tblTabla.getValueAt(fila, 7).toString());
//
//                        frmFacturaB.setVisible(true);
//
//                    } else {
//                        JOptionPane.showMessageDialog(frmFacturaA, "Debe seleccionar un registro");
//                    }
                   // break;

                case "Eliminar":
                    if (frmFacturaA.tblTabla.getSelectedRowCount() == 1) {

                        int fila = frmFacturaA.tblTabla.getSelectedRow();
                        factura.setIdFactura(Integer.parseInt(frmFacturaA.tblTabla.getValueAt(fila, 0).toString()));
                        int resp = JOptionPane.showConfirmDialog(frmFacturaA, "¿Desea eliminar el factura " + factura.getIdFactura() + "?", "Seleccionar una Opción", 0);

                        if (resp == 0) {
                            if (facturaModel.eliminarFactura(factura.getIdFactura())) {
                            } else {
                                JOptionPane.showMessageDialog(frmFacturaA, "Error al eliminar");
                            }
                        }

                    } else {
                        JOptionPane.showMessageDialog(frmFacturaA, "Debe seleccionar un registro");
                    }
                    break;

            }
        }

        if (frmFacturaB.isActive()) { //Botones del frmFacturaB
            switch (e.getActionCommand()) {

                case "Guardar":
                    try {
                    LocalDate localDate = frmFacturaB.txtFecha.getDate();
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                    String fecha = localDate.format(formatter);
                    factura.setIdFactura(Integer.parseInt(frmFacturaB.txtIdFactura.getText().trim()));
                    factura.setFecha(fecha);
                    factura.setIdOrden(Integer.parseInt(frmFacturaB.txtOrdenTrabajo.getText().trim()));
                    factura.setIdEmpleado(frmFacturaB.txtEmpleado.getText().trim());
                    factura.setIdCliente(Integer.parseInt(frmFacturaB.txtCliente.getText().trim()));
                    factura.setImpuesto(Integer.parseInt(frmFacturaB.txtImpuesto.getText().trim()));
                    factura.setSubTotal(Integer.parseInt(frmFacturaB.txtSubTotal.getText().trim()));
                    factura.setTotal(Integer.parseInt(frmFacturaB.txtTotal.getText().trim()));
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(frmFacturaB, "Error de tipo de datos");
                } catch (NullPointerException ex) {
                    JOptionPane.showMessageDialog(frmFacturaB, "Ingrese la fecha");
                }

                if (opcion == 1) {
                    if (facturaModel.insertarFactura(factura)) {
                        JOptionPane.showMessageDialog(frmFacturaB, "Factura Registrado");
                        frmFacturaB.dispose();
                    } else {
                        JOptionPane.showMessageDialog(frmFacturaB, "Error al guardar");
                    }
                } 
//                    if (facturaModel.modificarFactura(factura)) {
//                        JOptionPane.showMessageDialog(frmFacturaB, "Factura Actualizado");
//                        frmFacturaB.dispose();
//                    } else {
//                        JOptionPane.showMessageDialog(frmFacturaB, "Error al editar");
//                    }
               
                break;

                case "Limpiar":
                    limpiarFrmB();
                    break;

                case "Cancelar":
                    frmFacturaB.dispose();
                    break;

            }

            if (e.getSource() == frmFacturaB.btnBuscarOrden) {
                frmOrdenTrabajoA.btnNuevo.setVisible(false);
                frmOrdenTrabajoA.btnEditar.setVisible(false);
                frmOrdenTrabajoA.btnEliminar.setVisible(false);
                frmOrdenTrabajoA.tblTabla.addMouseListener(this);
                frmOrdenTrabajoA.lblTexto.setText("Haga doble click sobre un registro para seleccionarlo.");

                frmOrdenTrabajoA.setLocationRelativeTo(frmFacturaB.btnBuscarCliente);
                frmOrdenTrabajoA.setModal(true);
                frmOrdenTrabajoA.setVisible(true);
            }
            if (e.getSource() == frmFacturaB.btnBuscarEmpleado) {
                frmEmpleadosA.lblInfo.setVisible(true);
                frmEmpleadosA.btnNuevo.setVisible(false);
                frmEmpleadosA.btnEditar.setVisible(false);
                frmEmpleadosA.btnEliminar.setVisible(false);
                frmEmpleadosA.tblTabla.addMouseListener(this);

                frmEmpleadosA.setLocationRelativeTo(frmFacturaB.btnBuscarCliente);
                frmEmpleadosA.setModal(true);
                frmEmpleadosA.setVisible(true);
            }
            if (e.getSource() == frmFacturaB.btnBuscarCliente) {
                frmClientesA.lblInfo.setVisible(true);
                frmClientesA.btnNuevo.setVisible(false);
                frmClientesA.btnEditar.setVisible(false);
                frmClientesA.btnEliminar.setVisible(false);
                frmClientesA.tblTabla.addMouseListener(this);

                frmClientesA.setLocationRelativeTo(frmFacturaB.btnBuscarCliente);
                frmClientesA.setModal(true);
                frmClientesA.setVisible(true);
            }
        }

    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getClickCount() == 2) {

            if (e.getSource() == frmFacturaA.tblTabla) {

                int fila = frmFacturaA.tblTabla.getSelectedRow();
                DetalleFacturaController detalleFacturaController = new DetalleFacturaController(detalleFactura, detalleFacturaModel, frmDetalleFacturaA, frmDetalleFacturaB, (int) frmFacturaA.tblTabla.getValueAt(fila, 0));
                frmDetalleFacturaB.txtFactura.setText(frmFacturaA.tblTabla.getValueAt(fila, 0).toString());

                frmDetalleFacturaA.setLocationRelativeTo(frmFacturaA.btnEliminar);
                frmDetalleFacturaA.setVisible(true);

            }

            if (e.getSource() == frmEmpleadosA.tblTabla) {
                int fila = frmEmpleadosA.tblTabla.getSelectedRow();
                frmFacturaB.txtEmpleado.setText(frmEmpleadosA.tblTabla.getValueAt(fila, 0).toString());
                frmFacturaB.txtNombreEmpleado.setText(frmEmpleadosA.tblTabla.getValueAt(fila, 1).toString());
                frmEmpleadosA.dispose();
            }

            if (e.getSource() == frmClientesA.tblTabla) {
                int fila = frmClientesA.tblTabla.getSelectedRow();
                frmFacturaB.txtCliente.setText(frmClientesA.tblTabla.getValueAt(fila, 0).toString());
                frmFacturaB.txtNombreCliente.setText(frmClientesA.tblTabla.getValueAt(fila, 1).toString());
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
        if (e.getSource() == frmFacturaA.lblInfo) {
            frmFacturaA.lblTexto.setVisible(true);
        }
    }

    @Override
    public void mouseExited(MouseEvent e) {
        if (e.getSource() == frmFacturaA.lblInfo) {
            frmFacturaA.lblTexto.setVisible(false);
        }
    }

}
