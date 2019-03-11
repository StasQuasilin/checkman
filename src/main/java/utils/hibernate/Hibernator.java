package utils.hibernate;

import org.hibernate.Session;
import utils.hibernate.DateContainers.*;

import javax.persistence.criteria.*;
import java.sql.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Quasilin on 09.09.2018.
 */
public class Hibernator {
    private static final Hibernator instance = new Hibernator();

    public static Hibernator getInstance() {
        return instance;
    }

    public void delete(Object object){
        Session session = HibernateSessionFactory.getSession();
        session.delete(object);
        session.beginTransaction().commit();
        HibernateSessionFactory.putSession(session);
    }

    public <T> List<T> LimitQuery(Class<T> tClass, String idString, boolean forward) {
        Session session = HibernateSessionFactory.getSession();
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<T> query = criteriaBuilder.createQuery(tClass);
        Root<T> root = query.from(tClass);

        int id = Integer.parseInt(idString);
        if (id > 0) {

            Predicate[] predicates = new Predicate[1];

            if (forward) {
                predicates[0] = criteriaBuilder.lessThan(root.get("id"), id);
            } else {
                predicates[0] = criteriaBuilder.greaterThan(root.get("id"), id);
            }

            query.where(predicates);
        }

        List<T> resultList = session.createQuery(query)
                .setMaxResults(20)
                .getResultList();

        HibernateSessionFactory.putSession(session);

        return resultList;
    }

    public <T>List<T> LimitQuery(Class<T> tClass, HashMap<String, Object> parameters, int limit) {
        Session session = HibernateSessionFactory.getSession();
        CriteriaQuery<T> query = getCriteriaQuery(session, tClass, parameters);

        List<T> resultList = session.createQuery(query)
                .setMaxResults(limit)
                .getResultList();

        HibernateSessionFactory.putSession(session);

        return resultList;
    }

    private <T> CriteriaQuery<T> getCriteriaQuery(Session session, Class<T> tClass, HashMap<String, Object> parameters) {
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<T> query = criteriaBuilder.createQuery(tClass);
        Root<T> from = query.from(tClass);

        if (parameters != null){

            Predicate[] predicates = new Predicate[parameters.size()];
            int i = 0;

            for (Map.Entry<String, Object> entry : parameters.entrySet()){
                String[] split = entry.getKey().split("/");

                Path<Date> objectPath = null;

                for (String s : split){
                    if (objectPath == null) {
                        objectPath = from.get(s);
                    } else {
                        objectPath = objectPath.get(s);
                    }
                }

                if (entry.getValue() == null || entry.getValue().equals(State.isNull)){
                    predicates[i] = criteriaBuilder.isNull(objectPath);
                } else if(entry.getValue().equals(State.notNull)) {
                    predicates[i] = criteriaBuilder.isNotNull(objectPath);
                } else if (entry.getValue() instanceof EQ){
                    EQ eq = (EQ) entry.getValue();
                    predicates[i] = criteriaBuilder.equal(objectPath, eq.getDate());
                }else if (entry.getValue() instanceof NOT) {
                    NOT not = (NOT) entry.getValue();
                    predicates[i] = criteriaBuilder.notEqual(objectPath, not.getObject());
                } else if (entry.getValue() instanceof BETWEEN){
                    BETWEEN between = (BETWEEN) entry.getValue();
                    predicates[i] = criteriaBuilder.between(objectPath,between.getFrom(),between.getTo());
                }else if (entry.getValue() instanceof GE) {
                    GE ge = (GE) entry.getValue();
                    predicates[i] = criteriaBuilder.greaterThanOrEqualTo(objectPath, ge.getDate());
                    query.orderBy(criteriaBuilder.asc(objectPath));
                }else if (entry.getValue() instanceof GT) {
                    GT gt = (GT) entry.getValue();
                    predicates[i] = criteriaBuilder.greaterThan(objectPath, gt.getDate());
                    query.orderBy(criteriaBuilder.asc(objectPath));
                } else if (entry.getValue() instanceof LE) {
                    LE le = (LE) entry.getValue();
                    predicates[i] = criteriaBuilder.lessThanOrEqualTo(objectPath, le.getDate());
                    query.orderBy(criteriaBuilder.desc(objectPath));
                } else if (entry.getValue() instanceof LT) {
                    LT lt = (LT) entry.getValue();
                    predicates[i] = criteriaBuilder.lessThan(objectPath, lt.getDate());
                    query.orderBy(criteriaBuilder.desc(objectPath));
                } else {
                    predicates[i] = criteriaBuilder.equal(objectPath, entry.getValue());
                }
                i++;
            }
            query.where(predicates);
        }

        return query;
    }


    public <T>List<T> query(Class<T> tClass, HashMap<String, Object> params){
        Session session = HibernateSessionFactory.getSession();
        CriteriaQuery<T> query = getCriteriaQuery(session, tClass, params);

        List<T> resultList = session.createQuery(query).getResultList();

        HibernateSessionFactory.putSession(session);

        return resultList;
    }
    HashMap<String, Object> params = new HashMap<>();

    public <T> List<T> query(Class<T> tClass, String key, Object value) {
        params.clear();
        params.put(key, value);
        return query(tClass, params);
    }

    public <T>T get(Class<T> tClass, String key, Object value){
        params.clear();
        params.put(key, value);
        return get(tClass, params);
    }

    public <T> T get(Class<T> tClass, HashMap<String, Object> parameters) {

        List<T> query = query(tClass, parameters);
        if (query == null || query.isEmpty()) {
            return null;
        } else {
            return query.get(0);
        }
    }


    public <T> void Clear(Class<T> tClass) {
        Session session = HibernateSessionFactory.getSession();

        List<T> query = query(tClass, null);

        query.forEach(session::delete);

        session.beginTransaction().commit();

        System.out.println("\tdelete " + query.size() + " " + tClass.getSimpleName() + " items");
        query.clear();
        HibernateSessionFactory.putSession(session);
    }

    public <T> void removeList(List<T> list) {
        Session session = HibernateSessionFactory.getSession();
        list.forEach(session::delete);
        session.beginTransaction().commit();
        HibernateSessionFactory.putSession(session);
    }

    public <T> List<T> find (Class<T> tClass, HashMap<String, String> params){

        Session session = HibernateSessionFactory.getSession();

        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<T> query = criteriaBuilder.createQuery(tClass);
        Root<T> from = query.from(tClass);

        if (params != null) {
            Predicate[] predicates = new Predicate[params.size()];
            int i = 0;

            for (Map.Entry<String, String> entry : params.entrySet()){
                String[] split = entry.getKey().split("/");
                Path<String> objectPath = null;

                for (String s : split){
                    if (objectPath == null) {
                        objectPath = from.get(s);
                    } else {
                        objectPath = objectPath.get(s);
                    }
                }
                if (entry.getValue() != null) {
                    predicates[i] = criteriaBuilder.like(criteriaBuilder.upper(objectPath), "%" + entry.getValue().toUpperCase() + "%");
                } else {
                    predicates[i] = criteriaBuilder.isNull(objectPath);
                }
                i++;
            }
            query.where(predicates);
        }
        List<T> resultList = session.createQuery(query)
                .getResultList();

        HibernateSessionFactory.putSession(session);

        return resultList;
    }

    public <T> List<T> find (Class<T> tClass, String number, String key) {
        HashMap<String, String> par = new HashMap<>();
        par.put(number, key);
        return find(tClass, par);
    }

    public <T> int sumOfIntegers(Class<T> tClass, String column, HashMap<String, Object> parameters){
        Session session = HibernateSessionFactory.getSession();

        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<Integer> query = criteriaBuilder.createQuery(Integer.class);
        Root<T> from = query.from(tClass);

        if (parameters != null) {
            Predicate[] predicates = new Predicate[parameters.size()];
            int i = 0;

            for (Map.Entry<String, Object> entry : parameters.entrySet()){
                String[] split = entry.getKey().split("/");
                Path<String> objectPath = null;

                for (String s : split){
                    if (objectPath == null) {
                        objectPath = from.get(s);
                    } else {
                        objectPath = objectPath.get(s);
                    }
                }
                if (entry.getValue() != null) {
                    predicates[i] = criteriaBuilder.equal(objectPath, entry.getValue());
                } else {
                    predicates[i] = criteriaBuilder.isNull(objectPath);
                }
                i++;
            }
            query.where(predicates);
        }

        query.select(criteriaBuilder.sum(from.get(column)));
        int sum = 0;
        try {
            sum = session.createQuery(query).uniqueResult();
        } catch (Exception ignored) {}

        HibernateSessionFactory.putSession(session);

        return sum;
    }


    public void save(Object ... objects) {
        Session session = HibernateSessionFactory.getSession();
        for (Object o : objects){
            session.saveOrUpdate(o);
        }
        session.beginTransaction().commit();
    }
}
