package com.abocidee.config;

import org.springframework.web.cors.CorsConfiguration;

//@Configuration
public class CrossDomainConfig {
    private CorsConfiguration buildConfig() {
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.addAllowedOrigin("*"); // 允许任何域名使用
        corsConfiguration.addAllowedHeader("*"); // 允许任何头
        corsConfiguration.addAllowedMethod("*"); // 允许任何方法
        corsConfiguration.setAllowCredentials(true);
        return corsConfiguration;
    }
}
