/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.daw.pruebadae;

import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

/**
 *
 * @author josejimenezdelapaz
 */
@Component
public class Clinica {
     String nombre;
    String cif;
    ApplicationContext context;

    public Clinica() {
        nombre="";
        cif="43534kj5h34kj";
    }
    
   
    public void setNombre(String nom) {
        nombre = nom;
    }

    public String getNombre() {
        return nombre;
    }
     public void setCif(String _cif) {
        nombre = _cif;
    }

    public String getCif() {
        return cif;
    }

}
