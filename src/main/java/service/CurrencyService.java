package service;

import entity.BaseEntity;
import entity.Currency;
import entity.Params;
import org.hibernate.Session;
import org.hibernate.query.Query;
import utils.HibernateSessionFactory;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class CurrencyService extends ServiceImpl {

    public List<Currency> getAllCurrency() {
        ArrayList<Currency> list;
        try (Session session = HibernateSessionFactory.getSession()) {
            session.beginTransaction();
            Query query = session.createQuery("FROM Currency as c");
            list = (ArrayList<Currency>) query.list();
            session.getTransaction().commit();
        }
        return list;
    }

    public Currency findById(long id)
    {
        try (Session session = HibernateSessionFactory.getSession()) {
            session.beginTransaction();
            Query query = session.createQuery("FROM Currency as p WHERE p.id =:id")
                    .setParameter("id", id);
            Currency currency = (Currency) query.uniqueResult();
            return currency;
        }
    }

    public List<Currency> findListByDate(LocalDate date)
    {
        try (Session session = HibernateSessionFactory.getSession()) {
            session.beginTransaction();
            Query query = session.createQuery("FROM Currency as p WHERE p.date =:date")
                    .setParameter("date", date);
            List<Currency> list = (List<Currency>)query.list();
            return list;
        }
    }
}
