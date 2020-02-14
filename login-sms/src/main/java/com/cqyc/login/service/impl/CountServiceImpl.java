package com.cqyc.login.service.impl;

import com.cqyc.login.domain.CommEntity;
import com.cqyc.login.mapper.UserMapper;
import com.cqyc.login.service.CountService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

/**
 * @author: cqyc
 * Description:
 * Created by cqyc on 20-1-21
 */
@Service
@Slf4j
public class CountServiceImpl implements CountService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public CommEntity countRegister(LocalDate localMonth, LocalDate beforeMonth) {
        List<Map> counts = userMapper.countRegister(localMonth.toString(), beforeMonth.toString());
        log.info("{}", counts);
        return CommEntity.create(counts, 200);
    }
}
