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
    private String idMantenimiento;
    private String descripcion;
    private int precio;

    //Constructores
    public Mantenimiento() {
        this.idMantenimiento = "";
        this.descripcion = "";
        this.precio = 0;
    }

    public Mantenimiento(String idMantenimiento, String descripcion, int precio) {
        this.idMantenimiento = idMantenimiento;
        this.descripcion = descripcion;
        this.precio = precio;
    }

    //Getters y setters
    public String getIdMantenimiento() {
        return idMantenimiento;
    }

    public void setIdMantenimiento(String idMantenimiento) {
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
