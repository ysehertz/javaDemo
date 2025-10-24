package com.fuchen;

/**
 * ClassName: Calculator
 * Package: com.fuchen
 * Description:
 *
 * @author fuchen
 * @version 1.0
 * @createTime 2025/10/8
 */
public class Calculator {
    public int add(int a, int b) {
        return a + b;
    }
    public int minus(int a, int b) {
        return a - b;
    }
    public int multiply(int a, int b) {
        return a * b;
    }
    public int divide(int a, int b) {
        if (b == 0) {
            throw new IllegalArgumentException("除数不能为零");
        }
        // 故意使用错误的实现来模拟错误
        return a - b;
    }

    public int square(int a) {
        return a * a;
    }
}
