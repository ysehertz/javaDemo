package com.fuchen;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

/**
 * ClassName: Calculator
 * Package: com.fuchen
 * Description: 数学题目生成器，支持加减乘除运算，可输出到CSV文件
 *
 * @author fuchen
 * @createTime 2025/10/13
 * @version 2.0
 */
public class Calculator {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Random random = new Random();
        List<String> problems = new ArrayList<>();

        // 询问用户需要什么类型的题目
        System.out.println("请选择题目类型：");
        System.out.println("1 - 纯加法题");
        System.out.println("2 - 纯减法题");
        System.out.println("3 - 纯乘法题");
        System.out.println("4 - 纯除法题");
        System.out.println("5 - 加减混合题");
        System.out.println("6 - 乘除混合题");
        System.out.println("7 - 三数加减乘除混合题");
        System.out.print("请输入选项（1-7）：");
        int type = scanner.nextInt();

        // 询问用户需要生成多少道题
        System.out.print("请输入需要生成的题目数量：");
        int count = scanner.nextInt();

        // 询问每行显示多少列
        System.out.print("请输入每行显示的列数（默认5列）：");
        int columns = scanner.nextInt();
        if (columns <= 0) {
            columns = 5;
        }

        // 询问文件名
        System.out.print("请输入保存的文件名（不含扩展名）：");
        String fileName = scanner.next();

        // 生成题目
        for (int i = 0; i < count; i++) {
            String problem = "";
            switch (type) {
                case 1:
                    problem = generateAdditionProblem(random, i + 1);
                    break;
                case 2:
                    problem = generateSubtractionProblem(random, i + 1);
                    break;
                case 3:
                    problem = generateMultiplicationProblem(random, i + 1);
                    break;
                case 4:
                    problem = generateDivisionProblem(random, i + 1);
                    break;
                case 5:
                    problem = generateAddSubMixedProblem(random, i + 1);
                    break;
                case 6:
                    problem = generateMulDivMixedProblem(random, i + 1);
                    break;
                case 7:
                    problem = generateComplexMixedProblem(random, i + 1);
                    break;
                default:
                    System.out.println("无效的选择！");
                    scanner.close();
                    return;
            }
            problems.add(problem);
            System.out.println(problem);
        }

        // 保存到CSV文件
        try {
            saveToCSV(problems, fileName, columns);
            System.out.println("题目已保存到文件：" + fileName + ".csv");
        } catch (IOException e) {
            System.err.println("保存文件时出错：" + e.getMessage());
        }

        scanner.close();
    }

    /**
     * 生成加法题
     *
     * @param random 随机数生成器
     * @param index  题目序号
     * @return 题目字符串
     */
    private static String generateAdditionProblem(Random random, int index) {
        int num1 = random.nextInt(98) + 1;
        int num2 = random.nextInt(98) + 1;
        return index + "： " + num1 + " + " + num2 + " = ";
    }

    /**
     * 生成减法题
     *
     * @param random 随机数生成器
     * @param index  题目序号
     * @return 题目字符串
     */
    private static String generateSubtractionProblem(Random random, int index) {
        int num1 = random.nextInt(98) + 1;
        int num2 = random.nextInt(98) + 1;

        // 确保结果不为负数
        if (num1 < num2) {
            int temp = num1;
            num1 = num2;
            num2 = temp;
        }
        return index + "： " + num1 + " - " + num2 + " = ";
    }

    /**
     * 生成乘法题
     *
     * @param random 随机数生成器
     * @param index  题目序号
     * @return 题目字符串
     */
    private static String generateMultiplicationProblem(Random random, int index) {
        int num1 = random.nextInt(12) + 1; // 乘法使用较小的数字
        int num2 = random.nextInt(12) + 1;
        return index + "： " + num1 + " × " + num2 + " = ";
    }

    /**
     * 生成除法题
     *
     * @param random 随机数生成器
     * @param index  题目序号
     * @return 题目字符串
     */
    private static String generateDivisionProblem(Random random, int index) {
        int divisor = random.nextInt(12) + 1; // 除数1-12
        int quotient = random.nextInt(12) + 1; // 商1-12
        int dividend = divisor * quotient; // 被除数=除数×商，确保整除
        return index + "： " + dividend + " ÷ " + divisor + " = ";
    }

    /**
     * 生成加减混合题
     *
     * @param random 随机数生成器
     * @param index  题目序号
     * @return 题目字符串
     */
    private static String generateAddSubMixedProblem(Random random, int index) {
        int num1 = random.nextInt(98) + 1;
        int num2 = random.nextInt(98) + 1;
        
        if (random.nextBoolean()) {
            return index + "： " + num1 + " + " + num2 + " = ";
        } else {
            // 确保减法结果不为负数
            if (num1 < num2) {
                int temp = num1;
                num1 = num2;
                num2 = temp;
            }
            return index + "： " + num1 + " - " + num2 + " = ";
        }
    }

    /**
     * 生成乘除混合题
     *
     * @param random 随机数生成器
     * @param index  题目序号
     * @return 题目字符串
     */
    private static String generateMulDivMixedProblem(Random random, int index) {
        if (random.nextBoolean()) {
            // 乘法
            int num1 = random.nextInt(12) + 1;
            int num2 = random.nextInt(12) + 1;
            return index + "： " + num1 + " × " + num2 + " = ";
        } else {
            // 除法
            int divisor = random.nextInt(12) + 1;
            int quotient = random.nextInt(12) + 1;
            int dividend = divisor * quotient;
            return index + "： " + dividend + " ÷ " + divisor + " = ";
        }
    }

    /**
     * 生成三数加减乘除混合题
     *
     * @param random 随机数生成器
     * @param index  题目序号
     * @return 题目字符串
     */
    private static String generateComplexMixedProblem(Random random, int index) {
        int operation = random.nextInt(4); // 0-3对应四种运算类型
        
        switch (operation) {
            case 0: // 加减混合
                int num1 = random.nextInt(50) + 1;
                int num2 = random.nextInt(50) + 1;
                int num3 = random.nextInt(50) + 1;
                if (random.nextBoolean()) {
                    return index + "： " + num1 + " + " + num2 + " - " + num3 + " = ";
                } else {
                    // 确保第一个减法不为负
                    if (num1 < num2) {
                        int temp = num1;
                        num1 = num2;
                        num2 = temp;
                    }
                    return index + "： " + num1 + " - " + num2 + " + " + num3 + " = ";
                }
            case 1: // 乘加混合
                int a = random.nextInt(9) + 1;
                int b = random.nextInt(9) + 1;
                int c = random.nextInt(50) + 1;
                return index + "： " + a + " × " + b + " + " + c + " = ";
            case 2: // 乘减混合
                int d = random.nextInt(9) + 1;
                int e = random.nextInt(9) + 1;
                int f = random.nextInt(30) + 1;
                int product = d * e;
                if (product < f) {
                    return index + "： " + f + " - " + d + " × " + e + " = ";
                } else {
                    return index + "： " + d + " × " + e + " - " + f + " = ";
                }
            case 3: // 除加混合
                int divisor = random.nextInt(9) + 1;
                int quotient = random.nextInt(9) + 1;
                int dividend = divisor * quotient;
                int addend = random.nextInt(30) + 1;
                return index + "： " + dividend + " ÷ " + divisor + " + " + addend + " = ";
            default:
                return generateAddSubMixedProblem(random, index);
        }
    }

    /**
     * 保存题目到CSV文件
     *
     * @param problems 题目列表
     * @param fileName 文件名
     * @param columns  每行列数
     * @throws IOException 文件操作异常
     */
    private static void saveToCSV(List<String> problems, String fileName, int columns) throws IOException {
        FileWriter writer = new FileWriter(fileName + ".csv", false);
        
        for (int i = 0; i < problems.size(); i++) {
            writer.write(problems.get(i));
            
            // 如果不是一行的最后一列且不是最后一个题目，添加逗号
            if ((i + 1) % columns != 0 && i < problems.size() - 1) {
                writer.write(",");
            } else {
                // 换行
                writer.write("\n");
            }
        }
        
        writer.close();
    }
}
