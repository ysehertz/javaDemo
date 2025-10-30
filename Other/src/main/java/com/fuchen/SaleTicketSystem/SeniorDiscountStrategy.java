package com.fuchen.SaleTicketSystem;

/**
 * 老年人折扣策略
 * 60岁以上老人半价
 */
public class SeniorDiscountStrategy implements DiscountStrategy {
    
    private static final int SENIOR_AGE_LIMIT = 60;
    private static final double SENIOR_DISCOUNT_RATE = 0.5;
    
    @Override
    public double calculatePrice(double originalPrice, UserInfo userInfo) {
        if (userInfo.getAge() > SENIOR_AGE_LIMIT) {
            return originalPrice * SENIOR_DISCOUNT_RATE;
        }
        return originalPrice;
    }
    
    @Override
    public String getDescription() {
        return "老年人折扣：60岁以上老人享受半价优惠";
    }
}