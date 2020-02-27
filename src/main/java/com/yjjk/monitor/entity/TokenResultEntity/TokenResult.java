package com.yjjk.monitor.entity.TokenResultEntity;

import lombok.Data;

/**
 * @program: monitor_pt
 * @description: 获取token
 * @author: CentreS
 * @create: 2020-02-24 10:36:38
 **/
@Data
public class TokenResult {

    private String token;
    private Integer code;
    private String refreshToken;
}
