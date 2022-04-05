package service;

import entity.BaseEntity;
import org.apache.poi.ss.formula.functions.T;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.HibernateSessionFactory;

import java.util.List;

public abstract class ServiceImpl implements Service{

    private final Logger logger = LoggerFactory.getLogger(ServiceImpl.class);

    public void saveEntity(BaseEntity o)
    {
        try (Session session = HibernateSessionFactory.getSession()) {
            session.beginTransaction();
            try {
                session.save(o);
                session.getTransaction().commit();
            }
            catch (HibernateException e)
            {
                logger.error("Error creating new Data {}!", o,e);
                session.getTransaction().rollback();
                throw e;
            }
        }
    }

   public void updateEntity(BaseEntity o)
    {
        try (Session session = HibernateSessionFactory.getSession()) {
            session.beginTransaction();
            try {
                session.update(o);
                session.getTransaction().commit();
            }
            catch (HibernateException e)
            {
                logger.error("Error updating new Data {}!", o,e);
                session.getTransaction().rollback();
                throw e;
            }
        }
    }

}
