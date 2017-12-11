/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.ujaen.dae.libros.dtos;

import es.ujaen.dae.libros.Servidor.Libro;
import org.springframework.stereotype.Component;

/**
 *
 * @author josejimenezdelapaz
 */

public class LibroDTO {

  
    private String isbn;
    private String tematica;
    private String titulo;
    private String fechaPublicacion;
    private String estado;
    private String texto; //contenido del libro
    private int nota1;
    private int nota2;
    private int nota3;
    
    
    public LibroDTO(){}
    
    
    public LibroDTO(Libro l){
        this.isbn=l.getIsbn();
        this.tematica=l.getTematica();
        this.titulo=l.getTitulo();
        this.fechaPublicacion=l.getFechaPublicacion();
        this.estado=l.getEstado();
        this.texto=l.getTexto();
        this.nota1=l.getNota1();
        this.nota2=l.getNota2();
        this.nota3=l.getNota3();
    }
    
    
      /**
     * @return the isbn
     */
    public String getIsbn() {
        return isbn;
    }

    /**
     * @param isbn the isbn to set
     */
    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    /**
     * @return the tematica
     */
    public String getTematica() {
        return tematica;
    }

    /**
     * @param tematica the tematica to set
     */
    public void setTematica(String tematica) {
        this.tematica = tematica;
    }

    /**
     * @return the titulo
     */
    public String getTitulo() {
        return titulo;
    }

    /**
     * @param titulo the titulo to set
     */
    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    /**
     * @return the fechaPublicacion
     */
    public String getFechaPublicacion() {
        return fechaPublicacion;
    }

    /**
     * @param fechaPublicacion the fechaPublicacion to set
     */
    public void setFechaPublicacion(String fechaPublicacion) {
        this.fechaPublicacion = fechaPublicacion;
    }

    /**
     * @return the estado
     */
    public String getEstado() {
        return estado;
    }

    /**
     * @param estado the estado to set
     */
    public void setEstado(String estado) {
        this.estado = estado;
    }

    /**
     * @return the texto
     */
    public String getTexto() {
        return texto;
    }

    /**
     * @param texto the texto to set
     */
    public void setTexto(String texto) {
        this.texto = texto;
    }

    /**
     * @return the nota1
     */
    public int getNota1() {
        return nota1;
    }

    /**
     * @param nota1 the nota1 to set
     */
    public void setNota1(int nota1) {
        this.nota1 = nota1;
    }

    /**
     * @return the nota2
     */
    public int getNota2() {
        return nota2;
    }

    /**
     * @param nota2 the nota2 to set
     */
    public void setNota2(int nota2) {
        this.nota2 = nota2;
    }

    /**
     * @return the nota3
     */
    public int getNota3() {
        return nota3;
    }

    /**
     * @param nota3 the nota3 to set
     */
    public void setNota3(int nota3) {
        this.nota3 = nota3;
    }
}
