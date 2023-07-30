package com.example.test;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.junit.Test;

import com.example.pojo.Category;

public class TestSecondBuffer {
    // TestSecondBuffer
    @Test
    public void testSecondBuffer() {
        System.out.println("TestSecondBuffer");
        SessionFactory sf = new Configuration().configure().buildSessionFactory();
        Session s = sf.openSession();
        s.beginTransaction();
        Category c1 = (Category)s.get(Category.class, 1);
        System.out.println("log1: "+c1.getName());
        Category c2= (Category)s.get(Category.class, 1);
        System.out.println("log2 "+c2.getName());
        s.getTransaction().commit();
        s.close();
        Session s2 = sf.openSession();
        s2.beginTransaction();
        Category c3 = (Category)s2.get(Category.class, 1);
        System.out.println("log3 "+c3.getName());
        s2.getTransaction().commit();
        s2.close();
        sf.close();
    }
}
