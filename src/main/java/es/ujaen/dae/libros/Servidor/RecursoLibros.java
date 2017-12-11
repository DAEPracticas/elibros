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
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import es.ujaen.dae.libros.dtos.LibroDTO;
import java.text.ParseException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author josejimenezdelapaz
 */
@RestController
@RequestMapping("/elibros")
public class RecursoLibros {

    @Autowired
    LibroDAO libDAO;
    @Autowired
    EditorDAO editDAO;
    @Autowired
    RevisorDAO revDAO;
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

        @RequestMapping(value = "/libro", method = RequestMethod.GET,
             produces = "application/json")
    public ResponseEntity<LibroDTO> buscarLibroTitulo(@RequestParam(value = "nombre", defaultValue = "Desco", required = true) String nombre) throws ParseException {
        LibroDTO lib=null;
        if (nombre != "Desco") {
            Libro l = null;
           
            l = servicios.consultarLibroTitulo(nombre);
            if (l != null) {
                //Transforma a DTO
                 lib=new LibroDTO(l);
           }else{
                    return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
         
     
        return  new ResponseEntity<>(lib,HttpStatus.OK);
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
    
    
     @RequestMapping(value = "datos",
            produces = "application/json")
    public void datosPrueba() {
         
        //crear 5 revisores de ejemplo
        Revisor r1 = new Revisor("luis revisor", "luisr", "1234", 2);

        r1.setToken("skdjfasdf3q4-R");
        revDAO.insertar(r1);
        Revisor r2 = new Revisor("jesus revisor", "jesusr", "1234", 4);
        //String token1 = generarToken("Revisor");
        r2.setToken("345234652345jt6n245-R");
        revDAO.insertar(r2);
        
        Revisor r5 = new Revisor("pepito revisor", "pepito", "1234", 4);
        r5.setToken("asd98a7sd9a-R");
        revDAO.insertar(r5);
        
        
        Revisor r6 = new Revisor("tony revisor", "tony", "1234", 4);
        r6.setToken("ds8dx25s-R");
        revDAO.insertar(r6);
        
        
        Revisor r7 = new Revisor("laura revisor", "laura", "1234", 4);
        r7.setToken("d8fr2c8esw56s-R");
        revDAO.insertar(r7);
//        Revisor r3 = new Revisor("Antonio revisor", "antonior", "1234",3);
//        String token2 = generarToken("Revisor");
//        r3.setToken(token2);
//        revDAO.insertar(r3);
//        Revisor r4 = new Revisor("miguel revisor", "miguelr", "1234",5);
//        String token3 = generarToken("Revisor");
//        r4.setToken(token3);
//        revDAO.insertar(r4);
//        Revisor r5 = new Revisor("fran revisor", "franr", "1234",2);
//        String token4 = generarToken("Revisor");
//        r5.setToken(token4);
//        revDAO.insertar(r5);
//        

        Libro l1 = new Libro("978-6754", "Aventuras", "Libro1",
                "23/2/2013", "revisonContenido", "Texto vacío");
        Libro l2 = new Libro("978-121123", "Aventuras", "Libro2",
                "23/2/2013", "revisionContenido", "Texto vacío");
        Libro l3 = new Libro("978-43", "Aventuras", "Libro3",
                "23/2/2013", "revisionContenido", "Texto vacío");
      
        Libro l5 = new Libro("978-22", "Aventuras", "Libro5",
                "23/2/2013", "revisionContenido", "Texto vacío");
        Libro l6 = new Libro("978-84998423299619", "Aventuras", "Libro6",
                "23/2/2013", "revisionContenido", "Texto vacío");
        Libro l7 = new Libro("978-42342", "Clasico", "Libro6",
                "23/2/2013", "revisionContenido", "Texto vacío");

        libDAO.insertar(l1);
        libDAO.insertar(l2);
        libDAO.insertar(l3);
     
        libDAO.insertar(l5);
        libDAO.insertar(l6);
        libDAO.insertar(l7);

        /*r1.asignarLibro(l1);
        r1.setFecha(Calendar.getInstance());
        revDAO.actualizar(r1);
        //r2.asignarLibro(l2);
        r2.asignarLibro(l3);
        
        r2.setFecha(Calendar.getInstance());
        revDAO.actualizar(r2);*/
//        r3.asignarLibro(l5);
//        r3.asignarLibro(l7);
//        r4.asignarLibro(l6);

//        Libro l8 = new Libro("43243241", "Terror", "Dracula",
//                "23/2/1967", "revisionContenido", "Lorem ipsum dolor sit amet, consectetur adipiscing elit, "
//                + "sed eiusmod tempor incidunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, "
//                + "quis nostrud exercitation ullamco laboris nisi ut aliquid ex ea commodi consequat. "
//                + "Quis aute iure reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. "
//                + "Excepteur sint obcaecat cupiditat non proident, sunt in culpa qui officia");
//        Libro l9 = new Libro("43243243", "Historia", "Roma",
//                "23/2/1987", "revisionContenido", "Lorem ipsum dolor sit amet, consectetur adipiscing elit, "
//                + "sed eiusmod tempor incidunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, "
//                + "quis nostrud exercitation ullamco laboris nisi ut aliquid ex ea commodi consequat. "
//                + "Quis aute iure reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. "
//                + "Excepteur sint obcaecat cupiditat non proident, sunt in culpa qui officia");
//        Libro l10 = new Libro("43243242", "Aventuras", "El hobbit",
//                "23/2/1967", "publicado", "Lorem ipsum dolor sit amet, consectetur adipiscing elit, "
//                + "sed eiusmod tempor incidunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, "
//                + "quis nostrud exercitation ullamco laboris nisi ut aliquid ex ea commodi consequat. "
//                + "Quis aute iure reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. "
//                + "Excepteur sint obcaecat cupiditat non proident, sunt in culpa qui officia");
//        libDAO.insertar(l8);
//        libDAO.insertar(l9);
//        libDAO.insertar(l10);
        Editor ed1 = new Editor("marcelo editor", "marceloe", "1234");
        //String token7 = generarToken("Editor");
        ed1.setToken("1134f434tvrvsgfdt34-E");
        editDAO.insertar(ed1);

    
    }
}
