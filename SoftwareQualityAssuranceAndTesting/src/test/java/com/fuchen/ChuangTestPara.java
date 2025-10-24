package com.fuchen;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.stream.Stream;

/**
 * ClassName: ChuangTestPara
 * Package: com.fuchen
 * Description: JUnit 5参数化测试类
 *
 * @author gomkiri
 * @version 1.0
 */

public class ChuangTestPara {
    private Chuang testObj; // 被测试类
    
    // 准备测试数据
    public static Stream<Arguments> testDataset() {
        return Stream.of(
            Arguments.of(6.0, 8.0, "优秀"),
            Arguments.of(4.0, 6.0, "良好"),
            Arguments.of(3.0, 5.0, "中等"),
            Arguments.of(2.0, 4.0, "及格"),
            Arguments.of(2.0, 3.0, "不及格")
        );
    }
    
    @BeforeEach
    public void setUp() throws Exception {
        // 在每个测试方法中单独创建对象
    }
    
    @AfterEach
    public void tearDown() throws Exception {
        testObj = null;
    }
    
    @ParameterizedTest
    @MethodSource("testDataset")
    public void test(double singlemax, double sum, String expected) {
        // 创建测试对象
        testObj = new Chuang(singlemax, sum);
        // 执行测试并验证结果
        assertEquals(expected, testObj.getGrade());
    }
}
