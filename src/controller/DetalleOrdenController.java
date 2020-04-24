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
import javaBeans.DetalleOrden;
import javaBeans.Mantenimiento;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import model.DetalleOrdenModel;
import model.MantenimientoModel;
import view.FrmDetalleOrdenView;
import view.FrmMantenimientoView;
import view.FrmNuevoDetalleOrdenView;
import view.FrmNuevoMantenimientoView;

/**
 *
 * @author Arthur
 */
public class DetalleOrdenController implements WindowListener, ActionListener, KeyListener, MouseListener, FocusListener {

    //Atributos
    private DetalleOrden detalleOrden;
    private DetalleOrdenModel detalleOrdenModel;
    private FrmDetalleOrdenView frmDetalleOrdenA;
    private FrmNuevoDetalleOrdenView frmDetalleOrdenB;
    private int idOrdenTrabajo;

    private DefaultTableModel modelo;
    private int opcion;

    Mantenimiento mantenimiento = new Mantenimiento();
    MantenimientoModel mantenimientoModel = new MantenimientoModel();
    FrmMantenimientoView frmMantenimientoA = new FrmMantenimientoView(null, true);
    FrmNuevoMantenimientoView frmMantenimientoB = new FrmNuevoMantenimientoView(null, true);
    MantenimientoController mantenimientoController = new MantenimientoController(mantenimiento, mantenimientoModel, frmMantenimientoA, frmMantenimientoB);

    //Constructor
    public DetalleOrdenController(DetalleOrden detalleOrden, DetalleOrdenModel detalleOrdenModel, FrmDetalleOrdenView frmDetalleOrdenA, FrmNuevoDetalleOrdenView frmDetalleOrdenB,
            int idOrdenTrabajo) {
        this.detalleOrden = detalleOrden;
        this.detalleOrdenModel = detalleOrdenModel;
        this.frmDetalleOrdenA = frmDetalleOrdenA;
        this.frmDetalleOrdenB = frmDetalleOrdenB;
        this.idOrdenTrabajo = idOrdenTrabajo;

        //Componentes del FrameA
        this.frmDetalleOrdenA.addWindowListener(this);
        this.frmDetalleOrdenA.tblTabla.requestFocus();
        this.frmDetalleOrdenA.tblTabla.getTableHeader().setResizingAllowed(false);
        this.frmDetalleOrdenA.tblTabla.getTableHeader().setReorderingAllowed(false);
        this.frmDetalleOrdenA.btnNuevo.addActionListener(this);
        this.frmDetalleOrdenA.btnEditar.addActionListener(this);
        this.frmDetalleOrdenA.btnEliminar.addActionListener(this);
        this.frmDetalleOrdenA.txtBuscar.addKeyListener(this);
        this.frmDetalleOrdenA.txtBuscar.addFocusListener(this);

        //Componentes del FrameB
        this.frmDetalleOrdenB.btnGuardar.addActionListener(this);
        this.frmDetalleOrdenB.btnLimpiar.addActionListener(this);
        this.frmDetalleOrdenB.btnCancelar.addActionListener(this);
        this.frmDetalleOrdenB.btnBuscarMantenimiento.addActionListener(this);
    }

    //Metodos
    public void limpiarFrmB() {
        if (opcion==1) {
            frmDetalleOrdenB.txtIdOrden.setText(null);
            frmDetalleOrdenB.txtMantenimiento.setText(null);
            frmDetalleOrdenB.txaObservaciones.setText(null);
            frmDetalleOrdenB.txtPrecio.setText(null);
            frmDetalleOrdenB.btnBuscarMantenimiento.requestFocus();
        }
    }

    @Override
    public void focusGained(FocusEvent e) {
        if (frmDetalleOrdenA.txtBuscar.getText().equals("Filtrar por ID de Detalle")) {
            frmDetalleOrdenA.txtBuscar.setText(null);
            frmDetalleOrdenA.txtBuscar.setForeground(Color.BLACK);
        }
    }

    @Override
    public void focusLost(FocusEvent e) {
        if (frmDetalleOrdenA.txtBuscar.getText().equals("")) {
            frmDetalleOrdenA.txtBuscar.setText("Filtrar por ID de Detalle");
            frmDetalleOrdenA.txtBuscar.setForeground(Color.LIGHT_GRAY);
        }
    }

    @Override
    public void windowActivated(WindowEvent e) {
        String columnas[] = {"ID Detalle", "ID Orden", "ID Mantenimiento", "Precio", "Observaciones"};
        modelo = new DefaultTableModel(null, columnas) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        frmDetalleOrdenA.txtBuscar.setText("Filtrar por ID de Detalle");
        frmDetalleOrdenA.txtBuscar.setForeground(Color.LIGHT_GRAY);
        frmDetalleOrdenA.tblTabla.requestFocus();
        ResultSet rs = detalleOrdenModel.mostrarDetalleOrden(idOrdenTrabajo);

        try {

            if (rs.isFirst()) {
                do {
                    detalleOrden = new DetalleOrden(rs.getInt(1), rs.getInt(2), rs.getString(3), rs.getInt(4), rs.getString(5));
                    Object newRow[] = {detalleOrden.getIdDetalleOrden(), detalleOrden.getIdOrdenTrabajo(), detalleOrden.getIdMantenimiento(),
                        detalleOrden.getPrecio(), detalleOrden.getObservaciones()};
                    modelo.addRow(newRow);
                } while (rs.next());
            }
            frmDetalleOrdenA.tblTabla.setModel(modelo);
            frmDetalleOrdenA.lblCantidadRegistros.setText("Cantidad de Registros: " + modelo.getRowCount());

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(frmDetalleOrdenA, ex.getMessage());
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        String columnas[] = {"ID Detalle", "ID Orden", "ID Mantenimiento", "Precio", "Observaciones"};
        modelo = new DefaultTableModel(null, columnas) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        ResultSet rs = detalleOrdenModel.filtrarDetalleOrden(frmDetalleOrdenA.txtBuscar.getText().trim());

        try {

            if (rs.isFirst()) {
                do {
                    detalleOrden = new DetalleOrden(rs.getInt(1), rs.getInt(2), rs.getString(3), rs.getInt(4), rs.getString(5));
                    Object newRow[] = {detalleOrden.getIdDetalleOrden(), detalleOrden.getIdOrdenTrabajo(), detalleOrden.getIdMantenimiento(),
                        detalleOrden.getPrecio(), detalleOrden.getObservaciones()};
                    modelo.addRow(newRow);
                } while (rs.next());
            }
            frmDetalleOrdenA.tblTabla.setModel(modelo);
            frmDetalleOrdenA.lblCantidadRegistros.setText("Cantidad de Registros: " + modelo.getRowCount());

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(frmDetalleOrdenA, ex.getMessage());
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (frmDetalleOrdenA.isActive()) { //Botones del frmDetalleOrdenA
            switch (e.getActionCommand()) {

                case "Nuevo":
                    opcion = 1;
                    frmDetalleOrdenB.setTitle("Registro de DetalleOrden");
                    limpiarFrmB();
                    frmDetalleOrdenB.setLocationRelativeTo(frmDetalleOrdenA.btnNuevo);
                    frmDetalleOrdenB.setVisible(true);
                    break;

                case "Editar":
                    if (frmDetalleOrdenA.tblTabla.getSelectedRowCount() == 1) {

                        opcion = 2;
                        frmDetalleOrdenB.setTitle("Actualización de DetalleOrden");
                        frmDetalleOrdenB.txtIdOrden.setEnabled(false);
                        int fila = frmDetalleOrdenA.tblTabla.getSelectedRow();
                        detalleOrden.setIdDetalleOrden(Integer.parseInt(frmDetalleOrdenA.tblTabla.getValueAt(fila, 0).toString()));
                        frmDetalleOrdenB.txtIdOrden.setText(frmDetalleOrdenA.tblTabla.getValueAt(fila, 0).toString());
                        frmDetalleOrdenB.txtOrden.setText(frmDetalleOrdenA.tblTabla.getValueAt(fila, 1).toString());
                        frmDetalleOrdenB.txtMantenimiento.setText(frmDetalleOrdenA.tblTabla.getValueAt(fila, 2).toString());
                        frmDetalleOrdenB.txtPrecio.setText(frmDetalleOrdenA.tblTabla.getValueAt(fila, 3).toString());
                        frmDetalleOrdenB.txtMantenimiento.setText(frmDetalleOrdenA.tblTabla.getValueAt(fila, 4).toString());

                        frmDetalleOrdenB.setLocationRelativeTo(frmDetalleOrdenA.btnNuevo);
                        frmDetalleOrdenB.setVisible(true);

                    } else {
                        JOptionPane.showMessageDialog(frmDetalleOrdenA, "Debe seleccionar un registro");
                    }
                    break;

                case "Eliminar":
                    if (frmDetalleOrdenA.tblTabla.getSelectedRowCount() == 1) {

                        int fila = frmDetalleOrdenA.tblTabla.getSelectedRow();
                        detalleOrden.setIdDetalleOrden(Integer.parseInt(frmDetalleOrdenA.tblTabla.getValueAt(fila, 0).toString()));
                        int resp = JOptionPane.showConfirmDialog(frmDetalleOrdenA, "¿Desea eliminar el detalleOrden " + detalleOrden.getIdDetalleOrden() + "?", "Seleccionar una Opción", 0);

                        if (resp == 0) {
                            if (detalleOrdenModel.eliminarDetalleOrden(detalleOrden.getIdDetalleOrden())) {
                                JOptionPane.showMessageDialog(frmDetalleOrdenA, "DetalleOrden eliminado");
                            } else {
                                JOptionPane.showMessageDialog(frmDetalleOrdenA, "Error al eliminar");
                            }
                        }

                    } else {
                        JOptionPane.showMessageDialog(frmDetalleOrdenA, "Debe seleccionar un registro");
                    }
                    break;

            }
        }

        if (frmDetalleOrdenB.isActive()) { //Botones del frmDetalleOrdenB
            switch (e.getActionCommand()) {

                case "Guardar":
                    try {
                    detalleOrden.setIdDetalleOrden(Integer.parseInt(frmDetalleOrdenB.txtIdOrden.getText().trim()));
                    detalleOrden.setIdOrdenTrabajo(Integer.parseInt(frmDetalleOrdenB.txtOrden.getText().trim()));
                    detalleOrden.setIdMantenimiento((frmDetalleOrdenB.txtMantenimiento.getText().trim()));
                    detalleOrden.setPrecio(Integer.parseInt(frmDetalleOrdenB.txtPrecio.getText().trim()));
                    detalleOrden.setObservaciones(frmDetalleOrdenB.txaObservaciones.getText().trim());
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(frmDetalleOrdenB, "Error de tipo de datos");
                }

                if (opcion == 1) {
                    if (detalleOrdenModel.insertarDetalleOrden(detalleOrden)) {
                        JOptionPane.showMessageDialog(frmDetalleOrdenB, "DetalleOrden Registrado");
                        frmDetalleOrdenB.dispose();
                    } else {
                        JOptionPane.showMessageDialog(frmDetalleOrdenB, "Error al guardar");
                    }
                } else {
                    if (detalleOrdenModel.modificarDetalleOrden(detalleOrden)) {
                        JOptionPane.showMessageDialog(frmDetalleOrdenB, "DetalleOrden Actualizado");
                        frmDetalleOrdenB.dispose();
                    } else {
                        JOptionPane.showMessageDialog(frmDetalleOrdenB, "Error al editar");
                    }
                }
                break;

                case "Limpiar":
                    limpiarFrmB();
                    break;

                case "Cancelar":
                    frmDetalleOrdenB.dispose();
                    break;

            }

            if (e.getSource() == frmDetalleOrdenB.btnBuscarMantenimiento) {
                frmMantenimientoA.lblInfo.setVisible(true);
                frmMantenimientoA.btnNuevo.setVisible(false);
                frmMantenimientoA.btnEditar.setVisible(false);
                frmMantenimientoA.btnEliminar.setVisible(false);
                frmMantenimientoA.tblTabla.addMouseListener(this);

                frmMantenimientoA.setLocationRelativeTo(frmDetalleOrdenB.btnBuscarMantenimiento);
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
                frmDetalleOrdenB.txtMantenimiento.setText(frmMantenimientoA.tblTabla.getValueAt(fila, 0).toString());
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
