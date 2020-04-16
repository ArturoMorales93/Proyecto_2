package javaBeans;

import java.io.Serializable;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Arthur
 */
public class Empleado implements Serializable {

    //Atributos
    private String idEmpleado;
    private String nombreEmpleado;
    private String tipoEmpleado;
    private int codigoEmpresarial;

    //Constructores
    public Empleado() {
        this.idEmpleado = null;
        this.nombreEmpleado = null;
        this.tipoEmpleado = null;
        this.codigoEmpresarial = 0;
    }

    public Empleado(String idEmpleado, String nombreEmpleado, String tipoEmpleado, int codigoEmpresarial) {
        this.idEmpleado = idEmpleado;
        this.nombreEmpleado = nombreEmpleado;
        this.tipoEmpleado = tipoEmpleado;
        this.codigoEmpresarial = codigoEmpresarial;
    }

    //Getters y setters
    public String getIdEmpleado() {
        return idEmpleado;
    }

    public void setIdEmpleado(String idEmpleado) {
        this.idEmpleado = idEmpleado;
    }

    public String getNombreEmpleado() {
        return nombreEmpleado;
    }

    public void setNombreEmpleado(String nombreEmpleado) {
        this.nombreEmpleado = nombreEmpleado;
    }

    public String getTipoEmpleado() {
        return tipoEmpleado;
    }

    public void setTipoEmpleado(String tipoEmpleado) {
        this.tipoEmpleado = tipoEmpleado;
    }

    public int getCodigoEmpresarial() {
        return codigoEmpresarial;
    }

    public void setCodigoEmpresarial(int codigoEmpresarial) {
        this.codigoEmpresarial = codigoEmpresarial;
    }

}
