package com.fuchen.CalculatorV2.Generators;

import com.fuchen.CalculatorV2.Problem;

import java.util.Random;

public class ComplexMixedGenerator implements IProblemGenerator {
    @Override
    public Problem generate(Random random) {
        // 必须使用循环，因为你的原始逻辑不保证结果 < 100
        while (true) {
            int operation = random.nextInt(4); // 0-3
            Problem p = switch (operation) {
                case 0 -> generateAddSub(random);
                case 1 -> generateMulAdd(random);
                case 2 -> generateMulSub(random);
                case 3 -> generateDivAdd(random);
                default -> generateAddSub(random); // 兜底
            };
            
            // 检查生成的题目是否满足最终约束
            // (各个子方法内部已经检查了中间步骤)
            if (p != null && checkConstraints(p.answer())) {
                return p;
            }
            // 如果 p == null (子方法生成失败) 或不满足约束，循环会继续
        }
    }

    // a + b - c 或 a - b + c
    private Problem generateAddSub(Random random) {
        int a = random.nextInt(50) + 1;
        int b = random.nextInt(50) + 1;
        int c = random.nextInt(50) + 1;
        int temp, answer;
        String q;
        
        if (random.nextBoolean()) { // a + b - c
            temp = a + b;
            answer = temp - c;
            q = String.format("%d + %d - %d", a, b, c);
        } else { // a - b + c
            temp = a - b;
            answer = temp + c;
            q = String.format("%d - %d + %d", a, b, c);
        }
        
        // 检查所有中间和最终步骤
        if (checkConstraints(answer, a, b, c, temp)) {
            return new Problem(q, answer);
        }
        return null; // 生成失败
    }

    // a * b + c
    private Problem generateMulAdd(Random random) {
        int a = random.nextInt(10) + 1; // 乘法数小一点
        int b = random.nextInt(10) + 1;
        int c = random.nextInt(50) + 1;
        int temp = a * b;
        int answer = temp + c;
        if (checkConstraints(answer, a, b, c, temp)) {
            return new Problem(String.format("%d × %d + %d", a, b, c), answer);
        }
        System.out.println("模拟第一次提交");
        System.out.println("模拟第二次提交");
        System.out.println("模拟第三次提交");
        System.out.println("模拟第四次提交");
        System.out.println("模拟第五次提交");
        return null;
    }

    // a * b - c 或 c - a * b
    private Problem generateMulSub(Random random) {
        int a = random.nextInt(10) + 1;
        int b = random.nextInt(10) + 1;
        int c = random.nextInt(50) + 1;
        int temp = a * b;
        int answer;
        String q;

        if (temp > c) { // a * b - c
            answer = temp - c;
            q = String.format("%d × %d - %d", a, b, c);
        } else { // c - a * b
            answer = c - temp;
            q = String.format("%d - %d × %d", c, a, b);
        }
        
        if (checkConstraints(answer, a, b, c, temp)) {
            return new Problem(q, answer);
        }
        return null;
    }

    // a / b + c
    private Problem generateDivAdd(Random random) {
        int b = random.nextInt(10) + 1; // 除数
        int temp = random.nextInt(10) + 1; // 商
        int a = b * temp; // 被除数
        int c = random.nextInt(50) + 1;
        int answer = temp + c;

        if (checkConstraints(answer, a, b, c, temp)) {
            return new Problem(String.format("%d ÷ %d + %d", a, b, c), answer);
        }
        return null;
    }

    @Override
    public String getStrategyKey() { return "7"; }

    @Override
    public String getDescription() { return "三数加减乘除混合题"; }
}