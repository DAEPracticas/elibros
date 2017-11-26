/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.ujaen.dae.libros.Servidor;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 *
 * @author josejimenezdelapaz
 */
@Entity
public class Autor {
   
    @Id
    private String token;
    private String nick;
    private String nombre;
    private String pass;
  

    public Autor() {
    }

    public Autor(String _nom, String _nick, String _pass) {
        nombre = _nom;
        nick = _nick;
        token=""; //se asigna despues de ejecutar generarToken()
        pass = _pass;
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
    @Id
    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
