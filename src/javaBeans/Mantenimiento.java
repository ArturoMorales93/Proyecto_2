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
public class Mantenimiento implements Serializable {

    //Atributos
    private int idMantenimiento;
    private String descripcion;
    private int precio;

    //Constructores
    public Mantenimiento() {
        this.idMantenimiento = 0;
        this.descripcion = null;
        this.precio = 0;
    }

    public Mantenimiento(int idMantenimiento, String descripcion, int precio) {
        this.idMantenimiento = idMantenimiento;
        this.descripcion = descripcion;
        this.precio = precio;
    }

    //Getters y setters
    public int getIdMantenimiento() {
        return idMantenimiento;
    }

    public void setIdMantenimiento(int idMantenimiento) {
        this.idMantenimiento = idMantenimiento;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getPrecio() {
        return precio;
    }

    public void setPrecio(int precio) {
        this.precio = precio;
    }

}
