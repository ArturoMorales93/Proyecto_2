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
            CallableStatement cst = db.getConexion().prepareCall("call pa_insertarOrdenDeTrabajo(?,?,?,?,?,?)");
            //Parametros de entrada del procedimiento almacenado
            cst.setInt(1, ordenTrabajo.getIdOrdenTrabajo());
            cst.setInt(2, ordenTrabajo.getIdCliente());
            cst.setString(3, ordenTrabajo.getIdEmpleado());
            cst.setInt(4, ordenTrabajo.getIdEquipo());
            cst.setInt(5, ordenTrabajo.getTotal());

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

    public boolean modificarOrdenTrabajo(OrdenTrabajo ordenTrabajo) {

        DataBase db = new DataBase();

        try {
            db.conectar();
            CallableStatement cst = db.getConexion().prepareCall("call pa_editarOrdenDeTrabajo(?,?,?,?,?,?)");
            //Parametros de entrada del procedimiento almacenado
            cst.setInt(1, ordenTrabajo.getIdOrdenTrabajo());
            cst.setInt(2, ordenTrabajo.getIdCliente());
            cst.setString(3, ordenTrabajo.getIdEmpleado());
            cst.setInt(4, ordenTrabajo.getIdEquipo());
            cst.setInt(5, ordenTrabajo.getTotal());

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

    public boolean eliminarOrdenTrabajo(int idOrdenTrabajo) {

        DataBase db = new DataBase();

        try {
            db.conectar();
            CallableStatement cst = db.getConexion().prepareCall("call pa_eliminarOrdenDeTrabajo(?,?)");
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

        db.ejecutarSqlSelect("SELECT oc.idOrden, idCliente, idEmpleado, idEquipo, \n"
                + "(SELECT sum(precio) FROM detalleOrden WHERE idOrden = oc.idOrden) AS total \n"
                + "FROM ordendetrabajo AS oc");
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
