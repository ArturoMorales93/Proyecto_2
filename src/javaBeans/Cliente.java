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
public class Cliente implements Serializable {

    //Atributos
    private int idCliente;
    private String nombreCliente;
    private int numeroTelefono;
    private String direccion;
    private String email;

    //Constructores
    public Cliente() {
        this.idCliente = 0;
        this.nombreCliente = null;
        this.numeroTelefono = 0;
        this.direccion = null;
        this.email = null;
    }

    public Cliente(int idCliente, String nombreCliente, int numeroTelefono, String direccion, String email) {
        this.idCliente = idCliente;
        this.nombreCliente = nombreCliente;
        this.numeroTelefono = numeroTelefono;
        this.direccion = direccion;
        this.email = email;
    }

    //Getters y setters
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

    public int getNumeroTelefono() {
        return numeroTelefono;
    }

    public void setNumeroTelefono(int numeroTelefono) {
        this.numeroTelefono = numeroTelefono;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

}
