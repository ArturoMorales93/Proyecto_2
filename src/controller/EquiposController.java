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
import javaBeans.Equipo;
import javaBeans.Mantenimiento;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import model.ClienteModel;
import model.EquipoModel;
import view.FrmClientesView;
import view.FrmEquiposView;
import view.FrmNuevoClienteView;
import view.FrmNuevoEquipoView;

/**
 *
 * @author Daniel-san
 */
public class EquiposController implements ActionListener, KeyListener, WindowListener, MouseListener, FocusListener {

    private Equipo equipo;

    private EquipoModel equiModel;
    private FrmEquiposView frmA;
    private FrmNuevoEquipoView frmB;

    private DefaultTableModel modelo;
    private int opcion;

    // datos de cliente
    Cliente cliente = new Cliente();
    ClienteModel clienteModel = new ClienteModel();
    FrmClientesView frmClientesA = new FrmClientesView(null, true);
    FrmNuevoClienteView frmClientesB = new FrmNuevoClienteView(null, true);

    public EquiposController(Equipo equipo, EquipoModel equiModel, FrmEquiposView frmA, FrmNuevoEquipoView frmB) {
        this.equipo = equipo;
        this.equiModel = equiModel;
        this.frmA = frmA;
        this.frmB = frmB;

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
        this.frmB.btnBuscarCliente.addActionListener(this);

    }

    //Metodos
    public void limpiarFrmB() {
        frmB.txtIdEquipo.setText(null);
        frmB.txtIdCliente.setText(null);
        frmB.txtDescripcion.setText(null);
        frmB.txtIdEquipo.requestFocus();
        frmB.txtMarca.setText(null);
        frmB.txtNombreCliente.setText(null);
        frmB.txtNombreCliente.setText(null);

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
        String columnas[] = {"ID Equipos", "Descripcción", "Marca", "ID Cliente", "Nombre cliente"};
        modelo = new DefaultTableModel(null, columnas) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        frmA.txtBuscar.setText("Filtrar por ID o Nombre");
        frmA.txtBuscar.setForeground(Color.LIGHT_GRAY);
        frmA.tblTabla.requestFocus();
        ResultSet rs = equiModel.mostrarEquipo();

        try {

            if (rs.isFirst()) {
                do {
                    equipo = new Equipo(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getInt(4), rs.getString(5));
                    Object newRow[] = {equipo.getIdEquipo(), equipo.getDescripcion(), equipo.getMarca(), equipo.getIdCliente(), equipo.getNombreCliente()};
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
        String columnas[] = {"ID Equipos", "Descripcción", "Marca", "ID Cliente", "Nombre cliente"};
        modelo = new DefaultTableModel(null, columnas) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        ResultSet rs = equiModel.filtrarEquipo(frmA.txtBuscar.getText().trim());

        try {

            if (rs.isFirst()) {
                do {
                    equipo = new Equipo(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getInt(4), rs.getString(5));
                    Object newRow[] = {equipo.getIdEquipo(), equipo.getDescripcion(), equipo.getMarca(), equipo.getIdCliente()};
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
                    frmB.setTitle("Registro de equipo");
                    limpiarFrmB();
                    frmB.txtIdEquipo.setEnabled(true);
                    frmB.txtIdCliente.setEnabled(true);
                    frmB.setEnabled(true);
                    frmB.setVisible(true);
                    break;

                case "Editar":
                    if (frmA.tblTabla.getSelectedRowCount() == 1) {

                        opcion = 2;
                        frmB.setTitle("Actualización de Equipo");
                        int fila = frmA.tblTabla.getSelectedRow();

                        frmB.txtIdEquipo.setEnabled(false);
                        frmB.txtIdEquipo.setText(frmA.tblTabla.getValueAt(fila, 0).toString());
                        frmB.txtDescripcion.setText(frmA.tblTabla.getValueAt(fila, 1).toString());
                        frmB.txtMarca.setText(frmA.tblTabla.getValueAt(fila, 2).toString());
                        frmB.txtIdCliente.setText(frmA.tblTabla.getValueAt(fila, 3).toString());
                        frmB.txtNombreCliente.setText(frmA.tblTabla.getValueAt(fila, 4).toString());

                        frmB.setVisible(true);

                    } else {
                        JOptionPane.showMessageDialog(frmA, "Debe seleccionar un registro");
                    }
                    break;

                case "Eliminar":
                    if (frmA.tblTabla.getSelectedRowCount() == 1) {

                        int fila = frmA.tblTabla.getSelectedRow();
                        equipo.setIdEquipo(Integer.parseInt(frmA.tblTabla.getValueAt(fila, 0).toString()));
                        int resp = JOptionPane.showConfirmDialog(frmA, "¿Desea eliminar el equipo " + equipo.getIdEquipo() + "?", "Seleccionar una Opción", 0);

                        if (resp == 0) {
                            if (equiModel.eliminarEquipo(equipo.getIdEquipo())) {
                                JOptionPane.showMessageDialog(frmA, "Equipo eliminado");
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
                    equipo.setIdEquipo(Integer.parseInt(frmB.txtIdEquipo.getText().trim()));
                    equipo.setDescripcion(frmB.txtDescripcion.getText().trim());
                    equipo.setMarca(frmB.txtMarca.getText().trim());
                    equipo.setIdCliente(Integer.parseInt(frmB.txtIdCliente.getText().trim()));
                    equipo.setNombreCliente(frmB.txtNombreCliente.getText().trim());

                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(frmB, "Error de tipo de datos");
                }

                if (opcion == 1) {
                    if (equiModel.insertarEquipo(equipo)) {
                        JOptionPane.showMessageDialog(frmB, "Equipo Registrado");
                        frmB.dispose();
                    } else {
                        JOptionPane.showMessageDialog(frmB, "Error al guardar");
                    }
                } else {
                    if (equiModel.modificarEquipo(equipo)) {
                        JOptionPane.showMessageDialog(frmB, "Equipos Actualizado");
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

                case "...":
                   ClienteController clienteController = new ClienteController(cliente, clienteModel, frmClientesA, frmClientesB);

                    frmClientesA.lblInfo.setVisible(true);
                    frmClientesA.btnNuevo.setVisible(true);
                    frmClientesA.btnEditar.setVisible(false);
                    frmClientesA.btnEliminar.setVisible(false);
                    frmClientesA.tblTabla.addMouseListener(this);

                    frmClientesA.setLocationRelativeTo(null);
                    frmClientesA.setVisible(true);

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

        if (e.getSource() == frmClientesA.tblTabla && e.getClickCount() == 2) {

            int fila = frmClientesA.tblTabla.getSelectedRow();
            frmB.txtIdCliente.setText(frmClientesA.tblTabla.getValueAt(fila, 0).toString());
            frmB.txtNombreCliente.setText(frmClientesA.tblTabla.getValueAt(fila, 1).toString());
            frmClientesA.dispose();

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
        frmA.lblTexto.setVisible(true);
    }

    @Override
    public void mouseExited(MouseEvent e) {
        frmA.lblTexto.setVisible(false);
    }

}
