/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javaBeans.OrdenTrabajo;

/**
 *
 * @author Arthur
 */
public class OrdenTrabajoModel {

    public boolean insertarOrdenTrabajo(OrdenTrabajo ordenTrabajo) {

        DataBase db = new DataBase();

        try {
            db.conectar();
            CallableStatement cst = db.getConexion().prepareCall("call pa_insertarOrdenTrabajo(?)");
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

    public boolean modificarOrdenTrabajo(OrdenTrabajo ordenTrabajo) {

        DataBase db = new DataBase();

        try {
            db.conectar();
            CallableStatement cst = db.getConexion().prepareCall("call pa_modificarOrdenTrabajo(?)");
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

    public boolean eliminarOrdenTrabajo(int idOrdenTrabajo) {

        DataBase db = new DataBase();

        try {
            db.conectar();
            CallableStatement cst = db.getConexion().prepareCall("call pa_eliminarOrdenTrabajo(?,?)");
            //Parametros de entrada del procedimiento almacenado
            cst.setInt(1, idOrdenTrabajo);

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

    public ResultSet mostrarOrdenTrabajo() {

        DataBase db = new DataBase();
        ResultSet rs;

        db.ejecutarSqlSelect("Select * from ordenDeTrabajo");
        rs = db.obtenerRegistro();

        return rs;
    }
    
    public ResultSet filtrarOrdenTrabajo(String texto) {

        DataBase db = new DataBase();
        ResultSet rs;

        db.ejecutarSqlSelect("Select * from ordenDeTrabajo where idOrden like '%" + texto + "%' or idEmpleado like '%" + texto + "%'");
        rs = db.obtenerRegistro();

        return rs;
    }

}
