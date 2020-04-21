/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javaBeans.Equipo;

/**
 *
 * @author Arthur
 */
public class EquipoModel {

    public boolean insertarEquipo(Equipo equipo) {

        DataBase db = new DataBase();

        try {
            db.conectar();
            CallableStatement cst = db.getConexion().prepareCall("call pa_insertarEquipos(?,?,?,?,?,?)");
            //Parametros de entrada del procedimiento almacenado
            cst.setInt(1, equipo.getIdEquipo());
            cst.setString(2, equipo.getDescripcion());
            cst.setString(3, equipo.getMarca());
            cst.setInt(4, equipo.getIdCliente());
            cst.setString(5, equipo.getNombreCliente());
            //Parametro de salida del procedimiento almacenado
            cst.registerOutParameter(6, java.sql.Types.BOOLEAN);

            //Ejecucion del procedimiento almacenado
            cst.execute();

            return cst.getBoolean(6);

        } catch (SQLException e) {
            return false;
        } finally {
            db.desconectar();
        }
    }

    public boolean modificarEquipo(Equipo equipo) {

        DataBase db = new DataBase();

        try {
            db.conectar();
            CallableStatement cst = db.getConexion().prepareCall("call pa_editarEquipos(?,?,?,?,?,?)");
            //Parametros de entrada del procedimiento almacenado
            cst.setInt(1, equipo.getIdEquipo());
            cst.setString(2, equipo.getDescripcion());
            cst.setString(3, equipo.getMarca());
            cst.setInt(4, equipo.getIdCliente());
            cst.setString(5, equipo.getNombreCliente());
            //Parametro de salida del procedimiento almacenado
            cst.registerOutParameter(6, java.sql.Types.BOOLEAN);

            //Ejecucion del procedimiento almacenado
            cst.execute();

            return cst.getBoolean(6);

        } catch (SQLException e) {
            return false;
        } finally {
            db.desconectar();
        }
    }

    public boolean eliminarEquipo(int idEquipo) {

        DataBase db = new DataBase();

        try {
            db.conectar();
            CallableStatement cst = db.getConexion().prepareCall("call pa_eliminarEquipos(?,?)");
            //Parametros de entrada del procedimiento almacenado
            cst.setInt(1, idEquipo);

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

    public ResultSet mostrarEquipo() {

        DataBase db = new DataBase();
        ResultSet rs;

        db.ejecutarSqlSelect("Select * from equipos");
        rs = db.obtenerRegistro();

        return rs;
    }

    public ResultSet filtrarEquipo(String texto) {

        DataBase db = new DataBase();
        ResultSet rs;

        db.ejecutarSqlSelect("Select * from equipo where idEquipos like '%" + texto + "%' or marca like '%" + texto + "%'");
        rs = db.obtenerRegistro();

        return rs;
    }

}
