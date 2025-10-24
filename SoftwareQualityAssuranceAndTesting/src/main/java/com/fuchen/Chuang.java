package com.fuchen;

public class Chuang {
    double single_Max;  // 单项最高分
    double sum;         // 总分

    public double getSingle_Max() { return single_Max; } // Getter
    public void setSingle_Max(double singlemax) { this.single_Max = singlemax; } // Setter

    public double getSum() { return sum; }
    public void setSum(double sum) { this.sum = sum; }

    public Chuang(double sm, double s) { // 参数初始化
        single_Max = sm;
        sum = s;
    }

    // 根据单项最高分和总分判断成绩
    public String getGrade() {
        String result = "";
        if (single_Max >= 5 || sum >= 7)
            result = "优秀";
        else if (single_Max >= 4 || sum >= 6)
            result = "良好";
        else if ((single_Max >= 3 && sum >= 4) || sum >= 5)
            result = "中等";
        else if (sum >= 4)
            result = "及格";
        else
            result = "不及格";
        return result;
    }
}
