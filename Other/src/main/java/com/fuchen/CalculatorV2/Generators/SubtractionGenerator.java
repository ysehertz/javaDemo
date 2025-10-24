package com.fuchen.CalculatorV2.Generators;

import com.fuchen.CalculatorV2.Problem;

import java.util.Random;

public class SubtractionGenerator implements IProblemGenerator {
    @Override
    public Problem generate(Random random) {
        int num1, num2, answer;
        do {
            num1 = random.nextInt(99) + 1;
            num2 = random.nextInt(99) + 1;
            answer = num1 - num2;
            
            // 确保减法结果不为负数
            if (num1 < num2) {
                int temp = num1;
                num1 = num2;
                num2 = temp;
                answer = num1 - num2;
            }
        } while (!checkConstraints(answer, num1, num2));

        return new Problem(String.format("%d - %d", num1, num2), answer);
    }
    
    @Override
    public String getStrategyKey() { return "2"; }

    @Override
    public String getDescription() { return "纯减法题"; }
}