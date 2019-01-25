package me.zhyx.proxypool.service;

import java.io.Serializable;
import java.util.List;

/**
 * @Auther: zhyx
 * @Date: 2019/1/25 13:47
 * @Description: ip在redis中的相关操作。
 */
public interface IProxyIpRedisService {
    /**
     *
     * @param pageNumber
     * @param pageSize
     * @return
     */
    List<Serializable> findAllByPageRt(int pageNumber, int pageSize);
}
