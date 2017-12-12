/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.ujaen.dae.libros.Servidor;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author alvarogonzalezgonzalez
 */
public interface ServicioLibros {

    public boolean publicarLibro(Libro l, String token);

    public List<Revisor> consultarRevisores();

    public Libro consultarLibroTitulo(String titulo);

    public List<Libro> consultarLibroTematica(String tem);

    public Integer asignarNota(Libro l, int n, String token);
    
    public void aceptarLibro(Libro l, int estado, String token);
    
    public void rechazarLibro(Libro l, int estado, String token);

    public String registroUsuario(String nombre, String nick, String pass, String rol);

    public String registroUsuarioRevisor(String nombre, String nick, String pass, String rol, int carga);

//    public void examinarContLibro(Libro l, String token);

//    public void modificarEstado(Libro l, String estado);

    public String login(String nick, String pass);

    public List<Libro> librosParaRevisar(String token,String estado);

    public List<Libro> librosParaPuntuar(String token);
    // public Map<String, Revisor> getRevisores();
    public void rellenarColaRevisores() ;
    public void holaBuscaToken(String tok);
    
    //Login SPRING SECURITY
    public Autor loginAutor(String nick);
     public Editor loginEditor(String nick);
      public Revisor loginRevisor(String nick);
}
