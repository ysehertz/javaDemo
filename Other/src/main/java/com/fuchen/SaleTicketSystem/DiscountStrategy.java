package com.fuchen.SaleTicketSystem;

/**
 * 折扣策略接口
 * 策略模式的核心接口，定义了计算折扣后价格的方法
 */
public interface DiscountStrategy {
    
    /**
     * 计算折扣后的价格
     * @param originalPrice 原始票价
     * @param userInfo 用户信息
     * @return 折扣后的价格
     */
    double calculatePrice(double originalPrice, UserInfo userInfo);
    
    /**
     * 获取策略描述
     * @return 策略描述信息
     */
    String getDescription();
}