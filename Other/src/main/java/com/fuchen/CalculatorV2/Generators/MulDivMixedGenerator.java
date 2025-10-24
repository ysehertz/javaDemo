package com.fuchen.CalculatorV2.Generators;

import com.fuchen.CalculatorV2.Problem;

import java.util.Random;

public class MulDivMixedGenerator implements IProblemGenerator {
    @Override
    public Problem generate(Random random) {
        // 复用已有策略
        if (random.nextBoolean()) {
            return new MultiplicationGenerator().generate(random);
        } else {
            return new DivisionGenerator().generate(random);
        }
    }

    @Override
    public String getStrategyKey() { return "6"; }

    @Override
    public String getDescription() { return "乘除混合题"; }
}