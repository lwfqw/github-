package com.lwf.first.service;

import com.lwf.first.dao.UserDao;
import com.lwf.first.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;

/**
 * @author lwf
 * @title: UserService
 * @projectName spring_cloud_first
 * @description: TODO
 * @date 2020/12/917:45
 */
@Service
public class MyUserService {
@Resource
private UserDao userDao;
public List<User> list(){return userDao.queryAll();}
}
