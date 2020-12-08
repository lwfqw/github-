package com.xxxx.crm.exceptions;

import com.xxxx.crm.base.ResultInfo;
import freemarker.core.InvalidReferenceException;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

/**
 * @author lwf
 * @title: MyHandlerException
 * @projectName crm
 * @description: TODO
 * @date 2020/12/219:59
 */
@ControllerAdvice
public class MyHandlerException{
    @ExceptionHandler(SqlFoundOrNo.class)
    @ResponseBody
    public ResultInfo sql(SqlFoundOrNo e){
        ResultInfo info = new ResultInfo();
        info.setCode(300);
        info.setMsg(e.getMessage());
        return info;
    }

    @ExceptionHandler(ParamsException.class)
    @ResponseBody
    public ResultInfo parm(ParamsException e){
        ResultInfo info=new ResultInfo();
        info.setCode(300);
        info.setMsg(e.getMsg());
        return info;
    }
    @ExceptionHandler(NoLoginException.class)
    public String login(HttpServletRequest request){
        request.setAttribute("ctx", request.getContextPath());
        return "index";
    }
    @ExceptionHandler(BindException.class)
    @ResponseBody
    public ResultInfo bind(BindException e){
        ResultInfo info=new ResultInfo();
        info.setCode(300);
        info.setMsg(e.getBindingResult().getFieldError().getDefaultMessage());
        return info;
    }
    @ExceptionHandler(InvalidReferenceException.class)
    public ModelAndView error(InvalidReferenceException e){
        ModelAndView mv=new ModelAndView();
        mv.addObject("code", 500);
        mv.addObject("msg","错误的参数，后台fremark解析失败");
        mv.setViewName("error");
        return mv;
    }

    @ExceptionHandler(Exception.class)
    public ModelAndView e(Exception e){
        ModelAndView mv=new ModelAndView();
        mv.addObject("code", 500);
        mv.addObject("msg","错误的参数，后台fremark解析失败");
        mv.setViewName("error");
        return mv;
    }
}
