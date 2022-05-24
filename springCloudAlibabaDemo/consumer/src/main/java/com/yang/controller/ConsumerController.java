package com.yang.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@RestController
@Slf4j
public class ConsumerController {

    // 不能自动装载
    private final RestTemplate restTemplate;
    // 自动装载 可以直接用
    private final DiscoveryClient discoveryClient;

    public ConsumerController(DiscoveryClient discoveryClient, RestTemplate restTemplate) {
        this.discoveryClient = discoveryClient;
        this.restTemplate = restTemplate;
    }

    @GetMapping("/instances")
    public List<ServiceInstance> serviceInstances() {
        return this.discoveryClient.getInstances("provider");
    }

    // @GetMapping("/index")
    // public String index() {
    //     //调用provider服务的index方法
    //     List<ServiceInstance> instancesList = this.discoveryClient.getInstances("provider");
    //     int index = ThreadLocalRandom.current().nextInt(instancesList.size());
    //     ServiceInstance serviceInstance = instancesList.get(index);
    //     String url = serviceInstance.getUri() + "/index";
    //     String response = this.restTemplate.getForObject(url, String.class);
    //     log.info("调用的端口是:{}", serviceInstance.getPort());
    //
    //     return response;
    // }

    @GetMapping("/index")
    public String index() {
        return this.restTemplate.getForObject("http://provider/index", String.class);
    }
}
