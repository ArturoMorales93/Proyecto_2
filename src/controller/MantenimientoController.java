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
import javaBeans.Mantenimiento;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import model.MantenimientoModel;
import view.FrmMantenimientoView;
import view.FrmNuevoMantenimientoView;

/**
 *
 * @author Daniel-san
 */
public class MantenimientoController implements WindowListener, ActionListener, KeyListener, MouseListener, FocusListener {

    private Mantenimiento mantenimiento;
    private MantenimientoModel manModel;
    private FrmMantenimientoView frmA;
    private FrmNuevoMantenimientoView frmB;

    private DefaultTableModel modelo;
    private int opcion;

    //Constructor
    public MantenimientoController(Mantenimiento mantenimiento, MantenimientoModel manModel, FrmMantenimientoView frmA, FrmNuevoMantenimientoView frmB) {
        this.mantenimiento = mantenimiento;
        this.manModel = manModel;
        this.frmA = frmA;
        this.frmB = frmB;

        //Componentes del FrameA
        this.frmA.setLocationRelativeTo(null);
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

        //Componentes del FrameB
        this.frmB.setLocationRelativeTo(null);
        this.frmB.btnGuardar.addActionListener(this);
        this.frmB.btnLimpiar.addActionListener(this);
        this.frmB.btnCancelar.addActionListener(this);
    }

    //Metodos
    public void limpiarFrmB() {
        frmB.txtCodigoMantenimiento.setText(null);
        frmB.txaDescripcion.setText(null);
        frmB.txtPrecio.setText(null);
        frmB.txtCodigoMantenimiento.requestFocus();
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

    @Override
    public void windowActivated(WindowEvent e) {
        String columnas[] = {"ID Manteniento", "Descripcción", "Precio"};
        modelo = new DefaultTableModel(null, columnas) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        frmA.txtBuscar.setText("Filtrar por ID o Nombre");
        frmA.txtBuscar.setForeground(Color.LIGHT_GRAY);
        frmA.tblTabla.requestFocus();
        ResultSet rs = manModel.mostrarMantenimiento();

        try {

            if (rs.isFirst()) {
                do {
                    mantenimiento = new Mantenimiento(rs.getString(1), rs.getString(2), rs.getInt(3));
                    Object newRow[] = {mantenimiento.getIdMantenimiento(), mantenimiento.getDescripcion(), mantenimiento.getPrecio()};
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
    public void keyReleased(KeyEvent e) {
       String columnas[] = {"ID Manteniento", "Descripcción", "Precio"};
        modelo = new DefaultTableModel(null, columnas) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        ResultSet rs = manModel.filtrarMantenimiento(frmA.txtBuscar.getText().trim());

        try {

            if (rs.isFirst()) {
                do {
                    mantenimiento = new Mantenimiento(rs.getString(1), rs.getString(2), rs.getInt(3));
                    Object newRow[] = {mantenimiento.getIdMantenimiento(), mantenimiento.getDescripcion(), mantenimiento.getPrecio()};
                    modelo.addRow(newRow);
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
    public void actionPerformed(ActionEvent e) {
        if (frmA.isActive()) { //Botones del FrameA
            switch (e.getActionCommand()) {

                case "Nuevo":
                    opcion = 1;
                    frmB.setTitle("Registro de Mantenimiento");
                    limpiarFrmB();
                    frmB.txtCodigoMantenimiento.setEnabled(true);
                    frmB.setVisible(true);
                    break;

                case "Editar":
                    if (frmA.tblTabla.getSelectedRowCount() == 1) {

                        opcion = 2;
                        frmB.setTitle("Actualización de Cliente");
                        int fila = frmA.tblTabla.getSelectedRow();

                        frmB.txtCodigoMantenimiento.setEnabled(false);
                        frmB.txtCodigoMantenimiento.setText(frmA.tblTabla.getValueAt(fila, 0).toString());
                        frmB.txaDescripcion.setText(frmA.tblTabla.getValueAt(fila, 1).toString());
                        frmB.txtPrecio.setText(frmA.tblTabla.getValueAt(fila, 2).toString());

                        frmB.setVisible(true);

                    } else {
                        JOptionPane.showMessageDialog(frmA, "Debe seleccionar un registro");
                    }
                    break;

                case "Eliminar":
                    if (frmA.tblTabla.getSelectedRowCount() == 1) {

                        int fila = frmA.tblTabla.getSelectedRow();
                        mantenimiento.setIdMantenimiento(String.valueOf(frmA.tblTabla.getValueAt(fila, 0)));
                        int resp = JOptionPane.showConfirmDialog(frmA, "¿Desea eliminar el mantenimiento " + mantenimiento.getIdMantenimiento() + "?", "Seleccionar una Opción", 0);

                        if (resp == 0) {
                            if (manModel.eliminarMantenimiento(mantenimiento.getIdMantenimiento())) {
                                JOptionPane.showMessageDialog(frmA, "Mantenimiento eliminado");
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
                    mantenimiento.setIdMantenimiento(frmB.txtCodigoMantenimiento.getText().trim());
                    mantenimiento.setDescripcion(frmB.txaDescripcion.getText().trim());
                    mantenimiento.setPrecio(Integer.parseInt(frmB.txtPrecio.getText().trim()));
                   
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(frmB, "Error de tipo de datos");
                }

                if (opcion == 1) {
                    if (manModel.insertarMantenimiento(mantenimiento)) {
                        JOptionPane.showMessageDialog(frmB, "Mantenimiento Registrado");
                        frmB.dispose();
                    } else {
                        JOptionPane.showMessageDialog(frmB, "Error al guardar");
                    }
                } else {
                    if (manModel.modificarMantenimiento(mantenimiento)) {
                        JOptionPane.showMessageDialog(frmB, "Mantenimiento Actualizado");
                        frmB.dispose();
                    } else {
                        JOptionPane.showMessageDialog(frmB, "Error al editar");
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

}
