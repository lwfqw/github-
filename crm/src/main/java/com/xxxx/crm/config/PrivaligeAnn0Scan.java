package com.xxxx.crm.config;

import com.xxxx.crm.exceptions.NoLoginException;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author lwf
 * @title: PrivaligeAnn0Scan
 * @projectName crm
 * @description: 权限检查
 * @date 2020/12/519:20
 */
@Aspect
@Component
public class PrivaligeAnn0Scan {
    @Resource
    HttpServletRequest request;
    @Around(value = "@annotation(com.xxxx.crm.config.Privalige)")
    public Object around(ProceedingJoinPoint pjp) throws Throwable {
        List<String> permissions = (List<String>) request.getAttribute("privalige");
        if(null == permissions || permissions.size()==0){
            throw new NoLoginException();
        }
        Object result =null;
        MethodSignature methodSignature = (MethodSignature) pjp.getSignature();
        Privalige requirePermission =
                methodSignature.getMethod().getDeclaredAnnotation(Privalige.class);
        if(!(permissions.contains(requirePermission.value()))){
            throw new NoLoginException();
        }
        result= pjp.proceed();
        return result;
    }

}
