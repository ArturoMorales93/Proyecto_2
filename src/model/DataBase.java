/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author Arthur
 */
public class DataBase {

    //Atributos
    private final String base = "tiendaelectronica_db";
    private final String user = "root";
    private final String password = "root";
    private final String url = "jdbc:mysql://localhost/" + base + "?useSSL=false";

    private Connection conexion;
    private ResultSet datos;
    private Statement sql;

    //Getters y setters
    public Connection getConexion() {
        return conexion;
    }

    public void setConexion(Connection conexion) {
        this.conexion = conexion;
    }

    public ResultSet getDatos() {
        return datos;
    }

    //Metodos
    private boolean cargarControlador() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            return true;
        } catch (ClassNotFoundException ex) {
            JOptionPane.showMessageDialog(null, "Error...No se cargó el controlador de Bases de Datos " + ex.getMessage());
            return false;
        }
    }

    public boolean conectar() {
        this.cargarControlador();
        try {
            this.conexion = (Connection) DriverManager.getConnection(this.url, this.user, this.password);
            this.sql = this.conexion.createStatement();
            return true;
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error...No hay conexión con la Base de Datos " + ex.getMessage());
            return false;
        }
    }

    public void desconectar() {
        try {
            this.sql.close();
            this.conexion.close();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al cerrar la conexión con la Base de Datos " + ex.getMessage());
        }
    }

    public void ejecutarSqlSelect(String consulta) {
        this.conectar();
        try {
            this.datos = this.sql.executeQuery(consulta);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al ejecutar consulta Select " + ex.getMessage());
        }
    }

    public void ejecutarSqlUpdate(String sql) {
        this.conectar();
        try {
            this.sql.executeUpdate(sql);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al ejecutar consulta Update " + ex.getMessage());
        }
        this.desconectar();
    }

    public ResultSet obtenerRegistro() {
        try {
            this.datos.first();
        } catch (SQLException ex) {
            Logger.getLogger(DataBase.class.getName()).log(Level.SEVERE, null, ex);
        }
        return this.datos;
    }

}
