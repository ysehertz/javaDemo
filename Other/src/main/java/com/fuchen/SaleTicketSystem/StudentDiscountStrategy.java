package com.fuchen.SaleTicketSystem;

/**
 * 学生折扣策略
 * 学生凭学生证可享受票价8折优惠
 */
public class StudentDiscountStrategy implements DiscountStrategy {
    
    private static final double STUDENT_DISCOUNT_RATE = 0.8;
    
    @Override
    public double calculatePrice(double originalPrice, UserInfo userInfo) {
        if (userInfo.isStudent()) {
            return originalPrice * STUDENT_DISCOUNT_RATE;
        }
        return originalPrice;
    }
    
    @Override
    public String getDescription() {
        return "学生折扣：凭学生证享受8折优惠";
    }
}