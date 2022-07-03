package com.newcoder.community.Dao;


import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

@Primary
@Repository
public class AlphaDaoMybatiesImpl implements AlphaDao{
    @Override
    public String Select() {
        return "Mybaties";
    }
}
