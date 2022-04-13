package service;

import entity.Currency;
import entity.TypeCurrency;
import org.hibernate.Session;
import org.hibernate.query.Query;
import utils.HibernateSessionFactory;

import java.util.ArrayList;
import java.util.List;

public class TypeCurrencyService extends ServiceImpl {


    public TypeCurrency findByType(String type)
    {
        try (Session session = HibernateSessionFactory.getSession()) {
            session.beginTransaction();
            Query query = session.createQuery("FROM TypeCurrency as p WHERE p.type =:type")
                    .setParameter("type", type);
            TypeCurrency typeCurrency = (TypeCurrency) query.uniqueResult();
            return typeCurrency;
        }
    }

    public List<TypeCurrency> getAll()
    {
        ArrayList<TypeCurrency> list;
        try (Session session = HibernateSessionFactory.getSession()) {
            session.beginTransaction();
            Query query = session.createQuery("FROM TypeCurrency as t");
            list = (ArrayList<TypeCurrency>) query.list();
            session.getTransaction().commit();
        }
        return list;
    }
}
