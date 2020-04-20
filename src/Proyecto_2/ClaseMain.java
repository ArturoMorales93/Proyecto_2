/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Proyecto_2;

import controller.MainController;
import view.FrmClientesView;
import view.FrmEmpleadosView;
import view.FrmEquiposView;
import view.FrmFacturacionView;
import view.FrmMantenimientoView;
import view.FrmOrdenTrabajoView;
import view.FrmTiendaElectronica;

/**
 *
 * @author Arthur
 */
public class ClaseMain {

    //Atributos
    private static final FrmTiendaElectronica winTiendaElectronica = new FrmTiendaElectronica();
    private static final FrmEquiposView winEquiposView = new FrmEquiposView(winTiendaElectronica, true);
    private static final FrmClientesView winClientesView = new FrmClientesView(winTiendaElectronica, true);
    private static final FrmEmpleadosView winEmpleadosView = new FrmEmpleadosView(winTiendaElectronica, true);
    private static final FrmFacturacionView winFacturacionView = new FrmFacturacionView(winTiendaElectronica, true);
    private static final FrmOrdenTrabajoView winOrdenTrabajoView = new FrmOrdenTrabajoView(winTiendaElectronica, true);
    private static final FrmMantenimientoView winMantenimientoView = new FrmMantenimientoView(winTiendaElectronica, true);

    public static void main(String[] args) {
        MainController mainController = new MainController(winTiendaElectronica, winEquiposView, winClientesView, winEmpleadosView, winFacturacionView, winOrdenTrabajoView, winMantenimientoView);
        mainController.iniciarVista();
    }

}
