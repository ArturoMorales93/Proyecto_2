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

    public static void main(String[] args) {

        //Codigo por defecto para settear el Look and Feel
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FrmTiendaElectronica.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }

        FrmTiendaElectronica winTiendaElectronica = new FrmTiendaElectronica();
        FrmEquiposView winEquiposView = new FrmEquiposView(winTiendaElectronica, true);
        FrmClientesView winClientesView = new FrmClientesView(winTiendaElectronica, true);
        FrmEmpleadosView winEmpleadosView = new FrmEmpleadosView(winTiendaElectronica, true);
        FrmFacturacionView winFacturacionView = new FrmFacturacionView(winTiendaElectronica, true);
        FrmOrdenTrabajoView winOrdenTrabajoView = new FrmOrdenTrabajoView(winTiendaElectronica, true);
        FrmMantenimientoView winMantenimientoView = new FrmMantenimientoView(winTiendaElectronica, true);

        MainController mainController = new MainController(winTiendaElectronica, winEquiposView, winClientesView, winEmpleadosView, winFacturacionView, winOrdenTrabajoView, winMantenimientoView);
        mainController.iniciarVista();
    }

}
