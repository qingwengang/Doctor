package com.ganggang.chapter2.test;

import com.ganggang.chapter2.model.Customer;
import com.ganggang.chapter2.service.CustomerService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

/**
 * Created by wgqing on 2015/11/10.
 */
public class CustomerServiceTest {
    private final CustomerService customerService;
    public CustomerServiceTest(){
        customerService=new CustomerService();
    }
    @Before
    public void init(){

    }
    @Test
    public void getCustomerListTest() throws Exception{
        List<Customer> customerList=customerService.getCustomerList();
        Assert.assertEquals(2,customerList.size());
    }

}
