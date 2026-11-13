package com.dynamic.orderflowapp.dao.impl;

import com.dynamic.orderflowapp.dao.CategoryDAO;
import com.dynamic.orderflowapp.entity.Category;

import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public class CategoryDAOImpl implements CategoryDAO {

    private final SessionFactory sessionFactory;

    public CategoryDAOImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void save(Category category) {
        sessionFactory.getCurrentSession().persist(category);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Category> findAll() {
        return sessionFactory.getCurrentSession()
                .createQuery(
                    "FROM Category ORDER BY categoryName",
                    Category.class
                ).getResultList();
    }

    @Override
    @Transactional(readOnly = true)
    public Category findById(Long id) {
        return sessionFactory.getCurrentSession()
                .get(Category.class, id);
    }
}