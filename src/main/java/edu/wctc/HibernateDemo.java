package edu.wctc;

import edu.wctc.entity.*;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateDemo {
    private SessionFactory factory;

    public HibernateDemo() {
        factory = new Configuration().configure("hibernate.cfg.xml")
                .buildSessionFactory();
    }

    public static void main(String[] args) {
        HibernateDemo demo = new HibernateDemo();

        try {
            demo.createPizza();
        } finally {
            demo.close();
        }
    }

    private void close() {
        factory.close();
    }

    private void createPizza() {
        Session session = factory.getCurrentSession();

        session.beginTransaction();

        // Do stuff
        Item pizza = new Item("Pizza", 6);

        session.save(pizza);

        session.getTransaction().commit();
    }
}
