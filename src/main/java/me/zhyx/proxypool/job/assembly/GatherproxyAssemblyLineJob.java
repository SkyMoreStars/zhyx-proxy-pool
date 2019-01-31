package me.zhyx.proxypool.job.assembly;

import lombok.extern.slf4j.Slf4j;
import me.zhyx.proxypool.entity.ProxyIp;
import me.zhyx.proxypool.entity.WebPage;
import me.zhyx.proxypool.job.scheduler.AbStractSchedulerJob;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.Date;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * @Auther: yssq
 * @Date: 2019/1/30 15:09
 * @Description:
 */
@Slf4j
public class GatherproxyAssemblyLineJob extends AbstractAssemblyLine {
    public GatherproxyAssemblyLineJob(ConcurrentLinkedQueue<ProxyIp> proxyIpQueue, String pageUrl) {
        super(proxyIpQueue,pageUrl);
    }

    /**
     * 解析请求得到的结果
     * @param webPage
     */
    @Override
    public void parsePage(WebPage webPage) {
        Document document = webPage.getDocument();
        Elements trs = document.select("table").select("tr");
        ProxyIp proxyIp = null;
        for (Element tr : trs) {
            Elements tds = tr.select("td");
            if (tds != null && tds.size() >= 10) {
                proxyIp = new ProxyIp();
                proxyIp.setIp(tds.get(1).text());
                proxyIp.setPort(Integer.valueOf(tds.get(2).text()));
                proxyIp.setType("SOCKS");
                proxyIp.setLocation(tds.get(3).text());
                proxyIp.setCountry(tds.get(0).select("img").select("alt").text());
                proxyIp.setAnonymity(tds.get(4).text());
                proxyIp.setAvailable(true);
                proxyIp.setCreateTime(new Date());
                proxyIp.setLastValidateTime(new Date());
                proxyIp.setValidateCount(0);
                proxyIpQueue.offer(proxyIp);
            }
        }
    }

}
