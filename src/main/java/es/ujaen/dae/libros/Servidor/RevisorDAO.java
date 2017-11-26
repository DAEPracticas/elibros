/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.ujaen.dae.libros.Servidor;

import es.ujaen.dae.libros.excepciones.ErrorModificarRevisor;
import es.ujaen.dae.libros.excepciones.ErrorNuevoRevisor;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
//import javax.transaction.Transactional;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import es.ujaen.dae.libros.excepciones.ErrorEliminarUsuario;

/**
 *
 * @author josejimenezdelapaz
 */
@Repository
//@Transactional
@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
public class RevisorDAO {

    @PersistenceContext
    EntityManager em;
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false, rollbackFor=ErrorNuevoRevisor.class)
    public void insertar(Revisor rev) {
        try {
            em.persist(rev);
            em.flush();
        } catch (Exception e) {
            throw new ErrorNuevoRevisor();
        }

    }
@Transactional(propagation = Propagation.REQUIRED, readOnly = false, rollbackFor=ErrorNuevoRevisor.class)
    public void actualizar(Revisor rev) {
        try {
            em.merge(rev); //cambios actualizados en la BD
       //     em.flush();
        } catch (Exception e) {
            throw new ErrorNuevoRevisor();
        }

    }
@Transactional(propagation = Propagation.REQUIRED, readOnly = false, rollbackFor=ErrorEliminarUsuario.class)
    public void eliminar(Revisor rev) {
        try {
            em.remove(em.merge(rev)); //cambios actualizados en la BD
            em.flush();
        } catch (Exception e) {
            throw new ErrorEliminarUsuario();
        }
        
    }

    public List<Revisor> getRevisores() {
        List<Revisor> revisores = em.createQuery("select rev from Revisor rev ").getResultList();
        return revisores;
    }

    public Revisor buscarPorToken(String token) {
        return em.find(Revisor.class, token);
    }

    public Revisor buscarPorNick(String nickk) {
        Query q = em.createQuery("from Revisor rev where rev.nick=:nikk").setParameter("nikk", nickk);
        List<Revisor> l = q.getResultList();
        if (l.isEmpty()) {
            return null;
        } else {
            return l.get(0);
        }
    }

    /*
     Query query = entityManagerReference.createQuery(
"SELECT msg FROM ImportedMessage msg " 
+ "WHERE msg.siteId = :siteId ORDER BY msg.createDate desc");

query.setParameter("siteId", 12345);
query.setMaxResults(1);
     */
    public Revisor getRevAsignacionMasAntigua() {
        Query q = em.createQuery("from Revisor rev order by rev.fecha desc");
        q.setMaxResults(1);
        List<Revisor> l = q.getResultList();
        if (l.isEmpty()) {
            return null;
        } else {
            return l.get(0);
        }
    }

}
