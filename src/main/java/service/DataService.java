package service;

import entity.DataObject;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.HibernateSessionFactory;

import java.util.ArrayList;
import java.util.List;

public class DataService extends ServiceImpl{

    private final Logger logger = LoggerFactory.getLogger(DataService.class);

    public List<DataObject> getAll() {
        ArrayList<DataObject> list;
        try (Session session = HibernateSessionFactory.getSession()) {
            session.beginTransaction();
            Query query = session.createQuery("FROM DataObject as d");
            list = (ArrayList<DataObject>) query.list();
            session.getTransaction().commit();
        }
        return list;
    }

}
