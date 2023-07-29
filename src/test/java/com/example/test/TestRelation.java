package com.example.test;

import java.util.HashSet;
import java.util.Set;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.junit.Test;

import com.example.pojo.Category;
import com.example.pojo.Product;
import com.example.pojo.User;

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
    @Test
    public void one2Many(){
        SessionFactory sf = new Configuration().configure().buildSessionFactory();
        Session s = sf.openSession();
        s.beginTransaction();
        Category c = (Category) s.get(Category.class, 2);
        Set<Product> ps = c.getProducts();
        for (Product p : ps) {
            System.out.println(p.getName());
        }
        s.getTransaction().commit();
        s.close();
        sf.close();
    }
    //多对多
    // 实现多对多关系，必须有一张中间表 user_product 用于维护 User和Product
    // 先增加3个用户,演示产品1被用户1，2,3购买, 产品2被用户1,2,3购买
    @Test
    public void many2many(){
        SessionFactory sf = new Configuration().configure().buildSessionFactory();
        Session s = sf.openSession();
        s.beginTransaction();
        //增加3个用户
        Set<User> users = new HashSet<User>();
        for (int i = 0; i < 3; i++) {
            User u =new User();
            u.setName("user"+i);
            users.add(u);
            s.persist(u);
        }        
        //产品1被用户1,2,3购买
        Product p1 = (Product) s.get(Product.class, 1); 
        p1.setUsers(users);
        //产品2被用户1,2,3购买
        Product p2 = (Product) s.get(Product.class, 2);
        p2.setUsers(users);
        s.persist(p1);
        s.getTransaction().commit();
        s.close();
        sf.close();
    }
}
