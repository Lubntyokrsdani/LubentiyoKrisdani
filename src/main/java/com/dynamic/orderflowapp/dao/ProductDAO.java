package com.dynamic.orderflowapp.dao;

import com.dynamic.orderflowapp.entity.Product;
import java.util.List;

public interface ProductDAO {

    void save(Product product);

    List<Product> findAll();

    List<Product> search(String keyword);
}