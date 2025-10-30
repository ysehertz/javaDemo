package com.fuchen.SaleTicketSystem;

/**
 * 儿童折扣策略
 * 年龄在10周岁及以下的儿童可享受每张票减免10元的优惠（原始票价需大于等于30元）
 */
public class ChildDiscountStrategy implements DiscountStrategy {
    
    private static final int CHILD_AGE_LIMIT = 10;
    private static final double DISCOUNT_AMOUNT = 10.0;
    private static final double MIN_PRICE_FOR_DISCOUNT = 30.0;
    
    @Override
    public double calculatePrice(double originalPrice, UserInfo userInfo) {
        if (userInfo.getAge() <= CHILD_AGE_LIMIT && originalPrice >= MIN_PRICE_FOR_DISCOUNT) {
            return originalPrice - DISCOUNT_AMOUNT;
        }
        return originalPrice;
    }
    
    @Override
    public String getDescription() {
        return "儿童折扣：10周岁及以下儿童，票价满30元可减免10元";
    }
}