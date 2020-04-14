package com.product.dao;

import com.product.model.ProductAttr;

public interface ProductAttrMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ProductAttr record);

    int insertSelective(ProductAttr record);

    ProductAttr selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ProductAttr record);

    int updateByPrimaryKey(ProductAttr record);
}