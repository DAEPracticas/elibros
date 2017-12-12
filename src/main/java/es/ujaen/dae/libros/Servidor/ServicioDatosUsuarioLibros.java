/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.ujaen.dae.libros.Servidor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

/**
 *
 * @author josejimenezdelapaz
 */
@Component
public class ServicioDatosUsuarioLibros implements UserDetailsService {

    @Autowired
    ServicioLibros servicios;

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {

        Autor aut = null;
        Revisor rev = null;
        Editor edit = null;
        aut = servicios.loginAutor(userName);
        rev = servicios.loginRevisor(userName);
        edit = servicios.loginEditor(userName);
        if (aut != null) {
            return User.withUsername(aut.getNick()).password(aut.getPass()).roles("AUTOR").build();
        } else if (rev != null) {
            return User.withUsername(rev.getNick()).password(rev.getPass()).roles("REVISOR").build();
        } else if (edit != null) {
            return User.withUsername(edit.getNick()).password(edit.getPass()).roles("EDITOR").build();
        } else {
            throw new UsernameNotFoundException("Usuario no encontrado");
        }

    }
}
