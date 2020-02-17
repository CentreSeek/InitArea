package com.yjjk.monitor.entity.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "sign")
@PropertySource("classpath:AreaSign.properties")
@Data
public class AreaSign {
    private String areaSign;

}
