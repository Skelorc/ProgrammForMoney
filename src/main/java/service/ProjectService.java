package service;

import entity.Project;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.HibernateSessionFactory;

import java.util.ArrayList;
import java.util.List;

public class ProjectService extends ServiceImpl{

    private final Logger logger = LoggerFactory.getLogger(ProjectService.class);

    public List<Project> getAll() {
        ArrayList<Project> list;
        try (Session session = HibernateSessionFactory.getSession()) {
            session.beginTransaction();
            Query query = session.createQuery("FROM Project as d");
            list = (ArrayList<Project>) query.list();
            session.getTransaction().commit();
        }
        return list;
    }



}
