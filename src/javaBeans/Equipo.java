/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javaBeans;

import java.io.Serializable;

/**
 *
 * @author Arthur
 */
public class Equipo implements Serializable {

    //Atributos
    private int idEquipo;
    private String descripcion;
    private String marca;
    private int idCliente;
    private String nombreCliente;

    //Constructores
    public Equipo() {
        this.idEquipo = 0;
        this.descripcion = null;
        this.marca = null;
        this.idCliente = 0;
    }

    public Equipo(int idEquipo, String descripcion, String marca, int idCliente, String NombreCliente) {
        this.idEquipo = idEquipo;
        this.descripcion = descripcion;
        this.marca = marca;
        this.idCliente = idCliente;
        this.nombreCliente = NombreCliente;
    }

    //Getters y setters
    public int getIdEquipo() {
        return idEquipo;
    }

    public void setIdEquipo(int idEquipo) {
        this.idEquipo = idEquipo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public int getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }

    public String getNombreCliente() {
        return nombreCliente;
    }

    public void setNombreCliente(String nombreCliente) {
        this.nombreCliente = nombreCliente;
    }

}
