package me.zhyx.proxypool.thread;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Auther: yssq
 * @Date: 2019/1/30 14:19
 * @Description:
 */
public class ThreadFactory implements java.util.concurrent.ThreadFactory {
    private AtomicInteger counter=new AtomicInteger(0);
    private String name ;

    public ThreadFactory(String name) {
        this.name = name;
    }

    @Override
    public Thread newThread(Runnable run) {
        Thread t = new Thread(run, name + "-t-" + counter);
        counter.incrementAndGet();
        return t;
    }
}
