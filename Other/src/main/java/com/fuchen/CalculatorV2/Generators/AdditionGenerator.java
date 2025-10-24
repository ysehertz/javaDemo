package com.fuchen.CalculatorV2.Generators;

import com.fuchen.CalculatorV2.Problem;

import java.util.Random;

public class AdditionGenerator implements IProblemGenerator {
    @Override
    public Problem generate(Random random) {
        int num1, num2, answer;
        // 循环直到找到满足约束的题目
        do {
            num1 = random.nextInt(99) + 1; // 1-99
            // 优化：num2 的范围可以缩小，避免过多无效循环
            num2 = random.nextInt(99 - num1) + 1; // 1 to (99-num1)
            answer = num1 + num2;
        } while (!checkConstraints(answer, num1, num2)); // 确保 num1, num2, answer 都在 (0, 100)

        return new Problem(String.format("%d + %d", num1, num2), answer);
    }

    @Override
    public String getStrategyKey() { return "1"; }

    @Override
    public String getDescription() { return "纯加法题"; }
}