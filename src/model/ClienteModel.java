/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javaBeans.Cliente;

/**
 *
 * @author Arthur
 */
public class ClienteModel {

    public boolean insertarCliente(Cliente cliente) {

        DataBase db = new DataBase();

        try {
            db.conectar();
            CallableStatement cst = db.getConexion().prepareCall("call pa_insertarCliente(?,?,?,?,?,?)");
            //Parametros de entrada del procedimiento almacenado
            cst.setInt(1, cliente.getIdCliente());
            cst.setString(2, cliente.getNombreCliente());
            cst.setInt(3, cliente.getNumeroTelefono());
            cst.setString(4, cliente.getDireccion());
            cst.setString(5, cliente.getEmail());
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

    public boolean modificarCliente(Cliente cliente) {

        DataBase db = new DataBase();

        try {
            db.conectar();
            CallableStatement cst = db.getConexion().prepareCall("call pa_editarCliente(?,?,?,?,?,?)");
            //Parametros de entrada del procedimiento almacenado
            cst.setInt(1, cliente.getIdCliente());
            cst.setString(2, cliente.getNombreCliente());
            cst.setInt(3, cliente.getNumeroTelefono());
            cst.setString(4, cliente.getDireccion());
            cst.setString(5, cliente.getEmail());
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

    public boolean eliminarCliente(int idCliente) {

        DataBase db = new DataBase();

        try {
            db.conectar();
            CallableStatement cst = db.getConexion().prepareCall("call pa_eliminarCliente(?,?)");
            //Parametros de entrada del procedimiento almacenado
            cst.setInt(1, idCliente);

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

    public ResultSet mostrarCliente() {

        DataBase db = new DataBase();
        ResultSet rs;

        db.ejecutarSqlSelect("Select * from cliente");
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
