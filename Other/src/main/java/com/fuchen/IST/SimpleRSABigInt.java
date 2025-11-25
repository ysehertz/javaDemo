package com.fuchen.IST;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;

public class SimpleRSABigInt {
    private final BigInteger n;
    private final BigInteger e;
    private final BigInteger d;
    private final int bitLength;

    public SimpleRSABigInt(int bitLength) {
        this.bitLength = bitLength;
        SecureRandom rnd = new SecureRandom();
        // 1) 生成 p 和 q
        BigInteger p, q;
        do {
            p = BigInteger.probablePrime(bitLength / 2, rnd);
        } while (p.signum() == 0);
        do {
            q = BigInteger.probablePrime(bitLength / 2, rnd);
        } while (q.equals(p) || q.signum() == 0);

        // 2) n 和 φ(n)
        n = p.multiply(q);
        BigInteger phi = (p.subtract(BigInteger.ONE)).multiply(q.subtract(BigInteger.ONE));

        // 3) 选取 e，计算 d
        e = BigInteger.valueOf(65537);
        d = e.modInverse(phi);
    }

    public BigInteger encrypt(BigInteger m) {
        return m.modPow(e, n);
    }

    public BigInteger decrypt(BigInteger c) {
        return c.modPow(d, n);
    }
//
//    // 示例用途：把字符串转换成大整数并进行加解密
//    public static void main(String[] args) {
//        SimpleRSABigInt rsa = new SimpleRSABigInt(1024);
//        String msg = "Hi";
//        byte[] mbytes = msg.getBytes(StandardCharsets.UTF_8);
//        BigInteger m = new BigInteger(1, mbytes);
//        if (m.compareTo(rsa.n) >= 0) {
//            System.out.println("Message too long for this RSA modulus.");
//            return;
//        }
//        BigInteger c = rsa.encrypt(m);
//        BigInteger m2 = rsa.decrypt(c);
//        byte[] m2bytes = m2.toByteArray();
//// 处理前导 0 字节的问题（简化处理）
//        if (m2bytes.length > 0 && m2bytes[0] == 0) {
//            byte[] tmp = new byte[m2bytes.length - 1];
//            System.arraycopy(m2bytes, 1, tmp, 0, tmp.length);
//            m2bytes = tmp;
//        }
//        System.out.println(new String(m2bytes, StandardCharsets.UTF_8));
//    }
}