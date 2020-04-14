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
public class Factura implements Serializable{
    
    //Atributos
    private int idFactura;
    private String fecha;
    private String empleado;
    private int idOrden;
    private int subTotal;
    private int impuesto;
    private int total;
    private int idCliente;
    
    //Constructores
    public Factura() {
        this.idFactura = 0;
        this.fecha = null;
        this.empleado = null;
        this.idOrden = 0;
        this.subTotal = 0;
        this.impuesto = 0;
        this.total = 0;
        this.idCliente = 0;
    }

    public Factura(int idFactura, String fecha, String empleado, int idOrden, int subTotal, int impuesto, int total, int idCliente) {
        this.idFactura = idFactura;
        this.fecha = fecha;
        this.empleado = empleado;
        this.idOrden = idOrden;
        this.subTotal = subTotal;
        this.impuesto = impuesto;
        this.total = total;
        this.idCliente = idCliente;
    }
    
    //Getters y setters
    public int getIdFactura() {
        return idFactura;
    }

    public void setIdFactura(int idFactura) {
        this.idFactura = idFactura;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getEmpleado() {
        return empleado;
    }

    public void setEmpleado(String empleado) {
        this.empleado = empleado;
    }

    public int getIdOrden() {
        return idOrden;
    }

    public void setIdOrden(int idOrden) {
        this.idOrden = idOrden;
    }

    public int getSubTotal() {
        return subTotal;
    }

    public void setSubTotal(int subTotal) {
        this.subTotal = subTotal;
    }

    public int getImpuesto() {
        return impuesto;
    }

    public void setImpuesto(int impuesto) {
        this.impuesto = impuesto;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }
    
}
