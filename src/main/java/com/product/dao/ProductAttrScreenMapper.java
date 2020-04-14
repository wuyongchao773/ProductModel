package com.product.dao;

import com.product.model.ProductAttrScreen;

public interface ProductAttrScreenMapper {
    int deleteByPrimaryKey(Integer productId);

    int insert(ProductAttrScreen record);

    int insertSelective(ProductAttrScreen record);

    ProductAttrScreen selectByPrimaryKey(Integer productId);

    int updateByPrimaryKeySelective(ProductAttrScreen record);

    int updateByPrimaryKey(ProductAttrScreen record);
}