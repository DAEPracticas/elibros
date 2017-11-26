/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.ujaen.dae.libros.Servidor;
import es.ujaen.dae.libros.Cliente.ClienteLibros;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

/**
 *
 * @author josejimenezdelapaz
 */
@SpringBootApplication
public class ServidorLibros {
      public static void main(String[] args) throws Exception {
        SpringApplication sistema = new SpringApplication(ServidorLibros.class);
        ApplicationContext context = sistema.run(args);
        
        //ClienteLibros clienteLibros = new ClienteLibros(context);
        //clienteLibros.run();
        
          RecursoLibros rl=new RecursoLibros(context);
    }
}
