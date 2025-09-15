package com.fuchen;

import java.util.Date;

/**
 * ClassName: MyRunnable
 * Package: com.fuchen
 * Description:
 *
 * @author fuchen
 * @version 1.0
 * @createTime 2025/9/14
 */
public class MyRunnable implements Runnable{
    private String command;

    public MyRunnable(String command) {
        this.command = command;
    }

    @Override
    public void run() {
        System.out.println(Thread.currentThread().getName() + " Start. Time = " + new Date());
        processCommand();
        System.out.println(Thread.currentThread().getName() + " End. Time = " + new Date());
    }

    private void processCommand() {
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String toString() {
        return "MyRunnable{" +
                "command='" + command + '\'' +
                '}';
    }
}
