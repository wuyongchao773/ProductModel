package com.product.dao;

import com.product.model.ProductType;

public interface ProductTypeMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ProductType record);

    int insertSelective(ProductType record);

    ProductType selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ProductType record);

    int updateByPrimaryKey(ProductType record);
}