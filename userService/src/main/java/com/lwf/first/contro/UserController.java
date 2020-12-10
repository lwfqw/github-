package com.lwf.first.contro;

import com.lwf.first.entity.User;
import com.lwf.first.service.MyUserService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author lwf
 * @title: UserController
 * @projectName spring_cloud_first
 * @description: TODO
 * @date 2020/12/919:47
 */
@RestController
public class UserController {
    @Resource
    private MyUserService myUserService;
    @RequestMapping("/")
    public List<User> list(){return myUserService.list();}
}
