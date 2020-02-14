package com.cqyc.zuul.filter;

import com.cqyc.zuul.comm.CommEntity;
import com.cqyc.zuul.comm.prop.PassUrlProperties;
import com.cqyc.zuul.comm.util.jwt.JwtUtils;
import com.netflix.discovery.converters.Auto;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import sun.misc.BASE64Decoder;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

/**
 * @author: cqyc
 * Description:
 * Created by cqyc on 19-10-13
 */
@Component
@Slf4j
@EnableConfigurationProperties(PassUrlProperties.class)
public class MyFilter extends ZuulFilter {

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Autowired
    private PassUrlProperties urlProp;

    @Override
    public String filterType() {
        return "pre";
    }

    @Override
    public int filterOrder() {
        return 0;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() throws ZuulException {
        RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletRequest request = ctx.getRequest();
        ctx.getResponse().setContentType("application/json;charset=utf-8");
        log.info(String.format("%s >>> %s", request.getMethod(), request.getRequestURL().toString()));
        String accessToken = request.getHeader("Authorization");
        String usernameKey = request.getHeader("username");

//        log.info("响应之后得到的状态码－－－>{}", ctx.getResponse().getStatus());
        //得到当前的url是否放行
        boolean isPass = passUrl(request);
        if (isPass) {
            return null;
        }
        Boolean isToken = redisTemplate.hasKey(usernameKey + ":token");
        if (isToken != null && isToken) {
            String redisToken = redisTemplate.opsForValue().get(usernameKey + ":token");
            String resToken = "Bearer " + redisToken;
            if (StringUtils.equals(resToken, accessToken) && redisToken != null) {
                //设置token过期时间为30分钟
                redisTemplate.opsForValue().set(usernameKey + ":token", redisToken, 1800, TimeUnit.SECONDS);
                return null;
            }
        }
        ctx.setSendZuulResponse(false);
        ctx.setResponseStatusCode(401);
        ctx.setResponseBody("用户身份已过期,请重新登录");
        return false;
//        if (StringUtils.isBlank(accessToken)) {
//            //判断该请求是否被放行
//            boolean isPass = passUrl(request);
//            if (isPass) {
//                return null;
//            }
//            log.warn("token is empty,so nobady is login, please login again");
//            ctx.setSendZuulResponse(false);
//            ctx.setResponseStatusCode(401);
//            ctx.setResponseBody("您还没有登录,请登录后获得访问权限");
//            return null;
//        }
//        boolean isPass = passUrl(request);
//        if (isPass) {
//            return null;
//        }
//        //jwt校验token
//        boolean verify = JwtUtils.verify(accessToken);
//
//        if (!verify) {
//            String newToken = refreshToken(usernameKey, accessToken);
//            if (StringUtils.isNotBlank(newToken)) {
//                ctx.setSendZuulResponse(false);
//                ctx.setResponseStatusCode(610);
//                ctx.setResponseBody(newToken);
//                return null;
//            }
//            ctx.setSendZuulResponse(false);
//            ctx.setResponseStatusCode(402);
//            ctx.setResponseBody("用户身份已过期,请重新登录");
//            return null;
//        }
//
//        return null;
    }

    protected Boolean passUrl(HttpServletRequest request) {
        //对配置文件的路径进行放行
        String res = "/" + StringUtils.substringAfter(StringUtils.substringAfter(request.getRequestURI(), "/"), "/");
        for (int i = 0; i < urlProp.getUrls().size(); i++) {
            //如果访问的请求包含
            if (StringUtils.contains(res, urlProp.getUrls().get(i))) {
                log.info("该请求已经需要被放行---> {}--->{}", request.getRequestURI(), urlProp.getUrls().get(i));
                return true;
            }
        }
        return false;
    }


//    protected String refreshToken(String usernameKey, String accessToken) {
//        Boolean isKey = redisTemplate.hasKey(usernameKey);
//        if (isKey) {
//            Long expire = redisTemplate.getExpire(usernameKey);
//            if (expire > 0) {
//                String resToken = redisTemplate.opsForValue().get(usernameKey);
//                log.info("两次比较结果为--->{}", StringUtils.equals(resToken, accessToken));
//                if (StringUtils.equals(resToken, accessToken)) {
//                    String newToken = JwtUtils.generateToken(usernameKey);
//                    redisTemplate.opsForValue().set(usernameKey, newToken, JwtUtils.REFRESH_TOKEN_EXPIRE_TIME, TimeUnit.SECONDS);
//                    return newToken;
//                }
//            }
//        }
//        return null;
//    }
}
