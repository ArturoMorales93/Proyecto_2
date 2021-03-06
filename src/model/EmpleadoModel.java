/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javaBeans.Empleado;

/**
 *
 * @author Arthur
 */
public class EmpleadoModel {

    public boolean insertarEmpleado(Empleado empleado) {

        DataBase db = new DataBase();

        try {
            db.conectar();
            CallableStatement cst = db.getConexion().prepareCall("call pa_insertarEmpleado(?,?,?,?,?)");
            //Parametros de entrada del procedimiento almacenado
              cst.setString(1, empleado.getIdEmpleado());
              cst.setString(2, empleado.getNombreEmpleado());
              cst.setString(3, empleado.getTipoEmpleado());
              cst.setInt(4, empleado.getCodigoEmpresarial());
            //Parametro de salida del procedimiento almacenado
            cst.registerOutParameter(5, java.sql.Types.BOOLEAN);

            //Ejecucion del procedimiento almacenado
            cst.execute();

            return cst.getBoolean(5);

        } catch (SQLException e) {
            return false;
        } finally {
            db.desconectar();
        }
    }

    public boolean modificarEmpleado(Empleado empleado) {

        DataBase db = new DataBase();

        try {
            db.conectar();
            CallableStatement cst = db.getConexion().prepareCall("call pa_editarEmpleado(?,?,?,?,?)");
            //Parametros de entrada del procedimiento almacenado
               cst.setString(1, empleado.getIdEmpleado());
              cst.setString(2, empleado.getNombreEmpleado());
              cst.setString(3, empleado.getTipoEmpleado());
              cst.setInt(4, empleado.getCodigoEmpresarial());
            //Parametro de salida del procedimiento almacenado
            cst.registerOutParameter(5, java.sql.Types.BOOLEAN);

            //Ejecucion del procedimiento almacenado
            cst.execute();

            return cst.getBoolean(5);

        } catch (SQLException e) {
            return false;
        } finally {
            db.desconectar();
        }
    }

    public boolean eliminarEmpleado(String idEmpleado) {

        DataBase db = new DataBase();

        try {
            db.conectar();
            CallableStatement cst = db.getConexion().prepareCall("call pa_eliminarEmpleado(?,?)");
            //Parametros de entrada del procedimiento almacenado
            cst.setString(1, idEmpleado);

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

    public ResultSet mostrarEmpleado() {

        DataBase db = new DataBase();
        ResultSet rs;

        db.ejecutarSqlSelect("Select * from empleado");
        rs = db.obtenerRegistro();

        return rs;
    }
    
    public ResultSet filtrarEmpleado(String texto) {

        DataBase db = new DataBase();
        ResultSet rs;

        db.ejecutarSqlSelect("Select * from empleado where idEmpleado like '%"+texto+"%' or nombreDeEmpleado like'%"+texto+"%'");
        rs = db.obtenerRegistro();

        return rs;
    }

}
