package com.xxxx.crm.base;


import com.xxxx.crm.service.PermisionService;
import com.xxxx.crm.utils.LoginUserUtil;
import org.springframework.web.bind.annotation.ModelAttribute;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

public class BaseController {
@Resource
private PermisionService permisionService;
    @ModelAttribute
    public void preHandler(HttpServletRequest request){
        request.setAttribute("ctx", request.getContextPath());
        Integer userId= LoginUserUtil.releaseUserIdFromCookie(request);
        request.setAttribute("privalige", permisionService.privaligeByUser(userId));
    }


    public ResultInfo success(){
        return new ResultInfo();
    }

    public ResultInfo success(String msg){
        ResultInfo resultInfo= new ResultInfo();
        resultInfo.setMsg(msg);
        return resultInfo;
    }

    public ResultInfo success(String msg,Object result){
        ResultInfo resultInfo= new ResultInfo();
        resultInfo.setMsg(msg);
        resultInfo.setResult(result);
        return resultInfo;
    }

}
