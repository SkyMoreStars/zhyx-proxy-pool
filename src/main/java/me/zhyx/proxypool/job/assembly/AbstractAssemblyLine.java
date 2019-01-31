package me.zhyx.proxypool.job.assembly;

import lombok.extern.slf4j.Slf4j;
import me.zhyx.proxypool.entity.ProxyIp;
import me.zhyx.proxypool.entity.WebPage;
import me.zhyx.proxypool.job.scheduler.AbStractSchedulerJob;
import me.zhyx.proxypool.utils.HttpClientUtils;
import org.jsoup.Jsoup;
import org.springframework.http.HttpMethod;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * @Auther: yssq
 * @Date: 2019/1/30 15:11
 * @Description:
 */
@Slf4j
public abstract class AbstractAssemblyLine extends AbStractSchedulerJob implements AssemblyLine,Runnable {
    protected ConcurrentLinkedQueue<ProxyIp> proxyIpQueue;
    protected String pageUrl;
    protected WebPage webPage;
    protected HttpMethod httpMethd= HttpMethod.GET;
    protected Map<String,String> formParamMap;
    private Map<String, String> headerMap = new HashMap<String, String>() {{
        put("Connection", "keep-alive");
        put("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/68.0.3440.106 Safari/537.36");
        put("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
        put("Accept-Encoding", "gzip, deflate, sdch");
        put("Accept-Language", "zh-CN,zh;q=0.9");
        put("Redis-Control", "max-age=0");
        put("Upgrade-Insecure-Requests", "1");
    }};

    public AbstractAssemblyLine(ConcurrentLinkedQueue<ProxyIp> proxyIpQueue, String pageUrl) {
        this.proxyIpQueue = proxyIpQueue;
        this.pageUrl = pageUrl;
    }
    public AbstractAssemblyLine(ConcurrentLinkedQueue<ProxyIp> proxyIpQueue, String pageUrl,HttpMethod httpMethd,Map<String,String> formParamMap) {
        this.proxyIpQueue = proxyIpQueue;
        this.pageUrl = pageUrl;
        this.httpMethd=httpMethd;
        this.formParamMap=formParamMap;
    }

    @Override
    public void run() {
        try {
            getPage();
            parsePage(webPage);
        }catch (Exception e){
            log.error("{} page process error",pageUrl,e);
        }
    }

    @Override
    public WebPage getPage() {
        WebPage webPage = null;
        try {
            log.debug("start get page:{}", pageUrl);
            headerMap.put("Referer", pageUrl);
            String pageContent="";
            if(httpMethd==HttpMethod.GET){
                pageContent= HttpClientUtils.sendGet(pageUrl, headerMap);
            }else if(httpMethd==HttpMethod.POST){
                pageContent= HttpClientUtils.sendPostForm(pageUrl, "",headerMap,formParamMap);
            }
            webPage = new WebPage();
            webPage.setCrawlTime(new Date());
            webPage.setPage(pageContent);
            webPage.setDocument(Jsoup.parse(pageContent));
            webPage.setHtml(Jsoup.parse(pageContent).html());
            this.webPage = webPage;
            log.debug("end get page:{}", pageUrl);
        } catch (Exception e) {
            log.error("get page:{}", pageUrl, e);
        }
        return webPage;
    }



    public String getPageUrl() {
        return pageUrl;
    }

    public void setPageUrl(String pageUrl) {
        this.pageUrl = pageUrl;
    }
}
