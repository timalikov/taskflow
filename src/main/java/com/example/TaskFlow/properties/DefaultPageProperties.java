package com.example.TaskFlow.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "app.default")
@Getter
@Setter
public class DefaultPageProperties {
    private int pageSize;
    private int startPage;
    private String sortField;
}
