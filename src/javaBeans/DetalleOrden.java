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
public class DetalleOrden implements Serializable {

    //Atributos
    private int idDetalleOrden;
    private int idOrdenTrabajo;
    private int idMantenimiento;
    private int precio;
    private String observaciones;

    //Constructores
    public DetalleOrden() {
        this.idDetalleOrden = 0;
        this.idOrdenTrabajo = 0;
        this.idMantenimiento = 0;
        this.precio = 0;
        this.observaciones = null;
    }

    public DetalleOrden(int idDetalleOrden, int idOrdenTrabajo, int idMantenimiento, int precio, String observaciones) {
        this.idDetalleOrden = idDetalleOrden;
        this.idOrdenTrabajo = idOrdenTrabajo;
        this.idMantenimiento = idMantenimiento;
        this.precio = precio;
        this.observaciones = observaciones;
    }

    //Getters y setters
    public int getIdDetalleOrden() {
        return idDetalleOrden;
    }

    public void setIdDetalleOrden(int idDetalleOrden) {
        this.idDetalleOrden = idDetalleOrden;
    }

    public int getIdOrdenTrabajo() {
        return idOrdenTrabajo;
    }

    public void setIdOrdenTrabajo(int idOrdenTrabajo) {
        this.idOrdenTrabajo = idOrdenTrabajo;
    }

    public int getIdMantenimiento() {
        return idMantenimiento;
    }

    public void setIdMantenimiento(int idMantenimiento) {
        this.idMantenimiento = idMantenimiento;
    }

    public int getPrecio() {
        return precio;
    }

    public void setPrecio(int precio) {
        this.precio = precio;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

}
