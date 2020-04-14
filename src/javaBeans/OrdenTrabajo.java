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
public class OrdenTrabajo implements Serializable {

    //Atributos
    private int idOrdenTrabajo;
    private int idCliente;
    private int idEmpleado;
    private int idEquipo;
    private int total;

    //Constructores
    public OrdenTrabajo() {
        this.idOrdenTrabajo = 0;
        this.idCliente = 0;
        this.idEmpleado = 0;
        this.idEquipo = 0;
        this.total = 0;
    }

    public OrdenTrabajo(int idOrdenTrabajo, int idCliente, int idEmpleado, int idEquipo, int total) {
        this.idOrdenTrabajo = idOrdenTrabajo;
        this.idCliente = idCliente;
        this.idEmpleado = idEmpleado;
        this.idEquipo = idEquipo;
        this.total = total;
    }

    //Getters y setters
    public int getIdOrdenTrabajo() {
        return idOrdenTrabajo;
    }

    public void setIdOrdenTrabajo(int idOrdenTrabajo) {
        this.idOrdenTrabajo = idOrdenTrabajo;
    }

    public int getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }

    public int getIdEmpleado() {
        return idEmpleado;
    }

    public void setIdEmpleado(int idEmpleado) {
        this.idEmpleado = idEmpleado;
    }

    public int getIdEquipo() {
        return idEquipo;
    }

    public void setIdEquipo(int idEquipo) {
        this.idEquipo = idEquipo;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

}
