package me.zhyx.proxypool.job.execute;


import me.zhyx.proxypool.job.scheduler.AbStractSchedulerJob;

import java.util.concurrent.TimeUnit;

/**
 * @Auther: yssq
 * @Date: 2019/1/30 14:33
 * @Description:
 */
public interface ISchedulerJobExecutor {
    /**
     * 执行定时任务
     * @param abStractSchedulerJob
     * @param delayTime
     * @param intervalTime
     * @param timeUnit
     */
    void execute(AbStractSchedulerJob abStractSchedulerJob, long delayTime, long intervalTime, TimeUnit timeUnit);
    void shutdown();
}
