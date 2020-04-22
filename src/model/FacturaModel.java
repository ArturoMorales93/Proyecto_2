/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javaBeans.Factura;

/**
 *
 * @author Arthur
 */
public class FacturaModel {

    public boolean insertarFactura(Factura factura) {

        DataBase db = new DataBase();

        try {
            db.conectar();
            CallableStatement cst = db.getConexion().prepareCall("call pa_insertarFactura(?,?,?,?,?,?,?,?,?)");
            //Parametros de entrada del procedimiento almacenado
            cst.setInt(1, factura.getIdFactura());
            cst.setString(2, factura.getFecha());
            cst.setInt(3, factura.getIdOrden());
            cst.setString(4, factura.getIdEmpleado());
            cst.setInt(5, factura.getIdCliente());
            cst.setInt(6, factura.getImpuesto());
            cst.setInt(7, factura.getSubTotal());
            cst.setInt(8, factura.getTotal());
            
            //Parametro de salida del procedimiento almacenado
            cst.registerOutParameter(9, java.sql.Types.BOOLEAN);

            //Ejecucion del procedimiento almacenado
            cst.execute();

            return cst.getBoolean(9);

        } catch (SQLException e) {
            return false;
        } finally {
            db.desconectar();
        }
    }

    public boolean modificarFactura(Factura factura) {

        DataBase db = new DataBase();

        try {
            db.conectar();
            CallableStatement cst = db.getConexion().prepareCall("call pa_editarFactura(?,?,?,?,?,?,?,?,?)");
            //Parametros de entrada del procedimiento almacenado
            cst.setInt(1, factura.getIdFactura());
            cst.setString(2, factura.getFecha());
            cst.setInt(3, factura.getIdOrden());
            cst.setString(4, factura.getIdEmpleado());
            cst.setInt(5, factura.getIdCliente());
            cst.setInt(6, factura.getImpuesto());
            cst.setInt(7, factura.getSubTotal());
            cst.setInt(8, factura.getTotal());

            //Parametro de salida del procedimiento almacenado
            cst.registerOutParameter(9, java.sql.Types.BOOLEAN);

            //Ejecucion del procedimiento almacenado
            cst.execute();

            return cst.getBoolean(9);

        } catch (SQLException e) {
            return false;
        } finally {
            db.desconectar();
        }
    }

    public boolean eliminarFactura(int idFactura) {

        DataBase db = new DataBase();

        try {
            db.conectar();
            CallableStatement cst = db.getConexion().prepareCall("call pa_eliminarFactura(?,?)");
            //Parametros de entrada del procedimiento almacenado
            cst.setInt(1, idFactura);

            //Parametro de salida del procedimiento almacenado
            cst.registerOutParameter(2, java.sql.Types.BOOLEAN);

            //Ejecucion del procedimiento almacenado
            cst.execute();

            return cst.getBoolean(2);

        } catch (SQLException e) {
            return false;
        } finally {
            db.desconectar();
        }
    }

    public ResultSet mostrarFactura() {

        DataBase db = new DataBase();
        ResultSet rs;

        db.ejecutarSqlSelect("Select * from factura");
        rs = db.obtenerRegistro();

        return rs;
    }

    public ResultSet filtrarFactura(String texto) {

        DataBase db = new DataBase();
        ResultSet rs;

        db.ejecutarSqlSelect("Select * from factura where idFactura like '%" + texto + "%'");
        rs = db.obtenerRegistro();

        return rs;
    }

}
