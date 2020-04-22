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
import javaBeans.DetalleFactura;
import javaBeans.Mantenimiento;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import model.DetalleFacturaModel;
import model.MantenimientoModel;
import view.FrmDetalleFacturaView;
import view.FrmMantenimientoView;
import view.FrmNuevoDetalleFacturaView;
import view.FrmNuevoMantenimientoView;

/**
 *
 * @author Arthur
 */
public class DetalleFacturaController implements WindowListener, ActionListener, KeyListener, MouseListener, FocusListener {

    //Atributos
    private DetalleFactura detalleFactura;
    private DetalleFacturaModel detalleFacturaModel;
    private FrmDetalleFacturaView frmDetalleFacturaA;
    private FrmNuevoDetalleFacturaView frmDetalleFacturaB;
    private int idFactura;

    private DefaultTableModel modelo;
    private int opcion;

    Mantenimiento mantenimiento = new Mantenimiento();
    MantenimientoModel mantenimientoModel = new MantenimientoModel();
    FrmMantenimientoView frmMantenimientoA = new FrmMantenimientoView(null, true);
    FrmNuevoMantenimientoView frmMantenimientoB = new FrmNuevoMantenimientoView(null, true);
    MantenimientoController mantenimientoController = new MantenimientoController(mantenimiento, mantenimientoModel, frmMantenimientoA, frmMantenimientoB);

    //Constructor
    public DetalleFacturaController(DetalleFactura detalleFactura, DetalleFacturaModel detalleFacturaModel, FrmDetalleFacturaView frmDetalleFacturaA, FrmNuevoDetalleFacturaView frmDetalleFacturaB,
            int idFactura) {
        this.detalleFactura = detalleFactura;
        this.detalleFacturaModel = detalleFacturaModel;
        this.frmDetalleFacturaA = frmDetalleFacturaA;
        this.frmDetalleFacturaB = frmDetalleFacturaB;
        this.idFactura = idFactura;

        //Componentes del FrameA
        this.frmDetalleFacturaA.addWindowListener(this);
        this.frmDetalleFacturaA.tblTabla.requestFocus();
        this.frmDetalleFacturaA.tblTabla.getTableHeader().setResizingAllowed(false);
        this.frmDetalleFacturaA.tblTabla.getTableHeader().setReorderingAllowed(false);
        this.frmDetalleFacturaA.btnNuevo.addActionListener(this);
        this.frmDetalleFacturaA.btnEditar.addActionListener(this);
        this.frmDetalleFacturaA.btnEliminar.addActionListener(this);
        this.frmDetalleFacturaA.txtBuscar.addKeyListener(this);
        this.frmDetalleFacturaA.txtBuscar.addFocusListener(this);

        //Componentes del FrameB
        this.frmDetalleFacturaB.setLocationRelativeTo(null);
        this.frmDetalleFacturaB.btnGuardar.addActionListener(this);
        this.frmDetalleFacturaB.btnLimpiar.addActionListener(this);
        this.frmDetalleFacturaB.btnCancelar.addActionListener(this);
        this.frmDetalleFacturaB.btnBuscarMantenimiento.addActionListener(this);
    }

    //Metodos
    public void limpiarFrmB() {
        frmDetalleFacturaB.txtMantenimiento.setText(null);
        frmDetalleFacturaB.txtCantidad.setText(null);
        frmDetalleFacturaB.txtSubTotal.setText(null);
        frmDetalleFacturaB.txtPrecio.setText(null);
        frmDetalleFacturaB.btnBuscarMantenimiento.requestFocus();
    }

    @Override
    public void focusGained(FocusEvent e) {
        if (frmDetalleFacturaA.txtBuscar.getText().equals("Filtrar por ID de Detalle")) {
            frmDetalleFacturaA.txtBuscar.setText(null);
            frmDetalleFacturaA.txtBuscar.setForeground(Color.BLACK);
        }
    }

    @Override
    public void focusLost(FocusEvent e) {
        if (frmDetalleFacturaA.txtBuscar.getText().equals("")) {
            frmDetalleFacturaA.txtBuscar.setText("Filtrar por ID de Detalle");
            frmDetalleFacturaA.txtBuscar.setForeground(Color.LIGHT_GRAY);
        }
    }

    @Override
    public void windowActivated(WindowEvent e) {
        String columnas[] = {"ID Detalle", "ID Factura", "Cantidad", "ID Mantenimiento", "Precio", "Subtotal"};
        modelo = new DefaultTableModel(null, columnas) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        frmDetalleFacturaA.txtBuscar.setText("Filtrar por ID de Detalle");
        frmDetalleFacturaA.txtBuscar.setForeground(Color.LIGHT_GRAY);
        frmDetalleFacturaA.tblTabla.requestFocus();
        ResultSet rs = detalleFacturaModel.mostrarDetalleFactura(idFactura);

        try {

            if (rs.isFirst()) {
                do {
                    detalleFactura = new DetalleFactura(rs.getInt(1), rs.getInt(2), rs.getInt(3), rs.getInt(4), rs.getInt(5), rs.getInt(6));
                    Object newRow[] = {detalleFactura.getIdDetalleFactura(), detalleFactura.getIdFactura(), detalleFactura.getCantidad(),
                        detalleFactura.getIdMantenimiento(), detalleFactura.getPrecio(), detalleFactura.getSubTotal()};
                    modelo.addRow(newRow);
                } while (rs.next());
            }
            frmDetalleFacturaA.tblTabla.setModel(modelo);
            frmDetalleFacturaA.lblCantidadRegistros.setText("Cantidad de Registros: " + modelo.getRowCount());

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(frmDetalleFacturaA, ex.getMessage());
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        String columnas[] = {"ID Detalle", "ID Factura", "Cantidad", "ID Mantenimiento", "Precio", "Subtotal"};
        modelo = new DefaultTableModel(null, columnas) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        ResultSet rs = detalleFacturaModel.filtrarDetalleFactura(frmDetalleFacturaA.txtBuscar.getText().trim());

        try {

            if (rs.isFirst()) {
                do {
                    detalleFactura = new DetalleFactura(rs.getInt(1), rs.getInt(2), rs.getInt(3), rs.getInt(4), rs.getInt(5), rs.getInt(6));
                    Object newRow[] = {detalleFactura.getIdDetalleFactura(), detalleFactura.getIdFactura(), detalleFactura.getCantidad(),
                        detalleFactura.getIdMantenimiento(), detalleFactura.getPrecio(), detalleFactura.getSubTotal()};
                    modelo.addRow(newRow);
                } while (rs.next());
            }
            frmDetalleFacturaA.tblTabla.setModel(modelo);
            frmDetalleFacturaA.lblCantidadRegistros.setText("Cantidad de Registros: " + modelo.getRowCount());

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(frmDetalleFacturaA, ex.getMessage());
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (frmDetalleFacturaA.isActive()) { //Botones del frmDetalleFacturaA
            switch (e.getActionCommand()) {

                case "Nuevo":
                    opcion = 1;
                    frmDetalleFacturaB.setTitle("Registro de DetalleFactura");
                    limpiarFrmB();
                    frmDetalleFacturaB.setLocationRelativeTo(frmDetalleFacturaA.btnNuevo);
                    frmDetalleFacturaB.setVisible(true);
                    break;

                case "Editar":
                    if (frmDetalleFacturaA.tblTabla.getSelectedRowCount() == 1) {

                        opcion = 2;
                        frmDetalleFacturaB.setTitle("Actualización de DetalleFactura");
                        int fila = frmDetalleFacturaA.tblTabla.getSelectedRow();
                        detalleFactura.setIdDetalleFactura(Integer.parseInt(frmDetalleFacturaA.tblTabla.getValueAt(fila, 0).toString()));

                        frmDetalleFacturaB.txtFactura.setText(frmDetalleFacturaA.tblTabla.getValueAt(fila, 1).toString());
                        frmDetalleFacturaB.txtCantidad.setText(frmDetalleFacturaA.tblTabla.getValueAt(fila, 2).toString());
                        frmDetalleFacturaB.txtMantenimiento.setText(frmDetalleFacturaA.tblTabla.getValueAt(fila, 3).toString());
                        frmDetalleFacturaB.txtPrecio.setText(frmDetalleFacturaA.tblTabla.getValueAt(fila, 4).toString());
                        frmDetalleFacturaB.txtSubTotal.setText(frmDetalleFacturaA.tblTabla.getValueAt(fila, 5).toString());

                        frmDetalleFacturaB.setLocationRelativeTo(frmDetalleFacturaA.btnNuevo);
                        frmDetalleFacturaB.setVisible(true);

                    } else {
                        JOptionPane.showMessageDialog(frmDetalleFacturaA, "Debe seleccionar un registro");
                    }
                    break;

                case "Eliminar":
                    if (frmDetalleFacturaA.tblTabla.getSelectedRowCount() == 1) {

                        int fila = frmDetalleFacturaA.tblTabla.getSelectedRow();
                        detalleFactura.setIdDetalleFactura(Integer.parseInt(frmDetalleFacturaA.tblTabla.getValueAt(fila, 0).toString()));
                        int resp = JOptionPane.showConfirmDialog(frmDetalleFacturaA, "¿Desea eliminar el detalleFactura " + detalleFactura.getIdDetalleFactura() + "?", "Seleccionar una Opción", 0);

                        if (resp == 0) {
                            if (detalleFacturaModel.eliminarDetalleFactura(detalleFactura.getIdDetalleFactura())) {
                                JOptionPane.showMessageDialog(frmDetalleFacturaA, "DetalleFactura eliminado");
                            } else {
                                JOptionPane.showMessageDialog(frmDetalleFacturaA, "Error al eliminar");
                            }
                        }

                    } else {
                        JOptionPane.showMessageDialog(frmDetalleFacturaA, "Debe seleccionar un registro");
                    }
                    break;

            }
        }

        if (frmDetalleFacturaB.isActive()) { //Botones del frmDetalleFacturaB
            switch (e.getActionCommand()) {

                case "Guardar":
                    try {
                    detalleFactura.setIdFactura(Integer.parseInt(frmDetalleFacturaB.txtFactura.getText().trim()));
                    detalleFactura.setCantidad(Integer.parseInt(frmDetalleFacturaB.txtCantidad.getText().trim()));
                    detalleFactura.setIdMantenimiento(Integer.parseInt(frmDetalleFacturaB.txtMantenimiento.getText().trim()));
                    detalleFactura.setPrecio(Integer.parseInt(frmDetalleFacturaB.txtPrecio.getText().trim()));
                    detalleFactura.setSubTotal(Integer.parseInt(frmDetalleFacturaB.txtSubTotal.getText().trim()));
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(frmDetalleFacturaB, "Error de tipo de datos");
                }

                if (opcion == 1) {
                    if (detalleFacturaModel.insertarDetalleFactura(detalleFactura)) {
                        JOptionPane.showMessageDialog(frmDetalleFacturaB, "DetalleFactura Registrado");
                        frmDetalleFacturaB.dispose();
                    } else {
                        JOptionPane.showMessageDialog(frmDetalleFacturaB, "Error al guardar");
                    }
                } else {
                    if (detalleFacturaModel.modificarDetalleFactura(detalleFactura)) {
                        JOptionPane.showMessageDialog(frmDetalleFacturaB, "DetalleFactura Actualizado");
                        frmDetalleFacturaB.dispose();
                    } else {
                        JOptionPane.showMessageDialog(frmDetalleFacturaB, "Error al editar");
                    }
                }
                break;

                case "Limpiar":
                    limpiarFrmB();
                    break;

                case "Cancelar":
                    frmDetalleFacturaB.dispose();
                    break;

            }

            if (e.getSource() == frmDetalleFacturaB.btnBuscarMantenimiento) {
                frmMantenimientoA.lblInfo.setVisible(true);
                frmMantenimientoA.btnNuevo.setVisible(false);
                frmMantenimientoA.btnEditar.setVisible(false);
                frmMantenimientoA.btnEliminar.setVisible(false);
                frmMantenimientoA.tblTabla.addMouseListener(this);

                frmMantenimientoA.setLocationRelativeTo(frmDetalleFacturaB.btnBuscarMantenimiento);
                frmMantenimientoA.setModal(true);
                frmMantenimientoA.setVisible(true);
            }
        }

    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getClickCount() == 2) {

            if (e.getSource() == frmMantenimientoA.tblTabla) {

                int fila = frmMantenimientoA.tblTabla.getSelectedRow();
                frmDetalleFacturaB.txtMantenimiento.setText(frmMantenimientoA.tblTabla.getValueAt(fila, 0).toString());
                frmMantenimientoA.dispose();

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
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

}
