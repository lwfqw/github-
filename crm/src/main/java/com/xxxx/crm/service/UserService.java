package com.xxxx.crm.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xxxx.crm.base.BaseService;
import com.xxxx.crm.base.ResultInfo;
import com.xxxx.crm.dao.TUserDao;
import com.xxxx.crm.dao.UserMapper;
import com.xxxx.crm.query.UserQuery;
import com.xxxx.crm.exceptions.SqlFoundOrNo;
import com.xxxx.crm.model.UserModel;
import com.xxxx.crm.utils.AssertUtil;
import com.xxxx.crm.utils.Md5Util;
import com.xxxx.crm.utils.PhoneUtil;
import com.xxxx.crm.utils.UserIDBase64;
import com.xxxx.crm.query.LoginBean;
import com.xxxx.crm.vo.TUser;
import com.xxxx.crm.vo.User;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserService extends BaseService<User,Integer> {

    @Resource
    private UserMapper userMapper;//后面使用了easyCode生成dao层代码就这样了，也懒得改了
     @Resource
     private TUserDao tUserDao;
     @Resource
     private UserRoleService userRoleService;
    /**
     * 登录校验
         1.校验前台传来的参数（用户名和密码）
            如果为空，抛出异常
         2.调用dao层 用户名查询数据库，判断账号是否存在
             如果为空，抛出异常，账号不存在
         3.用前台接收来的密码和数据库中的密码对比（将前台密码加密，再比对）
            如果不一致，抛出异常，密码错误
         4.登录成功，返回数据 ResultInfo(封装了UserModel对象，前台需要存入cookie的用户数据)
     */
    public UserModel userLogin(LoginBean bean){
        User user = userMapper.selectUserByName(bean.getUserName());
        if(user==null) {
            throw new SqlFoundOrNo(300, "用户不存在");
        }
        if(!Md5Util.encode(bean.getUserPwd()).equals(user.getUserPwd())){
            throw new SqlFoundOrNo(300, "密码错误");
        }
        return buildUserModel(user);
    }

    /**
     * 封装了UserModel对象，前台需要存入cookie的用户数据
     * @param user
     */
    private UserModel buildUserModel(User user) {
        UserModel userModel = new UserModel();
        //将id加密
        String userIdStr = UserIDBase64.encoderUserID(user.getId());
        userModel.setUserIdStr(userIdStr);
        userModel.setTrueName(user.getTrueName());
        userModel.setUserName(user.getUserName());
        return userModel;
    }

    /**修改用户密码
     */
    @Transactional
    public void updateUserPwd(Integer userId, String oldPassword, String newPassword,String confirmPassword){
        //通过id查询账户
        User user = userMapper.selectByPrimaryKey(userId);
        //id查询判断用户存在
       // AssertUtil.isTrue(user == null,"用户未登录");
        if(user==null){
            throw new SqlFoundOrNo(300, "用户不存在");
        }
        //校验数据参数
        checkUserUpdateParams(oldPassword,newPassword,confirmPassword,user.getUserPwd());
        //设置新密码
        AssertUtil.isTrue(StringUtils.isBlank(newPassword), "密码空");
        user.setUserPwd(Md5Util.encode(newPassword));
        user.setUpdateDate(new Date());

        //执行修改操作，返回结果信息ResultInfo
        AssertUtil.isTrue(userMapper.updateByPrimaryKeySelective(user) < 1,"修改密码失败");
    }

    /**
     * 校验修改密码的数据
             1.获取cookie中id  非空   id查询判断用户存在
             2.原始密码 非空   与数据库中密码保持一致
             3.新密码   非空   新密码与原始密码不能一致
             4.确认密码 非空   确认密码与新密码一致
             5.执行修改操作，返回结果信息ResultInfo
     * @param oldPassword  用户输入原始密码
     * @param newPassword  新密码
     * @param confirmPassword  确认密码
     * @param dbPassword    数据库中密码
     */
    private void checkUserUpdateParams(String oldPassword, String newPassword, String confirmPassword,String dbPassword) {
        // 2.原始密码 非空   与数据库中密码保持一致
        AssertUtil.isTrue(StringUtils.isBlank(oldPassword),"原始密码不能为空");
        AssertUtil.isTrue(!dbPassword.equals(Md5Util.encode(oldPassword)),"原始密码错误");

        // 3.新密码   非空   新密码与原始密码不能一致
        AssertUtil.isTrue(StringUtils.isBlank(newPassword),"新密码不能为空");
        AssertUtil.isTrue(newPassword.equals(oldPassword),"新密码与原始密码不能一致");

        //确认密码 非空   确认密码与新密码一致
        AssertUtil.isTrue(StringUtils.isBlank(confirmPassword),"确认密码不能为空");
        AssertUtil.isTrue(!confirmPassword.equals(newPassword),"新密码与确认密码不一致");
    }

    /**
     * 查询所有的销售人员
     * @return
     */

    public List<Map<String, Object>> queryAllSales() {
        return userMapper.queryAllSales();
    }

    /**
     * 用户列表
     * @param query 查询数据
     * @return
     */
    public Map<String, Object> queryAllUser(UserQuery query){
        Map<String, Object> map=new HashMap<>();
        map.put("code", 0);
        map.put("msg", "success");
        PageHelper.startPage(query.getPage(), query.getLimit());
        PageInfo<TUser> info=new PageInfo<>(tUserDao.queryParams(query));
        map.put("data", info.getList());
        return map;
    }
    /**
     * 添加用户
     * 1. 参数校验
     * 用户名 非空           唯一性
     * 邮箱 非空
     * 手机号 非空            格式合法
     * 密码不为空
     * 2. 设置默认参数
     * isValid 1
     * creteDate 当前时间
     * updateDate 当前时间
     * userPwd 123456 -> md5加密
     * 3. 执行添加，判断结果
     */
    @Transactional
    public ResultInfo save(@Valid TUser user,String roleIds){
        AssertUtil.isTrue(user.getId()!=null, "id不需要");
        //找到
        if(null!=tUserDao.queryByName(user.getUserName())){
            throw new SqlFoundOrNo(300, "该用户名存在");
        }
        AssertUtil.isTrue(!PhoneUtil.isMobile(user.getPhone()), "号码格式错误");
        AssertUtil.isTrue(StringUtils.isBlank(user.getUserPwd()), "输入密码");
        user.setIsValid(1);
        user.setCreateDate(new Date());
        user.setUpdateDate(new Date());
        user.setUserPwd(Md5Util.encode(user.getUserPwd()));
        if(tUserDao.insert(user)<1){
            throw new SqlFoundOrNo(300, "用户添加失败，貌似后台出小问题了，正在紧急抢救中");
        }
        userRoleService.delete(user.getId());
        userRoleService.inserts(user.getId(), roleIds);
        return new ResultInfo();
    }
    /**
     * 更新用户
     * 1. 参数校验
     * id 非空 记录必须存在
     * 用户名 非空 唯一性
     * email 非空
     * 手机号 非空 格式合法
     * 2. 设置默认参数
     * updateDate
     * 3. 执行更新，判断结果
     * @param user
     */
      @Transactional
    public ResultInfo update(TUser user,String roleIds){
          AssertUtil.isTrue(user.getId()==null, "id为空");
          AssertUtil.isTrue(tUserDao.queryById(user.getId())==null, "id不存在");
          AssertUtil.isTrue(StringUtils.isBlank(user.getUserName()), "用户名为空");
          //找到
          if(null!=tUserDao.queryByName(user.getUserName())&&tUserDao.queryByName(user.getUserName()).getId()!=user.getId()){
              throw new SqlFoundOrNo(300, "该用户名存在");
          }
          AssertUtil.isTrue(StringUtils.isBlank(user.getEmail()), "email为空");
          AssertUtil.isTrue(StringUtils.isBlank(user.getPhone()), "phone为空");
          AssertUtil.isTrue(!PhoneUtil.isMobile(user.getPhone()), "号码格式错误");
          user.setUpdateDate(new Date());
          if(tUserDao.update(user)<1){
              throw new SqlFoundOrNo(300,"插入异常");
          }
          userRoleService.delete(user.getId());
          userRoleService.inserts(user.getId(), roleIds);
          return new ResultInfo();
      }
      //离职
    @Transactional
    public ResultInfo deleteButHave(Integer[] id){
         AssertUtil.isTrue(id.length<1, "输入需要删除的用户");
         Integer len=tUserDao.updateSome(id);
         if(len<id.length){
             throw new SqlFoundOrNo(300, "删除数据失败，以删除："+len);
         }
        ResultInfo info = new ResultInfo();
         info.setMsg("操作成功，已删除："+len);
        return info;
    }
}
