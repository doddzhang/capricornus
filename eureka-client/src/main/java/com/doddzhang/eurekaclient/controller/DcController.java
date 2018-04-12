package com.doddzhang.eurekaclient.controller;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * @program: capricornus
 * @description:
 * @author: zhangdong
 * @create: 2018-04-12 11:04
 **/
@RestController
@RefreshScope
public class DcController {

    @Autowired
    DiscoveryClient discoveryClient;

    @Value("${mydata}")
    String mydata;

    @GetMapping("/mydata")
    public String mydata() {
        return mydata;
    }

    @GetMapping("/dc")
    @HystrixCommand(fallbackMethod = "fallback")
    public String dc() {
        try {
            Thread.sleep(5000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        String services = "Services: " + discoveryClient.getServices();
        System.out.println(services);
        return services;
    }

    public String fallback() {
        return "fallback";
    }

    @RestController
    public class UploadController {

        @PostMapping(value = "/uploadFile", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
        public String handleFileUpload(@RequestPart(value = "file") MultipartFile file) {
            return file.getName();
        }

    }

}
