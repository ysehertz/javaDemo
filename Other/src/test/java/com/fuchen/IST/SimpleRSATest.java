package com.fuchen.IST;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;

/**
 * ClassName: SimpleRSATest
 * Package: com.fuchen.IST
 * Description:
 *
 * @author gomkiri
 * @version 1.0
 */
@Slf4j
public class SimpleRSATest {
    @Test
    public void test1(){
        SimpleRSABigInt rsa = new SimpleRSABigInt(1024);
        String msg = "Hello, RSA!";
        byte[] mbytes = msg.getBytes(StandardCharsets.UTF_8);

        BigInteger m = new BigInteger(1, mbytes);
        log.info("对消息进行加密：{}",msg);
        BigInteger c = rsa.encrypt(m);
        log.info("对密文进行解密");
        BigInteger m2 = rsa.decrypt(c);
        byte[] m2bytes = m2.toByteArray();
        // 处理前导 0 字节的问题（简化处理）
        if (m2bytes.length > 0 && m2bytes[0] == 0) {
            byte[] tmp = new byte[m2bytes.length - 1];
            System.arraycopy(m2bytes, 1, tmp, 0, tmp.length);
            m2bytes = tmp;
        }
        String s = new String(m2bytes, StandardCharsets.UTF_8);
        log.info("解密得到的消息: {}", s);
    }
}
