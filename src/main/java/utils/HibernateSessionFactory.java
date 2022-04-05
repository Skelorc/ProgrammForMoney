package utils;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HibernateSessionFactory {

    private static SessionFactory sessionFactory = buildSessionFactory();
    private static final Logger logger = LoggerFactory.getLogger(HibernateSessionFactory.class);
    private static Session session;

    protected static SessionFactory buildSessionFactory() {
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure()
                .build();
        try {
            sessionFactory = new MetadataSources( registry ).buildMetadata().buildSessionFactory();
        }
        catch (Exception e) {
            StandardServiceRegistryBuilder.destroy( registry );
            assert logger != null;
            logger.error("Ошибка инициализации сессии {}",sessionFactory,e);
            throw new ExceptionInInitializerError("Ошибка инициализации сессии" + e);
        }
        return sessionFactory;
    }


    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public static Session getSession()
    {
        session = getSessionFactory().getCurrentSession();
        if(session == null)
            return getSessionFactory().openSession();
        else
            return session;
    }

    public static void shutdown() {
        getSessionFactory().close();
    }
}
