package com.chensha.universalsurveysystem.config.interceptor;

import cn.hutool.core.util.StrUtil;
import com.chensha.universalsurveysystem.service.sysUserService;
import com.chensha.universalsurveysystem.vo.ErrorCode;
import com.chensha.universalsurveysystem.vo.Result;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import java.io.IOException;
import java.io.PrintWriter;

public class JwtInterceptor implements HandlerInterceptor {

    @Autowired
    private sysUserService userService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws JsonProcessingException {

        // 如果不是映射到方法直接通过
        if(!(handler instanceof HandlerMethod)){
            return true;
        }

        String token = request.getHeader("Authorization");
        //Token为空
        if (StrUtil.isBlank(token)){
            //throw new AuthServiceException(ErrorCode.NO_PERMISSION.getCode(), ErrorCode.NO_PERMISSION.getMsg());
            ObjectMapper objectMapper = new ObjectMapper();
            String result = objectMapper.writeValueAsString(Result.fail(ErrorCode.NO_PERMISSION.getCode(),ErrorCode.NO_PERMISSION.getMsg()));
            try {
                returnResult(response,result);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return false;

        }
        if (userService.authToken(token)){
            //认证通过
            return true;
        }else {
            //throw new AuthServiceException(ErrorCode.NO_PERMISSION.getCode(), ErrorCode.NO_PERMISSION.getMsg());
            try {
                ObjectMapper objectMapper = new ObjectMapper();
                String result = objectMapper.writeValueAsString(Result.fail(ErrorCode.NO_PERMISSION.getCode(),ErrorCode.NO_PERMISSION.getMsg()));
                returnResult(response,result);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return false;
        }
    }

    /**
     * 返回客户端数据
     */
    private void returnResult(HttpServletResponse response, String result) throws Exception {
        PrintWriter writer = null;
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        try {
            writer = response.getWriter();
            writer.print(result);

        } catch (IOException e) {
        } finally {
            if (writer != null) {
                writer.close();
            }
        }

    }

}
