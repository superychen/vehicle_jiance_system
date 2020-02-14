package com.cqyc.auth.config;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.cqyc.auth.domain.User;
import com.cqyc.auth.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

/**
 * @author: cqyc
 * Description:
 * Created by cqyc on 20-1-13
 */
@Service
public class MyUserDetailServiceImpl implements UserDetailsService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userMapper.selectUserRoleAndPer(username);
        if (user == null) {
            throw new UsernameNotFoundException(username);
        }

        Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
        //可用性
        boolean enabled = true;
        //过期性
        boolean accountNotExpired = true;
        //有效性
        boolean credentialsNonExpired = true;
        //锁定性
        boolean accountNonLocked = true;
        user.getRoles().forEach(role -> {
            //角色必须是ROLE_开头，可以在数据库设置
            SimpleGrantedAuthority grantedAuthority = new SimpleGrantedAuthority(role.getRname());
            grantedAuthorities.add(grantedAuthority);
            //获取权限
            role.getPermissions().forEach(permission -> {
                SimpleGrantedAuthority grantedAuthorityPer = new SimpleGrantedAuthority(permission.getName());
                grantedAuthorities.add(grantedAuthorityPer);
            });
        });
        org.springframework.security.core.userdetails.User securityUser = new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(),
                enabled, accountNotExpired, credentialsNonExpired, accountNonLocked, grantedAuthorities);
        return securityUser;
    }
}
