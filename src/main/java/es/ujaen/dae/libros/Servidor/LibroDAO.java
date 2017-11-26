/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.ujaen.dae.libros.Servidor;

import es.ujaen.dae.libros.excepciones.ErrorModificarLibro;
//import es.ujaen.dae.libros.excepciones.ErrorNuevoLibro;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
//import javax.transaction.Transactional;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import es.ujaen.dae.libros.excepciones.ErrorLibroExistente;
import es.ujaen.dae.libros.excepciones.ErrorLibroNoExiste;

/**
 *
 * @author josejimenezdelapaz
 */
@Repository
//@Transactional
@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
public class LibroDAO {

    @PersistenceContext
    EntityManager em;

    public Libro buscar(String isbn) {

        return em.find(Libro.class, isbn);
    }

    @Transactional(propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = ErrorLibroExistente.class)
    public void insertar(Libro lib) {
        try {
            em.persist(lib);
            em.flush();
        } catch (Exception e) {
            throw new ErrorLibroExistente();
        }
    }

    @Transactional(propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = ErrorModificarLibro.class)
    public void actualizar(Libro lib) {
        try {
            em.merge(lib); //cambios actualizados en la BD
//            em.flush();
        } catch (Exception e) {
            throw new ErrorModificarLibro();
        }
    }

    @Transactional(propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = ErrorLibroNoExiste.class)
    public void eliminar(Libro lib) {
        try {
            em.remove(em.merge(lib)); //cambios actualizados en la BD
            em.flush();
        } catch (Exception e) {
            throw new ErrorLibroNoExiste();
        }

    }

    public Libro buscarPorTitulo(String tit) {
         Query q = em.createQuery("from Libro l where l.titulo=:tit").setParameter("tit", tit);
        List<Libro> l = q.getResultList();
        if (l.isEmpty()) {
            return null;
        } else {
            return l.get(0);
        }
        
        //return em.find(Libro.class, tit);
    }

    public List<Libro> getLibros() {
        List<Libro> libros = em.createQuery("select lib from Libro lib ").getResultList();

        return libros;

    }

    public List<Libro> buscarPorTematica(String temat) {
        Query q = em.createQuery("select lib from Libro lib  where lib.tematica=:tem").setParameter("tem", temat);
        List<Libro> l = q.getResultList();
        if (l.isEmpty()) {
            return null;
        } else {
            return l;
        }
    }

    public List<Libro> getLibrosRevisores() {
        List<Libro> libros;
        libros = em.createQuery("select lib from Libro lib where lib.estado = 'pendienteRevisores' ").getResultList();
        return libros;
    }

    //obtener los libros para revisar contenido
    public List<Libro> getLibrosParaRevision(String est) {
        List<Libro> libros;
        Query q = em.createQuery("select lib from Libro lib where lib.estado =:estado ").setParameter("estado", est);
        libros = q.getResultList();
        if (libros.isEmpty()) {
            return null;
        } else {
            return libros;
        }
    }

}
