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

public class clinicaCliente {

    ApplicationContext context;

    public clinicaCliente(ApplicationContext _context) {

        this.context = _context;
    }

    public void run() {
            Clinica clinica=(Clinica) context.getBean("clinica");
            System.out.println("El cif de la clinica es "+clinica.getCif());
    }
}
