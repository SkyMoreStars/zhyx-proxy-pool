package me.zhyx.proxypool.job.assembly;

import com.sun.java.browser.net.ProxyService;
import lombok.extern.slf4j.Slf4j;
import me.zhyx.proxypool.entity.ProxyIp;
import me.zhyx.proxypool.job.execute.ISchedulerJobExecutor;
import me.zhyx.proxypool.job.execute.impl.SchedulerJobExecutor;
import me.zhyx.proxypool.service.IProxyIpService;
import me.zhyx.proxypool.thread.ThreadFactory;

import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @Auther: yssq
 * @Date: 2019/1/30 14:14
 * @Description:
 */
@Slf4j
public class AssemblyLineJob implements Runnable {
    private volatile static ExecutorService executorService = Executors.newFixedThreadPool(5, new ThreadFactory("AssemblyLineJob-consumer"));

    private ISchedulerJobExecutor schedulerJobExecutor = new SchedulerJobExecutor(30, "AssemblyLineJob-producer");

    private IProxyIpService proxyIpService;

    @Override
    public void run() {
        try {


            ConcurrentLinkedQueue<ProxyIp> linkedQueue = new ConcurrentLinkedQueue<>();
            //生产者
            schedulerJobExecutor.execute(new GatherproxyAssemblyLineJob(linkedQueue, "https://www.xicidaili.com/nn"), 70, 100, TimeUnit.SECONDS);
            schedulerJobExecutor.execute(new GatherproxyAssemblyLineJob(linkedQueue, "https://www.kuaidaili.com/free/"), 70, 100, TimeUnit.SECONDS);

            //消费者
            for (int i = 0; i < 5; i++) {
                executorService.execute(new Runnable() {
                    @Override
                    public void run() {
                        while (true && !Thread.currentThread().isInterrupted()) {
                            try {
                                log.info("the proxyIpQueue current  size:{}", linkedQueue.size());
                                ProxyIp proxyIp = linkedQueue.poll();
                                if (proxyIp != null) {
                                    log.debug("get proxy ip:{}", proxyIp.toString());
                                    if (proxyIpService.findByIpEqualsAndPortEqualsAndTypeEquals(proxyIp.getIp(), proxyIp.getPort(), proxyIp.getType()) == null) {
                                        proxyIpService.save(proxyIp);
                                    } else {
                                        log.debug("the proxy ip exist:{}", proxyIp.toString());
                                    }
                                } else {
                                    TimeUnit.SECONDS.sleep(3);
                                }
                            } catch (Exception e) {
                                log.error("get the proxy ip  failed! error:{}", e.getMessage());
                                //e.printStackTrace();
                                try {
                                    TimeUnit.SECONDS.sleep(3);
                                } catch (InterruptedException e1) {
                                    e1.printStackTrace();
                                }
                            }
                        }
                    }
                });

            }
        } catch (Exception e) {
            log.error("Assembly Line error:{}", e);
            executorService.shutdown();
            schedulerJobExecutor.shutdown();
        }
    }
}
