package bussiness;

import play.db.jpa.JPAApi;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Root;
import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by mjovel on 11-15-16.
 */
public class BasicBO<T, K extends Serializable> implements Crud<T, K> {

    protected Class<T> entityClass;

    @Inject
    protected JPAApi jpaApi;

    public BasicBO() {
        entityClass = (Class<T>)
                ((ParameterizedType)getClass().getGenericSuperclass())
                        .getActualTypeArguments()[0];
    }


    @Override
    public void persist(T object) {
        jpaApi.em().persist(object);
    }

    @Override
    public List<T> findAll() {
        return findAll(null);
    }

    @Override
    public List<T> findAll(String... orderBy) {
        return findAllPaged(-1, -1, orderBy);
    }

    @Override
    public List<T> findAllPaged(int first, int size, String... orderBy) {
        CriteriaBuilder cb = jpaApi.em().getCriteriaBuilder();
        CriteriaQuery<T> query = cb.createQuery(entityClass);
        Root<T> from = query.from(entityClass);
        TypedQuery<T> typedQuery;
        ArrayList<Order> orders = new ArrayList<>();
        query.select(from);
        
        if(orderBy != null) {
            for (String order :
                    orderBy) {
                orders.add(cb.asc(from.get(order)));
            }
        }

        if(!orders.isEmpty()) {
            query.orderBy(orders);
        }

        typedQuery = jpaApi.em().createQuery(query);

        if(first >= 0) {
            typedQuery.setFirstResult(first);
        }

        if(size > 0) {
            typedQuery.setMaxResults(size);
        }

        return typedQuery.getResultList();
    }

    @Override
    public T get(K id) {
        return jpaApi.em().find(entityClass, id);
    }

    @Override
    public void delete(T object) {
        jpaApi.em().remove(object);
    }

    @Override
    public void delete(K key) {
        delete(get(key));
    }

    public EntityManager em() {
        return jpaApi.em();
    }
}
