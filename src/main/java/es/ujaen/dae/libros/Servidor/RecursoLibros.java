/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.ujaen.dae.libros.Servidor;

import static org.hibernate.annotations.CacheModeType.GET;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author josejimenezdelapaz
 */
@RestController
@RequestMapping("/elibros")
public class RecursoLibros {

    @Autowired
    LibroDAO libDAO;
    EditorDAO editDAO;
    ApplicationContext context;
    ServicioLibros servicios;
    
    RecursoLibros(ApplicationContext context) {
        this.context = context;
        this.servicios = context.getBean(ServicioLibros.class);
        
       /* Editor ed1 = new Editor("marcelo editor", "marceloe", "1234");
        ed1.setToken("1134f434tvrvsgfdt34-E");
        editDAO.insertar(ed1);*/

       /* Libro l1 = new Libro("978-6754", "Aventuras", "Libro1",
                "23/2/2013", "revisonContenido", "Texto vacío");

        libDAO.insertar(l1);*/

    }

    @RequestMapping("hola")
    public String saludo() {
        return "ELIBROS le manda un saludo";
    }

    @RequestMapping(value = "libros/{titulo}",
            produces = "application/json")
    public Libro buscaLibro(@PathVariable String titulo) {
       Libro l= servicios.consultarLibroTitulo("Libro6");
      
        return l;
    }

    @RequestMapping(value = "editor/{nick}",
            produces = "application/json")
    public Editor buscaEditor(@PathVariable String nick) {

        Editor e=null;
        return e;
    }
}
