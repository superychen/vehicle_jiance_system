package com.cqyc.zuul.comm.prop;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

/**
 * @author: cqyc
 * Description:
 * Created by cqyc on 19-10-27
 */
@ConfigurationProperties(prefix = "zuul.request.pass")
@Data
public class PassUrlProperties {

    private List<String> urls;

}
