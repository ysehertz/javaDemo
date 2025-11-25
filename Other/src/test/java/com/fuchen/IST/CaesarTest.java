package com.fuchen.IST;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

/**
 * ClassName: CaeserTest
 * Package: com.fuchen.IST
 * Description:
 *
 * @author gomkiri
 * @version 1.0
 */
@Slf4j
public class CaesarTest {
    @Test
    public void testEncrypt(){
        Caesar caesar = new Caesar();
        String plaintext = "HelloWorld";
        String encrypt = caesar.encrypt(plaintext);
        assert encrypt.equals("KhoorZruog");
    }
    @Test
    public void testDecrypt(){
        Caesar caesar = new Caesar();
        String plaintext = "KhoorZruog";
        String encrypt = caesar.decrypt(plaintext);
        assert encrypt.equals("HelloWorld");
    }
}
