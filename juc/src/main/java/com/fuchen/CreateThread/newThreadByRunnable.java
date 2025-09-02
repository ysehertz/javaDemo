package com.fuchen.CreateThread;

import lombok.extern.slf4j.Slf4j;

/**
 * ClassName: newThreadByRunnable
 * Package: com.fuchen.CreateThread
 * Description:
 *
 * @author fuchen
 * @version 1.0
 * @createTime 2025/9/2
 */
@Slf4j
public class newThreadByRunnable {
    public static void main(String[] args) {
        Runnable runnable = new RunnableDemo();
        Thread thread = new Thread(runnable,"Runnable实现的线程");
        thread.start();
    }
}

@Slf4j
class RunnableDemo implements Runnable {

    @Override
    public void run() {
        log.info("RunnableDemo run...");
    }
}
