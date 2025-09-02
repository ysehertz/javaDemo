package com.fuchen.CreateThread;

import lombok.extern.slf4j.Slf4j;

/**
 * ClassName: newThread1
 * Package: com.fuchen.CreateThread
 * Description:
 *
 * @author fuchen
 * @version 1.0
 * @createTime 2025/9/2
 */
@Slf4j
public class newThreadByThread {
    public static void main(String[] args) {
        Thread t1 = new Thread(() -> {
            log.debug("running");
        },"线程1");
        t1.start();

        Thread t2 = new MThread();
        // 设置线程名
        t2.setName("线程2");
        t2.start();
    }
}
@Slf4j
class MThread extends Thread{
    @Override
    public void run() {
        log.debug("running");
    }
}
