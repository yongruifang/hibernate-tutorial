package com.example.test;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.junit.Test;

import com.example.pojo.Category;
import com.example.pojo.Product;
import com.ibm.icu.impl.units.UnitsData.Categories;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;

import java.util.List;
 
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
    //通过id删除Product对象
    @Test
    public void deleteProductByID(){
        SessionFactory sf = new Configuration().configure().buildSessionFactory();
        Session s = sf.openSession();
        s.beginTransaction();
        Product p = (Product) s.get(Product.class, 11);
        s.remove(p);
        s.getTransaction().commit();
        s.close();
        sf.close();        
    }
    //把id=6的产品名称修改为 iphone-modifed
    @Test
    public void updateProductByID(){
        SessionFactory sf = new Configuration().configure().buildSessionFactory();
        Session s = sf.openSession();
        s.beginTransaction();
        Product p = (Product) s.get(Product.class, 6);
        p.setName("iphone-modified");
        s.merge(p);
        s.getTransaction().commit();
        s.close();
        sf.close();        
    }
    //使用HQL,根据name进行模糊查询
    @Test
    public void queryProductByName(){
        SessionFactory sf = new Configuration().configure().buildSessionFactory();
        Session s = sf.openSession();
        s.beginTransaction();
        String name = "iphone";
        String hql = "from Product p where p.name like '%"+name+"%'";
        System.out.println("模糊查询的HQL语句是: "+hql);
        List<Product> ps = s.createQuery(hql,Product.class).list();
        for (Product p : ps) {
            System.out.println("模糊查询的结果之一是："+p.getName());
        }
        s.getTransaction().commit();
        s.close();
        sf.close();        
    }
    //使用JPA, 根据关键字进行模糊查询
    @Test
    public void queryProductByNameWithCriteria(){
        SessionFactory sf = new Configuration().configure().buildSessionFactory();
        Session s = sf.openSession();
        s.beginTransaction();
        String searchKeyWord = "modified";
        // 使用JPA标准查询API查询实体列表
        CriteriaBuilder builder = s.getCriteriaBuilder();
        CriteriaQuery<Product> criteria = builder.createQuery(Product.class);
        Root<Product> root = criteria.from(Product.class);
        criteria.select(root);
         // Create a lower-case expression for the name attribute
        criteria.where(builder.like(root.<String> get("name"), "%"+searchKeyWord+"%"));
        List<Product> ps = s.createQuery(criteria).getResultList();
        for(Product p : ps){
            System.out.println("JPA标准查询API查询的结果之一是："+p.getName());
        }
        s.getTransaction().commit();
        s.close();
        sf.close();        
    }
    //使用标准SQL进行模糊查询
    @Test
    public void queryProductByNameWithSQL(){
        SessionFactory sf = new Configuration().configure().buildSessionFactory();
        Session s = sf.openSession();
        s.beginTransaction();
        String searchKeyWord = "modified";
        String sql = "select * from product_ p where p.name like '%"+searchKeyWord+"%'";
        System.out.println("标准SQL查询语句是: "+sql);
        List<Product> ps = s.createNativeQuery(sql,Product.class).list();
        for(Product p : ps){
            System.out.println("标准SQL查询的结果之一是："+p.getName());
        }
        s.getTransaction().commit();
        s.close();
        sf.close();        
    }
    // 使用JPA进行分页查询
    @Test
    public void queryProductByPage(){
        SessionFactory sf = new Configuration().configure().buildSessionFactory();
        Session s = sf.openSession();
        s.beginTransaction();
        int start = 2;
        int count = 5;
        System.out.println("使用JPA进行分页查询，从第"+start+"条开始，查询"+count+"条");
        // 使用JPA标准查询API查询实体列表
        CriteriaBuilder builder = s.getCriteriaBuilder();
        CriteriaQuery<Product> criteria = builder.createQuery(Product.class);
        Root<Product> root = criteria.from(Product.class);
        criteria.select(root);
        List<Product> ps = s.createQuery(criteria).setFirstResult(start).setMaxResults(count).getResultList();
        for(Product p : ps){
            System.out.println(p.getName());
        }
        s.getTransaction().commit();
        s.close();
        sf.close();        
    }
    // 使用数据库连接池c3p0
    @Test
    public void testPool(){
        SessionFactory sf = new Configuration().configure().buildSessionFactory();
        Session s = sf.openSession();
        s.beginTransaction();
        s.createQuery("from Category",Category.class).list();
        s.getTransaction().commit();
        s.close();
        sf.close();
    }
}