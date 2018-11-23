package com.credit.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by lilei on 2017/5/3.
 */
@ConfigurationProperties(prefix = "wechat")
@Component
public class SecurityUrlProperties {


    private List<String> securityUrl;

    public List<String> getSecurityUrl() {
        return securityUrl;
    }

    public void setSecurityUrl(List<String> securityUrl) {
        this.securityUrl = securityUrl;
    }
}
