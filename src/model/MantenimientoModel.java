/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javaBeans.Mantenimiento;

/**
 *
 * @author Arthur
 */
public class MantenimientoModel {

    public boolean insertarMantenimiento(Mantenimiento mantenimiento) {

        DataBase db = new DataBase();

        try {
            db.conectar();
            CallableStatement cst = db.getConexion().prepareCall("call pa_insertarMantenimiento(?)");
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

    public boolean modificarMantenimiento(Mantenimiento mantenimiento) {

        DataBase db = new DataBase();

        try {
            db.conectar();
            CallableStatement cst = db.getConexion().prepareCall("call pa_modificarMantenimiento(?)");
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

    public boolean eliminarMantenimiento(int idMantenimiento) {

        DataBase db = new DataBase();

        try {
            db.conectar();
            CallableStatement cst = db.getConexion().prepareCall("call pa_eliminarMantenimiento(?,?)");
            //Parametros de entrada del procedimiento almacenado
            cst.setInt(1, idMantenimiento);

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

    public ResultSet mostrarMantenimiento() {

        DataBase db = new DataBase();
        ResultSet rs;

        db.ejecutarSqlSelect("Select * from mantenimiento");
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
