package com.newcoder.community.Dao;

import org.springframework.stereotype.Repository;

@Repository("alphaHibernate")
public class AlphaDaoHibernateImpl implements  AlphaDao{
    @Override
    public String Select() {
        return "Hibernate";
    }
}
