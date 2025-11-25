package com.fuchen;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.io.IOException;

/**
 * ClassName: AtomicRedissonTest
 * Package: com.fuchen
 * Description:
 *
 * @author fuchen
 * @version 1.0
 * @createTime 2025/9/21
 */
@Slf4j
public class AtomicRedissonTest {
    @Test
    public void testAtomicLong(){
        try {
            AtomicRedisson client = new AtomicRedisson("./src/main/resources/redisson-config.yaml");
            client.atomicLongI("long1",10000L);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
