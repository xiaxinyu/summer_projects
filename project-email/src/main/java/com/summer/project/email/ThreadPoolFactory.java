package com.summer.project.email;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by xiaxinyu3@crc.com.hk on 2019.4.2
 * Build thread pool with custom requests
 */
@Configuration
public class ThreadPoolFactory {

    @Autowired
    private DevopsProperties devopsProperties;

    @Bean("sonarReportEmailThreadPool")
    public ThreadPoolExecutor sonarReportEmailThreadPool(){
        ThreadPoolProperties p = devopsProperties.getThreadpool();
        return new ThreadPoolExecutor(p.getCorePoolSize(), p.getMaxPoolSize(), p.getKeepLiveTime(), TimeUnit.MILLISECONDS, new LinkedBlockingQueue<>(p.getQueueSize()));
    }
}
