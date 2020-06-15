package utils.hibernate.custom;

import entity.transport.Transportation;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import utils.hibernate.DateContainers.*;
import utils.hibernate.State;

import javax.persistence.criteria.*;
import java.sql.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static utils.hibernate.Hibernator.SLASH;

public class CustomHibernator {

    final SessionFactory sessionFactory;

    public CustomHibernator(String file){
        sessionFactory = CustomHibernateFactory.buildSessionFactory(file);
    }

    public void save(Object ... objects) {
        Session session = sessionFactory.openSession();
        for (Object o : objects){
            session.saveOrUpdate(o);
        }
        session.beginTransaction().commit();
    }

    public void shutdown(){
        sessionFactory.close();
    }

    public synchronized <T>List<T> query(Class<T> tClass, HashMap<String, Object> params, int limit){
        Session session = sessionFactory.openSession();
        CriteriaQuery<T> query = getCriteriaQuery(session, tClass, params);
        final Query<T> sessionQuery = session.createQuery(query);
        if (limit > 0){
            sessionQuery.setMaxResults(limit);
        }
        return sessionQuery.getResultList();
    }

    private <T> CriteriaQuery<T> getCriteriaQuery(Session session, Class<T> tClass, HashMap<String, Object> parameters) {
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        return getCriteriaQuery(criteriaBuilder, tClass, parameters);
    }

    private <T> CriteriaQuery<T> getCriteriaQuery(CriteriaBuilder builder, Class<T> tClass, HashMap<String, Object> parameters) {
        CriteriaQuery<T> query = builder.createQuery(tClass);
        buildQuery(builder, query, tClass, parameters);
        return query;
    }

    private <T, K> void buildQuery(CriteriaBuilder criteriaBuilder, CriteriaQuery<T> query, Class<K> tClass, HashMap<String, Object> param){
        Root<K> from = query.from(tClass);
        query.where(buildPredicates(criteriaBuilder, query, from, param));
    }

    <K, T> Predicate[] buildPredicates(CriteriaBuilder criteriaBuilder, CriteriaQuery<T> query, Root<K> root, HashMap<String, Object> param){
        if (param != null){
            Predicate[] predicates = new Predicate[param.size()];
            int i = 0;

            for (Map.Entry<String, Object> entry : param.entrySet()){

                Path<Date> path = parsePath(root, entry.getKey());

                if (entry.getValue() == null || entry.getValue().equals(State.isNull)){
                    predicates[i] = criteriaBuilder.isNull(path);
                } else if(entry.getValue().equals(State.notNull)) {
                    predicates[i] = criteriaBuilder.isNotNull(path);
                } else if (entry.getValue() instanceof EQ){
                    EQ eq = (EQ) entry.getValue();
                    predicates[i] = criteriaBuilder.greaterThanOrEqualTo(path, eq.getDate());
                }else if (entry.getValue() instanceof NOT) {
                    NOT not = (NOT) entry.getValue();
                    predicates[i] = criteriaBuilder.notEqual(path, not.getObject());
                } else if (entry.getValue() instanceof BETWEEN){
                    BETWEEN between = (BETWEEN) entry.getValue();
                    predicates[i] = criteriaBuilder.between(path, between.getFrom(), between.getTo());
                }else if (entry.getValue() instanceof GE) {
                    GE ge = (GE) entry.getValue();
                    predicates[i] = criteriaBuilder.greaterThanOrEqualTo(path, ge.getDate());
                    query.orderBy(criteriaBuilder.asc(path));
                }else if (entry.getValue() instanceof GT) {
                    GT gt = (GT) entry.getValue();
                    predicates[i] = criteriaBuilder.greaterThan(path, gt.getDate());
                    query.orderBy(criteriaBuilder.asc(path));
                } else if (entry.getValue() instanceof LE) {
                    LE le = (LE) entry.getValue();
                    predicates[i] = criteriaBuilder.lessThanOrEqualTo(path, le.getDate());
                    query.orderBy(criteriaBuilder.desc(path));
                } else if (entry.getValue() instanceof LT) {
                    LT lt = (LT) entry.getValue();
                    predicates[i] = criteriaBuilder.lessThan(path, lt.getDate());
                    query.orderBy(criteriaBuilder.desc(path));
                } else {
                    predicates[i] = criteriaBuilder.equal(path, entry.getValue());
                }
                i++;
            }
            return predicates;
        }
        return new Predicate[0];
    }

    private <T> Path<Date> parsePath(Root<T> root, String value){
        Path<Date> objectPath = null;
        String[] split = value.split(SLASH);

        for (String s : split){
            if (objectPath == null) {
                objectPath = root.get(s);
            } else {
                objectPath = objectPath.get(s);
            }
        }

        return objectPath;
    }

    public <T>List<T> query(Class<T> tClass, HashMap<String, Object> params) {
        return query(tClass, params, -1);
    }

    public <T> T get(Class<T> tClass, HashMap<String, Object> params){
        final List<T> query = query(tClass, params, 1);
        if (query == null || query.isEmpty()){
            return null;
        } else {
            return query.get(0);
        }
    }

    public void remove(Object o) {
        final Session session = sessionFactory.openSession();
        session.remove(o);
        session.beginTransaction().commit();
    }
}
