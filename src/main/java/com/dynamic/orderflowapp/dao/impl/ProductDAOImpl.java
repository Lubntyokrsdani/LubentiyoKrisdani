package com.dynamic.orderflowapp.dao.impl;

import com.dynamic.orderflowapp.dao.ProductDAO;
import com.dynamic.orderflowapp.entity.Product;

import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public class ProductDAOImpl implements ProductDAO {

    private final SessionFactory sessionFactory;

    public ProductDAOImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void save(Product product) {
        sessionFactory.getCurrentSession().persist(product);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Product> findAll() {
        return sessionFactory.getCurrentSession()
                .createQuery(
                    "FROM Product p JOIN FETCH p.category ORDER BY p.id DESC",
                    Product.class
                ).getResultList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Product> search(String keyword) {
        return sessionFactory.getCurrentSession()
                .createQuery(
                    "FROM Product p JOIN FETCH p.category " +
                    "WHERE LOWER(p.productName) LIKE :keyword " +
                    "OR LOWER(p.productCode) LIKE :keyword " +
                    "ORDER BY p.id DESC",
                    Product.class
                )
                .setParameter("keyword", "%" + keyword.toLowerCase() + "%")
                .getResultList();
    }
}