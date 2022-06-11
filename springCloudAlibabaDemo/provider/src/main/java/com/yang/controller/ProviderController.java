package com.yang.controller;

import com.yang.entity.Order;
import com.yang.service.ProviderService;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
public class ProviderController {

    @Autowired
    ProviderService providerService;

    @Value("${server.port}")
    private String port;

    @GetMapping("/index")
    public String index() {
        return this.port;
    }

    @GetMapping("/list")
    public String list() {
        return "list";
    }

    @GetMapping("/test1")
    public String test1() {
        this.providerService.test();
        return "test1";
    }

    @GetMapping("/test2")
    public String test2() {
        this.providerService.test();
        return "test2";
    }


    @Autowired
    private RocketMQTemplate rocketMQTemplate;

    @GetMapping("/create")
    public Order create() {
        Order order = new Order(1, "张三", "123123", "软件园", new Date());
        this.rocketMQTemplate.convertAndSend("myTopic", order);
        return order;
    }
}
