package utils.hibernate.custom;

import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

public class CustomHibernateFactory {
    public SessionFactory getSession(String file){
        return buildSessionFactory(file);
    }
    protected static SessionFactory buildSessionFactory(String file){
        final StandardServiceRegistry build = new StandardServiceRegistryBuilder()
                .configure(file)
                .build();
        return new MetadataSources(build).buildMetadata().buildSessionFactory();
    }
}
