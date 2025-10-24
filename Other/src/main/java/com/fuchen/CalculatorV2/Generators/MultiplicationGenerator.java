package com.fuchen.CalculatorV2.Generators;

import com.fuchen.CalculatorV2.Problem;

import java.util.Random;

public class MultiplicationGenerator implements IProblemGenerator {
    @Override
    public Problem generate(Random random) {
        int num1, num2, answer;
        do {
            // 为了让结果 < 100，操作数不能太大
            num1 = random.nextInt(10) + 1;
            num2 = random.nextInt(10) + 1;
            answer = num1 * num2;
        } while (!checkConstraints(answer, num1, num2));

        return new Problem(String.format("%d × %d", num1, num2), answer);
    }

    @Override
    public String getStrategyKey() { return "3"; }

    @Override
    public String getDescription() { return "纯乘法题"; }
}