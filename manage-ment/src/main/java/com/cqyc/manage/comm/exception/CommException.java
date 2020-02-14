package com.cqyc.manage.comm.exception;

import com.cqyc.manage.domain.CommEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;

/**
 * @author: cqyc
 * Description:
 * Created by cqyc on 19-10-14
 */
@ControllerAdvice
@Slf4j
public class CommException {

    @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
    public ResponseEntity<Object> sqlExceptionHandle(HttpServletRequest request, SQLException e) {
        log.error("********************Throw SqlException.url:{} ERROR:{}********************", request.getRequestURL(), e.getMessage(), e);
        //601为我们自定义的异常状态码，全是sql执行异常
        return ResponseEntity.status(601).body(e.getMessage());
    }

    @ExceptionHandler(CqycExceptions.class)
    @ResponseBody
    public CommEntity handlerException(HttpServletRequest request, CqycExceptions e) {
        //执行这个请求的方法
        String requestURI = request.getRequestURL().toString();
        //获取到自定义异常的枚举类
        ExceptionEnums enums = e.getExceptionEnums();
        log.info("错误信息：" + enums.getCode() + "," + enums.getMsg() + "---出现异常路径: {}", requestURI);
        return CommEntity.create(enums.getMsg(), enums.getCode());
    }

}
