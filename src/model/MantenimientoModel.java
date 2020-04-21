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
            CallableStatement cst = db.getConexion().prepareCall("call pa_insertarMantenimiento(?,?,?,?)");
            //Parametros de entrada del procedimiento almacenado
            cst.setString(1, mantenimiento.getIdMantenimiento());
            cst.setString(2, mantenimiento.getDescripcion());
            cst.setInt(3, mantenimiento.getPrecio());
            //Parametro de salida del procedimiento almacenado
            cst.registerOutParameter(4, java.sql.Types.BOOLEAN);

            //Ejecucion del procedimiento almacenado
            cst.execute();

            return cst.getBoolean(4);

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
            CallableStatement cst = db.getConexion().prepareCall("call pa_editarMantenimiento(?,?,?,?)");
            //Parametros de entrada del procedimiento almacenado
            cst.setString(1, mantenimiento.getIdMantenimiento());
            cst.setString(2, mantenimiento.getDescripcion());
            cst.setInt(3, mantenimiento.getPrecio());
            //Parametro de salida del procedimiento almacenado
            cst.registerOutParameter(4, java.sql.Types.BOOLEAN);

            //Ejecucion del procedimiento almacenado
            cst.execute();

            return cst.getBoolean(4);

        } catch (SQLException e) {
            return false;
        } finally {
            db.desconectar();
        }
    }

    public boolean eliminarMantenimiento(String idMantenimiento) {

        DataBase db = new DataBase();

        try {
            db.conectar();
            CallableStatement cst = db.getConexion().prepareCall("call pa_eliminarMantenimiento(?,?)");
            //Parametros de entrada del procedimiento almacenado
            cst.setString(1, idMantenimiento);

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

        db.ejecutarSqlSelect("Select * from mantenimiento order by idMantenimiento");
        rs = db.obtenerRegistro();

        return rs;
    }

    public ResultSet filtrarMantenimiento(String texto) {

        DataBase db = new DataBase();
        ResultSet rs;

        db.ejecutarSqlSelect("Select * from mantenimiento where idMantenimiento like '%" + texto + "%' or descripccion like '%" + texto + "%'");
        rs = db.obtenerRegistro();

        return rs;
    }

}
