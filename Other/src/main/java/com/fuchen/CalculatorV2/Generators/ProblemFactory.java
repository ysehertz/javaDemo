package com.fuchen.CalculatorV2.Generators;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.SimpleTimeZone;

public class ProblemFactory {

    // 使用 Map 存储所有策略
    private final Map<String, IProblemGenerator> generators = new HashMap<>();

    public ProblemFactory() {
        // 自动注册所有可用的策略
        registerStrategy(new AdditionGenerator());
        registerStrategy(new SubtractionGenerator());
        registerStrategy(new MultiplicationGenerator());
        registerStrategy(new DivisionGenerator());
        registerStrategy(new AddSubMixedGenerator());
        registerStrategy(new MulDivMixedGenerator());
        registerStrategy(new ComplexMixedGenerator());
        
        // ***** 扩展点 *****
        // 未来添加 "四数混合" 时:
        // 1. 创建 FourNumMixedGenerator.java
        // 2. 在这里添加: registerStrategy(new FourNumMixedGenerator());

    }

    private void registerStrategy(IProblemGenerator generator) {
        generators.put(generator.getStrategyKey(), generator);
    }

    /**
     * 根据用户的选择（Key）获取对应的策略实例
     */
    public Optional<IProblemGenerator> getGenerator(String strategyKey) {
        // 使用 Optional 避免返回 null
        return Optional.ofNullable(generators.get(strategyKey));
    }

    /**
     * 向用户显示所有可用的选项
     */
    public void printMenu() {
        System.out.println("请选择题目类型：");
        generators.values().stream()
                .sorted((g1, g2) -> g1.getStrategyKey().compareTo(g2.getStrategyKey()))
                .forEach(gen ->
                        System.out.printf("%s - %s%n", gen.getStrategyKey(), gen.getDescription())
                );
        System.out.printf("请输入选项（1 - %d）：", generators.size());
    }
}