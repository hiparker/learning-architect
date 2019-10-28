package com.edwin.practive.springbootmvcframe.core.properties;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Data
@Component
public class Config {


    @Value(value = "${weixin.appID}")
    private String appID;
    @Value(value = "${weixin.appsecret}")
    private String appsecret;
    @Value(value = "${weixin.tokenString}")
    private String tokenString;

}
