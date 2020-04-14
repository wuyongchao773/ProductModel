package com.product.dao;

import java.util.List;

import com.product.model.MakeLog;

public interface MakeLogMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(MakeLog record);

    int insertSelective(MakeLog record);

    MakeLog selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(MakeLog record);

    int updateByPrimaryKey(MakeLog record);
    
    List<MakeLog> selectAll(MakeLog makeLog);
    
}