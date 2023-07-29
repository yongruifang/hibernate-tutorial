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
    //循环插入10个Product对象到数据库中
    @Test
    public void insert10Products(){
        SessionFactory sf = new Configuration().configure().buildSessionFactory();
        Session s = sf.openSession();
        s.beginTransaction();
        for(int i = 0 ; i < 10 ; i ++){
            Product p  = new Product();
            p.setName("iphone"+i);
            p.setPrice(5000+i);
            s.persist(p);
        }
        s.getTransaction().commit();
        s.close();
        sf.close();
    }

    //通过id获取Product对象
    @Test
    public void getProductByID(){
        SessionFactory sf = new Configuration().configure().buildSessionFactory();
        Session s = sf.openSession();
        s.beginTransaction();
        Product p = (Product) s.get(Product.class, 6);
        System.out.println("id为6的产品名称是："+p.getName());
        s.getTransaction().commit();
        s.close();
        sf.close();        
    }
}