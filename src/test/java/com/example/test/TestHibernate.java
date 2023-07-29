package com.example.test;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.junit.Test;

import com.example.pojo.Product;
 
public class TestHibernate {
    @Test 
    public void test1(){
        SessionFactory sf = new Configuration().configure().buildSessionFactory();
        Session s = sf.openSession();
        s.beginTransaction();
        Product p = new Product();
        p.setName("iphone7");
        p.setPrice(7000);
        s.persist(p);
        s.getTransaction().commit();
        s.close();
        sf.close();
    }
 
}