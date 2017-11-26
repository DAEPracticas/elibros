/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.ujaen.dae.libros.Servidor;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
//import javax.transaction.Transactional;
import org.springframework.stereotype.Repository;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import es.ujaen.dae.libros.excepciones.ErrorUsuarioRegistrado;
import es.ujaen.dae.libros.excepciones.ErrorEliminarUsuario;



/**
 *
 * @author josejimenezdelapaz
 */

@Repository
//@Transactional
@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
public class AutorDAO {

    @PersistenceContext
    EntityManager em;
    

    @Transactional(propagation = Propagation.REQUIRED, readOnly = false, rollbackFor=ErrorUsuarioRegistrado.class)
    public void insertar(Autor aut) {
        try {
            em.persist(aut);
            em.flush();
        } catch (Exception e) {
            throw new ErrorUsuarioRegistrado();
        }
        
        
    }
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false, rollbackFor=ErrorUsuarioRegistrado.class)
    public void actualizar(Autor aut) {
        try {
            em.merge(aut); //cambios actualizados en la BD
            em.flush();
        } catch (Exception e) {
            throw new ErrorUsuarioRegistrado();
        }
        
    }
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false, rollbackFor=ErrorEliminarUsuario.class)
    public void eliminar(Autor aut) {
        try {
            em.remove(em.merge(aut)); //cambios actualizados en la BD
            em.flush();
        } catch (Exception e) {
            throw new ErrorEliminarUsuario();
        }
        
    } 
    
    public List<Autor> getAutores(){
        List<Autor> autores=em.createQuery("select aut from Autor aut ").getResultList();
        return autores;     
    }
    public Autor buscarPorToken(String token) {
        return em.find(Autor.class, token);
    }
    
    public Autor buscarPorNick(String nickk){
        Query q=em.createQuery("from Autor aut where aut.nick=:nikk").setParameter("nikk", nickk);    
        List<Autor>l=q.getResultList();
        if(l.isEmpty()){
            return null;
        }else{
            return l.get(0);
        }    
    }
}