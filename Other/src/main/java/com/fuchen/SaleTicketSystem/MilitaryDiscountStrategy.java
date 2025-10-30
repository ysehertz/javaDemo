package com.fuchen.SaleTicketSystem;

/**
 * 军人折扣策略
 * 军人免费
 */
public class MilitaryDiscountStrategy implements DiscountStrategy {
    
    @Override
    public double calculatePrice(double originalPrice, UserInfo userInfo) {
        if (userInfo.isMilitary()) {
            return 0.0; // 军人免费
        }
        return originalPrice;
    }
    
    @Override
    public String getDescription() {
        return "军人优惠：军人凭军官证免费观影";
    }
}