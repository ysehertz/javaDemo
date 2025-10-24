package com.fuchen.CalculatorV2;

import com.fuchen.CalculatorV2.Generators.IProblemGenerator;
import com.fuchen.CalculatorV2.Generators.ProblemFactory;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

/**
 * ClassName: CalculatorV2
 * Package: com.fuchen.CalculatorV2
 * Description:
 *
 * @author gomkiri
 * @version 1.0
 */
public class CalculatorV2 {
    public static void main(String[] args) {


        try (Scanner scanner = new Scanner(System.in)) {
            ProblemFactory factory = new ProblemFactory();
            Random random = new Random();
            List<Problem> problems = new ArrayList<>();


            factory.printMenu();
            String type = scanner.nextLine();

            // 从工厂获取生成器
            IProblemGenerator generator = factory.getGenerator(type)
                    .orElseThrow(() -> new IllegalArgumentException("无效的选择！"));

            // 4. 询问用户需要生成多少道题
            System.out.print("请输入需要生成的题目数量：");
            int count = Integer.parseInt(scanner.nextLine());

            // 5. 询问每行显示多少列
            System.out.print("请输入每行显示的列数（默认5列）：");
            String colInput = scanner.nextLine();
            int columns = colInput.isEmpty() ? 5 : Integer.parseInt(colInput);
            if (columns <= 0) {
                columns = 5;
            }

            // 6. 询问文件名
            System.out.print("请输入保存的文件名（不含扩展名）：");
            String fileName = scanner.nextLine();

            // 7. 生成题目 (逻辑和表现分离)
            System.out.println("正在生成题目...");
            for (int i = 0; i < count; i++) {
                problems.add(generator.generate(random));
            }

            // 8. 输出到控制台 (格式化)
            System.out.println("------ 题目预览 (每行 " + columns + " 列) ------");
            printToConsole(problems, columns);

            // 9. 保存到CSV文件 (格式化)
            try {
                saveToCSV(problems, fileName, columns);
                System.out.println("\n题目已保存到文件：" + fileName + ".csv");
            } catch (IOException e) {
                System.err.println("保存文件时出错：" + e.getMessage());
            }

        } catch (Exception e) {
            System.err.println("发生错误: " + e.getMessage());
        }
    }

    /**
     * 格式化输出到控制台
     *
     * @param problems 题目列表
     * @param columns  每行列数
     */
    private static void printToConsole(List<Problem> problems, int columns) {
        for (int i = 0; i < problems.size(); i++) {
            Problem p = problems.get(i);
            // 在这里添加序号和格式
            String formattedProblem = String.format("%d： %s = ", (i + 1), p.question());

            // 使用 printf 进行左对齐，%-20s 表示左对齐，宽度为20
            System.out.printf("%-20s", formattedProblem);

            if ((i + 1) % columns == 0 || i == problems.size() - 1) {
                System.out.println(); // 换行
            }
        }
    }


    /**
     * 保存题目到CSV文件
     *
     * @param problems 题目列表 (类型已改为 Problem)
     * @param fileName 文件名
     * @param columns  每行列数
     * @throws IOException 文件操作异常
     */
    private static void saveToCSV(List<Problem> problems, String fileName, int columns) throws IOException {
        // 使用 try-with-resources 自动关闭 writer
        try (PrintWriter writer = new PrintWriter(new FileWriter(fileName + ".csv", false))) {

            for (int i = 0; i < problems.size(); i++) {
                Problem p = problems.get(i);
                // 在这里添加序号和格式
                String formattedProblem = String.format("%d： %s = ", (i + 1), p.question());

                // 为 CSV 单元格内容添加双引号，防止题目中的逗号（如果有）破坏格式
                writer.print("\"" + formattedProblem + "\"");

                if ((i + 1) % columns == 0) {
                    writer.print("\n"); // 换行
                } else if (i < problems.size() - 1) {
                    writer.print(","); // 添加逗号
                }
            }
        }
    }
}