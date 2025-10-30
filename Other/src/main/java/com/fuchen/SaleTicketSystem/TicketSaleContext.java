package com.fuchen.SaleTicketSystem;

import java.util.ArrayList;
import java.util.List;

/**
 * 售票系统上下文类
 * 负责管理折扣策略，根据用户信息选择合适的策略并计算最终票价
 */
public class TicketSaleContext {
    
    private List<DiscountStrategy> strategies;
    
    public TicketSaleContext() {
        this.strategies = new ArrayList<>();
        // 初始化所有折扣策略
        initializeStrategies();
    }
    
    /**
     * 初始化所有折扣策略
     */
    private void initializeStrategies() {
        strategies.add(new MilitaryDiscountStrategy());    // 军人免费（优先级最高）
        strategies.add(new VipDiscountStrategy());         // VIP半价
        strategies.add(new SeniorDiscountStrategy());      // 老年人半价
        strategies.add(new StudentDiscountStrategy());     // 学生8折
        strategies.add(new ChildDiscountStrategy());       // 儿童减10元
    }
    
    /**
     * 计算最终票价
     * 会遍历所有策略，选择最优惠的价格
     * @param originalPrice 原始票价
     * @param userInfo 用户信息
     * @return 计算结果
     */
    public TicketPriceResult calculateFinalPrice(double originalPrice, UserInfo userInfo) {
        double bestPrice = originalPrice;
        DiscountStrategy bestStrategy = null;
        
        // 遍历所有策略，找到最优惠的价格
        for (DiscountStrategy strategy : strategies) {
            double currentPrice = strategy.calculatePrice(originalPrice, userInfo);
            if (currentPrice < bestPrice) {
                bestPrice = currentPrice;
                bestStrategy = strategy;
            }
        }
        
        return new TicketPriceResult(originalPrice, bestPrice, bestStrategy, userInfo);
    }
    
    /**
     * 添加新的折扣策略
     * @param strategy 折扣策略
     */
    public void addStrategy(DiscountStrategy strategy) {
        strategies.add(strategy);
    }
    
    /**
     * 移除折扣策略
     * @param strategy 折扣策略
     */
    public void removeStrategy(DiscountStrategy strategy) {
        strategies.remove(strategy);
    }
    
    /**
     * 获取所有可用的折扣策略描述
     * @return 策略描述列表
     */
    public List<String> getAllStrategyDescriptions() {
        List<String> descriptions = new ArrayList<>();
        for (DiscountStrategy strategy : strategies) {
            descriptions.add(strategy.getDescription());
        }
        return descriptions;
    }
}