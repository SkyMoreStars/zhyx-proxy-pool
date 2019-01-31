package me.zhyx.proxypool.job.execute.impl;


import me.zhyx.proxypool.job.scheduler.AbStractSchedulerJob;
import me.zhyx.proxypool.job.execute.ISchedulerJobExecutor;

import java.util.concurrent.TimeUnit;

/**
 * @Auther: yssq
 * @Date: 2019/1/30 14:36
 * @Description:
 */
public class SchedulerJobExecutor implements ISchedulerJobExecutor {
    public SchedulerJobExecutor(int corePool, String threadFactory) {
    }


    @Override
    public void execute(AbStractSchedulerJob abStractSchedulerJob, long delayTime, long intervalTime, TimeUnit timeUnit) {

    }

    @Override
    public void shutdown() {

    }
}
