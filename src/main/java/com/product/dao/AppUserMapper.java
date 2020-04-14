package com.product.dao;

import java.util.List;

import com.product.model.AppUser;

public interface AppUserMapper {
    int deleteByPrimaryKey(String id);

    int insert(AppUser record);

    int insertSelective(AppUser record);

    AppUser selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(AppUser record);

    int updateByPrimaryKey(AppUser record);
    
    List<AppUser> findAll(AppUser appUser);
    
    void batchAddBlack(List<String> list);
    
    void updateBlack(List<String> list);
    
}