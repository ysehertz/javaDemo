package com.fuchen;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Random;

/**
 * ClassName: CalculatorTest
 * Package: com.fuchen
 * Description: Calculator类的单元测试
 *
 * @author fuchen
 * @version 1.0
 * @createTime 2025/10/20
 */
public class CalculatorTest {
    
    private Random random;
    private final String testFileName = "test_output";
    private final String csvFile = testFileName + ".csv";
    
    @BeforeEach
    void setUp() {
        random = new Random(12345); // 使用固定种子保证测试结果可重现
        // 清理可能存在的测试文件
        deleteTestFile();
    }
    
    @AfterEach
    void tearDown() {
        // 清理测试文件
        deleteTestFile();
    }
    
    private void deleteTestFile() {
        try {
            Files.deleteIfExists(Paths.get(csvFile));
        } catch (IOException e) {
            // 忽略删除失败
        }
    }
    
    @Test
    @DisplayName("测试加法题生成格式")
    void testGenerateAdditionProblem() throws Exception {
        // 使用反射调用私有方法
        java.lang.reflect.Method method = Calculator.class.getDeclaredMethod(
            "generateAdditionProblem", Random.class, int.class);
        method.setAccessible(true);
        
        String result = (String) method.invoke(null, random, 1);
        
        // 验证格式：序号： 数字 + 数字 = 
        assertTrue(result.matches("1： \\d+ \\+ \\d+ = "), 
            "加法题格式不正确: " + result);
        
        // 验证数字范围（1-99）
        String[] parts = result.split("[： \\+ = ]+");
        int num1 = Integer.parseInt(parts[1]);
        int num2 = Integer.parseInt(parts[2]);
        assertTrue(num1 >= 1 && num1 <= 99, "第一个数字超出范围: " + num1);
        assertTrue(num2 >= 1 && num2 <= 99, "第二个数字超出范围: " + num2);
    }
    
    @Test
    @DisplayName("测试减法题生成格式和非负结果")
    void testGenerateSubtractionProblem() throws Exception {
        java.lang.reflect.Method method = Calculator.class.getDeclaredMethod(
            "generateSubtractionProblem", Random.class, int.class);
        method.setAccessible(true);
        
        // 多次测试确保减法结果不为负
        for (int i = 0; i < 10; i++) {
            String result = (String) method.invoke(null, random, i + 1);
            
            // 验证格式
            assertTrue(result.matches("\\d+： \\d+ - \\d+ = "), 
                "减法题格式不正确: " + result);
            
            // 验证结果非负
            String[] parts = result.split("[： \\- = ]+");
            int num1 = Integer.parseInt(parts[1]);
            int num2 = Integer.parseInt(parts[2]);
            assertTrue(num1 >= num2, 
                "减法结果为负: " + num1 + " - " + num2 + " = " + (num1 - num2));
        }
    }
    
    @Test
    @DisplayName("测试乘法题生成格式")
    void testGenerateMultiplicationProblem() throws Exception {
        java.lang.reflect.Method method = Calculator.class.getDeclaredMethod(
            "generateMultiplicationProblem", Random.class, int.class);
        method.setAccessible(true);
        
        String result = (String) method.invoke(null, random, 1);
        
        // 验证格式
        assertTrue(result.matches("1： \\d+ × \\d+ = "), 
            "乘法题格式不正确: " + result);
        
        // 验证数字范围（1-12）
        String[] parts = result.split("[： ×= ]+");
        int num1 = Integer.parseInt(parts[1]);
        int num2 = Integer.parseInt(parts[2]);
        assertTrue(num1 >= 1 && num1 <= 12, "第一个数字超出范围: " + num1);
        assertTrue(num2 >= 1 && num2 <= 12, "第二个数字超出范围: " + num2);
    }
    
    @Test
    @DisplayName("测试除法题生成格式和整除性")
    void testGenerateDivisionProblem() throws Exception {
        java.lang.reflect.Method method = Calculator.class.getDeclaredMethod(
            "generateDivisionProblem", Random.class, int.class);
        method.setAccessible(true);
        
        // 多次测试确保除法能整除
        for (int i = 0; i < 10; i++) {
            String result = (String) method.invoke(null, random, i + 1);
            
            // 验证格式
            assertTrue(result.matches("\\d+： \\d+ ÷ \\d+ = "), 
                "除法题格式不正确: " + result);
            
            // 验证能整除
            String[] parts = result.split("[： ÷= ]+");
            int dividend = Integer.parseInt(parts[1]);
            int divisor = Integer.parseInt(parts[2]);
            assertEquals(0, dividend % divisor, 
                "除法不能整除: " + dividend + " ÷ " + divisor);
            assertTrue(divisor >= 1 && divisor <= 12, "除数超出范围: " + divisor);
        }
    }
    
    @Test
    @DisplayName("测试加减混合题生成")
    void testGenerateAddSubMixedProblem() throws Exception {
        java.lang.reflect.Method method = Calculator.class.getDeclaredMethod(
            "generateAddSubMixedProblem", Random.class, int.class);
        method.setAccessible(true);
        
        boolean hasAddition = false;
        boolean hasSubtraction = false;
        
        // 生成多个题目，确保包含加法和减法
        for (int i = 0; i < 20; i++) {
            String result = (String) method.invoke(null, new Random(), i + 1);
            
            if (result.contains(" + ")) {
                hasAddition = true;
                assertTrue(result.matches("\\d+： \\d+ \\+ \\d+ = "), 
                    "加减混合题中的加法格式不正确: " + result);
            } else if (result.contains(" - ")) {
                hasSubtraction = true;
                assertTrue(result.matches("\\d+： \\d+ - \\d+ = "), 
                    "加减混合题中的减法格式不正确: " + result);
                
                // 验证减法结果非负
                String[] parts = result.split("[： \\- = ]+");
                int num1 = Integer.parseInt(parts[1]);
                int num2 = Integer.parseInt(parts[2]);
                assertTrue(num1 >= num2, "加减混合题中减法结果为负");
            }
        }
        
        assertTrue(hasAddition, "加减混合题中没有生成加法题");
        assertTrue(hasSubtraction, "加减混合题中没有生成减法题");
    }
    
    @Test
    @DisplayName("测试乘除混合题生成")
    void testGenerateMulDivMixedProblem() throws Exception {
        java.lang.reflect.Method method = Calculator.class.getDeclaredMethod(
            "generateMulDivMixedProblem", Random.class, int.class);
        method.setAccessible(true);
        
        boolean hasMultiplication = false;
        boolean hasDivision = false;
        
        // 生成多个题目，确保包含乘法和除法
        for (int i = 0; i < 20; i++) {
            String result = (String) method.invoke(null, new Random(), i + 1);
            
            if (result.contains(" × ")) {
                hasMultiplication = true;
                assertTrue(result.matches("\\d+： \\d+ × \\d+ = "), 
                    "乘除混合题中的乘法格式不正确: " + result);
            } else if (result.contains(" ÷ ")) {
                hasDivision = true;
                assertTrue(result.matches("\\d+： \\d+ ÷ \\d+ = "), 
                    "乘除混合题中的除法格式不正确: " + result);
                
                // 验证能整除
                String[] parts = result.split("[： ÷= ]+");
                int dividend = Integer.parseInt(parts[1]);
                int divisor = Integer.parseInt(parts[2]);
                assertEquals(0, dividend % divisor, "乘除混合题中除法不能整除");
            }
        }
        
        assertTrue(hasMultiplication, "乘除混合题中没有生成乘法题");
        assertTrue(hasDivision, "乘除混合题中没有生成除法题");
    }
    
    @Test
    @DisplayName("测试三数混合运算题生成")
    void testGenerateComplexMixedProblem() throws Exception {
        java.lang.reflect.Method method = Calculator.class.getDeclaredMethod(
            "generateComplexMixedProblem", Random.class, int.class);
        method.setAccessible(true);
        
        boolean[] operationTypes = new boolean[4]; // 跟踪四种运算类型
        
        // 生成多个题目，确保包含不同的运算组合
        for (int i = 0; i < 50; i++) {
            String result = (String) method.invoke(null, new Random(), i + 1);
            
            // 检查加减混合
            if (result.matches("\\d+： \\d+ [+\\-] \\d+ [+\\-] \\d+ = ")) {
                operationTypes[0] = true;
            }
            // 检查乘加混合
            else if (result.matches("\\d+： \\d+ × \\d+ \\+ \\d+ = ")) {
                operationTypes[1] = true;
            }
            // 检查乘减混合
            else if (result.matches("\\d+： \\d+ × \\d+ - \\d+ = ") || 
                     result.matches("\\d+： \\d+ - \\d+ × \\d+ = ")) {
                operationTypes[2] = true;
            }
            // 检查除加混合
            else if (result.matches("\\d+： \\d+ ÷ \\d+ \\+ \\d+ = ")) {
                operationTypes[3] = true;
            }
            
            // 验证基本格式
            assertTrue(result.startsWith(String.valueOf(i + 1) + "： "), 
                "三数混合题序号格式不正确: " + result);
            assertTrue(result.endsWith(" = "), 
                "三数混合题结尾格式不正确: " + result);
        }
        
        // 确保至少生成了几种不同的运算类型
        int typeCount = 0;
        for (boolean type : operationTypes) {
            if (type) typeCount++;
        }
        assertTrue(typeCount >= 2, "三数混合题运算类型不够丰富，只有 " + typeCount + " 种");
    }
    
    @Test
    @DisplayName("测试CSV文件保存功能")
    void testSaveToCSV() throws Exception {
        java.lang.reflect.Method method = Calculator.class.getDeclaredMethod(
            "saveToCSV", java.util.List.class, String.class, int.class);
        method.setAccessible(true);
        
        // 准备测试数据
        java.util.List<String> problems = java.util.Arrays.asList(
            "1： 1 + 2 = ",
            "2： 3 - 1 = ",
            "3： 2 × 3 = ",
            "4： 6 ÷ 2 = ",
            "5： 4 + 5 = ",
            "6： 8 - 3 = "
        );
        
        // 测试每行3列的格式
        method.invoke(null, problems, testFileName, 3);
        
        // 验证文件是否创建
        assertTrue(Files.exists(Paths.get(csvFile)), "CSV文件未创建");
        
        // 读取文件内容并验证格式
        String content = new String(Files.readAllBytes(Paths.get(csvFile)));
        String[] lines = content.split("\n");
        
        assertEquals(2, lines.length, "CSV文件行数不正确");
        
        // 验证第一行：3列用逗号分隔
        String firstLine = lines[0].trim();
        String[] firstRowCols = firstLine.split(",");
        assertEquals(3, firstRowCols.length, "第一行列数不正确");
        assertEquals("1： 1 + 2 = ", firstRowCols[0], "第一行第一列内容不正确");
        assertEquals("2： 3 - 1 = ", firstRowCols[1], "第一行第二列内容不正确");
        assertTrue(firstRowCols[2].startsWith("3： 2 × 3 ="), "第一行第三列内容不正确");
        
        // 验证第二行：3列用逗号分隔
        String secondLine = lines[1].trim();
        String[] secondRowCols = secondLine.split(",");
        assertEquals(3, secondRowCols.length, "第二行列数不正确");
        assertEquals("4： 6 ÷ 2 = ", secondRowCols[0], "第二行第一列内容不正确");
        assertEquals("5： 4 + 5 = ", secondRowCols[1], "第二行第二列内容不正确");
        assertTrue(secondRowCols[2].startsWith("6： 8 - 3 ="), "第二行第三列内容不正确");
    }
    
    @Test
    @DisplayName("测试CSV文件保存 - 不同列数")
    void testSaveToCSVDifferentColumns() throws Exception {
        java.lang.reflect.Method method = Calculator.class.getDeclaredMethod(
            "saveToCSV", java.util.List.class, String.class, int.class);
        method.setAccessible(true);
        
        // 准备5道题目
        java.util.List<String> problems = java.util.Arrays.asList(
            "1： 1 + 1 = ",
            "2： 2 + 2 = ",
            "3： 3 + 3 = ",
            "4： 4 + 4 = ",
            "5： 5 + 5 = "
        );
        
        // 测试每行2列的格式
        method.invoke(null, problems, testFileName, 2);
        
        String content = new String(Files.readAllBytes(Paths.get(csvFile)));
        String[] lines = content.split("\n");
        
        assertEquals(3, lines.length, "每行2列时应该有3行");
        
        // 第一行和第二行应该有2列
        assertEquals(2, lines[0].trim().split(",").length, "第一行应该有2列");
        assertEquals(2, lines[1].trim().split(",").length, "第二行应该有2列");
        // 第三行应该有1列（最后一个题目）
        assertEquals(1, lines[2].trim().split(",").length, "第三行应该有1列");
    }
    
    @Test
    @DisplayName("测试主程序输入输出流")
    void testMainProgramFlow() throws Exception {
        // 准备模拟输入
        String input = "1\n5\n3\ntest_main\n"; // 选择纯加法，5道题，每行3列，文件名test_main
        InputStream originalIn = System.in;
        PrintStream originalOut = System.out;
        
        try {
            // 设置模拟输入
            System.setIn(new ByteArrayInputStream(input.getBytes()));
            
            // 捕获输出
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            System.setOut(new PrintStream(outputStream));
            
            // 运行主程序
            Calculator.main(new String[]{});
            
            // 验证输出
            String output = outputStream.toString();
            assertTrue(output.contains("请选择题目类型"), "输出应包含题目类型选择提示");
            assertTrue(output.contains("题目已保存到文件：test_main.csv"), "输出应包含文件保存成功提示");
            
            // 验证文件是否创建
            assertTrue(Files.exists(Paths.get("test_main.csv")), "应该创建test_main.csv文件");
            
            // 读取文件验证题目数量
            String fileContent = new String(Files.readAllBytes(Paths.get("test_main.csv")));
            long problemCount = fileContent.chars()
                .filter(ch -> ch == '：')
                .count();
            assertEquals(5, problemCount, "文件中应该有5道题目");
            
            // 清理测试文件
            Files.deleteIfExists(Paths.get("test_main.csv"));
            
        } finally {
            // 恢复原始输入输出流
            System.setIn(originalIn);
            System.setOut(originalOut);
        }
    }
    
    @Test
    @DisplayName("测试无效选择处理")
    void testInvalidChoice() throws Exception {
        String input = "9\n5\n3\ntest_invalid\n"; // 无效选择，然后填充其他必需的输入
        InputStream originalIn = System.in;
        PrintStream originalOut = System.out;
        
        try {
            System.setIn(new ByteArrayInputStream(input.getBytes()));
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            System.setOut(new PrintStream(outputStream));
            
            Calculator.main(new String[]{});
            
            String output = outputStream.toString();
            assertTrue(output.contains("无效的选择"), "应该显示无效选择提示");
            
            // 清理可能创建的文件
            Files.deleteIfExists(Paths.get("test_invalid.csv"));
            
        } finally {
            System.setIn(originalIn);
            System.setOut(originalOut);
        }
    }
}
