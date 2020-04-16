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
public class Factura implements Serializable {

    //Atributos
    private int idFactura;
    private String fecha;
    private int idOrden;
    private String idEmpleado;
    private int idCliente;
    private int impuesto;
    private int subTotal;
    private int total;

    //Constructores
    public Factura() {
        this.idFactura = 0;
        this.fecha = null;
        this.idOrden = 0;
        this.idEmpleado = null;
        this.idCliente = 0;
        this.impuesto = 0;
        this.subTotal = 0;
        this.total = 0;
    }

    public Factura(int idFactura, String fecha, int idOrden, String idEmpleado, int idCliente, int impuesto, int subTotal, int total) {
        this.idFactura = idFactura;
        this.fecha = fecha;
        this.idOrden = idOrden;
        this.idEmpleado = idEmpleado;
        this.idCliente = idCliente;
        this.impuesto = impuesto;
        this.subTotal = subTotal;
        this.total = total;
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

    public int getIdOrden() {
        return idOrden;
    }

    public void setIdOrden(int idOrden) {
        this.idOrden = idOrden;
    }

    public String getIdEmpleado() {
        return idEmpleado;
    }

    public void setIdEmpleado(String idEmpleado) {
        this.idEmpleado = idEmpleado;
    }

    public int getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }

    public int getImpuesto() {
        return impuesto;
    }

    public void setImpuesto(int impuesto) {
        this.impuesto = impuesto;
    }

    public int getSubTotal() {
        return subTotal;
    }

    public void setSubTotal(int subTotal) {
        this.subTotal = subTotal;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

}
