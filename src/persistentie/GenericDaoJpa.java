package persistentie;

import java.util.List;
import javax.persistence.*;

public class GenericDaoJpa<T, K> implements GenericDao<T, K> {

    private static final String PU_NAME = "persistentie";
    private static final EntityManagerFactory emf
            = Persistence.createEntityManagerFactory(PU_NAME);
    protected static final EntityManager em
            = emf.createEntityManager();
    private final Class<T> type;

    public static void closePersistency() {
        em.close();
        emf.close();
    }

    public static void startTransaction() {
        em.getTransaction().begin();
    }

    public static void commitTransaction() {
        em.getTransaction().commit();
    }

    public static void rollbackTransaction() {
        em.getTransaction().rollback();
    }

    public GenericDaoJpa(Class<T> type) {
        this.type = type;
    }

    public List<T> getAll() {
        return em.createQuery("select entity from "
                + type.getName() + " entity", type).getResultList();
    }

    /**
     *
     * @param item
     */
    public void insert(T item) {
        em.persist(item);
    }

    /**
     *
     * @param item
     */
    public void delete(T item) {
        em.remove(em.merge(item));
    }

    /**
     *
     * @param id
     */
    public T get(K id) {
        T entity = em.find(type, id);

        return entity;
    }

    /**
     *
     * @param id
     */
    public boolean exists(K id) {
        T entity = em.find(type, id);
        return entity != null;

    }

    /**
     *
     * @param item
     */
    public T update(T item) {
        return em.merge(item);
    }

}