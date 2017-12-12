/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.ujaen.dae.libros.Servidor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 *
 * @author josejimenezdelapaz
 */
@Configuration
public class SeguridadLibros extends WebSecurityConfigurerAdapter {

    @Autowired
    ServicioDatosUsuarioLibros servDatosUsuariosLibros;

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(servDatosUsuariosLibros);
    }

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.csrf().disable();//desactiva ataques cross-site request forgery 
        httpSecurity.httpBasic(); //Autenticación básica
        httpSecurity.authorizeRequests().antMatchers("/elibros/autor/**").hasRole("AUTOR");
        httpSecurity.authorizeRequests().antMatchers("/elibros/editor/**").hasRole("EDITOR");
        httpSecurity.authorizeRequests().antMatchers("/elibros/revisor/**").hasRole("REVISOR");
    }
}
