package com.lwf;



import com.xxxx.crm.service.UserService;

import com.xxxx.crm.query.LoginBean;
import org.junit.Test;

import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

/**
 * Unit test for simple App.
 */
@SpringBootTest
public class AppTest 
{
    @Resource
    private UserService userService;
    /**
     * Rigorous Test :-)
     */
    @Test
    public void shouldAnswerWithTrue(){
   userService.userLogin(new LoginBean());
    }
}
