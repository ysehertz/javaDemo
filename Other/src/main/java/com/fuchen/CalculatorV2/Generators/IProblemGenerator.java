package com.fuchen.CalculatorV2.Generators;

import com.fuchen.CalculatorV2.Problem;
import java.util.Random;

/**
 * 题目生成策略接口 (Strategy Pattern)
 */
public interface IProblemGenerator {

    /**
     * 生成一道符合约束的题目
     * @param random 共享的随机数生成器
     * @return 一个 Problem 实例
     */
    Problem generate(Random random);

    /**
     * @return 该策略的唯一标识符 (用于工厂注册和查找)
     */
    String getStrategyKey();

    /**
     * @return 该策略的描述 (用于向用户显示菜单)
     */
    String getDescription();

    /**
     * 约束检查帮助方法 (需求 3: 均 >0 且 <100)
     * @param result  最终结果
     * @param operands 所有参与运算的数 (包括中间结果)
     * @return true 如果所有数都满足 (0, 100) 区间
     */
    default boolean checkConstraints(int result, int... operands) {
        if (result <= 0 || result >= 100) {
            return false;
        }
        for (int num : operands) {
            if (num <= 0 || num >= 100) {
                return false;
            }
        }
        return true;
    }
}