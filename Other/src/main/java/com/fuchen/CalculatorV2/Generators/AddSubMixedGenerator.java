package com.fuchen.CalculatorV2.Generators;

import com.fuchen.CalculatorV2.Problem;

import java.util.Random;

public class AddSubMixedGenerator implements IProblemGenerator {
    @Override
    public Problem generate(Random random) {
        // 复用已有策略来生成
        if (random.nextBoolean()) {
            return new AdditionGenerator().generate(random); 
        } else {
            return new SubtractionGenerator().generate(random);
        }
    }

    @Override
    public String getStrategyKey() { return "5"; }

    @Override
    public String getDescription() { return "加减混合题"; }
}