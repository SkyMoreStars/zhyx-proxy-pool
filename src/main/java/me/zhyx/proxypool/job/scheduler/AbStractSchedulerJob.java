package me.zhyx.proxypool.job.scheduler;

import me.zhyx.proxypool.entity.ProxyIp;
import me.zhyx.proxypool.thread.ThreadFactory;

import java.util.concurrent.*;

/**
 * @Auther: yssq
 * @Date: 2019/1/30 14:39
 * @Description:
 */
public abstract class AbStractSchedulerJob implements Runnable {
    private volatile transient ExecutorService executorService = Executors.newCachedThreadPool(new ThreadFactory("validate"));

    /**
     * 返回线程执行结果
     * @param callable
     * @return
     */
    public Future<?>execute(Callable<?> callable){
        initInstance();
        return executorService.submit(callable);
    }

    /**
     * 返回线程执行结果
     * @param task
     * @return
     */
    public Future<?> execute(FutureTask<?> task){
        initInstance();
        return executorService.submit(task);
    }
    private void initInstance() {
        if(executorService.isShutdown()){
            synchronized (AbStractSchedulerJob.class){
                if(executorService.isShutdown()){
                    executorService = Executors.newCachedThreadPool(new ThreadFactory("validate"));
                }
            }
        }
    }
    public void shutdown(){
        executorService.shutdown();
    }

    /**
     * 验证ip的可用性
     * @param proxyIp
     * @return
     */
    public boolean validateIp(ProxyIp proxyIp){
        boolean available=false;
        return available;
    }
}
