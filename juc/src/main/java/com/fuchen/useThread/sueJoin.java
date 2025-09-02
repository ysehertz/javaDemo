package com.fuchen.useThread;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

/**
 * ClassName: sueJoin
 * Package: com.fuchen.useThread
 * Description:
 *
 * @author fuchen
 * @version 1.0
 * @createTime 2025/9/2
 */
@Slf4j
public class sueJoin {
    public static void main(String[] args) {
        Thread t1 = new Thread(() -> {
            log.debug("t1 start");
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            log.debug("t1 end");
        });
        t1.start();
        try {
//            t1.join(1000);
            t1. join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        log.debug("main start");
    }

    //为join方法设置过期时间，等待时间超过1秒则取消等待，继续执行后续代码
    @Test
    public void test1(){
        Thread t1 = new Thread(() -> {
            log.debug("t1 start");
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            log.debug("t1 end");
        });
        t1.start();
        try {
            t1.join(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        log.debug("main start");
    }

    // 这个测试演示了：在t1线程休眠的情况下，且main.join(t1)，如果此时执行t1.interrupt()，则t1线程会抛出异常，并结束运行。main结束等待；
    @Test
    public void test2(){
        Thread t1 = new Thread(() -> {
            log.debug("t1 start");
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            log.debug("t1 end");
        });
        t1.start();
        try {
            // 启动一个新的线程，用于取消t1的等待，然后观察main线程的运行结果
            new Thread(() -> {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                log.debug("t2 start");
                t1.interrupt();
                log.debug("t2 end");
            }).start();

            t1.join(3000);
        } catch (InterruptedException e) {
            log.error("t1等待被取消");
            throw new RuntimeException(e);
        }
        log.debug("main start");
    }

    // 这个测试演示了：在t1正常情况下，且main.join(t1)，如果此时执行t1.interrupt()不会有任何影响，因为t1没有做中断处理。
    @Test
    public void test3(){
        Thread t1 = new Thread(() -> {
            log.debug("t1 start");
            // 用死循环正常运行。
                while (true){
                }

        });
        t1.start();
        try {
            // 启动一个新的线程，用于取消t1的等待，然后观察main线程的运行结果
            new Thread(() -> {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                log.debug("t2 start");
                t1.interrupt();
                log.debug("t2 end");
            }).start();

            t1.join(3000);
        } catch (InterruptedException e) {
            log.error("t1等待被取消");
            throw new RuntimeException(e);
        }
        log.debug("main end");
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

    }
}
