package com.example.TaskFlow.config;

import com.example.TaskFlow.properties.DefaultPageProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
@RequiredArgsConstructor
public class PaginationWebConfig implements WebMvcConfigurer {
    private final DefaultPageProperties defaultPageProperties;

    @Override
    public void addArgumentResolvers (List<HandlerMethodArgumentResolver> resolvers) {
        PageableHandlerMethodArgumentResolver pageableResolver = new PageableHandlerMethodArgumentResolver();

        pageableResolver.setFallbackPageable(
                PageRequest.of(
                        defaultPageProperties.getStartPage(),
                        defaultPageProperties.getPageSize(),
                        Sort.by(Sort.Direction.DESC, defaultPageProperties.getSortField())
                )
        );

        resolvers.add(pageableResolver);
    }
}
