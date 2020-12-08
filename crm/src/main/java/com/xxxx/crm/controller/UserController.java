package com.xxxx.crm.controller;

import com.alibaba.fastjson.JSON;
import com.xxxx.crm.base.BaseController;
import com.xxxx.crm.base.ResultInfo;
import com.xxxx.crm.config.Privalige;
import com.xxxx.crm.query.UserQuery;
import com.xxxx.crm.model.UserModel;
import com.xxxx.crm.service.UserService;
import com.xxxx.crm.utils.LoginUserUtil;
import com.xxxx.crm.query.LoginBean;
import com.xxxx.crm.vo.TUser;
import com.xxxx.crm.vo.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/user")
public class UserController extends BaseController {

    @Resource
    private UserService userService;
    @PostMapping("/login")
    @ResponseBody
    public ResultInfo userLogin(@Valid  LoginBean lb){
        System.out.println(lb);
        ResultInfo resultInfo = new ResultInfo();
        UserModel model = userService.userLogin(lb);
        resultInfo.setResult(model);
        return resultInfo;
    }

    //密码修改
    @PostMapping("/update")
    @ResponseBody
    public ResultInfo updateUserPwd(HttpServletRequest request, String oldPassword, String newPassword, String confirmPassword){
        ResultInfo resultInfo = new ResultInfo();
        //获取id
        int userId = LoginUserUtil.releaseUserIdFromCookie(request);
        //调用service
        userService.updateUserPwd(userId,oldPassword,newPassword,confirmPassword);
        return resultInfo;
    }

     //密码修改页面
    @RequestMapping("toPasswordPage")
    public String toPasswordPage(){
        return "user/password";
    }
    //去setting页面
    @GetMapping("toSettingPage")
    public String toSetting(HttpServletRequest request, Model model){
        //获取id
        int userId = LoginUserUtil.releaseUserIdFromCookie(request);
        if(userId==0){
            return "index";
        }
        User user = userService.selectByPrimaryKey(userId);
       model.addAttribute("user", user);
       return "setting";
    }
    /**
     * 查询所有的销售人员
     * @return
     */
    @RequestMapping("queryAllSales")
    @ResponseBody
    public List<Map<String, Object>> queryAllSales() {
        List<Map<String, Object>> res=userService.queryAllSales();
        return res;
    }

    /**
     * 去用户首页
     * @return
     */
    @Privalige("6010")
    @RequestMapping("index")
    public String toList(){return "user/user";}
    /**
     * 用户列表
     * @param query 查询数据
     * @return
     */

    @RequestMapping("/list")
    @ResponseBody
    public Map<String, Object> queryAllUser(UserQuery query){
        System.out.println(JSON.toJSON(userService.queryAllUser(query)));
        return userService.queryAllUser(query);
    }
    /**
     * 打开用户添加或更新对话框
     */
    @RequestMapping("addOrUpdateUserPage")
    public String goAddUpdate(Integer id,Model model){
        if(id!=null){
          //修改视图携带数据
           model.addAttribute("user", userService.selectByPrimaryKey(id));
        }
        return "user/add_update";
    }
    //保存
    @RequestMapping("save")
    @ResponseBody
    public ResultInfo save(TUser user,String roleIds){
        return userService.save(user,roleIds);
    }
    //修改
    @RequestMapping("updateAll")
    @ResponseBody
    public ResultInfo update(TUser user,String roleIds){
        return userService.update(user,roleIds);
    }
    //离职
    @RequestMapping("leaf")
    @ResponseBody
    public ResultInfo leaf(Integer[] id){
        return userService.deleteButHave(id);
    }
}
