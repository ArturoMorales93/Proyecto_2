/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Exepciones;

/**
 * Clase de excepciones necesarias durante el proyecto
 * 1 para combobox de empleado, 2 para campos vacios en los Frm Secundarios
 * @author Daniel-san
 */
public class ProyectoException extends Exception{
    private int codigoError;
     
/**
 * 
 * 
 *@throw Mensage de error de acuerdo a lo requerido
 * 
 */
    public ProyectoException(int codigoError){
        super();
        this.codigoError=codigoError;
    }
     
    @Override
    public String getMessage(){
         
        String mensaje="";
         
        switch(codigoError){
            case 1:
                mensaje="Debe Seleccionar un tipo de Empleado";
                break;
            case 2:
                mensaje="Los Campos no deben estar vacios";
                break;
            case 333:
                mensaje="Error, el numero esta entre 21 y 30";
                break;
        }
         
        return mensaje;
         
    }
}
