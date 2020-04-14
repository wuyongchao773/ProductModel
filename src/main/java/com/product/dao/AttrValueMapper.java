package com.product.dao;

import com.product.model.AttrValue;

public interface AttrValueMapper {
    int deleteByPrimaryKey(Integer attrValueId);

    int insert(AttrValue record);

    int insertSelective(AttrValue record);

    AttrValue selectByPrimaryKey(Integer attrValueId);

    int updateByPrimaryKeySelective(AttrValue record);

    int updateByPrimaryKey(AttrValue record);
}