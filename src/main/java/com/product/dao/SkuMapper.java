package com.product.dao;

import com.product.model.Sku;

public interface SkuMapper {
    int deleteByPrimaryKey(Integer skuId);

    int insert(Sku record);

    int insertSelective(Sku record);

    Sku selectByPrimaryKey(Integer skuId);

    int updateByPrimaryKeySelective(Sku record);

    int updateByPrimaryKey(Sku record);
}