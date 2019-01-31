package me.zhyx.proxypool.job.assembly;

import me.zhyx.proxypool.entity.WebPage;

/**
 * @Auther: yssq
 * @Date: 2019/1/30 15:13
 * @Description:
 */
public interface AssemblyLine {
    WebPage getPage();

    void parsePage(WebPage webPage);
}
