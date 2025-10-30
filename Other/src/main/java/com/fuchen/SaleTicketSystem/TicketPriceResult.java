package com.fuchen.SaleTicketSystem;

/**
 * 票价计算结果类
 * 封装票价计算的结果信息
 */
public class TicketPriceResult {
    private double originalPrice;
    private double finalPrice;
    private DiscountStrategy appliedStrategy;
    private UserInfo userInfo;
    
    public TicketPriceResult(double originalPrice, double finalPrice, 
                           DiscountStrategy appliedStrategy, UserInfo userInfo) {
        this.originalPrice = originalPrice;
        this.finalPrice = finalPrice;
        this.appliedStrategy = appliedStrategy;
        this.userInfo = userInfo;
    }
    
    public double getOriginalPrice() {
        return originalPrice;
    }
    
    public double getFinalPrice() {
        return finalPrice;
    }
    
    public DiscountStrategy getAppliedStrategy() {
        return appliedStrategy;
    }
    
    public UserInfo getUserInfo() {
        return userInfo;
    }
    
    public double getDiscountAmount() {
        return originalPrice - finalPrice;
    }
    
    public double getDiscountRate() {
        if (originalPrice == 0) return 0;
        return (originalPrice - finalPrice) / originalPrice;
    }
    
    /**
     * 获取详细的计算结果信息
     * @return 格式化的结果字符串
     */
    public String getDetailedResult() {
        StringBuilder sb = new StringBuilder();
        sb.append("=== 票价计算结果 ===\n");
        sb.append("用户信息：").append(userInfo.getName())
          .append("（年龄：").append(userInfo.getAge()).append("岁）\n");
        sb.append("原始票价：￥").append(String.format("%.2f", originalPrice)).append("\n");
        
        if (appliedStrategy != null) {
            sb.append("应用策略：").append(appliedStrategy.getDescription()).append("\n");
            sb.append("优惠金额：￥").append(String.format("%.2f", getDiscountAmount())).append("\n");
            sb.append("优惠比例：").append(String.format("%.1f", getDiscountRate() * 100)).append("%\n");
        } else {
            sb.append("应用策略：无优惠\n");
        }
        
        sb.append("最终票价：￥").append(String.format("%.2f", finalPrice)).append("\n");
        
        // 如果是VIP用户，显示积分信息
        if (userInfo.isVip()) {
            sb.append("VIP积分：").append(userInfo.getVipPoints()).append("分\n");
        }
        
        sb.append("==================");
        return sb.toString();
    }
    
    @Override
    public String toString() {
        return getDetailedResult();
    }
}