package service;

import entity.Params;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.HibernateSessionFactory;

import java.util.ArrayList;
import java.util.List;

public class ParamsService extends ServiceImpl{

    private final Logger logger = LoggerFactory.getLogger(ParamsService.class);

    public List<Params> getAllValues() {
        ArrayList<Params> list;
        try (Session session = HibernateSessionFactory.getSession()) {
            session.beginTransaction();
            Query query = session.createQuery("FROM Params as p");
            list = (ArrayList<Params>) query.list();
            session.getTransaction().commit();
        }
        return list;
    }

    public Params findParamsByName(String name) {
        Params params;
        try (Session session = HibernateSessionFactory.getSession()) {
            session.beginTransaction();
            Query query = session.createQuery("FROM Params as p WHERE p.name like :name")
                    .setParameter("name", "%" + name + "%");
            params = (Params) query.uniqueResult();
            return params;
        }
    }

    public void deleteValueFromParams(String params, String value)
    {
        try(Session session = HibernateSessionFactory.getSession())
        {
            session.beginTransaction();
            Query query = session.createQuery("FROM Params as p WHERE p.name = :name")
                    .setParameter("name", params);
            Params params_from_db = (Params)query.uniqueResult();
            params_from_db.getValues().remove(value);
            session.update(params_from_db);
            session.getTransaction().commit();
        }
    }

}
