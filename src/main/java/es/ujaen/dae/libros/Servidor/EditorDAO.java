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
@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
public class EditorDAO {

    @PersistenceContext
    EntityManager em;

    @Transactional(propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = ErrorUsuarioRegistrado.class)
    public void insertar(Editor edit) {

        try {
            em.persist(edit);
            em.flush();
        } catch (Exception e) {
            throw new ErrorUsuarioRegistrado();
        }
    }

    @Transactional(propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = ErrorEliminarUsuario.class)
    public void actualizar(Editor edit) {
        try {
            em.merge(edit); //cambios actualizados en la BD
            em.flush();
        } catch (Exception e) {
            throw new ErrorEliminarUsuario();
        }
    }

    @Transactional(propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = ErrorUsuarioRegistrado.class)
    public void eliminar(Editor edit) {
        try {
            em.remove(em.merge(edit)); //cambios actualizados en la BD
            em.flush();
        } catch (Exception e) {
            throw new ErrorUsuarioRegistrado();
        }
    }

    public List<Editor> getEditores() {
        List<Editor> editores = em.createQuery("select edit from Editor edit ").getResultList();
        return editores;
    }

    public Editor buscarPorToken(String token) {
        return em.find(Editor.class, token);
    }

    public Editor buscarPorNick(String nickk) {
        Query q = em.createQuery("from Editor ed where ed.nick=:nikk").setParameter("nikk", nickk);
        List<Editor> l = q.getResultList();
        if (l.isEmpty()) {
            return null;
        } else {
            return l.get(0);
        }
    }

    public List<Libro> librosPorRevisar() {
        String estado = "revisionContenido";
        Query q = em.createQuery("from Libro lib where lib.estado=:est").setParameter("est", estado);
        List<Libro> l = q.getResultList();
        if (l.isEmpty()) {
            return null;
        } else {
            return l;
        }
    }
}
