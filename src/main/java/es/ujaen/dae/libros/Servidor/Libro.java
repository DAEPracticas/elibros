/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.ujaen.dae.libros.Servidor;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import org.hibernate.annotations.Proxy;

/**
 *
 * @author josejimenezdelapaz
 */
@Proxy(lazy = false)
@Entity
public class Libro {

    @Id
    private String isbn;
    private String tematica;
    private String titulo;
    private String fechaPublicacion;
    private String estado;
    private String texto; //contenido del libro
    private int nota1;
    private int nota2;
    private int nota3;

    /*@ManyToMany(mappedBy="librosPendientes")
    private List<Revisor> revisoresAsignados= new ArrayList<>();;
     */
    //private Autor escritoPor;
    /*
    private ArrayList<Integer> notas;*/
//    @OneToMany(fetch = FetchType.EAGER)
//    @JoinTable(
//            name = "Libro_Revisor",
//            joinColumns = @JoinColumn(name = "libro_isbn"),
//            inverseJoinColumns = @JoinColumn(name = "revisor_token")
//    )
//    
    
    //private List<Revisor> revisoresAsignados;

    public Libro() {
        isbn = "";
        tematica = "";
        titulo = "";
        fechaPublicacion = "";
        //escritoPor = null;
        estado = "revisionContenido";
        texto = "";
        //notas = new ArrayList<>();
//        revisoresAsignados = new ArrayList<>();
        nota1 = -1;
        nota2 = -1;
        nota3 = -1;
    }

    public Libro(String _isbn, String _temat, String _tit, String _fech, String _estado, String _texto) {
        isbn = _isbn;
        tematica = _temat;
        titulo = _tit;
        fechaPublicacion = _fech;
        estado = _estado;
        texto = _texto;
        //notas = new ArrayList<>();
//        revisoresAsignados = new ArrayList<>();
        nota1 = -1;
        nota2 = -1;
        nota3 = -1;
    }

    /*
    public void agregarNota(int n) {
        notas.add(n);
    }*/
    public boolean checkNuevaNota(int n) {

        if (n < 2) {
            setEstado("noPublicable");
            return false;
        }
        return true;
    }

    /*
     * Comprobamos si tiene 3 notas asignadas, si tiene las 3 notas, comprobamos que 
     * la suma de todas ellas sea mayor que 10, si lo es, será publicable y devolveremos un 1
     * Si no es, devolvemos -2.
     * Si no están las 3 notas, devolvemos un -1
     */
    public Integer checkNotaFinal() {
        Integer aux = 0;
        if (nota1 != -1 && nota2 != -1 && nota3 != -1) {
            aux = nota1 + nota2 + nota3;
            if (aux < 10) {
                setEstado("noPublicable");
                return -2;
            } else {
                return 1;
            }
        }
        return -1;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getTematica() {
        return tematica;
    }

    public void setTematica(String tematica) {
        this.tematica = tematica;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getFechaPublicacion() {
        return fechaPublicacion;
    }

    public void setFechaPublicacion(String fechaPublicacion) {
        this.fechaPublicacion = fechaPublicacion;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

//    public ArrayList<Integer> getNotas() {
//        return notas;
//    }
//
//    public void setNotas(ArrayList<Integer> notas) {
//        this.notas = notas;
//    }
    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }
//
//    public Autor getEscritoPor() {
//        return escritoPor;
//    }
//
//    public void setEscritoPor(Autor escritoPor) {
//        this.escritoPor = escritoPor;
//    }

//    public List<Revisor> getRevisoresAsignados() {
//        return revisoresAsignados;
//    }
//
//    public void setRevisoresAsignados(ArrayList<Revisor> revisoresAsignados) {
//        this.revisoresAsignados = revisoresAsignados;
//    }
//
//    public void agregarRevisor(Revisor r) {
//        revisoresAsignados.add(r);
//    }
}
