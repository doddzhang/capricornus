package com.doddzhang.eurekaconsumer.controller;

import com.doddzhang.eurekaconsumer.client.DcClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * @program: capricornus
 * @description:
 * @author: zhangdong
 * @create: 2018-04-12 11:30
 **/
@RestController
@RefreshScope
public class DcController {

    @Autowired
    LoadBalancerClient loadBalancerClient;
    @Autowired
    RestTemplate restTemplate;
    @Autowired
    DcClient dcClient;

    @Value("${mydata}")
    String mydata;

    @GetMapping("/mydata")
    public String mydata() {
        return mydata;
    }

    @GetMapping("/consumer")
    public String dc() {
        ServiceInstance serviceInstance = loadBalancerClient.choose("eureka-client");
        String url = "http://" + serviceInstance.getHost() + ":" + serviceInstance.getPort() + "/dc";
        System.out.println(url);
        return restTemplate.getForObject(url, String.class);
    }

    @GetMapping("/dcClient")
    public String dcClient() {
        return dcClient.consumer();
    }
}
