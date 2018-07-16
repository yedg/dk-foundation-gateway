package com.dk.foundation.gateway;

import com.dk.foundation.gateway.event.RefreshRouteService;
import com.dk.foundation.gateway.filter.GrayscaleFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.cloud.netflix.zuul.web.ZuulHandlerMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@EnableZuulProxy
@EnableDiscoveryClient
@SpringCloudApplication
@RestController
public class Startup {
    public static void main(String[] args) throws Exception {
        SpringApplication.run(Startup.class, args);
    }

    @Autowired
    RefreshRouteService refreshRouteService;

    @Autowired
    ZuulHandlerMapping zuulHandlerMapping;

    @RequestMapping("/refreshRoute")
    public String refreshRoute(){
        refreshRouteService.refreshRoute();
        return "refreshRoute";
    }

    @RequestMapping("/watchNowRoute")
    public String watchNowRoute(){
        //可以用debug模式看里面具体是什么
        Map<String, Object> handlerMap = zuulHandlerMapping.getHandlerMap();
        return "watchNowRoute";
    }

//    @Bean
//    public GrayscaleFilter grayscaleFilter()
//    {
//        return new GrayscaleFilter();
//    }
}
