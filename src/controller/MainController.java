/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javaBeans.Cliente;
import javaBeans.Empleado;
import javaBeans.Equipo;
import javaBeans.Factura;
import javaBeans.Mantenimiento;
import javaBeans.OrdenTrabajo;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import model.ClienteModel;
import model.EmpleadoModel;
import model.EquipoModel;
import model.FacturaModel;
import model.MantenimientoModel;
import model.OrdenTrabajoModel;
import view.FrmClientesView;
import view.FrmEmpleadosView;
import view.FrmEquiposView;
import view.FrmFacturacionView;
import view.FrmMantenimientoView;
import view.FrmNuevoClienteView;
import view.FrmNuevoEmpleadoView;
import view.FrmNuevoEquipoView;
import view.FrmNuevoFacturaView;
import view.FrmNuevoMantenimientoView;
import view.FrmNuevoOrdenTrabajoView;
import view.FrmOrdenTrabajoView;
import view.FrmTiendaElectronica;

/**
 *
 * @author Arthur
 */
public class MainController implements ActionListener {

    //Atributos
    private final FrmTiendaElectronica winTiendaElectronica;
    private final FrmEquiposView winEquiposA;
    private final FrmClientesView winClientesA;
    private final FrmEmpleadosView winEmpleadosA;
    private final FrmFacturacionView winFacturacionA;
    private final FrmOrdenTrabajoView winOrdenTrabajoA;
    private final FrmMantenimientoView winMantenimientoA;

    //Contructor
    public MainController(FrmTiendaElectronica winTiendaElectronica, FrmEquiposView winEquiposA, FrmClientesView winClientesA,
            FrmEmpleadosView winEmpleadosA, FrmFacturacionView winFacturacionA, FrmOrdenTrabajoView winOrdenTrabajoA, FrmMantenimientoView winMantenimientoA) {

        this.winTiendaElectronica = winTiendaElectronica;
        this.winEquiposA = winEquiposA;
        this.winClientesA = winClientesA;
        this.winEmpleadosA = winEmpleadosA;
        this.winFacturacionA = winFacturacionA;
        this.winOrdenTrabajoA = winOrdenTrabajoA;
        this.winMantenimientoA = winMantenimientoA;

        this.winTiendaElectronica.btnEquipos.addActionListener(this);
        this.winTiendaElectronica.btnClientes.addActionListener(this);
        this.winTiendaElectronica.btnEmpleados.addActionListener(this);
        this.winTiendaElectronica.btnFacturacion.addActionListener(this);
        this.winTiendaElectronica.btnOrdenTrabajo.addActionListener(this);
        this.winTiendaElectronica.btnMantenimiento.addActionListener(this);

        this.winTiendaElectronica.mnuArchivo.addActionListener(this);
        this.winTiendaElectronica.mnuInicio.addActionListener(this);
        this.winTiendaElectronica.mniAyuda.addActionListener(this);
        this.winTiendaElectronica.mniSalir.addActionListener(this);

        //JavaBeans
        Equipo equipo = new Equipo();
        Cliente cliente = new Cliente();
        Factura factura = new Factura();
        Empleado empleado = new Empleado();
        OrdenTrabajo ordenTrabajo = new OrdenTrabajo();
        Mantenimiento mantenimiento = new Mantenimiento();

        //Models
        EquipoModel equipoModel = new EquipoModel();
        ClienteModel clienteModel = new ClienteModel();
        FacturaModel facturaModel = new FacturaModel();
        EmpleadoModel empleadoModel = new EmpleadoModel();
        OrdenTrabajoModel ordenTrabajoModel = new OrdenTrabajoModel();
        MantenimientoModel mantenimientoModel = new MantenimientoModel();

        //Frames B
        FrmNuevoEquipoView winEquiposB = new FrmNuevoEquipoView(winTiendaElectronica, true);
        FrmNuevoClienteView winClientesB = new FrmNuevoClienteView(winTiendaElectronica, true);
        FrmNuevoEmpleadoView winEmpleadosB = new FrmNuevoEmpleadoView(winTiendaElectronica, true);
        FrmNuevoFacturaView winFacturacionB = new FrmNuevoFacturaView(winTiendaElectronica, true);
        FrmNuevoOrdenTrabajoView winOrdenTrabajoB = new FrmNuevoOrdenTrabajoView(winTiendaElectronica, true);
        FrmNuevoMantenimientoView winMantenimientoB = new FrmNuevoMantenimientoView(winTiendaElectronica, true);

        //Controllers
        EquiposController equipoController = new EquiposController(equipo, equipoModel, winEquiposA, winEquiposB);
        ClienteController clienteController = new ClienteController(cliente, clienteModel, winClientesA, winClientesB);
        FacturaController facturaController = new FacturaController(factura, facturaModel, winFacturacionA, winFacturacionB, winClientesA, winEmpleadosA, winOrdenTrabajoA);
        OrdenTrabajoController ordenTrabajoController = new OrdenTrabajoController(ordenTrabajo, ordenTrabajoModel, winOrdenTrabajoA, winOrdenTrabajoB, winEquiposA, winClientesA, winEmpleadosA, winFacturacionB);
        MantenimientoController mantenimientoController = new MantenimientoController(mantenimiento, mantenimientoModel, winMantenimientoA, winMantenimientoB);
        EmpleadoController empleadoController = new EmpleadoController(empleado, empleadoModel, winEmpleadosA, winEmpleadosB);
    }

    //Metodos
    public void iniciarVista() {
        ImageIcon img = new ImageIcon("/img/icono.png");
        this.winTiendaElectronica.setIconImage(new ImageIcon(getClass().getResource("/img/icono2.png")).getImage());
        this.winTiendaElectronica.setLocationRelativeTo(null);
        this.winTiendaElectronica.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        switch (e.getActionCommand()) {
            case "Clientes":
                winClientesA.setLocationRelativeTo(null);
                winClientesA.setModal(false);
                winClientesA.setVisible(true);
                winClientesA.btnNuevo.setVisible(true);
                winClientesA.btnEditar.setVisible(true);
                winClientesA.btnEliminar.setVisible(true);
                winClientesA.lblInfo.setVisible(false);
                break;

            case "Equipos":
                winEquiposA.setLocationRelativeTo(null);
                winEquiposA.setModal(false);
                winEquiposA.setVisible(true);
                winEquiposA.btnNuevo.setVisible(true);
                winEquiposA.btnEditar.setVisible(true);
                winEquiposA.btnEliminar.setVisible(true);
                winEquiposA.lblInfo.setVisible(false);
                break;

            case "Empleados":
                winEmpleadosA.setLocationRelativeTo(null);
                winEmpleadosA.setModal(false);
                winEmpleadosA.setVisible(true);
                winEmpleadosA.btnNuevo.setVisible(true);
                winEmpleadosA.btnEditar.setVisible(true);
                winEmpleadosA.btnEliminar.setVisible(true);
                winEmpleadosA.lblInfo.setVisible(false);
                break;

            case "Orden de trabajo":
                winOrdenTrabajoA.lblTexto.setText("Para acceder a los detalles de una Orden, haga doble click sobre el registro.");
                winOrdenTrabajoA.setLocationRelativeTo(null);
                winOrdenTrabajoA.setModal(false);
                winOrdenTrabajoA.setVisible(true);
                winOrdenTrabajoA.btnNuevo.setVisible(true);
                winOrdenTrabajoA.btnEditar.setVisible(true);
                winOrdenTrabajoA.btnEliminar.setVisible(true);
                break;

            case "Mantenimiento":
                winMantenimientoA.setLocationRelativeTo(null);
                winMantenimientoA.setModal(false);
                winMantenimientoA.setVisible(true);
                break;

            case "Facturación":
                winFacturacionA.setLocationRelativeTo(null);
                winFacturacionA.setModal(false);
                winFacturacionA.setVisible(true);
                break;

            case "Ayuda":
                Icon icono = new ImageIcon(getClass().getResource("/img/icono.png"));
                JOptionPane.showMessageDialog(winTiendaElectronica, "Software diseñado por estudiantes"
                        + " de la UTN\n de la carrera I.T.I, curso Programación II.\n Sede Chorotega, Liberia, 2020\n"
                        + "Derechos Reservados ༼ つ ◕_◕ ༽つ", "Contacto", JOptionPane.PLAIN_MESSAGE, icono);
                break;

            case "Salir":
                System.exit(0);
                break;
        }

    }

}
