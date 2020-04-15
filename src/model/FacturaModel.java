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
            CallableStatement cst = db.getConexion().prepareCall("call pa_insertarFactura(?)");
            //Parametros de entrada del procedimiento almacenado

            //Parametro de salida del procedimiento almacenado
            cst.registerOutParameter(1, java.sql.Types.BOOLEAN);

            //Ejecucion del procedimiento almacenado
            cst.execute();

            return cst.getBoolean(1);

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
            CallableStatement cst = db.getConexion().prepareCall("call pa_modificarFactura(?)");
            //Parametros de entrada del procedimiento almacenado

            //Parametro de salida del procedimiento almacenado
            cst.registerOutParameter(1, java.sql.Types.BOOLEAN);

            //Ejecucion del procedimiento almacenado
            cst.execute();

            return cst.getBoolean(1);

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
    
    public ResultSet filtrarCliente(String texto) {

        DataBase db = new DataBase();
        ResultSet rs;

        db.ejecutarSqlSelect("Select * from cliente where idCliente like '%" + texto + "%' or nombreCliente like '%" + texto + "%'");
        rs = db.obtenerRegistro();

        return rs;
    }

}
