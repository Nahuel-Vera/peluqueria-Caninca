/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Persistencia;

import Logica.MascotaCli;
import Persistencia.exceptions.NonexistentEntityException;
import Persistencia.exceptions.PreexistingEntityException;
import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.Persistence;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

/**
 *
 * @author Nahuu
 */
public class MascotaCliJpaController implements Serializable {

    public MascotaCliJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }
    
    public MascotaCliJpaController(){
        emf=Persistence.createEntityManagerFactory("Vera_Nahuel_tpo2_PU");
    }

    public void create(MascotaCli mascotaCli) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(mascotaCli);
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findMascotaCli(mascotaCli.getNum_cliente()) != null) {
                throw new PreexistingEntityException("MascotaCli " + mascotaCli + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(MascotaCli mascotaCli) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            mascotaCli = em.merge(mascotaCli);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                int id = mascotaCli.getNum_cliente();
                if (findMascotaCli(id) == null) {
                    throw new NonexistentEntityException("The mascotaCli with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(int id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            MascotaCli mascotaCli;
            try {
                mascotaCli = em.getReference(MascotaCli.class, id);
                mascotaCli.getNum_cliente();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The mascotaCli with id " + id + " no longer exists.", enfe);
            }
            em.remove(mascotaCli);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<MascotaCli> findMascotaCliEntities() {
        return findMascotaCliEntities(true, -1, -1);
    }

    public List<MascotaCli> findMascotaCliEntities(int maxResults, int firstResult) {
        return findMascotaCliEntities(false, maxResults, firstResult);
    }

    private List<MascotaCli> findMascotaCliEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(MascotaCli.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public MascotaCli findMascotaCli(int id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(MascotaCli.class, id);
        } finally {
            em.close();
        }
    }

    public int getMascotaCliCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<MascotaCli> rt = cq.from(MascotaCli.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
