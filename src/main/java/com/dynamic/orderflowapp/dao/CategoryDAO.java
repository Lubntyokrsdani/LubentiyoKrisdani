package com.dynamic.orderflowapp.dao;

import com.dynamic.orderflowapp.entity.Category;
import java.util.List;

public interface CategoryDAO {

    void save(Category category);

    List<Category> findAll();

    Category findById(Long id);
}