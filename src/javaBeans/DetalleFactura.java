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
public class DetalleFactura implements Serializable {

    //Atributos
    private int idDetalleFactura;
    private int idFactura;
    private int cantidad;
    private int idMantenimiento;
    private int precio;
    private int subTotal;

    //Constructores
    public DetalleFactura() {
        this.idDetalleFactura = 0;
        this.idFactura = 0;
        this.cantidad = 0;
        this.idMantenimiento = 0;
        this.precio = 0;
        this.subTotal = 0;
    }

    public DetalleFactura(int idDetalleFactura, int idFactura, int cantidad, int idMantenimiento, int precio, int subTotal) {
        this.idDetalleFactura = idDetalleFactura;
        this.idFactura = idFactura;
        this.cantidad = cantidad;
        this.idMantenimiento = idMantenimiento;
        this.precio = precio;
        this.subTotal = subTotal;
    }

    //Getters y setters
    public int getIdDetalleFactura() {
        return idDetalleFactura;
    }

    public void setIdDetalleFactura(int idDetalleFactura) {
        this.idDetalleFactura = idDetalleFactura;
    }

    public int getIdFactura() {
        return idFactura;
    }

    public void setIdFactura(int idFactura) {
        this.idFactura = idFactura;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
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

    public int getSubTotal() {
        return subTotal;
    }

    public void setSubTotal(int subTotal) {
        this.subTotal = subTotal;
    }

}
