package com.ganggang.chapter2.service;

import com.ganggang.chapter2.model.Customer;
import com.ganggang.chapter2.util.DatabaseHelper;
import com.ganggang.chapter2.util.PropsUtil;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * Created by wgqing on 2015/11/10.
 */
public class CustomerService {

    public List<Customer> getCustomerList(){
            String sql="select * from customer";
            return DatabaseHelper.queryEntityList(Customer.class,sql,null);
        }
    public List<Customer> getCustomerList(String keyWord){
        return null;
    }
    public Customer getCustomer(long id){
        return  null;
    }
    public boolean createCustomer(Map<String,Object> fileMap){
        return false;
    }
    public boolean updateCustomer(long id,Map<String,Object> filedMap){
        return false;
    }
    public boolean deleteCustomer(long id){
        return  false;
    }
}
