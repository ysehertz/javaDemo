package com.fuchen.CalculatorV2.Generators;

import com.fuchen.CalculatorV2.Problem;

import java.util.Random;

/**
 * ClassName: DivisionGenerator
 * Package: com.fuchen.CalculatorV2.Generators
 * Description:
 *
 * @author fuchen
 * @version 1.0
 * @createTime 2025/10/21
 */
public class DivisionGenerator implements IProblemGenerator{

    @Override
    public Problem generate(Random random) {
        int divisor , quotient, dividend;
        do{
            divisor = random.nextInt(10) + 1;
            // 先随机生成结果，在反推被除数，保证结果为整数
            quotient = random.nextInt(10) + 1;
            dividend = divisor * quotient;
        }while (! checkConstraints(quotient , dividend , divisor));

        return new Problem(String.format("%d ÷ %d = ?", dividend , divisor) , quotient);
    }

    @Override
    public String getStrategyKey() {
        return "4";
    }

    @Override
    public String getDescription() {
        return "纯除法题";
    }
}
