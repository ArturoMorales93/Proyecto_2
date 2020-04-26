/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javaBeans.DetalleFactura;

/**
 *
 * @author Arthur
 */
public class DetalleFacturaModel {

    public boolean insertarDetalleFactura(DetalleFactura detalleFactura) {

        DataBase db = new DataBase();

        try {
            db.conectar();
            CallableStatement cst = db.getConexion().prepareCall("call pa_insertarDetalleFactura(?,?,?,?,?,?,?)");
            //Parametros de entrada del procedimiento almacenado
            cst.setInt(1, detalleFactura.getIdDetalleFactura());
            cst.setInt(2, detalleFactura.getIdFactura());
            cst.setInt(3, detalleFactura.getCantidad());
            cst.setString(4, detalleFactura.getIdMantenimiento());
            cst.setInt(5, detalleFactura.getPrecio());
            cst.setInt(6, detalleFactura.getSubTotal());

            //Parametro de salida del procedimiento almacenado
            cst.registerOutParameter(7, java.sql.Types.BOOLEAN);

            //Ejecucion del procedimiento almacenado
            cst.execute();

            return cst.getBoolean(7);

        } catch (SQLException e) {
            return false;
        } finally {
            db.desconectar();
        }
    }

    public boolean modificarDetalleFactura(DetalleFactura detalleFactura) {

        DataBase db = new DataBase();

        try {
            db.conectar();
            CallableStatement cst = db.getConexion().prepareCall("call pa_editarDetalleFactura(?,?,?,?,?,?,?)");
            //Parametros de entrada del procedimiento almacenado
            cst.setInt(1, detalleFactura.getIdDetalleFactura());
            cst.setInt(2, detalleFactura.getIdFactura());
            cst.setInt(3, detalleFactura.getCantidad());
            cst.setString(4, detalleFactura.getIdMantenimiento());
            cst.setInt(5, detalleFactura.getPrecio());
            cst.setInt(6, detalleFactura.getSubTotal());

            //Parametro de salida del procedimiento almacenado
            cst.registerOutParameter(7, java.sql.Types.BOOLEAN);

            //Ejecucion del procedimiento almacenado
            cst.execute();

            return cst.getBoolean(7);

        } catch (SQLException e) {
            return false;
        } finally {
            db.desconectar();
        }
    }

    public boolean eliminarDetalleFactura(int idDetalleFactura) {

        DataBase db = new DataBase();

        try {
            db.conectar();
            CallableStatement cst = db.getConexion().prepareCall("call pa_eliminarDetalleFactura(?,?)");
            //Parametros de entrada del procedimiento almacenado
            cst.setInt(1, idDetalleFactura);

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

    public ResultSet mostrarDetalleFactura(int idFactura) {

        DataBase db = new DataBase();
        ResultSet rs;

        db.ejecutarSqlSelect("Select * from detalleFactura where idFactura = " + idFactura);
        rs = db.obtenerRegistro();

        return rs;
    }

    public ResultSet filtrarDetalleFactura(String texto) {

        DataBase db = new DataBase();
        ResultSet rs;

        db.ejecutarSqlSelect("Select * from detalleFactura where idDetalleFactura like '%" + texto + "%'");
        rs = db.obtenerRegistro();

        return rs;
    }

}
