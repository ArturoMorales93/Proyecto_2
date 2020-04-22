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
    private String idMantenimiento;
    private int precio;
    private String observaciones;

    //Constructores
    public DetalleOrden() {
        this.idDetalleOrden = 0;
        this.idOrdenTrabajo = 0;
        this.idMantenimiento = null;
        this.precio = 0;
        this.observaciones = null;
    }

    public DetalleOrden(int idDetalleOrden, int idOrdenTrabajo, String idMantenimiento, int precio, String observaciones) {
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

    public String getIdMantenimiento() {
        return idMantenimiento;
    }

    public void setIdMantenimiento(String idMantenimiento) {
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
