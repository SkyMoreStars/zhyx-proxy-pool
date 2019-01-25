package me.zhyx.proxypool.controller;

import lombok.extern.slf4j.Slf4j;
import me.zhyx.proxypool.service.IProxyIpRedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Auther: yssq
 * @Date: 2019/1/25 13:37
 * @Description:
 */
@Slf4j
@RestController
public class ProxyController extends BaseController{
    @Autowired
    IProxyIpRedisService iProxyIpRedisService;
    @GetMapping("/")
    public String index(ModelMap modelMap){
        List proxyIpList = iProxyIpRedisService.findAllByPageRt(0,20);
        modelMap.put("proxyIpList",proxyIpList);
        return "index";
    }
}
