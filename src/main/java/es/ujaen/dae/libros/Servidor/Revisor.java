/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.ujaen.dae.libros.Servidor;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author josejimenezdelapaz
 */
@Entity
public class Revisor {

    /**
     * @return the fecha
     */
    public Calendar getFecha() {
        return fecha;
    }

    /**
     * @param fecha the fecha to set
     */
    public void setFecha(Calendar fecha) {
        this.fecha = fecha;
    }

    @Id
    private String token;
    private String nombre;
    private String nick;
    private String pass;
    private int carga;
    @Temporal(TemporalType.DATE)
    private Calendar fecha;

    //@ManyToMany
    /* @OneToMany
    private List<Libro> librosPendientes;
     */
    @OneToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "Revisor_Libro",
            joinColumns = @JoinColumn(name = "revisor_token"),
            inverseJoinColumns = @JoinColumn(name = "libro_isbn")
    )
    private List<Libro> librosPendientes;

    @OneToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "Revisor_Libro_Historico",
            joinColumns = @JoinColumn(name = "revisor_token"),
            inverseJoinColumns = @JoinColumn(name = "libro_isbn")
    )
    private List<Libro> historicoLibros;

    public Revisor() {
        nombre = "";
        nick = "";
        pass = "";
        token = ""; //se asigna despues de ejecutar generarToken()
        carga = 0;
        librosPendientes = new ArrayList<>();
        historicoLibros = new ArrayList<>();
    }

    public Revisor(String _nom, String _nick, String _pass, int _carga) {
        nombre = _nom;
        nick = _nick;
        pass = _pass;
        carga = _carga;
        token = ""; //se asigna despues de ejecutar generarToken()
        librosPendientes = new ArrayList<>();
        historicoLibros = new ArrayList<>();

    }

    public void asignarLibro(Libro l) {
        librosPendientes.add(l);
    }

    public List<Libro> getLibrosPendientes() {
        return librosPendientes;
    }

    public void setLibrosPendientes(ArrayList<Libro> librosPendientes) {
        this.librosPendientes = librosPendientes;
    }

    public List<Libro> getHistoricoLibros() {
        return historicoLibros;
    }

    public void setHistoricoLibros(ArrayList<Libro> historicoLibros) {
        this.historicoLibros = historicoLibros;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public void agregarLibroPendiente(Libro l) {
        this.librosPendientes.add(l);
    }

    /**
     * @return the carga
     */
    public int getCarga() {
        return carga;
    }

    /**
     * @param carga the carga to set
     */
    public void setCarga(int carga) {
        this.carga = carga;
    }

}
