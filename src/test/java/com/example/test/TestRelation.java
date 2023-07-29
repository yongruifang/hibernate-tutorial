package com.example.test;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.junit.Test;

import com.example.pojo.Category;
import com.example.pojo.Product;

public class TestRelation {
    //多对一
    //一个产品对应一个分类，一个分类对应多个产品
    @Test
    public void many2One(){          
        SessionFactory sf = new Configuration().configure().buildSessionFactory();
        Session s = sf.openSession();
        s.beginTransaction();
        //新增一个类别，叫C2类
        Category c =new Category();
        c.setName("C2");
        s.persist(c);
        //查询id为6的产品
        Product p = (Product) s.get(Product.class, 6);
        //把产品p的分类设置为C2
        p.setCategory(c);
        s.get(Product.class, 2).setCategory(c);
        s.merge(p);
        s.getTransaction().commit();
        s.close();
        sf.close();
    }
    //一对多
    
    //多对多
}
