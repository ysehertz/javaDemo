package com.fuchen.useThread;

import lombok.extern.slf4j.Slf4j;

/**
 * ClassName: TwoPhaseTermination
 * Package: com.fuchen.useThread
 * Description:
 *
 * @author fuchen
 * @version 1.0
 * @createTime 2025/9/2
 */
@Slf4j
public class TwoPhaseTermination {
    public static void main(String[] args) throws InterruptedException {
        TPT tpt = new TPT();
        tpt.start();
        Thread.sleep(2000);
        tpt.stop();
    }
}
@Slf4j
class TPT {
    private Thread monitor;
    public void start(){
        monitor = new Thread(() -> {
            while (true){
                Thread current = Thread.currentThread();
                if(current.isInterrupted()){
                    log.info("线程被终止");
                    break;
                }
                try {
                    Thread.sleep(500);
                    log.info("线程在运行");
                } catch (InterruptedException e) {
                    log.error("线程在睡眠中被停止", e);
                    // 线程在睡眠中被停止，不会保留中断标记，而是直接抛出异常，需要重新设置中断标记
                    current.interrupt();
                }
            }
        });
        monitor.start();
    }

    public void stop(){
        monitor.interrupt();
    }
}
