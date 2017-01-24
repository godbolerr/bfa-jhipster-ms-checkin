package com.bfa.checkinms.config;

import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableFeignClients(basePackages = "com.bfa.checkinms")
public class FeignConfiguration {

}
