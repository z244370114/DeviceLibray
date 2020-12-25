package com.zy.devicelibrary.utils;


import androidx.annotation.NonNull;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by hailin on 2019/1/9.
 *
 * 线程池
 */

public class PoolExecutor {

//    核心线程数
    private static final int CPU_COUNT = Runtime.getRuntime().availableProcessors();

    private static final int MAX_CORE_POOL_SIZE = CPU_COUNT + 1;

    //存活30秒 回收线程
    private static final long KEEP_ALIVE_TIME = 50L;

   static ThreadFactory sThreadFactory = new ThreadFactory() {

      private AtomicInteger mCount = new AtomicInteger(1);

        @Override
        public Thread newThread(@NonNull Runnable r) {
            return new Thread(r,"YMGJ #" + mCount);
        }
    };

    public static ThreadPoolExecutor getPoolExecutor(){
        return getPoolExecutor(CPU_COUNT);
    }

    public static ThreadPoolExecutor getPoolExecutor(int corePoolSize){
        if (corePoolSize == 0){
            return null;
        }

        corePoolSize = Math.min(corePoolSize,CPU_COUNT);

        ThreadPoolExecutor executor = new ThreadPoolExecutor(corePoolSize,MAX_CORE_POOL_SIZE, KEEP_ALIVE_TIME, TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(64),sThreadFactory);
        executor.allowCoreThreadTimeOut(true);
        return executor;

    }
}