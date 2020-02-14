package com.cqyc.login.domain;

import lombok.Data;

/**
 * @author: cqyc
 * Description:
 * Created by cqyc on 20-1-14
 */
@Data
public class JWT {
    private String access_token;
    private String token_type;
    private String refresh_token;
    private int expires_in;
    private String scope;
    private String jti;
}
