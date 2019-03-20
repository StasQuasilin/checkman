package utils.hibernate;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import java.util.ArrayList;

/**
 * Created by Quasilin on 09.09.2018.
 */
public class HibernateSessionFactory {

    private static SessionFactory sessionFactory = buildSessionFactory();
    private static final int POOL_SIZE = 5;
    private static final ArrayList<Session> pool = new ArrayList<>();
    private static Session session;


    protected static SessionFactory buildSessionFactory() {
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure()
                .build();
        try {
            sessionFactory = new MetadataSources( registry ).buildMetadata().buildSessionFactory();
        } catch (Exception e) {
            StandardServiceRegistryBuilder.destroy( registry );
            throw new ExceptionInInitializerError("Initial SessionFactory failed" + e);
        }
        return sessionFactory;
    }

    public static void init() {
//        for (int i = 0; i < POOL_SIZE; i++) {
//            pool.add(sessionFactory.openSession());
//        }

        session = sessionFactory.openSession();
    }

    public synchronized static Session getSession() {
//        if (pool.size() == 0){
//            return sessionFactory.openSession();
//        } else {
//            return pool.remove(0);
//        }
        return sessionFactory.openSession();
    }

    public static void putSession(Session session){
        session.close();
    }

    public static void shutdown() {
        for (Session session : pool){
            session.close();
        }
        sessionFactory.close();
    }


}
