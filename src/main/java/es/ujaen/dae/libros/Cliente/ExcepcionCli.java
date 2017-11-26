package es.ujaen.dae.libros.Cliente;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author alvarogonzalezgonzalez
 */
class ExcepcionCli extends Exception {
     // Constructor
    public ExcepcionCli(){};
    
    // ExcepcionCli: Error Provocado
    public String excErrorMenu(){
        return "ERROR, debe insertertar un número válido. ";
    }
    //Execpcion para nombre vacio en el registro de un nuevo usuario
    public String excErrorNombre(){
        return "ERROR, nombre no válido, el campo está vacío. ";
    }
    
    //Execpcion de usuario ya existente en el registro de un nuevo usuario
    public String excUsuarioExistente(){
        return "ERROR, usuario ya existente. ";
    }
    
    //Execpcion de contraseña no válida en el registro de un nuevo usuario
    public String excErrorPass(){
        return "ERROR, contraseña no válida. Introduzca al menos más de 5 caracteres. ";
    }
    
    //Execpcion de estado no valido 
    public String excErrorEstado(){
        return "ERROR, estado no válido. ";
    }
}
