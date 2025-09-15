package com.fuchen;

import java.util.Objects;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * ClassName: MyThreadF
 * Package: com.fuchen
 * Description:
 *
 * @author fuchen
 * @version 1.0
 * @createTime 2025/9/14
 */
public class MyThreadFactory implements ThreadFactory {

    private AtomicInteger index = new AtomicInteger();
    @Override
    public Thread newThread(Runnable r) {
        if(Objects.nonNull(r)){
            Thread thread = new Thread(r);
            thread.setName("线程" + index.incrementAndGet());
            return thread;
        }
        return null;
    }
}
