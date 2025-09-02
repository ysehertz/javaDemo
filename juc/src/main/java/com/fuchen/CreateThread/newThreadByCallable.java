package com.fuchen.CreateThread;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;

/**
 * ClassName: newThreadByCallable
 * Package: com.fuchen.CreateThread
 * Description:
 *
 * @author fuchen
 * @version 1.0
 * @createTime 2025/9/2
 */
@Slf4j
public class newThreadByCallable {
    public static void main(String[] args) {
        Callable<Integer> c1 = new Callable1();
        Callable<Integer> c2 = new Callable2();
        try {
            Integer call = c1.call();
            log.debug("正常返回：{}",call);
            Integer call1 = c2.call();
            log.debug("异常返回：{}",call1);
        }catch (RuntimeException e){
            log.error("Callable线程发生异常"+ e.getMessage());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
@Slf4j
class Callable1 implements Callable<Integer> {
    @Override
    public Integer call() throws Exception {
        log.debug("正常返回");
        return 100;
    }
}

@Slf4j
class Callable2 implements Callable<Integer> {
    @Override
    public Integer call() throws Exception {
        log.debug("异常返回");
        throw new RuntimeException("异常");
    }
}