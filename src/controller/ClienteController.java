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
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import model.ClienteModel;
import view.FrmClientesView;
import view.FrmNuevoClienteView;

/**
 *
 * @author Arthur
 */
public class ClienteController implements WindowListener, ActionListener, KeyListener, MouseListener, FocusListener {

    //Atributos
    private Cliente cliente;
    private ClienteModel clienteModel;
    private FrmClientesView frmA;
    private FrmNuevoClienteView frmB;

    private DefaultTableModel modelo;
    private int opcion;

    //Constructor
    public ClienteController(Cliente cliente, ClienteModel clienteModel, FrmClientesView frmA, FrmNuevoClienteView frmB) {
        this.cliente = cliente;
        this.clienteModel = clienteModel;
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
        frmB.txtCorreo.setText(null);
        frmB.txtDireccion.setText(null);
        frmB.txtIdCliente.setText(null);
        frmB.txtNombre.setText(null);
        frmB.txtTelefono.setText(null);
        frmB.txtNombre.requestFocus();
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
        String columnas[] = {"ID", "Nombre", "Teléfono", "Dirección", "Correo"};
        modelo = new DefaultTableModel(null, columnas) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        frmA.txtBuscar.setText("Filtrar por ID o Nombre");
        frmA.txtBuscar.setForeground(Color.LIGHT_GRAY);
        frmA.tblTabla.requestFocus();
        ResultSet rs = clienteModel.mostrarCliente();

        try {

            if (rs.isFirst()) {
                do {
                    cliente = new Cliente(rs.getInt(1), rs.getString(2), rs.getInt(3), rs.getString(4), rs.getString(5));
                    Object newRow[] = {cliente.getIdCliente(), cliente.getNombreCliente(), cliente.getNumeroTelefono(),
                        cliente.getDireccion(), cliente.getEmail()};
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
        String columnas[] = {"ID", "Nombre", "Teléfono", "Dirección", "Correo"};
        modelo = new DefaultTableModel(null, columnas) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        ResultSet rs = clienteModel.filtrarCliente(frmA.txtBuscar.getText().trim());

        try {

            if (rs.isFirst()) {
                do {
                    cliente = new Cliente(rs.getInt(1), rs.getString(2), rs.getInt(3), rs.getString(4), rs.getString(5));
                    Object newRow[] = {cliente.getIdCliente(), cliente.getNombreCliente(), cliente.getNumeroTelefono(),
                        cliente.getDireccion(), cliente.getEmail()};
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
                    frmB.setTitle("Registro de Cliente");
                    limpiarFrmB();
                    frmB.setVisible(true);
                    break;

                case "Editar":
                    if (frmA.tblTabla.getSelectedRowCount() == 1) {

                        opcion = 2;
                        frmB.setTitle("Actualización de Cliente");
                        int fila = frmA.tblTabla.getSelectedRow();

                        frmB.txtIdCliente.setEnabled(false);
                        frmB.txtIdCliente.setText(frmA.tblTabla.getValueAt(fila, 0).toString());
                        frmB.txtNombre.setText(frmA.tblTabla.getValueAt(fila, 1).toString());
                        frmB.txtTelefono.setText(frmA.tblTabla.getValueAt(fila, 2).toString());
                        frmB.txtDireccion.setText(frmA.tblTabla.getValueAt(fila, 3).toString());
                        frmB.txtCorreo.setText(frmA.tblTabla.getValueAt(fila, 4).toString());

                        frmB.setVisible(true);

                    } else {
                        JOptionPane.showMessageDialog(frmA, "Debe seleccionar un registro");
                    }
                    break;

                case "Eliminar":
                    if (frmA.tblTabla.getSelectedRowCount() == 1) {

                        int fila = frmA.tblTabla.getSelectedRow();
                        cliente.setIdCliente(Integer.parseInt(frmA.tblTabla.getValueAt(fila, 0).toString()));
                        int resp = JOptionPane.showConfirmDialog(frmA, "¿Desea eliminar el cliente " + cliente.getIdCliente() + "?", "Seleccionar una Opción", 0);

                        if (resp == 0) {
                            if (clienteModel.eliminarCliente(cliente.getIdCliente())) {
                                JOptionPane.showMessageDialog(frmA, "Cliente eliminado");
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
                    cliente.setIdCliente(Integer.parseInt(frmB.txtIdCliente.getText().trim()));
                    cliente.setNombreCliente(frmB.txtNombre.getText().trim());
                    cliente.setNumeroTelefono(Integer.parseInt(frmB.txtTelefono.getText().trim()));
                    cliente.setDireccion(frmB.txtDireccion.getText().trim());
                    cliente.setEmail(frmB.txtCorreo.getText().trim());
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(frmB, "Error de tipo de datos");
                }

                if (opcion == 1) {
                    if (clienteModel.insertarCliente(cliente)) {
                        JOptionPane.showMessageDialog(frmB, "Cliente Registrado");
                        frmB.dispose();
                    } else {
                        JOptionPane.showMessageDialog(frmB, "Error al guardar");
                    }
                } else {
                    if (clienteModel.modificarCliente(cliente)) {
                        JOptionPane.showMessageDialog(frmB, "Cliente Actualizado");
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
