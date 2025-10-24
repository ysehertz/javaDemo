package com.fuchen;

import org.junit.jupiter.api.Test;

import java.util.Set;
import java.util.UUID;
import java.util.concurrent.*;

/**
 * ClassName: Claucutor
 * Package: com.fuchen
 * Description:
 *
 * @author fuchen
 * @version 1.0
 * @createTime 2025/10/8
 */
public class CalculatorTest {
    @Test
    public void testAdd(){
        Calculator cal = new Calculator();
        int result = cal.add(2, 3);
        assert result == 5;
    }

    @Test
    public void testMinus(){
        Calculator cal = new Calculator();
        int result = cal.minus(5, 3);
        assert result == 2;
    }

    @Test
    public void testDivide(){
        Calculator cal = new Calculator();
        int result = cal.divide(6, 3);
        assert result == 2;
    }

    @Test
    public void testSquare(){
        Calculator cal = new Calculator();
        int result = cal.square(4);
        assert result == 16;

        int result2 = cal.square(-7);
        assert result2 == 49;
    }

    /**
     * 临时写的测试类，用于判断同时使用两个线程分别生成10000个UUID，是否会有重复的UUID
     */
    @Test
    public void testUUId() throws InterruptedException, ExecutionException {
        Set<String> uuidSetOne = ConcurrentHashMap.newKeySet();
        Set<String> uuidSetTwo = ConcurrentHashMap.newKeySet();

        ExecutorService executor = new ThreadPoolExecutor(2 , 3 , 5 , TimeUnit.SECONDS , new LinkedBlockingDeque<>(1));
        Future<?> future1 = executor.submit(() -> {
            for (int i = 0; i < 10000; i++) {
                String uuid = UUID.randomUUID().toString();
                uuidSetOne.add(uuid);
            }
        });

        Future<?> future2 = executor.submit(() -> {
            for (int i = 0; i < 10000; i++) {
                String uuid = UUID.randomUUID().toString();
                uuidSetTwo.add(uuid);
            }
        });

        future1.get();
        future2.get();

        uuidSetOne.retainAll(uuidSetTwo);

        assert uuidSetOne.size() == 0;

        System.out.println("重复的UUID有: " + uuidSetOne);
    }
}
