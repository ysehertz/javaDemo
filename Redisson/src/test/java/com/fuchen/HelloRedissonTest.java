package com.fuchen;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.io.IOException;

/**
 * ClassName: com.fuchen.HelloRedissonTest
 * Package: PACKAGE_NAME
 * Description:
 *
 * @author fuchen
 * @version 1.0
 * @createTime 2025/9/21
 */
@Slf4j
public class HelloRedissonTest {
    @Test
    public void test(){
        try {
            log.debug("Hello Redisson");
            HelloRedisson client = new HelloRedisson("./src/main/resources/redisson-config.yaml");
            client.printClient();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void testBasicCrud() throws IOException {
        HelloRedisson client = new HelloRedisson("./src/main/resources/redisson-config.yaml");
        client.basicCrud();
    }

    @Test
    public void testGetSet(){
        HelloRedisson client = null;
        try {
            client = new HelloRedisson("./src/main/resources/redisson-config.yaml");
            client.getZset();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        client.basicCrud();
    }
}
