/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javaBeans.DetalleOrden;

/**
 *
 * @author Arthur
 */
public class DetalleOrdenModel {

    public boolean insertarDetalleOrden(DetalleOrden detalleOrden) {

        DataBase db = new DataBase();

        try {
            db.conectar();
            CallableStatement cst = db.getConexion().prepareCall("call pa_insertarDetalleOrden(?)");
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

    public boolean modificarDetalleOrden(DetalleOrden detalleOrden) {

        DataBase db = new DataBase();

        try {
            db.conectar();
            CallableStatement cst = db.getConexion().prepareCall("call pa_modificarDetalleOrden(?)");
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

    public boolean eliminarDetalleOrden(int idDetalleOrden) {

        DataBase db = new DataBase();

        try {
            db.conectar();
            CallableStatement cst = db.getConexion().prepareCall("call pa_eliminarDetalleOrden(?,?)");
            //Parametros de entrada del procedimiento almacenado
            cst.setInt(1, idDetalleOrden);

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

    public ResultSet mostrarDetalleOrden() {

        DataBase db = new DataBase();
        ResultSet rs;

        db.ejecutarSqlSelect("Select * from detalleOrden");
        rs = db.obtenerRegistro();

        return rs;
    }

}
