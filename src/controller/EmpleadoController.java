/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import Exepciones.ProyectoException;
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
import javaBeans.Empleado;
import javax.swing.JOptionPane;
import static javax.swing.JOptionPane.*;
import javax.swing.table.DefaultTableModel;
import model.EmpleadoModel;
import view.FrmEmpleadosView;
import view.FrmNuevoEmpleadoView;

/**
 *
 * @author Daniel-san
 */
public class EmpleadoController implements ActionListener, WindowListener, MouseListener, KeyListener, FocusListener {

    private Empleado empleado;
    private EmpleadoModel empleadoModel;
    private FrmEmpleadosView frmA;
    private FrmNuevoEmpleadoView frmB;
    private int opcion;
    private DefaultTableModel modelo;

    public EmpleadoController(Empleado empleado, EmpleadoModel empleadoModel, FrmEmpleadosView frmA, FrmNuevoEmpleadoView frmB) {
        this.empleado = empleado;
        this.empleadoModel = empleadoModel;
        this.frmA = frmA;
        this.frmB = frmB;

        //frame a
        this.frmA.addWindowListener(this);
        this.frmA.lblTexto.setVisible(false);
        this.frmA.lblInfo.setVisible(false);
        this.frmA.lblInfo.addMouseListener(this);
        this.frmA.tblTabla.requestFocus();
        this.frmA.tblTabla.getTableHeader().setResizingAllowed(false);
        this.frmA.tblTabla.getTableHeader().setReorderingAllowed(false);
        this.frmA.btnNuevo.addActionListener(this);
        this.frmA.btnEditar.addActionListener(this);
        this.frmA.btnEliminar.addActionListener(this);
        this.frmA.txtBuscar.addKeyListener(this);
        this.frmA.txtBuscar.addFocusListener(this);

        //frame b
        this.frmB.setLocationRelativeTo(null);
        this.frmB.btnGuardar.addActionListener(this);
        this.frmB.btnLimpiar.addActionListener(this);
        this.frmB.btnCancelar.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (frmA.isActive()) { //Botones del FrameA
            switch (e.getActionCommand()) {

                case "Nuevo":
                    opcion = 1;
                    frmB.setTitle("Registro de Empleado");
                    limpiarFrmB();

                    frmB.txtIdUsuario.setEnabled(true);
                    frmB.txtIdUsuario.requestFocus();
                    frmB.setVisible(true);
                    break;

                case "Editar":
                    if (frmA.tblTabla.getSelectedRowCount() == 1) {

                        opcion = 2;
                        frmB.setTitle("Actualización de Cliente");
                        int fila = frmA.tblTabla.getSelectedRow();

                        frmB.txtIdUsuario.setEnabled(false);
                        frmB.txtIdUsuario.setText(frmA.tblTabla.getValueAt(fila, 0).toString());
                        frmB.txtNombre.setText(frmA.tblTabla.getValueAt(fila, 1).toString());
                        frmB.cmbTipoEmpleado.setSelectedItem(frmA.tblTabla.getValueAt(fila, 2).toString());
                        frmB.txtCodigo.setText(frmA.tblTabla.getValueAt(fila, 3).toString());

                        frmB.setVisible(true);

                    } else {
                        JOptionPane.showMessageDialog(frmA, "Debe seleccionar un registro");
                    }
                    break;

                case "Eliminar":
                    if (frmA.tblTabla.getSelectedRowCount() == 1) {

                        int fila = frmA.tblTabla.getSelectedRow();
                        empleado.setIdEmpleado(frmA.tblTabla.getValueAt(fila, 0).toString());
                        int resp = JOptionPane.showConfirmDialog(frmA, "¿Desea eliminar el empleado " + empleado.getIdEmpleado() + "?", "Seleccionar una Opción", 0);

                        if (resp == 0) {
                            if (empleadoModel.eliminarEmpleado(empleado.getIdEmpleado())) {
                            } else {
                                JOptionPane.showMessageDialog(frmA, "Error al eliminar");
                            }
                        }

                    } else {
                        JOptionPane.showMessageDialog(frmA, "Debe seleccionar un registro");
                    }
                    break;

            }
        }

        if (frmB.isActive()) { //Botones del FrameB
            switch (e.getActionCommand()) {

                case "Guardar":
                    try {

                    if (frmB.cmbTipoEmpleado.getSelectedIndex() == 0) {
                        throw new ProyectoException(1);

                    }

                    empleado.setIdEmpleado(frmB.txtIdUsuario.getText());
                    empleado.setNombreEmpleado(frmB.txtNombre.getText().trim());
                    empleado.setTipoEmpleado(String.valueOf(frmB.cmbTipoEmpleado.getSelectedItem()));
                    empleado.setCodigoEmpresarial(Integer.parseInt(frmB.txtCodigo.getText().trim()));

                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(frmB, "Error de tipo de datos\nRevise");
                } catch (ProyectoException exc) {
                    JOptionPane.showMessageDialog(frmB, exc.getMessage(), "Aviso!", INFORMATION_MESSAGE);
                }

                if (opcion == 1) {
                    if (empleadoModel.insertarEmpleado(empleado)) {
                        JOptionPane.showMessageDialog(frmB, "Empleado Registrado");
                        frmB.dispose();
                    } else {
                        JOptionPane.showMessageDialog(frmB, "Error al guardar\nRevise Los Campos");
                    }
                } else {
                    if (empleadoModel.modificarEmpleado(empleado)) {
                        JOptionPane.showMessageDialog(frmB, "Empleado Actualizado");
                        frmB.dispose();
                    } else {
                        JOptionPane.showMessageDialog(frmB, "Error al editar\nRevise los campos");
                    }
                }
                break;

                case "Limpiar":
                    limpiarFrmB();
                    break;

                case "Cancelar":
                    frmB.dispose();
                    break;

            }
        }

    }

    public void limpiarFrmB() {
        if (opcion == 1) {
            frmB.txtIdUsuario.setText(null);
            frmB.txtIdUsuario.requestFocus();
        } else {
            frmB.txtNombre.requestFocus();
        }
        frmB.txtNombre.setText(null);
        frmB.cmbTipoEmpleado.setSelectedIndex(0);
        frmB.txtCodigo.setText(null);

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
    public void windowActivated(WindowEvent e) {
        String columnas[] = {"ID Empleado", "Nombre Empleado", "Tipo Empleado", "Código Empresarial"};
        modelo = new DefaultTableModel(null, columnas) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        frmA.txtBuscar.setText("Filtrar por ID o Nombre");
        frmA.txtBuscar.setForeground(Color.LIGHT_GRAY);
        frmA.tblTabla.requestFocus();
        ResultSet rs = empleadoModel.mostrarEmpleado();

        try {

            if (rs.isFirst()) {
                do {
                    empleado = new Empleado(rs.getString(1), rs.getString(2), rs.getString(3), rs.getInt(4));
                    Object newRow[] = {empleado.getIdEmpleado(), empleado.getNombreEmpleado(), empleado.getTipoEmpleado(),
                        empleado.getCodigoEmpresarial()};
                    modelo.addRow(newRow);
                } while (rs.next());
            }
            frmA.tblTabla.setModel(modelo);

            frmA.lblCantidadRegistros.setText("Cantidad de Registros: " + modelo.getRowCount());

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(frmA, ex.getMessage());
        }

    }

    @Override
    public void windowDeactivated(WindowEvent e) {

    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {
        frmA.lblTexto.setVisible(true);
    }

    @Override
    public void mouseExited(MouseEvent e) {
        frmA.lblTexto.setVisible(false);
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {
        String columnas[] = {"ID Empleado", "Nombre Empleado", "Tipo Empleado", "Código Empresarial"};
        modelo = new DefaultTableModel(null, columnas) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        ResultSet rs = empleadoModel.filtrarEmpleado(frmA.txtBuscar.getText().trim());

        try {

            if (rs.isFirst()) {
                do {
                    empleado = new Empleado(rs.getString(1), rs.getString(2), rs.getString(3), rs.getInt(4));
                    Object newRow[] = {empleado.getIdEmpleado(), empleado.getNombreEmpleado(), empleado.getTipoEmpleado(),
                        empleado.getCodigoEmpresarial()};
                    modelo.addRow(newRow);
                } while (rs.next());
            }
            frmA.tblTabla.setModel(modelo);

            frmA.lblCantidadRegistros.setText("Cantidad de Registros: " + modelo.getRowCount());

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(frmA, ex.getMessage());
        }
    }

    @Override
    public void focusGained(FocusEvent e) {
        if (frmA.txtBuscar.getText().equals("Filtrar por ID o Nombre")) {
            frmA.txtBuscar.setText(null);
            frmA.txtBuscar.setForeground(Color.BLACK);
        }
    }

    @Override
    public void focusLost(FocusEvent e) {
        if (frmA.txtBuscar.getText().equals("")) {
            frmA.txtBuscar.setText("Filtrar por ID o Nombre");
            frmA.txtBuscar.setForeground(Color.LIGHT_GRAY);
        }
    }

}
